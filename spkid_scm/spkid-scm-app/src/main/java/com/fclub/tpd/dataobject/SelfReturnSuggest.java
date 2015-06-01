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
 * @version $Id: SelfReturnSuggest.java, v 0.1 2013-07-15 17:21:24 auto-gene Exp $
 */
public class SelfReturnSuggest implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer recId;
  	/**  */
    private Integer applyId;
  	/** 意见类型 0:客服意见 1:供应商
正常意见 2:供应商非正常意见 3:其他意见 */
    private Integer suggestType;
  	/** 意见内容 */
    private String suggestContent;
  	/** 创建人id */
    private Integer createId;
  	/** 创建日期 */
    private Date createDate;
    
    private String createName;

    public SelfReturnSuggest() {
        
    }
	
	public Integer getRecId(){
        return recId;
    }
    public void setRecId(Integer recId){
        this.recId = recId;
    }
    public Integer getApplyId(){
        return applyId;
    }
    public void setApplyId(Integer applyId){
        this.applyId = applyId;
    }
    public Integer getSuggestType(){
        return suggestType;
    }
    public void setSuggestType(Integer suggestType){
        this.suggestType = suggestType;
    }
    public String getSuggestContent(){
        return suggestContent;
    }
    public void setSuggestContent(String suggestContent){
        this.suggestContent = suggestContent;
    }
    public Integer getCreateId(){
        return createId;
    }
    public void setCreateId(Integer createId){
        this.createId = createId;
    }
    public Date getCreateDate(){
        return createDate;
    }
    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }
  	
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
  	//-----------------自动生成 END-----------------//

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}