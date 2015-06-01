/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.vo;

import java.io.Serializable;

/**
 * 
 * @author likaiping
 * @version $Id: JsonResult.java, v 0.1 Jul 8, 2013 1:51:16 PM likaiping Exp $
 */
public class JsonResult  implements Serializable {

    /**  */
    private static final long serialVersionUID = 8089863432540751027L;

    private Object            result;

    private String            resultCode;

    private String            msg;

    private boolean           success          = true;

    /**
     * 
     */
    public JsonResult() {
        super();
    }

    /**
     * @param result
     * @param resultCode
     */
    public JsonResult(Object result, String resultCode) {
        super();
        this.result = result;
        this.resultCode = resultCode;
        this.success = true;
    }

    /**
     * @param result
     * @param resultCode
     * @param msg
     * @param success
     */
    public JsonResult(Object result, String resultCode, String msg, boolean success) {
        super();
        this.result = result;
        this.resultCode = resultCode;
        this.msg = msg;
        this.success = success;
    }

    /**
     * Getter method for property <tt>result</tt>.
     * 
     * @return property value of result
     */
    public Object getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     * 
     * @param result value to be assigned to property result
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     * 
     * @param result value to be assigned to property result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>resultCode</tt>.
     * 
     * @return property value of resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Setter method for property <tt>resultCode</tt>.
     * 
     * @param resultCode value to be assigned to property resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     * 
     * @return property value of msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setter method for property <tt>msg</tt>.
     * 
     * @param msg value to be assigned to property msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Getter method for property <tt>success</tt>.
     * 
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     * 
     * @param success value to be assigned to property success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

}
