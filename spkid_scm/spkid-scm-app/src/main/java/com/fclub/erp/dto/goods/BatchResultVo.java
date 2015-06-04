/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.erp.dto.goods;

import java.io.Serializable;

/**
 * 
 * @author likaiping
 * @version $Id: BatchResultVo.java, v 0.1 Jul 17, 2013 2:58:22 PM likaiping Exp $
 */
public class BatchResultVo  implements Serializable {

    /**  */
    private static final long serialVersionUID = -497140436027853258L;

    private String            goodsSn;
    private String            colorCode;
    private String            sizeCode;
    private String            message;
    private String            rep;
    //扩展展示字段-直接映射文件列
    String                    providerCode;
    String                    providerGoods;
    String                    colorName;
    String                    sizeName;
    int                       count;
    //fixed 导入和导出条码时顺序不一致问题
    Integer                       goodsId;

    public BatchResultVo() {

    }

    public BatchResultVo(String goodsSn, String colorCode) {
        this.goodsSn = goodsSn;
        this.colorCode = colorCode;
    }

    public BatchResultVo(String goodsSn, String colorCode, String sizeCode) {
        this.goodsSn = goodsSn;
        this.colorCode = colorCode;
        this.sizeCode = sizeCode;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderGoods() {
        return providerGoods;
    }

    public void setProviderGoods(String providerGoods) {
        this.providerGoods = providerGoods;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
