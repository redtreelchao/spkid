/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingWave.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public class ShippingWave implements Serializable {

    private static final long    serialVersionUID = 1L;
    //----------------自动生成 BEGIN----------------//
    /** primary key */
    private Integer              waveId;
    /** 波次号 */
    private String               waveSn;
    /** 订单数量 */
    private Integer              orderNum;
    /** 发货数量 */
    private Integer              shippingNum;
    /** 供应商ID */
    private Integer              providerId;
    /** 波次状态(0为拣货中、1为部分发货、2为完全发货) */
    private Integer              waveStatus;
    /** 创建时间 */
    private Date                 createTime;
    /** 完成时间 */
    private Date                 finishTime;
    /** 缺货数量 */
    private Integer              shortages;
    /** 是否打印装箱单 */
    private boolean              isPrintBox;

    ////////////////
    private List<ShippingPacket> shippingPackets;

    private String               createDateBegin;
    private String               createDateEnd;

    public ShippingWave() {

    }

    public Integer getWaveId() {
        return waveId;
    }

    public void setWaveId(Integer waveId) {
        this.waveId = waveId;
    }

    public String getWaveSn() {
        return waveSn;
    }

    public void setWaveSn(String waveSn) {
        this.waveSn = waveSn;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getShippingNum() {
        return shippingNum;
    }

    public void setShippingNum(Integer shippingNum) {
        this.shippingNum = shippingNum;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getWaveStatus() {
        return waveStatus;
    }

    public void setWaveStatus(Integer waveStatus) {
        this.waveStatus = waveStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getShortages() {
        return shortages;
    }

    public void setShortages(Integer shortages) {
        this.shortages = shortages;
    }

    public boolean getIsPrintBox() {
        return isPrintBox;
    }

    public void setIsPrintBox(boolean isPrintBox) {
        this.isPrintBox = isPrintBox;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    //-----------------自动生成 END-----------------//

    public String getCreateDateBegin() {
        return createDateBegin;
    }

    public List<ShippingPacket> getShippingPackets() {
        return shippingPackets;
    }

    public void setShippingPackets(List<ShippingPacket> shippingPackets) {
        this.shippingPackets = shippingPackets;
    }

    public void setCreateDateBegin(String createDateBegin) {
        this.createDateBegin = createDateBegin;
    }

    public String getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Integer getUnProcessNum() {
        int unprocess = orderNum == null ? 0 : orderNum;
        if (shippingNum != null) {
            unprocess = unprocess - shippingNum;
        }
        if (shortages != null) {
            unprocess = unprocess - shortages;
        }
        return unprocess;
    }
}