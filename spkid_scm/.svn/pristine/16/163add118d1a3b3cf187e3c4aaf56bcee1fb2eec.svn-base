/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fclub.cache.CacheDriver;
import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.Assert;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LogUtil;
import com.fclub.thread.ThreadPoolManager;
import com.fclub.tpd.batch.importing.ImportService;
import com.fclub.tpd.batch.importing.ImportTaskManager;
import com.fclub.tpd.batch.importing.dto.BatchImportSeach;
import com.fclub.tpd.batch.importing.dto.ConsoleOut;
import com.fclub.tpd.batch.importing.dto.GoodsData;
import com.fclub.tpd.batch.importing.dto.ImportBatchDetail;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.enums.ImportType;
import com.fclub.tpd.common.PropertyUtil;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.common.holder.AdminAuthorityHolder;
import com.fclub.tpd.dataobject.BatchImport;
import com.fclub.tpd.dataobject.ImportList;
import com.fclub.tpd.dataobject.ProductGallery;
import com.fclub.tpd.helper.ConstantsHelper;
import com.fclub.tpd.mapper.BatchImportMapper;
import com.fclub.tpd.mapper.ImportListMapper;
import com.fclub.tpd.mapper.ProductGalleryMapper;
import com.fclub.tpd.mapper.ProductMapper;

/**
 * 
 * @author likaiping
 * @version $Id: ImportServiceImpl.java, v 0.1 Oct 25, 2012 4:07:11 PM likaiping Exp $
 */
//@Service
public class ImportServiceImpl implements ImportService {
    LogUtil                            logger      = LogUtil.getLogger(getClass());

    protected static ThreadPoolManager THREAD_POOL = new ThreadPoolManager(30);

    @Autowired
    ImportTaskManager                  importTaskManager;
    @Autowired
    BatchImportMapper                  batchImportMapper;
    @Autowired
    ImportListMapper                   importListMapper;
    @Autowired
    CacheDriver                        cacheDriver;
    @Autowired
    ProductMapper                      productMapper;
    @Autowired
    ProductGalleryMapper               productGalleryMapper;

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#importProcess(com.fclub.erp.dto.importing.ImportContext)
     */
    @Override
    public ImportResult importProcess(ImportContext importContext) {
        Assert.notNull(importContext);
        Assert.notNull(importContext.getImportType());
        importContext.setImportAdmin(AdminAuthorityHolder.get().getProvider().getProviderId());
        return importTaskManager.execute(importContext);
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#retry(com.fclub.erp.dto.importing.ImportContext)
     */
    @Override
    public void retry(ImportContext importContext) {
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#delete(java.lang.String)
     */
    @Override
    public void delete(String batchNo) {
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#getByBatchNo(java.lang.String)
     */
    @Override
    public ImportBatchDetail getByBatchNo(String batchNo) {
        ImportBatchDetail detail = new ImportBatchDetail();
        BatchImport batchImport = batchImportMapper.selectByBatchNo(batchNo);
        List<ImportList> importLists = importListMapper.selectByBatchNo(batchNo);
        detail.setBatchImport(batchImport);
        detail.setImportLists(importLists);
        initState(detail);
        return detail;
    }

    /**
     * 批量导入状态控制：
        主要状态说明：状态分为三位字符，第一位字符为操作标识，第二位为提示标识，第三位为描述标识。
        操作标识:0-不可操作；1-可操作；
        提示标识 0-未完成;1-正在执行中;2-已完成;3-出错;
        描述标识:0-无提示;1-有提示;
    状态控制对象:
        主要信息导入控制对象（MC），颜色尺寸控制对象（CC），统一审核控制对象（VC）
        次要信息控制对象（SC），采购单控制对象（PC）
        尺寸图控制对象（BC），商品图控制对象（GC）
    ICON控制：true：亮；false：暗
        ICON1，ICON2，ICON3
     */
    public void initState(ImportBatchDetail detail) {
        BatchImport batchImport = detail.getBatchImport();
        List<ImportList> importLists = detail.getImportLists();
        Map<String, Integer> optionMap = new HashMap<>();
        Integer mc, cc, vc, sc, pc, bc, gc;
        Integer icon1 = 0, icon2 = 0, icon3 = 0;
        //统一审核后-MC,CC,VC都不可操作
        if (batchImport.isAudit() != null && batchImport.isAudit() == true) {
            mc = cc = vc = 0;
            sc = pc = bc = gc = 1;
            icon3 = 1;
        } else {
            sc = pc = bc = gc = 0;
            if (batchImport.isImpmain() != null && batchImport.isImpmain() == true) {
                mc = 0;
                cc = 1;
                icon1 = 1;
                //放开次要信息，颜色尺寸，商品图权限
                sc = 1;
                bc = 1;
                gc = 1;
                vc = 1;
                icon2 = 1;
            } else {
                mc = 1;
                cc = vc = 0;
            }
        }
        optionMap.put("mc", mc);
        optionMap.put("cc", cc);
        optionMap.put("vc", vc);
        optionMap.put("sc", sc);
        optionMap.put("pc", pc);
        optionMap.put("bc", bc);
        optionMap.put("gc", gc);
        optionMap.put("icon1", icon1);
        optionMap.put("icon2", icon2);
        optionMap.put("icon3", icon3);
        Map<String, Integer> stateMap = new HashMap<>();
        for (ImportList importList : importLists) {
            genState(importList, stateMap);
        }
        detail.setOptionMap(optionMap);
        detail.setStateMap(stateMap);
    }

    /**
     *  0-未完成;1-正在执行中;2-已完成;3-出错;
     * @param status
     * @param state
     * @return
     */
    private void genState(ImportList importList, Map<String, Integer> stateMap) {
        String code = importList.getImportType().getCode();
        switch (importList.getImportStatus()) {
            case RUNING:
                stateMap.put(code, 1);
                break;
            case FAIL:
                Integer state = stateMap.get(code);
                if (state == null || state == 0)
                    stateMap.put(code, 3);
                break;
            default:
                state = stateMap.get(code);
                if (state == null || state != 1)
                    stateMap.put(code, 2);
                break;
        }
    }

    /**
     * @see com.fclub.erp.biz.importing.ImportService#getProgress(java.lang.String)
     */
    @Override
    public String getProgress(String type) {
        String key = PROGRESS_PRE + type;
        if (cacheDriver.containsKey(key)) {
            return (String) cacheDriver.get(key);
        }
        return "100/0";
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#getState(java.lang.String, java.lang.String)
     */
    @Override
    public ImportStatus getState(String type, String batchNo) {
        ImportType importType = ImportType.valueOfCode(type);
        if (importType == null) {
            return null;
        }
        ImportList importList;
        Integer providerId = 1;
        if (importType == ImportType.GOODS_MAIN_INFORMATION) {
            importList = importListMapper.getLastByAdmin(type, providerId);
        } else if (importType == ImportType.GOODS_COLOR_SIZE) {
            importList = importListMapper.getLastByAdmin(type, providerId);
        } else {
            List<ImportList> importLists = importListMapper.getLastByBatchNo(type, batchNo);
            if (importLists != null && importLists.size() > 0) {
                ImportList temp = importLists.get(0);
                if (StringUtil.equals(temp.getImpBatchNo(), batchNo)) {
                    importList = temp;
                } else
                    return null;
            } else
                return null;
        }
        if (importList != null) {
            return ImportStatus.valueOfCode(importList.getStatus());
        } else {
            return null;
        }
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#findPage(com.fclub.common.dal.Page, com.fclub.erp.dto.importing.BatchImportSeach)
     */
    @Override
    public Page<BatchImport> findPage(Page<BatchImport> page, BatchImportSeach seach) {
        seach.setCrtUser(AdminAuthorityHolder.get().getProvider().getProviderId());
        page.setResult(batchImportMapper.findPage(page, seach));
        return page;
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#getConsoleOut(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ConsoleOut getConsoleOut(int listId) {
        ImportList importList = importListMapper.selectByPrimaryKey(listId);
        if (importList == null) {
            return new ConsoleOut(true);
        }
        String imprtBatchNo = importList.getImpBatchNo();
        String filePath = ConstantsHelper.getStaticRootPath() + "/"
                          + ConstantsHelper.getPram(SystemConstant.BATCH_CONSOLE_OUT) + "/"
                          + imprtBatchNo;
        String logFileName = importList.getLogFile();
        if (importList.getImportStatus() == ImportStatus.RUNING) {
            List<String> tmp = (List<String>) cacheDriver.get(filePath + "/" + logFileName);
            if (tmp != null) {
                return new ConsoleOut(tmp, false);
            }
        } else {
            File file = new File(filePath, logFileName);
            if (!file.exists()) {
                throw new BizException("日志文件不存在");
            }
            try {
                List<String> list = new ArrayList<>();
                @SuppressWarnings("resource")
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {
                    String string = bufferedReader.readLine();
                    if (string == null) {
                        break;
                    }
                    list.add(string);
                }
                return new ConsoleOut(list, true);
            } catch (Exception e) {
                logger.error("读取日志文件错误，listId：{0}", e, listId);
                throw new BizException("读取日志文件错误，请联系管理员");
            }
        }
        return new ConsoleOut(true);
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#getResultData(int)
     */
    @Override
    public String getResultData(int listId) {
        ImportList importList = importListMapper.selectByPrimaryKey(listId);
        if (importList == null) {
            throw new BizException("没有此执行记录");
        }
        String filePath = ConstantsHelper.getStaticRootPath() + "/"
                          + ConstantsHelper.getPram(SystemConstant.BATCH_RESULT) + "/"
                          + importList.getImpBatchNo();
        String resultFile = importList.getResultFile();
        if (StringUtil.isBlank(resultFile)) {
            throw new BizException("没有此执行记录对应的结果");
        }
        return filePath + "/" + resultFile;
    }

    public class ImportRunnable implements Runnable {

        ImportContext importContext;

        /**
         * @param importContext
         */
        public ImportRunnable(ImportContext importContext) {
            this.importContext = importContext;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            importTaskManager.execute(importContext);
        }
    }

    //～============= 导入商品图 =================
    /** 
     * @see com.fclub.erp.biz.importing.ImportService#findGalleryByPage(com.fclub.common.dal.Page, com.fclub.erp.dto.importing.BatchImportSeach)
     */
    @Override
    public Page<ImportList> findGalleryByPage(Page<ImportList> page, BatchImportSeach seach) {
        if (seach.isOwner()) {
            seach.setCrtUser(AdminAuthorityHolder.get().getProvider().getProviderId());
        }
        page.setResult(importListMapper.findGalleryByPage(page, seach));
        return page;
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#getLastByBatchNo(java.lang.String, java.lang.String)
     */
    @Override
    public List<ImportList> getLastByBatchNo(String type, String batchNo) {
        return importListMapper.getLastByBatchNo(type, batchNo);
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#getBatchImportByNo(java.lang.String)
     */
    @Override
    public BatchImport getBatchImportByNo(String batchNo) {
        return batchImportMapper.selectByBatchNo(batchNo);
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportService#stopImportGoodsGallery(int)
     */
    @Override
    public void stopImportGoodsGallery(int listId) {
        if (listId <= 0)
            return;
        ImportList importList = importListMapper.selectByPrimaryKey(listId);
        if (importList != null) {
            if (StringUtil.equals(importList.getStatus(), ImportStatus.RUNING.getCode())) {
                importList = new ImportList();
                importList.setId(listId);
                importList.setStatus(ImportStatus.FAIL.getCode());
                importList.setMsg("手工关闭");
                logger.warn("手工关闭未完成导入图片任务,操作记录ListId:{0},供应商:{1}", listId, AdminAuthorityHolder
                    .get().getProvider().getProviderId());
                importListMapper.updateByPrimaryKeySelective(importList);
            }
        }
    }

    @Override
    public List<GoodsData> getGoodsColorSizeData(String batchNo) {
        BatchImport batchImport = batchImportMapper.selectByBatchNo(batchNo);
        if (StringUtils.isBlank(batchImport.getImpGoodsIds())) {
            return null;
        }
        List<GoodsData> goodsList = productMapper.getGoodsDataList(StringUtil.split(
            batchImport.getImpGoodsIds(), ","));
        return goodsList;
    }

    /** 
     * @see com.fclub.tpd.batch.importing.ImportService#getPageByBatchNo(com.fclub.common.dal.Page, java.lang.String, java.lang.String)
     */
    @Override
    public Page<ImportList> getPageByBatchNo(Page<ImportList> page, String type, String batchNo) {
        page.setResult(importListMapper.getPageByBatchNo(page, type, batchNo));
        return page;
    }

    @Override
    public String getProgressBar(String batchNo) {
        String key = PROGRESS_PRE + batchNo;
        Object value = (String) PropertyUtil.getProgressValue(key);
        return value == null ? "100/0" : String.valueOf(value);
    }
    
    @Override
    public List<ProductGallery> queryGallerys(Set<Integer> goodsIds) {
    	if (goodsIds == null || goodsIds.isEmpty()) {
    		return Collections.emptyList();
    	}
    	List<ProductGallery> resultList = new ArrayList<ProductGallery>();
    	for (Integer goodsId : goodsIds) {
    		resultList.addAll(productGalleryMapper.selectGalleryByGoodsId(goodsId));
    	}
    	return resultList;
    }
    
}
