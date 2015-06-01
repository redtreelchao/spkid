/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.dataobject.erp;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author baolm
 * @version $Id: GoodsEntity.java, v 0.1 Jul 8, 2013 11:37:22 AM baolm Exp $
 */
public class GoodsEntity {

    private String                 productSn;
    private String                 productName;
    /** 市场价 */
    private BigDecimal             marketPrice;
    /** 售价 */
    private BigDecimal             shopPrice;
    /** 促销价 */
    private BigDecimal             promotePrice;
    /** 代销价 */
    private BigDecimal             consignPrice;
    /** 成本价 */
    private BigDecimal             costPrice;
    /** 浮动代销率 */
    private BigDecimal             consignRate;
    /** 税率 */
    private BigDecimal             productCess;

    private Integer                productId;
    private Integer                sizeId;
    private Integer                colorId;
    private Integer                productNum;
    private Integer				   batchId;
    
    private List<GoodsLaborEntity> goodsLaborList;

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }

    public BigDecimal getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(BigDecimal promotePrice) {
        this.promotePrice = promotePrice;
    }

    public BigDecimal getConsignPrice() {
        return consignPrice;
    }

    public void setConsignPrice(BigDecimal consignPrice) {
        this.consignPrice = consignPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getConsignRate() {
        return consignRate;
    }

    public void setConsignRate(BigDecimal consignRate) {
        this.consignRate = consignRate;
    }

    public BigDecimal getProductCess() {
        return productCess;
    }

    public void setProductCess(BigDecimal productCess) {
        this.productCess = productCess;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }
    
    public Integer getBatchId() {
		return batchId;
	}
    
    public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

    public List<GoodsLaborEntity> getGoodsLaborList() {
        return goodsLaborList;
    }

    public void setGoodsLaborList(List<GoodsLaborEntity> goodsLaborList) {
        this.goodsLaborList = goodsLaborList;
    }

}
