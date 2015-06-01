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

/**
 * 
 * @author auto-gene
 * @version $Id: SelfReturn.java, v 0.1 2013-07-12 16:18:00 auto-gene Exp $
 */
public class SelfReturn implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer applyId;
  	/** 订单id */
    private Integer orderId;
  	/** 用户id */
    private BigDecimal userId;
  	/** 供应商id */
    private Integer providerId;
  	/** 快递名称 */
    private String shippingName;
  	/** 运单号 */
    private String invoiceNo;
  	/** 寄件人姓名 */
    private String sentUserName;
  	/** 寄件人手机号 */
    private String mobile;
  	/** 寄件人电话 */
    private String tel;
  	/** 运费 */
    private BigDecimal shippingFee;
  	/** 退回地址 */
    private String backAddress;
  	/** 总件数 */
    private Integer productNumber;
  	/** 申请状态 0:待处
理 1:处理中 2:已处理 */
    private Integer applyStatus;
  	/** 供应商状态 0
未审核 1 已审核 */
    private Integer providerStatus;
  	/** 订单类型 0 普通订
单 1 第三方直发订单 */
    private Integer orderType;
  	/** 申请时间 */
    private Date applyTime;
  	/** 取消时间 */
    private Date cancelTime;
    
    private String orderSn;
    
    private Date startTime;
    
    private Date endTime;
    
    private List<SelfReturnProduct> productList;
    
    private String suggestContent;

    public SelfReturn() {
        
    }
	
	public Integer getApplyId(){
        return applyId;
    }
    public void setApplyId(Integer applyId){
        this.applyId = applyId;
    }
    public Integer getOrderId(){
        return orderId;
    }
    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }
    public BigDecimal getUserId(){
        return userId;
    }
    public void setUserId(BigDecimal userId){
        this.userId = userId;
    }
    public Integer getProviderId(){
        return providerId;
    }
    public void setProviderId(Integer providerId){
        this.providerId = providerId;
    }
    public String getShippingName(){
        return shippingName;
    }
    public void setShippingName(String shippingName){
        this.shippingName = shippingName == null ? null : shippingName.trim();
    }
    public String getInvoiceNo(){
        return invoiceNo;
    }
    public void setInvoiceNo(String invoiceNo){
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }
    public String getSentUserName(){
        return sentUserName;
    }
    public void setSentUserName(String sentUserName){
        this.sentUserName = sentUserName == null ? null : sentUserName.trim();
    }
    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getTel(){
        return tel;
    }
    public void setTel(String tel){
        this.tel = tel;
    }
    public BigDecimal getShippingFee(){
        return shippingFee;
    }
    public void setShippingFee(BigDecimal shippingFee){
        this.shippingFee = shippingFee;
    }
    public String getBackAddress(){
        return backAddress;
    }
    public void setBackAddress(String backAddress){
        this.backAddress = backAddress;
    }
    public Integer getProductNumber(){
        return productNumber;
    }
    public void setProductNumber(Integer productNumber){
        this.productNumber = productNumber;
    }
    public Integer getApplyStatus(){
        return applyStatus;
    }
    public void setApplyStatus(Integer applyStatus){
        this.applyStatus = applyStatus;
    }
    public Integer getProviderStatus(){
        return providerStatus;
    }
    public void setProviderStatus(Integer providerStatus){
        this.providerStatus = providerStatus;
    }
    public Integer getOrderType(){
        return orderType;
    }
    public void setOrderType(Integer orderType){
        this.orderType = orderType;
    }
    public Date getApplyTime(){
        return applyTime;
    }
    public void setApplyTime(Date applyTime){
        this.applyTime = applyTime;
    }
    public Date getCancelTime(){
        return cancelTime;
    }
    public void setCancelTime(Date cancelTime){
        this.cancelTime = cancelTime;
    }
  	
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
  	//-----------------自动生成 END-----------------//

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn == null ? orderSn : orderSn.trim();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<SelfReturnProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<SelfReturnProduct> productList) {
		this.productList = productList;
	}

	public String getSuggestContent() {
		return suggestContent;
	}

	public void setSuggestContent(String suggestContent) {
		this.suggestContent = suggestContent;
	}
}