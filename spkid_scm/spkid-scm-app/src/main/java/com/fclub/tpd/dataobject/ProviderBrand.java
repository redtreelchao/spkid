package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品供应商品牌关联表
 * @author likaiping
 * @version $Id: ProviderBrand.java, v 0.1 Sep 10, 2012 5:21:23 PM likaiping Exp $
 */
public class ProviderBrand implements Serializable {
    private Integer           id;

    private Integer           brandId;

    private Integer           providerId;

    private String            goodsCommissioner;

    private Date              gmCreate;

    private Date              gmModified;
    /** 品牌 */
    private Brand             brand;

    /** 供应商 */
    private Provider          provider;
    
    private String 			  commission;
    private String			  commissionHistory;
    

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getGoodsCommissioner() {
        return goodsCommissioner;
    }

    public void setGoodsCommissioner(String goodsCommissioner) {
        this.goodsCommissioner = goodsCommissioner == null ? null : goodsCommissioner.trim();
    }

    public Date getGmCreate() {
        return gmCreate;
    }

    public void setGmCreate(Date gmCreate) {
        this.gmCreate = gmCreate;
    }

    public Date getGmModified() {
        return gmModified;
    }

    public void setGmModified(Date gmModified) {
        this.gmModified = gmModified;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}
	
	public String getCommissionHistory() {
		return commissionHistory;
	}
	
	public void setCommissionHistory(String commissionHistory) {
		this.commissionHistory = commissionHistory;
	}

}