package com.fclub.tpd.dto;

import java.io.Serializable;
import java.util.Set;

public class TypeLinkQueryDTO implements Serializable {

    /**  */
    private static final long serialVersionUID = 1293998908793847450L;

    private Integer           catId;
    private Set<Integer>     catIds;
    private Integer           goodsTypeId;
    private Integer           brandId;
    private String            goodsSex;
    private String            goodsSn;
    private String            goodsName;
    private String            isLinked;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getGoodsSex() {
        return goodsSex;
    }

    public void setGoodsSex(String goodsSex) {
        this.goodsSex = goodsSex;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getIsLinked() {
        return isLinked;
    }

    public void setIsLinked(String isLinked) {
        this.isLinked = isLinked;
    }

    public Set<Integer> getCatIds() {
        return catIds;
    }

    public void setCatIds(Set<Integer> catIds) {
        this.catIds = catIds;
    }

}
