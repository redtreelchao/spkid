/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.dataobject.erp;

/**
 * 
 * @author baolm
 * @version $Id: GoodsLaborEntity.java, v 0.1 Jul 8, 2013 11:41:51 AM baolm Exp $
 */
public class GoodsLaborEntity {

    private Integer goodsId;
    private Integer sizeId;
    private Integer colorId;
    private Integer goodsNumber;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

}
