/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.dto;

import com.fclub.common.lang.utils.StringUtil;

/**
 * @version $Id: GoodsColorSize.java, v 0.1 Jul 15, 2013 2:00:46 PM likaiping Exp $
 */
public class GoodsColorSize {

    //商品款号
    private String        goodsSn;
    // 供应商货号(款号+批号)--仅做结果文件展示，不进行任何处理
    private String        providerSn;
    //颜色编码
    private String        colorCode;
    //尺寸编码
    private String        sizeCode;
    //商品条形码
    private String        barCode;
    //错误信息
    private StringBuilder error;

    public GoodsColorSize(String[] items) {
        if(items[0] == null){
            return;
        }
        this.goodsSn = items[0].trim();
        if (items.length > 1)
            this.providerSn = doProcessValue(items[1] != null ? items[1].trim() : "");
        if (items.length > 2)
            this.colorCode = doProcessValue(items[2] != null ? items[2].trim() : "");
        if (items.length > 3)
            this.sizeCode = doProcessValue(items[3] != null ? items[3].trim() : "");
        if (items.length > 4)
            this.barCode = doProcessValue(items[4] != null ? items[4].trim() : "");
        this.error = new StringBuilder();
    }

	public StringBuilder getError() {
        return error;
    }

    public void setError(StringBuilder error) {
        this.error = error;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProviderSn() {
        return providerSn;
    }

    public void setProviderSn(String providerSn) {
        this.providerSn = providerSn;
    }
    
    /* ---- private methods ---- */
    private static String doProcessValue(String value) {
    	if (StringUtil.isNotEmpty(value)) {
	    	try {
	            value = Integer.valueOf(value).toString();
	        } catch (NumberFormatException e) {
	            if (value.matches("^[0-9]\\d*\\.0*$")) { // 0.000
	            	value = String.valueOf(Double.valueOf(value).intValue());
	            }
	        }
    	}
		return value;
	}
    
}
