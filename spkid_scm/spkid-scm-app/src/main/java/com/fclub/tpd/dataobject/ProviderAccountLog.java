package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品供应商品牌关联表
 * 
 * @author tony tian
 * 
 */
public class ProviderAccountLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5285508195343782596L;

	private Integer logId;

	private Integer providerId;

	private Integer changeType;

	private Double changePrice;

	private Integer relatedId;

	private Integer operateAid;

	private Date operateTime;

	private Integer operateStatus;

	private Integer status;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public Double getChangePrice() {
		return changePrice;
	}

	public void setChangePrice(Double changePrice) {
		this.changePrice = changePrice;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public Integer getOperateAid() {
		return operateAid;
	}

	public void setOperateAid(Integer operateAid) {
		this.operateAid = operateAid;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}