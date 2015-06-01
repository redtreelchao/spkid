/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 专题商品查询对象。
 */
public class RushGoodsSearchDto implements Serializable {

    private static final long serialVersionUID = -814823061874732985L;

    private Integer           rushId;
    private Integer           providerId;
    private Integer           brandId;
    private Integer           catId;
    private String            goodsSn;
    
    public Integer getRushId() {
        return rushId;
    }
    
    public void setRushId(Integer rushId) {
        this.rushId = rushId;
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
    
    public Integer getCatId() {
        return catId;
    }
    
    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
