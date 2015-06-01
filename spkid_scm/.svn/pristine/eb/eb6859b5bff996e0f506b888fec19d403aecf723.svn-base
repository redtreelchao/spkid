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
 * @version $Id: WorkOrder.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public class WorkOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer woId;
  	/** 工单号 */
    private String woNo;
  	/** 工单类型(01-我方发起,02-第三方发起) */
    private String woType;
  	/** 供应商 */
    private Integer providerId;
  	/** 工单内容 */
    private String content;
  	/** 工单状态(0-草稿,1-待处理,2-已处理) */
    private String woStatus;
  	/** 工单附件 */
    private String woFile;
  	/** 回复人 */
    private Integer replyUser;
  	/** 回复意见 */
    private String replyOption;
  	/** 回复时间 */
    private Date replyTime;
  	/** 回复附件 */
    private String replyFile;
  	/**  */
    private Integer createUser;
  	/**  */
    private Date createTime;
  	/**  */
    private Integer updateUser;
  	/**  */
    private Date updateTime;
    /** 查询开始时间 */
    private Date startTime;
    /** 查询结束时间 */
    private Date endTime;
    /** 订单号 */
    private String orderSn;
    
    public WorkOrder() {
        
    }
    
	public Integer getWoId(){
        return woId;
    }
    public void setWoId(Integer woId){
        this.woId = woId;
    }
    public String getWoNo(){
        return woNo;
    }
    public void setWoNo(String woNo){
        this.woNo = woNo == null ? null : woNo.trim();
    }
    public String getWoType(){
        return woType;
    }
    public void setWoType(String woType){
        this.woType = woType;
    }
    public Integer getProviderId(){
        return providerId;
    }
    public void setProviderId(Integer providerId){
        this.providerId = providerId;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getWoStatus(){
        return woStatus;
    }
    public void setWoStatus(String woStatus){
        this.woStatus = woStatus;
    }
    public String getWoFile(){
        return woFile;
    }
    public void setWoFile(String woFile){
        this.woFile = woFile;
    }
    public Integer getReplyUser(){
        return replyUser;
    }
    public void setReplyUser(Integer replyUser){
        this.replyUser = replyUser;
    }
    public String getReplyOption(){
        return replyOption;
    }
    public void setReplyOption(String replyOption){
        this.replyOption = replyOption;
    }
    public Date getReplyTime(){
        return replyTime;
    }
    public void setReplyTime(Date replyTime){
        this.replyTime = replyTime;
    }
    public String getReplyFile(){
        return replyFile;
    }
    public void setReplyFile(String replyFile){
        this.replyFile = replyFile;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn == null ? null : orderSn.trim();
	}
}