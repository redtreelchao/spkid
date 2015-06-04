/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ShippingWave;
import com.fclub.tpd.dataobject.erp.GoodsEntity;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingWaveMapper.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface ShippingWaveMapper extends BaseMapper<ShippingWave> {

    /**
     * 更新波次表缺货数量
     */
    @Deprecated
    void updateWaveShortage(List<String> waveSnList);

    /**
     * 更新波次表已发货数量
     */
    @Deprecated
    void updateWaveShipping(List<String> waveSnList);

    /**
     * 更新波次表已发货状态
     */
    @Deprecated
    void updateWaveStatusFinish(List<String> waveSnList);

    /**
     * 更新波次表部分发货状态
     */
    @Deprecated
    void updateWaveStatusHalfFinish(List<String> waveSnList);

    /**
     * 更新订单缺货
     */
    void updateOrderShortage(List<Integer> orderIds);

    /**
     * 更新订单已发货
     */
    void updateOrderShipping(@Param("providerId") Integer providerId,
                             @Param("orderIds") List<Integer> orderIds, @Param("date") Date date);

    List<GoodsEntity> getShippingGoodsList(@Param("providerId") Integer providerId,
                                           @Param("orderIds") List<Integer> orderIds);

    /**
     * 扣除虚库销售数量
     */
    void subtractWaitNum(@Param("goodsId") Integer goodsId, @Param("colorId") Integer colorId,
                         @Param("sizeId") Integer sizeId, @Param("goodsNumber") Integer goodsNumber);

    /**
     * 订单出库
     */
    void insertOrderOut(@Param("providerId") Integer providerId,
                        @Param("orderIds") List<Integer> orderIds, 
                        @Param("depotId") Integer depotId,
                        @Param("locationId") Integer locationId,
                        @Param("date") Date date);

    /**
     * 订单商品的虚库标记、虚库商品数量扣除
     */
    void updateOrderGoodsConsign(@Param("providerId") Integer providerId,
                                 @Param("orderIds") List<Integer> orderIds);

    /**
     * 根据波次号更新波次发货/缺货数量及发货状态
     */
    void updateByWaveSn(ShippingWave shippingWave);

    /**
     * 更新为已打印
     */
    void updatePrintStatus(String waveSn);

	int countOrderNum(Integer waveId);

}
