/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author auto-gene
 * @version $Id: Shipping.java, v 0.1 2013-07-03 15:58:45 auto-gene Exp $
 */
public class Shipping implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer shippingId;
  	/**  */
    private String shippingCode;
  	/**  */
    private String shippingName;
  	/**  */
    private String shippingDesc;
  	/**  */
    private Boolean isUse;
  	/** 运单跟踪用到的快递公司名称 */
    private String trackName;

    public Shipping() {
        
    }
	
	public Integer getShippingId(){
        return shippingId;
    }
    public void setShippingId(Integer shippingId){
        this.shippingId = shippingId;
    }
    public String getShippingCode(){
        return shippingCode;
    }
    public void setShippingCode(String shippingCode){
        this.shippingCode = shippingCode;
    }
    public String getShippingName(){
        return shippingName;
    }
    public void setShippingName(String shippingName){
        this.shippingName = shippingName;
    }
    public String getShippingDesc(){
        return shippingDesc;
    }
    public void setShippingDesc(String shippingDesc){
        this.shippingDesc = shippingDesc;
    }
    public Boolean getIsUse(){
        return isUse;
    }
    public void setIsUse(Boolean isUse){
        this.isUse = isUse;
    }
    public String getTrackName(){
        return trackName;
    }
    public void setTrackName(String trackName){
        this.trackName = trackName;
    }
  	
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}