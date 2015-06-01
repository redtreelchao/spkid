package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class Area implements Serializable {
	private Integer areaId;

	private String areaCode;

	private String areaName;

	private String areaImg;

	private Short areaAid;

	private Date areaTime;

	private static final long serialVersionUID = 1L;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode == null ? null : areaCode.trim();
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName == null ? null : areaName.trim();
	}

	public String getAreaImg() {
		return areaImg;
	}

	public void setAreaImg(String areaImg) {
		this.areaImg = areaImg == null ? null : areaImg.trim();
	}

	public Short getAreaAid() {
		return areaAid;
	}

	public void setAreaAid(Short areaAid) {
		this.areaAid = areaAid;
	}

	public Date getAreaTime() {
		return areaTime;
	}

	public void setAreaTime(Date areaTime) {
		this.areaTime = areaTime;
	}
}