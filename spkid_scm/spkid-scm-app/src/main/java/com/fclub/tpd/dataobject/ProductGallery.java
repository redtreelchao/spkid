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
 * @version $Id: GoodsGallery.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public class ProductGallery implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer imgId;
  	/**  */
    private Integer goodsId;
  	/** 颜色id */
    private Integer colorId;
  	/** 原图相同-用来使用的(可能加水印) */
    private String imgUrl;
  	/**  */
    private String imgDesc;
  	/** 缩略图(72*96) */
    private String thumbUrl;
  	/** 600*800 图 */
    private String middleUrl;
  	/** 1200*1600 图 */
    private String bigUrl;
  	/** 30*40 图 */
    private String teenyUrl;
  	/** 180*240 图 */
    private String smallUrl;
  	/** 原始图片 */
    private String imgOriginal;
  	/** default 默认,part 局部,tonal 色片 */
    private String imgDefault;
  	/**  */
    private Integer imgAid;
  	/**  */
    private Date imgTime;
  	/** 102*160 */
    private String url120160;
  	/** 99*132 */
    private String url99132;
  	/** 480*640 */
    private String url480640;
  	/** 63*84 */
    private String url5684;
  	/**  */
    private String url222296;
  	/**  */
    private Integer sortOrder;
  	/** 342*455 */
    private String url342455;
  	/** 170*227 */
    private String url170227;
  	/** iphone3终端商品列表图 */
    private String url135180;
  	/** iphone3终端商品详情大图 */
    private String url251323;
  	/** ipad终端商品详情大图 */
    private String url502646;
  	/** 1200*1600 详情放大镜 */
    private String url12001600;
    
    // -------------------------
    private String goodsSn;
    private String providerGoods;
    private String colorName;
    
    public ProductGallery() {
        
    }
    
    // ---- extends ----------------
    public String getUrl8585() {
    	return genImgUrl(new int[]{85, 85});
    }
    
    private String genImgUrl(int[] size) {
    	if (imgUrl == null || imgUrl.isEmpty()) {
    		return null;
    	} else {
    		String suffix = imgUrl.substring(imgUrl.lastIndexOf("."));
    		return imgUrl + "." + size[0] + "x" + size[1] + suffix;
    	}
    }
	
	public Integer getImgId(){
        return imgId;
    }
    public void setImgId(Integer imgId){
        this.imgId = imgId;
    }
    public Integer getGoodsId(){
        return goodsId;
    }
    public void setGoodsId(Integer goodsId){
        this.goodsId = goodsId;
    }
    public Integer getColorId(){
        return colorId;
    }
    public void setColorId(Integer colorId){
        this.colorId = colorId;
    }
    public String getImgUrl(){
        return imgUrl;
    }
    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }
    public String getImgDesc(){
        return imgDesc;
    }
    public void setImgDesc(String imgDesc){
        this.imgDesc = imgDesc;
    }
    public String getThumbUrl(){
        return thumbUrl;
    }
    public void setThumbUrl(String thumbUrl){
        this.thumbUrl = thumbUrl;
    }
    public String getMiddleUrl(){
        return middleUrl;
    }
    public void setMiddleUrl(String middleUrl){
        this.middleUrl = middleUrl;
    }
    public String getBigUrl(){
        return bigUrl;
    }
    public void setBigUrl(String bigUrl){
        this.bigUrl = bigUrl;
    }
    public String getTeenyUrl(){
        return teenyUrl;
    }
    public void setTeenyUrl(String teenyUrl){
        this.teenyUrl = teenyUrl;
    }
    public String getSmallUrl(){
        return smallUrl;
    }
    public void setSmallUrl(String smallUrl){
        this.smallUrl = smallUrl;
    }
    public String getImgOriginal(){
        return imgOriginal;
    }
    public void setImgOriginal(String imgOriginal){
        this.imgOriginal = imgOriginal;
    }
    public String getImgDefault(){
        return imgDefault;
    }
    public void setImgDefault(String imgDefault){
        this.imgDefault = imgDefault;
    }
    public Integer getImgAid(){
        return imgAid;
    }
    public void setImgAid(Integer imgAid){
        this.imgAid = imgAid;
    }
    public Date getImgTime(){
        return imgTime;
    }
    public void setImgTime(Date imgTime){
        this.imgTime = imgTime;
    }
    public String getUrl120160(){
        return url120160;
    }
    public void setUrl120160(String url120160){
        this.url120160 = url120160;
    }
    public String getUrl99132(){
        return url99132;
    }
    public void setUrl99132(String url99132){
        this.url99132 = url99132;
    }
    public String getUrl480640(){
        return url480640;
    }
    public void setUrl480640(String url480640){
        this.url480640 = url480640;
    }
    public String getUrl5684(){
        return url5684;
    }
    public void setUrl5684(String url5684){
        this.url5684 = url5684;
    }
    public String getUrl222296(){
        return url222296;
    }
    public void setUrl222296(String url222296){
        this.url222296 = url222296;
    }
    public Integer getSortOrder(){
        return sortOrder;
    }
    public void setSortOrder(Integer sortOrder){
        this.sortOrder = sortOrder;
    }
    public String getUrl342455(){
        return url342455;
    }
    public void setUrl342455(String url342455){
        this.url342455 = url342455;
    }
    public String getUrl170227(){
        return url170227;
    }
    public void setUrl170227(String url170227){
        this.url170227 = url170227;
    }
    public String getUrl135180(){
        return url135180;
    }
    public void setUrl135180(String url135180){
        this.url135180 = url135180;
    }
    public String getUrl251323(){
        return url251323;
    }
    public void setUrl251323(String url251323){
        this.url251323 = url251323;
    }
    public String getUrl502646(){
        return url502646;
    }
    public void setUrl502646(String url502646){
        this.url502646 = url502646;
    }
    public String getUrl12001600(){
        return url12001600;
    }
    public void setUrl12001600(String url12001600){
        this.url12001600 = url12001600;
    }
    
    public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getProviderGoods() {
		return providerGoods;
	}

	public void setProviderGoods(String providerGoods) {
		this.providerGoods = providerGoods;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
  	//-----------------自动生成 END-----------------//
}