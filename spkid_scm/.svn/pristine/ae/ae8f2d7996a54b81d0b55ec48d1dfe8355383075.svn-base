/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fclub.common.lang.utils.DateUtil;
import com.fclub.tpd.common.DictUtil;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingPacket.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public class ShippingPacket implements Serializable {

    private static final long   serialVersionUID = 1L;
    //----------------自动生成 BEGIN----------------//
    /** primary key */
    private Integer             packetId;
    /** 波次号 */
    private String              waveSn;
    /** 供应商ID */
    private Integer             providerId;
    /** 订单ID */
    private Integer             orderId;
    /** 订单商品明细ID */
    private Integer             opId;
    /** 物流公司 */
    private Integer             shippingId;
    /** 运单号 */
    private String              packetSn;
    /** 运费 */
    private BigDecimal          shippingFee;
    /** 0为实发,1为虚发 */
    private boolean             virtualShipping;
    /** 0为拣货中，1已发货，2为缺货 */
    private Integer             status;
    /** 创建时间 */
    private Date                createTime;
    /** 完成时间 */
    private Date                finishTime;

    //////////////////
    private List<ShippingProduct> productList;

    private String              shippingName;

    // order info
    private String              orderSn;
    private Date                orderAddTime;
    private Date                orderConfirmTime;
    private String              region;
    private String              address;
    private String              consignee;
    private String              mobile;
    private String              tel;
    private String              bestTime;
    private String              userNotice;
    private Integer             shippingStatus;
    private BigDecimal          paidPrice;
    
    // for print
    private Integer             invRequired;
    private String              userName;

    private Integer             orderNum;

    private String              dateBegin;
    private String              dateEnd;
    private String              providerProductcode;

    public ShippingPacket() {

    }

    //------- for export --------//
    public String getStatusStr() {
        return DictUtil.getDictValue("ShippingPacket.status", status);
    }

    public String getOrderConfirmTimeStr() {
        return DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss").format(orderConfirmTime);
    }

    public String getCreateTimeStr() {
        return DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
    }

    public String getFinishTimeStr() {
        return DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss").format(finishTime);
    }

    public Integer getPacketId() {
        return packetId;
    }

    public void setPacketId(Integer packetId) {
        this.packetId = packetId;
    }

    public String getWaveSn() {
        return waveSn;
    }

    public void setWaveSn(String waveSn) {
        this.waveSn = waveSn;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getPacketSn() {
        return packetSn;
    }

    public void setPacketSn(String packetSn) {
        this.packetSn = packetSn;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public boolean getVirtualShipping() {
        return virtualShipping;
    }

    public void setVirtualShipping(boolean virtualShipping) {
        this.virtualShipping = virtualShipping;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<ShippingProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<ShippingProduct> productList) {
        this.productList = productList;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public Date getOrderAddTime() {
        return orderAddTime;
    }

    public void setOrderAddTime(Date orderAddTime) {
        this.orderAddTime = orderAddTime;
    }

    public Date getOrderConfirmTime() {
        return orderConfirmTime;
    }

    public void setOrderConfirmTime(Date orderConfirmTime) {
        this.orderConfirmTime = orderConfirmTime;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    public String getUserNotice() {
        return userNotice;
    }

    public void setUserNotice(String userNotice) {
        this.userNotice = userNotice;
    }

    public Integer getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public BigDecimal getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(BigDecimal paidPrice) {
        this.paidPrice = paidPrice;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getProviderProductcode() {
        return providerProductcode;
    }

    public void setProviderProductcode(String providerProductcode) {
        this.providerProductcode = providerProductcode;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getInvRequired() {
        return invRequired;
    }

    public void setInvRequired(Integer invRequired) {
        this.invRequired = invRequired;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    //-----------------自动生成 END-----------------//
}