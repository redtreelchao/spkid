/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @version $Id: Goods.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	//----------------自动生成 BEGIN----------------//
	/** primary key */
	private Integer goodsId;
  	/**  */
    private Integer catId;
  	/** 前台分类 */
    private Integer pagecatId;
  	/** 商品编码 */
    private String goodsSn;
  	/**  */
    private String goodsName;
  	/**  */
    private String goodsNameStyle;
  	/**  */
    private Integer clickCount;
  	/**  */
    private Integer brandId;
  	/**  */
    private String providerName;
  	/**  */
    private Integer goodsNumber;
  	/**  */
    private BigDecimal goodsWeight;
  	/**  */
    private BigDecimal marketPrice;
  	/**  */
    private BigDecimal shopPrice;
  	/**  */
    private BigDecimal promotePrice;
  	/** 代销价 */
    private BigDecimal consignPrice;
  	/** 成本价 */
    private BigDecimal costPrice;
  	/** 浮动代销率 */
    private BigDecimal consignRate;
  	/** 0为非代销1为固定代销价2为浮动代销率 */
    private Integer consignType;
  	/**  */
    private Integer promoteStartDate;
  	/**  */
    private Integer promoteEndDate;
  	/**  */
    private Integer warnNumber;
  	/**  */
    private String keywords;
  	/** 商品备注 */
    private String goodsBrief;
  	/** f-club点评 */
    private String goodsDesc;
  	/**  */
    private String goodsThumb;
  	/**  */
    private String goodsImg;
  	/**  */
    private String originalImg;
  	/**  */
    private Integer isReal;
  	/**  */
    private String extensionCode;
  	/** 0为下架,1为上架 */
    private Boolean isOnSale;
  	/**  */
    private Boolean isAloneSale;
  	/**  */
    private Integer integral;
  	/**  */
    private Integer sortOrder;
  	/** 回收站 1 */
    private Boolean isDelete;
  	/**  */
    private Boolean isBest;
  	/**  */
    private Boolean isNew;
  	/**  */
    private Boolean isHot;
  	/**  */
    private Boolean isPromote;
  	/** 断码 */
    private Integer isOffcode;
  	/**  */
    private Integer isEmpty;
  	/**  */
    private Integer bonusTypeId;
  	/**  */
    private Integer lastUpdate;
  	/**  */
    private Integer goodsType;
  	/**  */
    private String sellerNote;
  	/**  */
    private Integer giveIntegral;
  	/**  */
    private Integer rankIntegral;
  	/** 风格id */
    private Integer styleId;
  	/** 季节id */
    private Integer seasonId;
  	/** 供应商id */
    private Integer providerId;
  	/** 合作方式id */
    private Integer coopId;
  	/** 1为启用,0为停止订货 */
    private Integer goodsStop;
  	/** 供应商货号 */
    private String providerGoods;
  	/** 年 */
    private String goodsYear;
  	/** 月 */
    private String goodsMonth;
  	/** 性别(m.为男,w.为女,a.为全部) */
    private String goodsSex;
  	/** 计量单位id */
    private Integer unitId;
    private String unitName;
  	/** 是否审核(0 为未审核,1.为审核通过) */
    private Integer goodsAudit;
  	/** 面料 */
    private String goodsStuff;
  	/** 保养 */
    private String goodsMaterial;
  	/**  */
    private String goodsMaterialNew;
  	/** 审核管理员id */
    private Integer goodsAuditAid;
  	/** 审核时间 */
    private Date goodsAuditTime;
  	/** 修改时间 */
    private Date updateTime;
  	/**  */
    private Integer goodsAid;
  	/**  */
    private Date goodsTime;
  	/** 国旗 */
    private Integer areaId;
  	/**  */
    private Integer modelId;
  	/** 税率 */
    private BigDecimal goodsCess;
  	/**  */
    private Integer scId;
  	/**  */
    private String scDesc;
  	/** 尺寸图JSON内容 */
    private String scImageContent;
  	/** 商品模特图 */
    private String goodsModelimg;
  	/**  */
    private Integer isRush;
  	/** 赠品 */
    private Integer isGifts;
  	/**  */
    private String goodsDesc2;
  	/**  */
    private String goodsDescImg;
  	/** wms同步状态-1为不同步，0为未同步，1为已同步 */
    private Integer recordStatus;
  	/** 成分、尺寸规格、材质、防水性、适合人群、温馨提示信息，格式为JSON */
    private String goodsDescAdditional;
  	/** 每ID限购数量，不超过99，默认0不限制限购数量 */
    private Integer limitNum;
    private Integer limitDay;
  	/** 尺寸示意图编码 */
    private String diagramCode;
  	/** 商品状态(0-草稿,1-待审核,2-已审核) */
    private String tpdGoodsStatus;
    private Integer tpdGoodsId;
    /** 是否为虚拟商品 */
    private Boolean isVirtual;
    /** 虚拟卡生成方式:1系统生成 2手工导入 */
    private Integer generateMethod;
    
    //==========================扩增属性=============================//
    private String            url120160;
    private String            thumbUrl;
    private Integer           rgId;
    
    private List<ProductSub> goodsLabors;
    
    private List<GoodsColorVo> colorVos;
    
    private String 		  providerBarcode;
    
    private String 		  catName;
    
    private String 		  brandName;
    
    private String 		  seasonName;
    
    private String 		  providerCode;
    
    private Integer		  fcGoodsId;
    
    private String		  importStatus;
    
    private String        desc_composition;
    
    private String        desc_dimensions;
    
    private String        desc_material;
    
    private String        desc_waterproof;
    
    private String        desc_crowd;
    
    private String        desc_notes;
    
    private String		  typeCode;
    
    private String		  colorName;
    
    private String		  sizeName;
    
    private Integer		  consignNum;
    
    public Product() {
        
    }
	
    public String getUnitName() {
		return unitName;
	}
    public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getGoodsId(){
        return goodsId;
    }
    public void setGoodsId(Integer goodsId){
        this.goodsId = goodsId;
    }
    public Integer getCatId(){
        return catId;
    }
    public void setCatId(Integer catId){
        this.catId = catId;
    }
    public Integer getPagecatId(){
        return pagecatId;
    }
    public void setPagecatId(Integer pagecatId){
        this.pagecatId = pagecatId;
    }
    public String getGoodsSn(){
        return goodsSn;
    }
    public void setGoodsSn(String goodsSn){
        this.goodsSn = goodsSn;
    }
    public String getGoodsName(){
        return goodsName;
    }
    public void setGoodsName(String goodsName){
        this.goodsName = goodsName;
    }
    public String getGoodsNameStyle(){
        return goodsNameStyle;
    }
    public void setGoodsNameStyle(String goodsNameStyle){
        this.goodsNameStyle = goodsNameStyle;
    }
    public Integer getClickCount(){
        return clickCount;
    }
    public void setClickCount(Integer clickCount){
        this.clickCount = clickCount;
    }
    public Integer getBrandId(){
        return brandId;
    }
    public void setBrandId(Integer brandId){
        this.brandId = brandId;
    }
    public String getProviderName(){
        return providerName;
    }
    public void setProviderName(String providerName){
        this.providerName = providerName;
    }
    public Integer getGoodsNumber(){
        return goodsNumber;
    }
    public void setGoodsNumber(Integer goodsNumber){
        this.goodsNumber = goodsNumber;
    }
    public BigDecimal getGoodsWeight(){
        return goodsWeight;
    }
    public void setGoodsWeight(BigDecimal goodsWeight){
        this.goodsWeight = goodsWeight;
    }
    public BigDecimal getMarketPrice(){
        return marketPrice;
    }
    public void setMarketPrice(BigDecimal marketPrice){
        this.marketPrice = marketPrice;
    }
    public BigDecimal getShopPrice(){
        return shopPrice;
    }
    public void setShopPrice(BigDecimal shopPrice){
        this.shopPrice = shopPrice;
    }
    public BigDecimal getPromotePrice(){
        return promotePrice;
    }
    public void setPromotePrice(BigDecimal promotePrice){
        this.promotePrice = promotePrice;
    }
    public BigDecimal getConsignPrice(){
        return consignPrice;
    }
    public void setConsignPrice(BigDecimal consignPrice){
        this.consignPrice = consignPrice;
    }
    public BigDecimal getCostPrice(){
        return costPrice;
    }
    public void setCostPrice(BigDecimal costPrice){
        this.costPrice = costPrice;
    }
    public BigDecimal getConsignRate(){
        return consignRate;
    }
    public void setConsignRate(BigDecimal consignRate){
        this.consignRate = consignRate;
    }
    public Integer getConsignType(){
        return consignType;
    }
    public void setConsignType(Integer consignType){
        this.consignType = consignType;
    }
    public Integer getPromoteStartDate(){
        return promoteStartDate;
    }
    public void setPromoteStartDate(Integer promoteStartDate){
        this.promoteStartDate = promoteStartDate;
    }
    public Integer getPromoteEndDate(){
        return promoteEndDate;
    }
    public void setPromoteEndDate(Integer promoteEndDate){
        this.promoteEndDate = promoteEndDate;
    }
    public Integer getWarnNumber(){
        return warnNumber;
    }
    public void setWarnNumber(Integer warnNumber){
        this.warnNumber = warnNumber;
    }
    public String getKeywords(){
        return keywords;
    }
    public void setKeywords(String keywords){
        this.keywords = keywords;
    }
    public String getGoodsBrief(){
        return goodsBrief;
    }
    public void setGoodsBrief(String goodsBrief){
        this.goodsBrief = goodsBrief;
    }
    public String getGoodsDesc(){
        return goodsDesc;
    }
    public void setGoodsDesc(String goodsDesc){
        this.goodsDesc = goodsDesc;
    }
    public String getGoodsThumb(){
        return goodsThumb;
    }
    public void setGoodsThumb(String goodsThumb){
        this.goodsThumb = goodsThumb;
    }
    public String getGoodsImg(){
        return goodsImg;
    }
    public void setGoodsImg(String goodsImg){
        this.goodsImg = goodsImg;
    }
    public String getOriginalImg(){
        return originalImg;
    }
    public void setOriginalImg(String originalImg){
        this.originalImg = originalImg;
    }
    public Integer getIsReal(){
        return isReal;
    }
    public void setIsReal(Integer isReal){
        this.isReal = isReal;
    }
    public String getExtensionCode(){
        return extensionCode;
    }
    public void setExtensionCode(String extensionCode){
        this.extensionCode = extensionCode;
    }
    public Boolean getIsOnSale(){
        return isOnSale;
    }
    public void setIsOnSale(Boolean isOnSale){
        this.isOnSale = isOnSale;
    }
    public Boolean getIsAloneSale(){
        return isAloneSale;
    }
    public void setIsAloneSale(Boolean isAloneSale){
        this.isAloneSale = isAloneSale;
    }
    public Integer getIntegral(){
        return integral;
    }
    public void setIntegral(Integer integral){
        this.integral = integral;
    }
    public Integer getSortOrder(){
        return sortOrder;
    }
    public void setSortOrder(Integer sortOrder){
        this.sortOrder = sortOrder;
    }
    public Boolean getIsDelete(){
        return isDelete;
    }
    public void setIsDelete(Boolean isDelete){
        this.isDelete = isDelete;
    }
    public Boolean getIsBest(){
        return isBest;
    }
    public void setIsBest(Boolean isBest){
        this.isBest = isBest;
    }
    public Boolean getIsNew(){
        return isNew;
    }
    public void setIsNew(Boolean isNew){
        this.isNew = isNew;
    }
    public Boolean getIsHot(){
        return isHot;
    }
    public void setIsHot(Boolean isHot){
        this.isHot = isHot;
    }
    public Boolean getIsPromote(){
        return isPromote;
    }
    public void setIsPromote(Boolean isPromote){
        this.isPromote = isPromote;
    }
    public Integer getIsOffcode(){
        return isOffcode;
    }
    public void setIsOffcode(Integer isOffcode){
        this.isOffcode = isOffcode;
    }
    public Integer getIsEmpty(){
        return isEmpty;
    }
    public void setIsEmpty(Integer isEmpty){
        this.isEmpty = isEmpty;
    }
    public Integer getBonusTypeId(){
        return bonusTypeId;
    }
    public void setBonusTypeId(Integer bonusTypeId){
        this.bonusTypeId = bonusTypeId;
    }
    public Integer getLastUpdate(){
        return lastUpdate;
    }
    public void setLastUpdate(Integer lastUpdate){
        this.lastUpdate = lastUpdate;
    }
    public Integer getGoodsType(){
        return goodsType;
    }
    public void setGoodsType(Integer goodsType){
        this.goodsType = goodsType;
    }
    public String getSellerNote(){
        return sellerNote;
    }
    public void setSellerNote(String sellerNote){
        this.sellerNote = sellerNote;
    }
    public Integer getGiveIntegral(){
        return giveIntegral;
    }
    public void setGiveIntegral(Integer giveIntegral){
        this.giveIntegral = giveIntegral;
    }
    public Integer getRankIntegral(){
        return rankIntegral;
    }
    public void setRankIntegral(Integer rankIntegral){
        this.rankIntegral = rankIntegral;
    }
    public Integer getStyleId(){
        return styleId;
    }
    public void setStyleId(Integer styleId){
        this.styleId = styleId;
    }
    public Integer getSeasonId(){
        return seasonId;
    }
    public void setSeasonId(Integer seasonId){
        this.seasonId = seasonId;
    }
    public Integer getProviderId(){
        return providerId;
    }
    public void setProviderId(Integer providerId){
        this.providerId = providerId;
    }
    public Integer getCoopId(){
        return coopId;
    }
    public void setCoopId(Integer coopId){
        this.coopId = coopId;
    }
    public Integer getGoodsStop(){
        return goodsStop;
    }
    public void setGoodsStop(Integer goodsStop){
        this.goodsStop = goodsStop;
    }
    public String getProviderGoods(){
        return providerGoods;
    }
    public void setProviderGoods(String providerGoods){
        this.providerGoods = providerGoods;
    }
    public String getGoodsYear(){
        return goodsYear;
    }
    public void setGoodsYear(String goodsYear){
        this.goodsYear = goodsYear;
    }
    public String getGoodsMonth(){
        return goodsMonth;
    }
    public void setGoodsMonth(String goodsMonth){
        this.goodsMonth = goodsMonth;
    }
    public String getGoodsSex(){
        return goodsSex;
    }
    public void setGoodsSex(String goodsSex){
        this.goodsSex = goodsSex;
    }
    public Integer getUnitId(){
        return unitId;
    }
    public void setUnitId(Integer unitId){
        this.unitId = unitId;
    }
    public Integer getGoodsAudit(){
        return goodsAudit;
    }
    public void setGoodsAudit(Integer goodsAudit){
        this.goodsAudit = goodsAudit;
    }
    public String getGoodsStuff(){
        return goodsStuff;
    }
    public void setGoodsStuff(String goodsStuff){
        this.goodsStuff = goodsStuff;
    }
    public String getGoodsMaterial(){
        return goodsMaterial;
    }
    public void setGoodsMaterial(String goodsMaterial){
        this.goodsMaterial = goodsMaterial;
    }
    public String getGoodsMaterialNew(){
        return goodsMaterialNew;
    }
    public void setGoodsMaterialNew(String goodsMaterialNew){
        this.goodsMaterialNew = goodsMaterialNew;
    }
    public Integer getGoodsAuditAid(){
        return goodsAuditAid;
    }
    public void setGoodsAuditAid(Integer goodsAuditAid){
        this.goodsAuditAid = goodsAuditAid;
    }
    public Date getGoodsAuditTime(){
        return goodsAuditTime;
    }
    public void setGoodsAuditTime(Date goodsAuditTime){
        this.goodsAuditTime = goodsAuditTime;
    }
    public Date getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
    public Integer getGoodsAid(){
        return goodsAid;
    }
    public void setGoodsAid(Integer goodsAid){
        this.goodsAid = goodsAid;
    }
    public Date getGoodsTime(){
        return goodsTime;
    }
    public void setGoodsTime(Date goodsTime){
        this.goodsTime = goodsTime;
    }
    public Integer getAreaId(){
        return areaId;
    }
    public void setAreaId(Integer areaId){
        this.areaId = areaId;
    }
    public Integer getModelId(){
        return modelId;
    }
    public void setModelId(Integer modelId){
        this.modelId = modelId;
    }
    public BigDecimal getGoodsCess(){
        return goodsCess;
    }
    public void setGoodsCess(BigDecimal goodsCess){
        this.goodsCess = goodsCess;
    }
    public Integer getScId(){
        return scId;
    }
    public void setScId(Integer scId){
        this.scId = scId;
    }
    public String getScDesc(){
        return scDesc;
    }
    public void setScDesc(String scDesc){
        this.scDesc = scDesc;
    }
    public String getScImageContent(){
        return scImageContent;
    }
    public void setScImageContent(String scImageContent){
        this.scImageContent = scImageContent;
    }
    public String getGoodsModelimg(){
        return goodsModelimg;
    }
    public void setGoodsModelimg(String goodsModelimg){
        this.goodsModelimg = goodsModelimg;
    }
    public Integer getIsRush(){
        return isRush;
    }
    public void setIsRush(Integer isRush){
        this.isRush = isRush;
    }
    public Integer getIsGifts(){
        return isGifts;
    }
    public void setIsGifts(Integer isGifts){
        this.isGifts = isGifts;
    }
    public String getGoodsDesc2(){
        return goodsDesc2;
    }
    public void setGoodsDesc2(String goodsDesc2){
        this.goodsDesc2 = goodsDesc2;
    }
    public String getGoodsDescImg(){
        return goodsDescImg;
    }
    public void setGoodsDescImg(String goodsDescImg){
        this.goodsDescImg = goodsDescImg;
    }
    public Integer getRecordStatus(){
        return recordStatus;
    }
    public void setRecordStatus(Integer recordStatus){
        this.recordStatus = recordStatus;
    }
    public String getGoodsDescAdditional(){
        return goodsDescAdditional;
    }
    public void setGoodsDescAdditional(String goodsDescAdditional){
        this.goodsDescAdditional = goodsDescAdditional;
    }
    public Integer getLimitNum(){
        return limitNum;
    }
    public void setLimitNum(Integer limitNum){
        this.limitNum = limitNum;
    }
    public Integer getLimitDay() {
		return limitDay;
	}
    public void setLimitDay(Integer limitDay) {
		this.limitDay = limitDay;
	}
    public String getDiagramCode(){
        return diagramCode;
    }
    public void setDiagramCode(String diagramCode){
        this.diagramCode = diagramCode;
    }
    public String getTpdGoodsStatus(){
        return tpdGoodsStatus;
    }
    public void setTpdGoodsStatus(String tpdGoodsStatus){
        this.tpdGoodsStatus = tpdGoodsStatus;
    }
    public String getTpdGoodsStatusName() {
		if ("0".equals(tpdGoodsStatus)) {
			return "草稿";
		} else if ("1".equals(tpdGoodsStatus)) {
			return "未审核";
		} else if ("2".equals(tpdGoodsStatus)) {
			return "已审核";
		} else if ("3".equals(tpdGoodsStatus)) {
			return "已上架";
		} else {
			return "";
		}
	}
    public Integer getTpdGoodsId() {
        return tpdGoodsId;
    }
    public void setTpdGoodsId(Integer tpdGoodsId) {
        this.tpdGoodsId = tpdGoodsId;
    }
    public Boolean getIsVirtual() {
		return isVirtual;
	}
    public void setIsVirtual(Boolean isVirtual) {
		this.isVirtual = isVirtual;
	}
    public Integer getGenerateMethod() {
		return generateMethod;
	}
    public void setGenerateMethod(Integer generateMethod) {
		this.generateMethod = generateMethod;
	}
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
  	//-----------------自动生成 END-----------------//

    public String getUrl120160() {
        return url120160;
    }

    public void setUrl120160(String url120160) {
        this.url120160 = url120160;
    }
    
    public String getThumbUrl() {
        return thumbUrl;
    }
    
    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
    
    public Integer getRgId() {
        return rgId;
    }
    
    public void setRgId(Integer rgId) {
        this.rgId = rgId;
    }

	public List<ProductSub> getGoodsLabors() {
		return goodsLabors;
	}

	public void setGoodsLabors(List<ProductSub> goodsLabors) {
		this.goodsLabors = goodsLabors;
	}

	public List<GoodsColorVo> getColorVos() {
		return colorVos;
	}

	public void setColorVos(List<GoodsColorVo> colorVos) {
		this.colorVos = colorVos;
	}

	public String getProviderBarcode() {
		return providerBarcode;
	}

	public void setProviderBarcode(String providerBarcode) {
		this.providerBarcode = providerBarcode;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public Integer getFcGoodsId() {
		return fcGoodsId;
	}

	public void setFcGoodsId(Integer fcGoodsId) {
		this.fcGoodsId = fcGoodsId;
	}

	public String getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public String getDesc_composition() {
		return desc_composition;
	}

	public void setDesc_composition(String desc_composition) {
		this.desc_composition = desc_composition;
	}

	public String getDesc_dimensions() {
		return desc_dimensions;
	}

	public void setDesc_dimensions(String desc_dimensions) {
		this.desc_dimensions = desc_dimensions;
	}

	public String getDesc_material() {
		return desc_material;
	}

	public void setDesc_material(String desc_material) {
		this.desc_material = desc_material;
	}

	public String getDesc_waterproof() {
		return desc_waterproof;
	}

	public void setDesc_waterproof(String desc_waterproof) {
		this.desc_waterproof = desc_waterproof;
	}

	public String getDesc_crowd() {
		return desc_crowd;
	}

	public void setDesc_crowd(String desc_crowd) {
		this.desc_crowd = desc_crowd;
	}

	public String getDesc_notes() {
		return desc_notes;
	}

	public void setDesc_notes(String desc_notes) {
		this.desc_notes = desc_notes;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public Integer getConsignNum() {
		return consignNum;
	}

	public void setConsignNum(Integer consignNum) {
		this.consignNum = consignNum;
	}

	public static class GoodsColorVo {

        private int               colorId;
        private Integer           isPic;
        private String            colorName;
        private int               sizeCount;

        private List<GoodsSizeVo> sizeList;
        
        private List<ProductGallery> galleryList;

        public GoodsColorVo() {
        }

        public int getColorId() {
            return colorId;
        }

        public void setColorId(int colorId) {
            this.colorId = colorId;
        }

        public Integer getIsPic() {
			return isPic;
		}

		public void setIsPic(Integer isPic) {
			this.isPic = isPic;
		}

		public String getColorName() {
            return colorName;
        }

        public void setColorName(String colorName) {
            this.colorName = colorName;
        }

        public int getSizeCount() {
            return sizeCount;
        }

        public void setSizeCount(int sizeCount) {
            this.sizeCount = sizeCount;
        }

        public List<GoodsSizeVo> getSizeList() {
            return sizeList;
        }

        public void setSizeList(List<GoodsSizeVo> sizeList) {
            this.sizeList = sizeList;
            this.sizeCount = this.sizeList.size();
        }

        public List<ProductGallery> getGalleryList() {
			return galleryList;
		}

		public void setGalleryList(List<ProductGallery> galleryList) {
			this.galleryList = galleryList;
		}

		@Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
        }
    }

    public static class GoodsSizeVo {

        private Integer glId;
        private Integer glNum;
        private String  sizeCode;
        private String  sizeName;
        private String  providerBarcode;
        private boolean onSale;
        private Date    glOntime;
        private Integer consignNum;
        private boolean pic;

        public Integer getGlId() {
            return glId;
        }

        public void setGlId(Integer glId) {
            this.glId = glId;
        }

        public Integer getGlNum() {
            return glNum;
        }

        public void setGlNum(Integer glNum) {
            this.glNum = glNum;
        }

        public boolean isOnSale() {
            return onSale;
        }

        public void setOnSale(boolean onSale) {
            this.onSale = onSale;
        }

        public Integer getConsignNum() {
            return consignNum;
        }

        public void setConsignNum(Integer consignNum) {
            this.consignNum = consignNum;
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

        public String getProviderBarcode() {
            return providerBarcode;
        }

        public void setProviderBarcode(String providerBarcode) {
            this.providerBarcode = providerBarcode;
        }

        public Date getGlOntime() {
            return glOntime;
        }

        public void setGlOntime(Date glOntime) {
            this.glOntime = glOntime;
        }

        public boolean isPic() {
            return pic;
        }

        public void setPic(boolean isPic) {
            this.pic = isPic;
        }
        
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
        }
    }

}