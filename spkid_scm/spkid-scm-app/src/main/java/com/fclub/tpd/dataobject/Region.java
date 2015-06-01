package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class Region implements Serializable {

	private static final long serialVersionUID = -8538716637400937391L;
	
	private Integer regionId;
	private Integer parentId;
	private String  regionName;
	private Integer regionType;
	private Date	createDate;
	private Integer	createAdmin;
	
	// -----
	private int fee;
	private int price;
	
	
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public Integer getRegionType() {
		return regionType;
	}
	public void setRegionType(Integer regionType) {
		this.regionType = regionType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCreateAdmin() {
		return createAdmin;
	}
	public void setCreateAdmin(Integer createAdmin) {
		this.createAdmin = createAdmin;
	}
	
	public int getFee() {
		return fee;
	}
	public void setFee(int minFee) {
		this.fee = minFee;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int minPrice) {
		this.price = minPrice;
	}
	
}
