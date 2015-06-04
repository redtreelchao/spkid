/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.RandomUtils;
import com.fclub.tpd.biz.ShippingPacketService;
import com.fclub.tpd.biz.ShippingService;
import com.fclub.tpd.biz.ShippingWaveService;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.dataobject.Shipping;
import com.fclub.tpd.dataobject.ShippingPacket;
import com.fclub.tpd.dataobject.ShippingWave;
import com.fclub.tpd.dataobject.erp.DepotInMain;
import com.fclub.tpd.dataobject.erp.DepotInSub;
import com.fclub.tpd.dataobject.erp.GoodsEntity;
import com.fclub.tpd.dataobject.erp.Transaction;
import com.fclub.tpd.dto.ShippingImportDTO;
import com.fclub.tpd.mapper.DepotInLeafMapper;
import com.fclub.tpd.mapper.DepotInMainMapper;
import com.fclub.tpd.mapper.DepotInSubMapper;
import com.fclub.tpd.mapper.ProviderMapper;
import com.fclub.tpd.mapper.ShippingWaveMapper;
import com.fclub.tpd.mapper.TransactionMapper;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingWaveServiceImpl.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
@Service
@Scope("prototype")
public class ShippingWaveServiceImpl implements ShippingWaveService {

    @Autowired
    private ShippingWaveMapper    shippingWaveMapper;
    @Autowired
    private ShippingPacketService shippingPacketService;
    @Autowired
    private ShippingService       shippingService;

    @Autowired
    private DepotInMainMapper     depotInMainMapper;
    @Autowired
    private DepotInSubMapper      depotInSubMapper;
    @Autowired
    private DepotInLeafMapper     depotInLeafMapper;
    @Autowired
    private TransactionMapper     transactionMapper;
    @Autowired
    private ProviderMapper		  providerMapper;
    
    /**
     * 生成波次时，每个波次最多批量取的订单号。
     */
    public static final int BC_BATCH_NUMBER = 40;
    
    @Override
    public ShippingWave get(Integer id) {
        return shippingWaveMapper.get(id);
    }

    @Override
    public Page<ShippingWave> findPage(Page<ShippingWave> page, ShippingWave shippingWave) {

        page.setResult(shippingWaveMapper.findPage(page, shippingWave));
        return page;
    }

    @Override
    @Transactional
    public void save(ShippingWave shippingWave) {
        shippingWaveMapper.insert(shippingWave);
    }

    @Override
    @Transactional
    public void update(ShippingWave shippingWave) {
        shippingWaveMapper.update(shippingWave);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        shippingWaveMapper.delete(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void generateWave(Integer providerId) {
    	synchronized(getLock(providerId)) {
	    	
	    	// 每个波次最多40个订单
	        List<ShippingPacket> list = shippingPacketService.getExportOrder(providerId);
	        if (list.size() == 0) {
	            throw new BizException("没有需要导出的订单");
	        }
	        List<Integer> orderIdList = new ArrayList<Integer>(BC_BATCH_NUMBER);
	        for (ShippingPacket sp : list) {
	        	if (orderIdList.size() >= BC_BATCH_NUMBER) {
	        		break;
	        	}
	        	Integer orderId = sp.getOrderId();
	        	if (!orderIdList.contains(orderId)) {
	        		orderIdList.add(orderId);
	        	}
	        }
	        
	        // 生成波次
	        Date date = DateUtil.getCurrentTime();
	        ShippingWave shippingWave = new ShippingWave();
	        shippingWave.setProviderId(providerId);
	        shippingWave.setWaveStatus(0);
	        shippingWave.setOrderNum(orderIdList.size());
	        shippingWave.setCreateTime(date);
	        shippingWave.setIsPrintBox(false);
	        String waveSn = generateWaveSn(providerId);
	        shippingWave.setWaveSn(waveSn);
	        
	        shippingWaveMapper.insert(shippingWave);
	        Integer waveId = shippingWave.getWaveId();
	    	
	    	// 批量插入波次订单详情，使用not exists预防重复插入
	    	int row = shippingPacketService.batchInsertNotExists(providerId, waveSn, orderIdList);
	    	if (row == 0) {
	    		// 删除生成的无用的波次号
	    		shippingWaveMapper.delete(waveId);
	    		throw new BizException("请勿重复生成波次！");
	    	} else {
	    		int orderNum = shippingWaveMapper.countOrderNum(waveId);
	    		// 更新此波次实际生成的单量
	    		if (orderNum != orderIdList.size()) {
	    			shippingWave = new ShippingWave();
	    			shippingWave.setWaveId(waveId);
	    			shippingWave.setOrderNum(orderNum);
	    			shippingWaveMapper.update(shippingWave);
	    		}
	    	}
    	
    	}
    }

    @Override
    @Transactional
    public List<ShippingImportDTO> batchShipping(Integer providerId, String type,
                                                 List<ShippingImportDTO> shippingList) {

        List<Shipping> allShipping = shippingService.findAll();
        Map<String, Integer> shippingMap = new HashMap<>();
        for (Shipping shipping : allShipping) {
            shippingMap.put(shipping.getShippingCode(), shipping.getShippingId());
        }
        List<Integer> processOrderIds = new ArrayList<>(shippingList.size());
        Set<String> waveSnSet = new HashSet<>(shippingList.size());
        for (ShippingImportDTO dto : shippingList) {
        	String orderSn = dto.getOrderSn().trim();
        	
            List<ShippingPacket> packetList = shippingPacketService.getProviderPacket(providerId, orderSn);
            boolean error = false;
            if (packetList == null || packetList.size() == 0) {
                dto.setMessage("订单不存在");
                continue;
            }
            for (ShippingPacket sp : packetList) {
                if (sp.getStatus() == 1) {
                    dto.setMessage("订单已发货");
                    error = true;
                    break;
                }
                if (sp.getVirtualShipping() == true) {
                    dto.setMessage("订单已取消");
                    error = true;
                    break;
                }
                if (StringUtils.equals(type, "2") && sp.getStatus() == 2) {
                    dto.setMessage("订单已导缺货");
                    error = true;
                    break;
                }
            }
            if (error) {
                continue;
            }
            if (StringUtils.equals(type, "1")) {
                //FIXME invoiceNo是同订单唯一还是全表唯一，暂时check同订单必须唯一
                boolean existsInvoiceNo = shippingPacketService.existsInvoiceNo(dto.getOrderSn()
                    .trim(), dto.getInvoiceNo().trim());
                if (existsInvoiceNo) {
                    dto.setMessage("运单号重复");
                    continue;
                }
                Integer shippingId = shippingMap.get(dto.getShippingCode());
                if (shippingId == null) {
                    dto.setMessage("快递公司编码不存在");
                    continue;
                }
                dto.setShippingId(shippingId);
            }
            processOrderIds.add(packetList.get(0).getOrderId());
            dto.setOrderId(packetList.get(0).getOrderId());
            dto.setProviderId(providerId);
            waveSnSet.add(packetList.get(0).getWaveSn());
        }
        if (processOrderIds.size() == 0) {
            //throw new BizException("没有需要处理的订单");
            return shippingList;
        }
        List<String> waveSnList = new ArrayList<>(waveSnSet.size());
        waveSnList.addAll(waveSnSet);
        if (StringUtils.equals(type, "1")) {
            doBatchShipping(providerId, shippingList, processOrderIds, waveSnList);
        } else if (StringUtils.equals(type, "2")) {
            doShortageShipping(providerId, processOrderIds, waveSnList);
        } else {
            throw new BizException("无效的类型");
        }

        return shippingList;
    }

    /**
     * 发货处理
     */
    private void doBatchShipping(Integer providerId, List<ShippingImportDTO> shippingList,
                                 List<Integer> orderIds, List<String> waveSnList) {
    	Date date = new Date();
    	
    	Integer[] inIds = getInIds(providerId);
    	Integer depotId = inIds[0];
    	Integer locationId = inIds[1];
    	Integer depotTypeId = inIds[2];

        saveDepotIn(providerId, orderIds,depotId, locationId, depotTypeId, date);

        shippingWaveMapper.insertOrderOut(providerId, orderIds, depotId, locationId, date);

        shippingWaveMapper.updateOrderGoodsConsign(providerId, orderIds);

        updatePacketShipping(shippingList, date);

        updateWaveShipping(waveSnList, date);

        shippingWaveMapper.updateOrderShipping(providerId, orderIds, date);

    }

    /**
     * 更新包裹发货信息
     */
    private void updatePacketShipping(List<ShippingImportDTO> shippingList, Date date) {
        for (ShippingImportDTO dto : shippingList) {
            if (dto.getOrderId() == null) {
                continue;
            }
            ShippingPacket shippingPacket = new ShippingPacket();
            shippingPacket.setOrderId(dto.getOrderId());
            shippingPacket.setProviderId(dto.getProviderId());
            shippingPacket.setShippingId(dto.getShippingId());
            shippingPacket.setPacketSn(dto.getInvoiceNo());
            shippingPacket.setShippingFee(dto.getShippingFee());
            shippingPacket.setStatus(1);
            shippingPacket.setVirtualShipping(false);
            shippingPacket.setFinishTime(date);
            shippingPacketService.updatePacketShipping(shippingPacket);
        }
    }

    /**
     * 更新波次发货/缺货数量及发货状态
     */
    private void updateWaveShipping(List<String> waveSnList, Date date) {

        List<ShippingPacket> list = shippingPacketService.getPacketCount(waveSnList);
        Map<String, ShippingWave> tmp = new HashMap<>();
        for (ShippingPacket sp : list) {
            String waveSn = sp.getWaveSn();
            Integer orderNum = sp.getOrderNum();
            ShippingWave sw = null;
            if (tmp.get(waveSn) == null) {
                sw = new ShippingWave();
                sw.setWaveSn(waveSn);
                sw.setOrderNum(orderNum);
                tmp.put(waveSn, sw);
            } else {
                sw = tmp.get(waveSn);
                sw.setOrderNum(sw.getOrderNum() + orderNum);
            }
            if (sp.getStatus() == 1) {
                if (sp.getVirtualShipping()) {
                    sw.setShortages(orderNum);
                } else {
                    sw.setShippingNum(orderNum);
                }
            } else if (sp.getStatus() == 2) {
                sw.setShortages(orderNum);
            }
        }

        for (Map.Entry<String, ShippingWave> entry : tmp.entrySet()) {
            ShippingWave sw = entry.getValue();
            if (sw.getShippingNum() == null) {
                sw.setShippingNum(0);
            }
            if (sw.getShortages() == null) {
                sw.setShortages(0);
            }
            if (sw.getOrderNum() == sw.getShippingNum()) {
                sw.setWaveStatus(2); //finish
                sw.setFinishTime(date);
            }
            if (sw.getShippingNum() > 0 && sw.getShippingNum() < sw.getOrderNum()) {
                sw.setWaveStatus(1); //half finish
            }
            shippingWaveMapper.updateByWaveSn(sw);
        }

        //        shippingWaveMapper.updateWaveShipping(waveSnList);
        //        shippingWaveMapper.updateWaveShortage(waveSnList);
        //        shippingWaveMapper.updateWaveStatusFinish(waveSnList);
        //        shippingWaveMapper.updateWaveStatusHalfFinish(waveSnList);
    }

    /**
     * 新增入库单据
     */
    private void saveDepotIn(Integer providerId, List<Integer> orderIds, Integer depotId, Integer locationId, Integer depotTypeId, Date date) {
    	List<GoodsEntity> goodsList = shippingWaveMapper.getShippingGoodsList(providerId, orderIds);
        Map<Integer, DepotInSub> depotInSubMap = new LinkedHashMap<>();
        String depotInCode = "NRK" + DateUtil.getDateFormat("yyyyMMddHHmmss").format(new Date());
        int depotInNumber = 0;
        BigDecimal depotInAmount = BigDecimal.ZERO;
        DepotInMain depotInMain = new DepotInMain();
        depotInMain.setDepotInCode(depotInCode);
        depotInMain.setDepotDepotId(depotId);
        depotInMain.setDepotInReason("按发货订单生成入库单");
        depotInMain.setDepotInType(depotTypeId);
        depotInMain.setDepotInAid(providerId);
        depotInMain.setDepotInCheckAid(providerId);
        depotInMain.setDepotInDate(date);
        depotInMain.setDepotInTime(date);
        depotInMain.setDepotInCheckTime(date);
        depotInMain.setRecordStatus(-1);
        depotInMain.setDepotInFinished(true);

        for (GoodsEntity goods : goodsList) {
            if (depotInSubMap.get(goods.getProductId()) == null) {
                DepotInSub sub = new DepotInSub();
                sub.setGoodsId(goods.getProductId());
                sub.setGoodsName(goods.getProductName());
                sub.setColorId(goods.getColorId());
                sub.setSizeId(goods.getSizeId());
                sub.setSalePrice(goods.getShopPrice());
                sub.setGoodsNumber(goods.getProductNum());
                sub.setGoodsFinishedNumber(goods.getProductNum());
                sub.setGoodsAmount(goods.getShopPrice().multiply(new BigDecimal(goods.getProductNum())));
                sub.setDepotInAid(providerId);
                sub.setDepotInTime(date);
                sub.setDepotId(depotId);
                sub.setLocationId(locationId);
                sub.setBatchId(goods.getBatchId());
                
                // ---- XXX
                sub.setSalePrice(goods.getShopPrice());
                sub.setConsignPrice(goods.getConsignPrice());
                sub.setCostPrice(goods.getCostPrice());
                sub.setProductCess(goods.getProductCess());
                sub.setConsignRate(goods.getConsignRate());
                
                depotInSubMap.put(goods.getProductId(), sub);
            } else {
            	
                DepotInSub sub = depotInSubMap.get(goods.getProductId());
                int goodsNumber = sub.getGoodsNumber() + goods.getProductNum();
                sub.setGoodsNumber(goodsNumber);
                sub.setGoodsFinishedNumber(goodsNumber);
                sub.setGoodsAmount(goods.getShopPrice()
                    .multiply(new BigDecimal(goods.getProductNum())).add(sub.getGoodsAmount()));
            }
        }

        depotInMain.setDepotInNumber(depotInNumber);
        depotInMain.setDepotInAmount(depotInAmount);
        depotInMain.setDepotInFinishedNumber(depotInNumber);

        depotInMainMapper.insert(depotInMain);

        Integer depotInId = depotInMain.getDepotInId();

        for (Map.Entry<Integer, DepotInSub> entry : depotInSubMap.entrySet()) {
        	DepotInSub sub = entry.getValue();
            Integer goodsId = entry.getKey();
            
            sub.setDepotInId(depotInId);
            depotInSubMapper.insert(sub);
            
            Integer subId = sub.getDepotInSubId();

            Transaction trans = new Transaction();
            trans.setTransType(1);
            trans.setTransStatus(4);
            trans.setTransSn(depotInCode);
            trans.setGoodsId(goodsId);
            trans.setColorId(sub.getColorId());
            trans.setSizeId(sub.getSizeId());
            trans.setGoodsNumber(sub.getGoodsNumber());
            trans.setDepotId(depotId);
            trans.setPacketId(locationId);
            trans.setAddAid(providerId);
            trans.setAddTime(date);
            trans.setUpdateAid(providerId);
            trans.setUpdateTime(date);
            trans.setSubId(subId);
            trans.setBatchId(sub.getBatchId());
            trans.setTransDirection(1); // 0=出库 1=入库
            
            trans.setShopPrice(sub.getSalePrice());
            trans.setConsignPrice(sub.getConsignPrice());
            trans.setCostPrice(sub.getCostPrice());
            trans.setProductCess(sub.getProductCess());
            trans.setConsignRate(sub.getConsignRate());
            
            transactionMapper.insert(trans);

            shippingWaveMapper.subtractWaitNum(goodsId, sub.getColorId(), sub.getSizeId(), sub.getGoodsNumber());
        }
    }

    /**
     * 缺货处理
     */
    private void doShortageShipping(Integer providerId, List<Integer> orderIds, List<String> waveSnList) {

        shippingPacketService.updatePacketShortage(providerId, orderIds);

        shippingWaveMapper.updateOrderShortage(orderIds);

        // shippingWaveMapper.updateWaveShortage(waveSnList);
        updateWaveShipping(waveSnList, new Date());
    }

    /**
     * 生成波次号
     */
    private static String generateWaveSn(Integer providerId) {
    	StringBuilder buff = new StringBuilder("BC");
    	buff.append(getFixedProviderId(providerId));
    	buff.append(DateUtil.getCurrentTime().getTime());
    	buff.append(RandomUtils.generateNumString(1));
        
        return buff.toString();
    }
    
    private static String getFixedProviderId(Integer providerId) {
    	String tmp = String.valueOf(providerId);
        if (tmp.length() > 4) {
            return StringUtils.substring(tmp, -4);
        } else {
            return RandomUtils.toFixdLengthString(providerId, 4);
        }
    }
    
    @Override
    public void updatePrintStatus(String waveSn) {
        shippingWaveMapper.updatePrintStatus(waveSn);
    }
    
    /**
     * @return 根据供应商合作方式返回入库仓库ID，储位ID，入库类型ID。
     */
    private Integer[] getInIds(Integer providerId) {
    	//TODO:
    	Provider provider = providerMapper.get(providerId);
    	Integer cooperation = provider.getProviderCooperation();
    	if (Integer.valueOf(4).equals(cooperation)) { // MT服务（虚库）
    		return new Integer[]{
    			Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(20)
    		};
    	} else if (Integer.valueOf(6).equals(cooperation)) { // MT代销（虚库）
    		return new Integer[]{
    			Integer.valueOf(7), Integer.valueOf(6), Integer.valueOf(21)
    		};
    	} else {
    		throw new BizException("供应商合作方式错误，不能发货！");
    	}
    }
    
    /* ---- 获取生成波次业务锁的实现 --------------------------------------------------- */
//    @Autowired
//    private CacheDriver			  cacheDriver;
//    private static final Object LOCK = new Object();
//    private static final long EXPIRED_TIME = 30 * 1000L;
//    
//    private synchronized void lock(Integer providerId) {
//    	String cacheKey = generateCacheKey(providerId);
//    	Object lock = cacheDriver.get(cacheKey);
//    	
//    	while (lock != null) {
//    		try {
//				TimeUnit.MILLISECONDS.sleep(100L);
//			} catch (InterruptedException e) {
//				throw new BizException("生成波次失败！");
//			}
//    		lock = cacheDriver.get(cacheKey);
//    	}
//    	
//		cacheDriver.put(cacheKey, LOCK, EXPIRED_TIME, new CasOperation<Object>() {
//			
//			@Override
//			public Object getNewValue(Object currentValue) {
//				return LOCK;
//			}
//			
//			@Override
//			public int getMaxTries() {
//				return 5;
//			}
//		});
//    }
//    
//    private synchronized void unlock(Integer providerId) {
//    	String cacheKey = generateCacheKey(providerId);
//    	cacheDriver.remove(cacheKey);
//    }
//    
//    private static String generateCacheKey(Integer providerId) {
//    	return "fclub.provider.generate.wave." + providerId;
//    }
    
    // TODO: for future fix
    private static synchronized Object getLock(Integer providerId) {
    	Object lock = LOCK_MAP.get(providerId);
    	if (lock == null) {
    		lock = new Object();
    		LOCK_MAP.put(providerId, lock);
    	}
    	return lock;
    }
    
    public static final Map<Integer, Object> LOCK_MAP = new HashMap<Integer, Object>();
    
}