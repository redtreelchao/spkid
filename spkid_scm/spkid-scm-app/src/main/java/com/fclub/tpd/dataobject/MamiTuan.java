package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MamiTuan implements Serializable {
	
	private static final long serialVersionUID = -762830471708302616L;

	private Integer 	tuanId;
	private Integer		productId;
	private String		tuanName;
	private Integer		buyNum;
	private BigDecimal	tuanPrice;
	private Integer		status;
	private Date		tuanOnlineTime;
	private Date		tuanOfflineTime;
	private String		tuanDesc;
	private String		userDefine1;
	private String		userDefine2;
	private String		userDefine3;
	private String		img315207;
	private String		img168110;
	private String		img500450;
	private Float		productDiscount;
	private Integer		tuanSort;
	
	private Integer		opStopAid;
	private Date		opStopTime;
	private Integer		opAddAid;
	private Date		opAddTime;
	private Integer		opCheckAid;
	private Date		opCheckTime;
	private Integer		opUpdateAid;
	private Date		opUpdateTime;
	
	private Product		product;
	private Provider	provider;
	
	private String		tuanImg;
	
	/* ---- private methods ---- */
	public Integer getTuanId() {
		return tuanId;
	}
	public void setTuanId(Integer tuanId) {
		this.tuanId = tuanId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getTuanName() {
		return tuanName;
	}
	public void setTuanName(String tuanName) {
		this.tuanName = tuanName;
	}
	public Integer getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}
	public BigDecimal getTuanPrice() {
		return tuanPrice;
	}
	public void setTuanPrice(BigDecimal tuanPrice) {
		this.tuanPrice = tuanPrice;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getTuanOnlineTime() {
		return tuanOnlineTime;
	}
	public void setTuanOnlineTime(Date tuanOnlineTime) {
		this.tuanOnlineTime = tuanOnlineTime;
	}
	public Date getTuanOfflineTime() {
		return tuanOfflineTime;
	}
	public void setTuanOfflineTime(Date tuanOfflineTime) {
		this.tuanOfflineTime = tuanOfflineTime;
	}
	public String getTuanDesc() {
		return tuanDesc;
	}
	public void setTuanDesc(String tuanDesc) {
		this.tuanDesc = tuanDesc;
	}
	public String getUserDefine1() {
		return userDefine1;
	}
	public void setUserDefine1(String userDefine1) {
		this.userDefine1 = userDefine1;
	}
	public String getUserDefine2() {
		return userDefine2;
	}
	public void setUserDefine2(String userDefine2) {
		this.userDefine2 = userDefine2;
	}
	public String getUserDefine3() {
		return userDefine3;
	}
	public void setUserDefine3(String userDefine3) {
		this.userDefine3 = userDefine3;
	}
	public String getImg315207() {
		return img315207;
	}
	public void setImg315207(String img315207) {
		this.img315207 = img315207;
	}
	public String getImg168110() {
		return img168110;
	}
	public void setImg168110(String img168110) {
		this.img168110 = img168110;
	}
	public String getImg500450() {
		return img500450;
	}
	public void setImg500450(String img500450) {
		this.img500450 = img500450;
	}
	public Float getProductDiscount() {
		return productDiscount;
	}
	public void setProductDiscount(Float productDiscount) {
		this.productDiscount = productDiscount;
	}
	public Integer getTuanSort() {
		return tuanSort;
	}
	public void setTuanSort(Integer tuanSort) {
		this.tuanSort = tuanSort;
	}
	public Integer getOpStopAid() {
		return opStopAid;
	}
	public void setOpStopAid(Integer opStopAid) {
		this.opStopAid = opStopAid;
	}
	public Date getOpStopTime() {
		return opStopTime;
	}
	public void setOpStopTime(Date opStopTime) {
		this.opStopTime = opStopTime;
	}
	public Integer getOpAddAid() {
		return opAddAid;
	}
	public void setOpAddAid(Integer opAddAid) {
		this.opAddAid = opAddAid;
	}
	public Date getOpAddTime() {
		return opAddTime;
	}
	public void setOpAddTime(Date opAddTime) {
		this.opAddTime = opAddTime;
	}
	public Integer getOpCheckAid() {
		return opCheckAid;
	}
	public void setOpCheckAid(Integer opCheckAid) {
		this.opCheckAid = opCheckAid;
	}
	public Date getOpCheckTime() {
		return opCheckTime;
	}
	public void setOpCheckTime(Date opCheckTime) {
		this.opCheckTime = opCheckTime;
	}
	public Integer getOpUpdateAid() {
		return opUpdateAid;
	}
	public void setOpUpdateAid(Integer opUpdateAid) {
		this.opUpdateAid = opUpdateAid;
	}
	public Date getOpUpdateTime() {
		return opUpdateTime;
	}
	public void setOpUpdateTime(Date opUpdateTime) {
		this.opUpdateTime = opUpdateTime;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	public String getTuanImg() {
		return tuanImg;
	}
	public void setTuanImg(String tuanImg) {
		this.tuanImg = tuanImg;
	}
	
}
