/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.ShippingProduct;
import com.fclub.tpd.dataobject.ShippingPacket;
import com.fclub.tpd.dto.ShippingStatDTO;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingPacketService.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface ShippingPacketService {

    /**
     * 根据Id查询ShippingPacket
     */
    ShippingPacket get(Integer id);

    /**
     * 分页查询ShippingPacket
     */
    Page<ShippingPacket> findPage(Page<ShippingPacket> page, ShippingPacket shippingPacket);

    /**
     * 保存ShippingPacket
     */
    void save(ShippingPacket shippingPacket);

    /**
     * 更新ShippingPacket
     */
    void update(ShippingPacket shippingPacket);

    /**
     * 删除ShippingPacket
     */
    void delete(Integer id);

    /**
     * 查询波次订单信息
     */
    List<ShippingPacket> getShippingPacket(ShippingPacket shippingPacket);

    /**
     * 获取供应商当前需发货的订单数量
     */
    Integer getExportOrderNum(Integer providerId);
    
    /**
     * 获取供应商当前需发货的订单
     */
    List<ShippingPacket> getExportOrder(Integer providerId);

    /**
     * 查询供应商包裹
     */
    List<ShippingPacket> getProviderPacket(Integer providerId, String orderSn);

    /**
     * 更新包裹缺货状态
     */
    void updatePacketShortage(Integer providerId, List<Integer> orderIds);

    /**
     * 发货更新
     */
    void updatePacketShipping(ShippingPacket shippingPacket);
    
    /**
     * 获取包裹统计信息
     */
    List<ShippingPacket> getPacketCount(List<String> waveSnList);

    /**
     * 导出订单
     */
    List<ShippingPacket> findExportOrder(ShippingPacket shippingPacket);
    
    /**
     * 导出订单(统一行显示)
     */
    List<ShippingProduct> findExport(ShippingPacket shippingPacket);

    /**
     * 导出商品
     */
    List<ShippingProduct> findExportGoods(ShippingPacket shippingPacket);

    /**
     * 订单统计
     */
    ShippingStatDTO statOrder(ShippingPacket shippingPacket);

    /**
     * check 运单号是否存在
     */
    boolean existsInvoiceNo(String orderSn, String packetSn);
    
    /**
     * 批量插入波次详情。
     * @return 插入记录行数。
     */
    int batchInsertNotExists(Integer providerId, String waveSn, List<Integer> orderIdList);
    
}