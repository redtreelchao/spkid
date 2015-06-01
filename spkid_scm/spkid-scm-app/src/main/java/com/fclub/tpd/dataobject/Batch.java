package com.fclub.tpd.dataobject;

import java.io.Serializable;

public class Batch implements Serializable {

	private static final long serialVersionUID = 4667694533866722068L;
	
	private Integer batchId;
	private String  batchCode;
	private Integer providerId;
	private Integer brandId;
	private Integer createAdmin;
	
	
	public Integer getBatchId() {
		return batchId;
	}
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getCreateAdmin() {
		return createAdmin;
	}
	public void setCreateAdmin(Integer createAdmin) {
		this.createAdmin = createAdmin;
	}
	
}
