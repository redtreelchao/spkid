/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author auto-gene
 * @version $Id: SelfReturn.java, v 0.1 2013-07-12 16:18:00 auto-gene Exp $
 */
public class SelfReturnProduct implements Serializable {

    private static final long serialVersionUID = -6660448115775555977L;

    /** primary key */
    private Integer           recId;
    /** 申请退货id */
    private Integer           applyId;
    /**  */
    private Integer           productId;
    /**  */
    private Integer           colorId;
    /**  */
    private Integer           sizeId;
    /**  */
    private BigDecimal        productPrice;
    /**  */
    private String            productSn;
    /**  */
    private String            productName;
    /**  */
    private Integer           productNumber;
    /** 退货理由0:尺寸偏大 1:尺寸偏小 2:款式不喜欢 3:配送错误 4:其他 5:商品质量问题 */
    private Integer           returnReason;
    /** 问题描述 */
    private String            desc;
    /**  */
    private String            img;

    //...
    private String            colorName;
    private String            sizeName;
    private String            providerProductcode;

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

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

    public String getProviderProductcode() {
        return providerProductcode;
    }

    public void setProviderProductcode(String providerProductcode) {
        this.providerProductcode = providerProductcode;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(Integer returnReason) {
        this.returnReason = returnReason;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}