/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @version $Id: GoodsLabor.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public class ProductSub implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer glId;
  	/** 商品ID */
    private Integer goodsId;
  	/** 颜色ID */
    private Integer colorId;
  	/** 尺寸ID */
    private Integer sizeId;
  	/** 是否拍摄 */
    private Integer isPic;
  	/** 代销库存:-2--不限量代销;-1:不代销;>=0限量代销 */
    private Integer consignNum;
  	/** 大的在前面 */
    private Integer sortOrder;
  	/** 供应商条码(聚尚条码) */
    private String providerBarcode;
  	/** 供应商条码 */
    private String tpdProviderBarcode;
    
    //==========================扩增属性=============================//
    private String sizeCode;
    
    private String sizeName;
    
    private String colorName;
    
    private List<ProductGallery>  goodsGallerys;
    
    // ----
    private Integer tpdGoodsId;
    private Integer tpdGlId;
    

    public ProductSub() {
        
    }
	
	public Integer getGlId(){
        return glId;
    }
    public void setGlId(Integer glId){
        this.glId = glId;
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
    public Integer getSizeId(){
        return sizeId;
    }
    public void setSizeId(Integer sizeId){
        this.sizeId = sizeId;
    }
    public Integer getIsPic(){
        return isPic;
    }
    public void setIsPic(Integer isPic){
        this.isPic = isPic;
    }
    public Integer getConsignNum(){
        return consignNum;
    }
    public void setConsignNum(Integer consignNum){
        this.consignNum = consignNum;
    }
    public Integer getSortOrder(){
        return sortOrder;
    }
    public void setSortOrder(Integer sortOrder){
        this.sortOrder = sortOrder;
    }
    public String getProviderBarcode(){
        return providerBarcode;
    }
    public void setProviderBarcode(String providerBarcode){
        this.providerBarcode = providerBarcode;
    }
    public String getTpdProviderBarcode(){
        return tpdProviderBarcode;
    }
    public void setTpdProviderBarcode(String tpdProviderBarcode){
        this.tpdProviderBarcode = tpdProviderBarcode;
    }
  	
    public String getSizeCode() {
		return sizeCode;
	}

	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public List<ProductGallery> getGoodsGallerys() {
		return goodsGallerys;
	}

	public void setGoodsGallerys(List<ProductGallery> goodsGallerys) {
		this.goodsGallerys = goodsGallerys;
	}
	
	public Integer getTpdGoodsId() {
		return tpdGoodsId;
	}
	
	public void setTpdGoodsId(Integer tpdGoodsId) {
		this.tpdGoodsId = tpdGoodsId;
	}

	public Integer getTpdGlId() {
		return tpdGlId;
	}
	
	public void setTpdGlId(Integer tpdGlId) {
		this.tpdGlId = tpdGlId;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
	
}