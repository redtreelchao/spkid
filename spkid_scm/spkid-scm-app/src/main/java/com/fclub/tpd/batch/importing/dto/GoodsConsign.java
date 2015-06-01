package com.fclub.tpd.batch.importing.dto;

public class GoodsConsign {
	
	private String  goodsSn;
	private String  colorName;
	private String  sizeName;
	private Integer consignNum;
	
	private StringBuilder error;
	
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
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
	public Integer getConsignNum() {
		return consignNum;
	}
	public void setConsignNum(Integer consignNum) {
		this.consignNum = consignNum;
	}
	
	public synchronized StringBuilder getError() {
		if (error == null) {
			error = new StringBuilder();
		}
		return error;
	}
	public void setError(StringBuilder error) {
		this.error = error;
	}
	
}
