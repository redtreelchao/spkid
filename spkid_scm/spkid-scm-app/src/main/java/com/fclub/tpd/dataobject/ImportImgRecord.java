package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class ImportImgRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /** primary key */
    private Integer           irId;
    /** 商品ID */
    private Integer           goodsId;
    /** 商品款号 */
    private String            goodsSn;
    /** 颜色ID */
    private Integer           colorId;
    /** 颜色code */
    private String            colorCode;
    /** 操作人ID */
    private Integer           irAid;
    /** 添加时间 */
    private Date              irTime;

    /** 商品信息 */
    private Product             goods;
    /** 分类信息 */
    private Category          cat;
    /** 品牌信息 */
    private Brand             brand;
    /** 颜色信息 */
    private Color             color;
    /** 供应商信息 */
    private Provider          provider;

    public ImportImgRecord() {

    }

    public Integer getIrId() {
        return irId;
    }

    public void setIrId(Integer irId) {
        this.irId = irId;
    }

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

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Integer getIrAid() {
        return irAid;
    }

    public void setIrAid(Integer irAid) {
        this.irAid = irAid;
    }

    public Date getIrTime() {
        return irTime;
    }

    public void setIrTime(Date irTime) {
        this.irTime = irTime;
    }

    public Product getGoods() {
        return goods;
    }

    public void setGoods(Product goods) {
        this.goods = goods;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}