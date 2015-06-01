/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.enums;

import com.fclub.common.lang.utils.StringUtil;

/**
 * 导入状态
 * NONE-未知状态
 * RUNING-执行中
 * FAIL-执行失败
 * WARN-执行有异常
 * ERROR-执行发生错误
 * SUCCESS-成功
 * FINISH-完成
 * @author likaiping
 * @version $Id: ImportStatus.java, v 0.1 Oct 25, 2012 3:40:28 PM likaiping Exp $
 */
public enum ImportStatus {
    /** NONE-未知状态 */
    NONE("00", "未知状态"),
    /** INIT-初始化 */
    INIT("01", "初始化"),
    /** RUNING-执行中 */
    RUNING("02", "执行中"),
    /** FAIL-执行失败 */
    FAIL("03", "执行失败"),
    /** WARN-执行有异常 */
    WARN("04", "执行有异常"),
    /** ERROR-执行发生错误 */
    ERROR("05", "执行发生错误"),
    /** SUCCESS-成功 */
    SUCCESS("06", "成功"),
    /** FINISH-完成 */
    FINISH("07", "完成");

    private String code;
    private String desc;
    
    public static ImportStatus valueOfCode(String code){
        for(ImportStatus  status : values()){
            if(StringUtil.equals(code, status.getCode())){
                return status;
            }
        }
        return null;
    }
    /**
     * @param desc
     */
    private ImportStatus(String code, String desc) {
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
