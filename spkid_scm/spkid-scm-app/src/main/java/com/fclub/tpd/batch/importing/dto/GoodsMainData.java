/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.dto;

import java.math.BigDecimal;

import com.fclub.common.lang.utils.StringUtil;

/**
 * 商品主要信息导入映射文件
 * @version $Id: GoodsMainData.java, v 0.1 Jul 1, 2013 10:54:53 AM likaiping Exp $
 */
public class GoodsMainData extends GoodsDescDTO {

    // 商品款号
    private String       goodsSn;
    // 商品类别编码
    private String       categoryCode;
    // 商品名称
    private String       goodsName;
    // 品牌ID
    private int          brandId;
    // 季节编码
    private String       seasonCode;
    // 供应商编码
    private String       providerCode;
    // 合作方式编码
    private String       cooperationCode;
    // 供应商货号(款号+批号)
    private String       providerSn;
    // 商品年份
    private String       year;
    // 商品月份
    private String       month;
    // 国家编码
    private String       countryCode;
    // 商品税率
    private BigDecimal   taxRate;
    // 抢购商品
    private String       rushGoods;
    // 市场价
    private String       marketPrice;
    // fclub价
    private String       fclubPrice;
    // 代销价
    private String       consignPrice;
    // 成本价 - 默认 0
    private String       costPrice = "0.00";
    // 风格编码 -默认”时尚“
    private String       styleCode = "50";
    // 风格Id
    private Integer      styleId   = 50;
    // 计量单位Id
    private Integer      unitId    = 1;
    // 计量单位编码 - 默认“件”
    private String       unitCode  = "1";
    // 性别
    private String       sex;
    // 前台分类编码
    private String       foreBackCode;
    // 新商品保养编码（|线分割）
    private String       newGoodsCode;
    // 新商品保养ID
    private String       newGoodsId;
    // 三级分类编码
    private String       typeCode;
    // 错误信息
    private StringBuffer error     = new StringBuffer();

    //~==================== 中转数据 ===================//
    int                  categoryId;
    Integer              seasonId  = 8;                 //全季
    Integer              areaId;
    // 商品描述
    private String       goodsDesc;
    String[]             goodsArr;
    
    Integer				 typeId;


    public GoodsMainData(String[] items) {
    	this.brandId = getIntegerValueFromArray(items, 0);
    	this.providerSn = getStringValueFromArray(items, 1);
        this.goodsName = getStringValueFromArray(items, 2);
        this.marketPrice = getStringValueFromArray(items, 3);
        this.fclubPrice = getStringValueFromArray(items, 4);
        this.sex = getStringValueFromArray(items, 5);
        this.typeCode = getStringValueFromArray(items, 6);
        this.unitCode = getStringValueFromArray(items, 7);
        this.desc_composition = getStringValueFromArray(items, 8);
        this.desc_dimensions = getStringValueFromArray(items, 9);
        this.desc_material = getStringValueFromArray(items, 10);
        this.desc_waterproof = getStringValueFromArray(items, 11);
        this.desc_crowd = getStringValueFromArray(items, 12);
        this.desc_notes = getStringValueFromArray(items, 13);
        
        this.styleId = Integer.valueOf(1); // 时尚
        this.seasonId = Integer.valueOf(1); // 全季
    }

    private Integer getIntegerValueFromArray(String[] items, int index) {
    	Integer field = 0;
    	if (items.length > index) {
			String itemValue = items[index];
			if (itemValue != null && !itemValue.isEmpty()) {
				if (itemValue.matches("^[0-9]\\d*\\.0*$")) {
					field = Double.valueOf(itemValue).intValue();
				} else {
					field = Integer.parseInt(items[index]);
				}
			}
		}
    	
    	return field;
	}

	private String getStringValueFromArray(String[] items, int index) {
		String field = "";
		if (items.length > index) {
			field = StringUtil.trim(items[index]);
		}
		
		return field;
	}

	public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getCooperationCode() {
        return cooperationCode;
    }

    public void setCooperationCode(String cooperationCode) {
        this.cooperationCode = cooperationCode;
    }

    public String getProviderSn() {
        return providerSn;
    }

    public void setProviderSn(String providerSn) {
        this.providerSn = providerSn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getRushGoods() {
        return rushGoods;
    }

    public void setRushGoods(String rushGoods) {
        this.rushGoods = rushGoods;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getFclubPrice() {
        return fclubPrice;
    }

    public void setFclubPrice(String fclubPrice) {
        this.fclubPrice = fclubPrice;
    }

    public String getConsignPrice() {
        return consignPrice;
    }

    public void setConsignPrice(String consignPrice) {
        this.consignPrice = consignPrice;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getForeBackCode() {
        return foreBackCode;
    }

    public void setForeBackCode(String foreBackCode) {
        this.foreBackCode = foreBackCode;
    }

    public String getNewGoodsCode() {
        return newGoodsCode;
    }

    public void setNewGoodsCode(String newGoodsCode) {
        this.newGoodsCode = newGoodsCode;
    }

    public String getNewGoodsId() {
        return newGoodsId;
    }

    public void setNewGoodsId(String newGoodsId) {
        this.newGoodsId = newGoodsId;
    }

    public StringBuffer getError() {
        return error;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Integer getTypeId() {
		return typeId;
	}
    
    public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

}
