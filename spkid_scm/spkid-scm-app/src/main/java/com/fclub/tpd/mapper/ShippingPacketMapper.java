/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ShippingPacket;
import com.fclub.tpd.dataobject.ShippingProduct;
import com.fclub.tpd.dto.ShippingStatDTO;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingPacketMapper.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface ShippingPacketMapper extends BaseMapper<ShippingPacket> {

    /**
     * 获取供应商当前需发货的订单数量
     */
    Integer getExportOrderNum(Integer providerId);

    /**
     * 获取供应商当前需发货的订单及商品
     */
    List<ShippingPacket> getExportOrder(Integer providerId);

    /**
     * 根据波次查询所有相关包裹
     */
    List<ShippingPacket> getShippingPacket(ShippingPacket shippingPacket);

    /**
     * 查询包裹商品
     */
    List<ShippingProduct> getShippingGoods(@Param("providerId") Integer providerId,
                                         @Param("orderIds") List<Integer> orderIds);

    /**
     * 查询供应商包裹
     */
    List<ShippingPacket> getProviderPacket(@Param("providerId") Integer providerId,
                                           @Param("orderSn") String orderSn);

    /**
     * 更新包裹缺货状态
     */
    void updatePacketShortage(@Param("providerId") Integer providerId,
                              @Param("orderIds") List<Integer> orderIds);

    /**
     * 发货更新
     */
    void updatePacketShipping(ShippingPacket shippingPacket);

    /**
     * 订单统计
     */
    ShippingStatDTO statOrder(ShippingPacket shippingPacket);

    /**
     * 有效订单统计
     */
    ShippingStatDTO statValidOrder(ShippingPacket shippingPacket);

    /**
     * 获取包裹统计信息
     */
    List<ShippingPacket> getPacketCount(List<String> waveSnList);

    List<ShippingProduct> findExport(ShippingPacket shippingPacket);

    int getCountByInvoiceNo(@Param("orderSn") String orderSn, @Param("packetSn") String packetSn);

	int batchInsertNotExists(@Param("providerId") Integer providerId, @Param("waveSn") String waveSn, @Param("orderIdList") List<Integer> orderIdList);

}
