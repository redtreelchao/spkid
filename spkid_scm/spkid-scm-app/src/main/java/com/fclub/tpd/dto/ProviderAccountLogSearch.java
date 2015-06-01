package com.fclub.tpd.dto;

import java.io.Serializable;

public class ProviderAccountLogSearch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7892618499667072097L;

	public ProviderAccountLogSearch() {

	}

	private String dateStart;
	private String dateEnd;
	private Integer providerId;
	private Integer changeType;
	private boolean owner;
	private Integer operateStatus;

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
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

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

}
