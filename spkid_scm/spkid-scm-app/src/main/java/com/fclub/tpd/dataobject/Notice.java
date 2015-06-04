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
 * @version $Id: Notice.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public class Notice implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer noticeId;
  	/** 公告标题 */
    private String noticeTitle;
  	/** 公告内容 */
    private String content;
  	/** 审核人 */
    private Integer auditUser;
  	/** 审核时间 */
    private Date auditTime;
  	/**  */
    private Integer createUser;
  	/**  */
    private String createTime;
  	/**  */
    private Integer updateUser;
  	/**  */
    private Date updateTime;
    
    public Notice() {
        
    }
	
	public Integer getNoticeId(){
        return noticeId;
    }
    public void setNoticeId(Integer noticeId){
        this.noticeId = noticeId;
    }
    public String getNoticeTitle(){
        return noticeTitle;
    }
    public void setNoticeTitle(String noticeTitle){
    	this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public Integer getAuditUser(){
        return auditUser;
    }
    public void setAuditUser(Integer auditUser){
        this.auditUser = auditUser;
    }
    public Date getAuditTime(){
        return auditTime;
    }
    public void setAuditTime(Date auditTime){
        this.auditTime = auditTime;
    }
    public Integer getCreateUser(){
        return createUser;
    }
    public void setCreateUser(Integer createUser){
        this.createUser = createUser;
    }
    public String getCreateTime(){
        return createTime;
    }
    public void setCreateTime(String createTime){
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