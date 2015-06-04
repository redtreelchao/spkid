/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.dto;


/**
 * 
 * @author likaiping
 * @version $Id: GoodsData.java, v 0.1 Jul 15, 2013 2:19:22 PM likaiping Exp $
 */
public class GoodsData {

    // 商品Id
    private Integer goodsId;
    // 商品款号
    private String  goodsSn;
    // 供应商货号（款号+批号）
    private String  providerGoods;
    //颜色编码
    private String  colorCode;
    //尺寸编码
    private String  sizeCode;
    //供应商条码
    private String  barCode;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getProviderGoods() {
        return providerGoods;
    }

    public void setProviderGoods(String providerGoods) {
        this.providerGoods = providerGoods;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
