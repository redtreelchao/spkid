package com.fclub.tpd.dataobject;

import java.io.Serializable;

public class ReportForm implements Serializable {
	private static final long    serialVersionUID = 1L;
	private String goodsSn;
	private Integer brandId;
	private String confirmTime;
	private String colorName;
	private String sizeName;
	private Double goodsAmount;
	private Integer goodsNumber;
	private String createDateBegin;
    private String createDateEnd; 
    private Integer providerId; 
    private String brandName;
    private Integer totalGoodsNum = new Integer(0); 
    private Double totalGoodsAmt =new Double(0.0);
	/*** 合作方式*/
    private Integer coopId;
    public Integer getTotalGoodsNum() {
		return totalGoodsNum;
	}
	public void setTotalGoodsNum(Integer totalGoodsNum) {
		this.totalGoodsNum = totalGoodsNum;
	}
	public Double getTotalGoodsAmt() {
		return totalGoodsAmt;
	}
	public void setTotalGoodsAmt(Double totalGoodsAmt) {
		this.totalGoodsAmt = totalGoodsAmt;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public String getCreateDateBegin() {
		return createDateBegin;
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
	public Integer getCoopId() {
		return coopId;
	}
	public void setCoopId(Integer coopId) {
		this.coopId = coopId;
	}
	
	public Double getGoodsAmount() {
		return goodsAmount;
	}
	public void setGoodsAmount(Double goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	public Integer getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}


	
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getSizeName() {
		return sizeName;
	}
	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
