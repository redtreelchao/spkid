package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductType implements Serializable, Comparable<ProductType> {

	/**  */
	private static final long serialVersionUID = -1852612169101317816L;

	private Integer typeId;
	private String typeName;
	private Integer parentId;
	private Integer parentId2;
	private boolean isShowCat;
	private Integer sortOrder;
	private String typeCode;
	private Integer adminId;
	private Date addTime;
	private Integer categoryId;
	/******** 新增元素 **********/
	// 一级name
	private String oneLevel;
	// 二级name
	private String twoLevel;
	/******** end 新增元素 **********/

	private Integer linkId;
	private List<ProductType> subTypes = new ArrayList<>();


	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName == null ? null : typeName.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParentId2() {
		return parentId2;
	}

	public void setParentId2(Integer parentId2) {
		this.parentId2 = parentId2;
	}

	public boolean isShowCat() {
		return isShowCat;
	}

	public void setShowCat(boolean isShowCat) {
		this.isShowCat = isShowCat;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode == null ? null : typeCode.trim();
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public List<ProductType> getSubTypes() {
		return subTypes;
	}

	public void setSubTypes(List<ProductType> subTypes) {
		this.subTypes = subTypes;
	}

	public String getOneLevel() {
		return oneLevel;
	}

	public void setOneLevel(String oneLevel) {
		this.oneLevel = oneLevel;
	}

	public String getTwoLevel() {
		return twoLevel;
	}

	public void setTwoLevel(String twoLevel) {
		this.twoLevel = twoLevel;
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductType other = (ProductType) obj;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
			return false;
		return true;
	}

	/**
	 * Sort by sortOrder ASC, typeId ASC.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ProductType goodsType) {
		if (sortOrder < goodsType.getSortOrder()) {
			return -1;
		} else if (sortOrder > goodsType.getSortOrder()) {
			return 1;
		} else {
			if (typeId < goodsType.getTypeId()) {
				return -1;
			} else if (typeId > goodsType.getTypeId()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}