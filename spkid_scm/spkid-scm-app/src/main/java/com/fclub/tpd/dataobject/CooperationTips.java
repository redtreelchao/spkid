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
 * @version $Id: CooperationTips.java, v 0.1 2013-06-28 15:30:48 auto-gene Exp $
 */
public class CooperationTips implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer ctId;
  	/** 合作须知 */
    private String content;
  	/**  */
    private Integer createUser;
  	/**  */
    private Date createTime;
  	/**  */
    private Integer updateUser;
  	/**  */
    private Date updateTime;

    public CooperationTips() {
        
    }
	
	public Integer getCtId(){
        return ctId;
    }
    public void setCtId(Integer ctId){
        this.ctId = ctId;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
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
    public Integer getUpdateUser(){
        return updateUser;
    }
    public void setUpdateUser(Integer updateUser){
        this.updateUser = updateUser;
    }
    public Date getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
  	
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
  	//-----------------自动生成 END-----------------//
}