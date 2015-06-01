/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author auto-gene
 * @version $Id: Log.java, v 0.1 2013-06-28 15:30:48 auto-gene Exp $
 */
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer logId;
  	/** 操作模块(01-活动,02-商品) */
    private String logType;
  	/** 操作类型(11-初审成功,12-初审失败,13-确认排期,14-排期驳回,15-驳回,16-作废,21-审核成功,22-审核失败) */
    private String logCode;
  	/** 操作描述 */
    private String logDesc;
  	/**  */
    private Integer createUser;
  	/**  */
    private Date createTime;

    public Log() {
        
    }
	
	public Integer getLogId(){
        return logId;
    }
    public void setLogId(Integer logId){
        this.logId = logId;
    }
    public String getLogType(){
        return logType;
    }
    public void setLogType(String logType){
        this.logType = logType;
    }
    public String getLogCode(){
        return logCode;
    }
    public void setLogCode(String logCode){
        this.logCode = logCode;
    }
    public String getLogDesc(){
        return logDesc;
    }
    public void setLogDesc(String logDesc){
        this.logDesc = logDesc;
    }
    public Integer getCreateUser(){
        return createUser;
    }
    public void setCreateUser(Integer createUser){
        this.createUser = createUser;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
  	
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
  	//-----------------自动生成 END-----------------//
}