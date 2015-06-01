package com.fclub.tpd.dataobject;

import java.io.Serializable;

public class ProductTypeLink implements Serializable {
    
    /**  */
    private static final long serialVersionUID = 288022899187216723L;
    
    private Integer id;
    private Product goods;
    private ProductType goodsType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getGoods() {
        return goods;
    }

    public void setGoods(Product goods) {
        this.goods = goods;
    }

    public ProductType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(ProductType goodsType) {
        this.goodsType = goodsType;
    }

}