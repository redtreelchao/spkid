/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject.erp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author auto-gene
 * @version $Id: Transaction.java, v 0.1 2013-07-08 13:27:49 auto-gene Exp $
 */
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    //----------------自动生成 BEGIN----------------//
    /** primary key */
    private Integer           transactionId;
    /** 事务类型 */
    private Integer           transType;
    /** 状态 */
    private Integer           transStatus;
    /** 事务单号 */
    private String            transSn;
    /** 商品款号 */
    private Integer           goodsId;
    /** 颜色id */
    private Integer           colorId;
    /** 尺寸id */
    private Integer           sizeId;
    /** 数量 */
    private Integer           goodsNumber;
    /** 仓库id */
    private Integer           depotId;
    /** 储位code */
    private Integer           packetId;
    /** 添加时间 */
    private Date              addTime;
    /** 添加管理员 */
    private Integer           addAid;
    /** 更新时间 */
    private Date              updateTime;
    /** 更新人 */
    private Integer           updateAid;
    /** 取消时间 */
    private Integer           cancelTime;
    /** 取消人 */
    private Integer           cancelAid;
    /** 0=出库 1=入库 */
    private Integer           transDirection;
    /** 子表主键 */
    private Integer           subId;
    /** 财审时间 */
    private Integer           financeCheckTime;
    /** 财审人 */
    private Integer           financeCheckAid;
    /** 关联ID */
    private Integer           relatedId;
    
    private Integer			  batchId;
    
    private BigDecimal 		  shopPrice;
    private BigDecimal 		  consignPrice;
    private BigDecimal 		  costPrice;
    private BigDecimal 		  consignRate;
    private BigDecimal 		  productCess;
    
    
    public Transaction() {

    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
    }

    public String getTransSn() {
        return transSn;
    }

    public void setTransSn(String transSn) {
        this.transSn = transSn;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    public Integer getPacketId() {
        return packetId;
    }

    public void setPacketId(Integer packetId) {
        this.packetId = packetId;
    }

    public Integer getAddAid() {
        return addAid;
    }

    public void setAddAid(Integer addAid) {
        this.addAid = addAid;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateAid() {
        return updateAid;
    }

    public void setUpdateAid(Integer updateAid) {
        this.updateAid = updateAid;
    }

    public Integer getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Integer cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Integer getCancelAid() {
        return cancelAid;
    }

    public void setCancelAid(Integer cancelAid) {
        this.cancelAid = cancelAid;
    }

    public Integer getTransDirection() {
        return transDirection;
    }

    public void setTransDirection(Integer transDirection) {
        this.transDirection = transDirection;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public Integer getFinanceCheckTime() {
        return financeCheckTime;
    }

    public void setFinanceCheckTime(Integer financeCheckTime) {
        this.financeCheckTime = financeCheckTime;
    }

    public Integer getFinanceCheckAid() {
        return financeCheckAid;
    }

    public void setFinanceCheckAid(Integer financeCheckAid) {
        this.financeCheckAid = financeCheckAid;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }
    
    public Integer getBatchId() {
		return batchId;
	}
    
    public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

    public BigDecimal getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(BigDecimal shopPrice) {
		this.shopPrice = shopPrice;
	}

	public BigDecimal getConsignPrice() {
		return consignPrice;
	}

	public void setConsignPrice(BigDecimal consignPrice) {
		this.consignPrice = consignPrice;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getConsignRate() {
		return consignRate;
	}

	public void setConsignRate(BigDecimal consignRate) {
		this.consignRate = consignRate;
	}

	public BigDecimal getProductCess() {
		return productCess;
	}

	public void setProductCess(BigDecimal productCess) {
		this.productCess = productCess;
	}

	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}