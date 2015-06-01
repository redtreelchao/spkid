package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class SMS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer smsId;
	private Integer providerId;
	private String content;
	private Date createTime;
	private Date commitTime;
	private Date checkTime;
	private Integer checkAdmin;
	private Date sendTime;
	private Integer status;

	private String providerCode;
	private Boolean isAdmin;

	private String mobile;
	private String sourceType;
	private Date updateTime;

	private Date sendStartTime;
	private Date sendEndTime;
	private boolean isHistory;
	private Double smsPrice;
	private String memo;
	private Integer smsNum;

	public Integer getSmsNum() {
		return smsNum;
	}

	public void setSmsNum(Integer smsNum) {
		this.smsNum = smsNum;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSmsId() {
		return smsId;
	}

	public void setSmsId(Integer smsId) {
		this.smsId = smsId;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Integer getCheckAdmin() {
		return checkAdmin;
	}

	public void setCheckAdmin(Integer checkAdmin) {
		this.checkAdmin = checkAdmin;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getSendStartTime() {
		return sendStartTime;
	}

	public void setSendStartTime(Date sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	public Date getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(Date sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	public boolean getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}

	public Double getSmsPrice() {
		return smsPrice;
	}

	public void setSmsPrice(Double smsPrice) {
		this.smsPrice = smsPrice;
	}

}
