/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.enums;

import com.fclub.common.lang.utils.StringUtil;

/**
 * 导入类型
 * @version $Id: ImportType.java, v 0.1 Oct 25, 2012 3:39:45 PM likaiping Exp $
 */
public enum ImportType {
    /** GOODS_MAIN_INFORMATION：01-商品主要信息 */
    GOODS_MAIN_INFORMATION("01", "商品主要信息"),

    /** GOODS_COLOR_SIZE：02-商品颜色尺寸 */
    GOODS_COLOR_SIZE("02", "商品颜色尺寸"),

    /** GOODS_VERIFY：03-统一审核 */
    GOODS_VERIFY("03", "统一审核"),

    /** GOODS_SUB_INFORMATION：04-商品次要信息 */
    GOODS_SUB_INFORMATION("04", "商品次要信息"),

    /** PURCHASE_ORDER：05-采购单 */
    PURCHASE_ORDER("05", "采购单"),

    /** GOODS_GALLERY：06-商品图片 */
    GOODS_GALLERY("06", "商品图片"),

    /** GOODS_BCS_IMAGE：07-尺寸对照图 */
    GOODS_BCS_IMAGE("07", "尺寸对照图"),
    
    /** GOODS_CONSIGN：08-商品虚库库存 */
    GOODS_CONSIGN("08", "商品虚库库存");

    private String code;
    private String desc;

    public static ImportType valueOfCode(String code){
        for(ImportType  type : values()){
            if(StringUtil.equals(code, type.getCode())){
                return type;
            }
        }
        return null;
    }
    /**
     * @param desc
     */
    private ImportType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
