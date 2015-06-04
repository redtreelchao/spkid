package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.fclub.common.lang.utils.StringUtil;

public class ProductCard implements Serializable {

	private static final long serialVersionUID = 8753916410067130658L;
	
	private Integer 	cardId;
	private String		cardNo;
	private String		cardPwd;
	private Boolean		isUsed;
	private Date		createTime;
	private Date		orderTime;
	private Date		useTime;
	
	private Integer		subId;
	private Integer		opId;
	
	private Integer		glId;
	private String		productSn;
	private String		colorName;
	private String		sizeName;
	private String		orderSn;
	
	private String		cardPwdHide;
	private String		isUsedStr;
	private Integer		providerId;
	
	/* ---- private methods ---- */
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardPwd() {
		return cardPwd;
	}
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
		if (this.cardPwd != null) {
			this.cardPwdHide = doHideCardPwd(this.cardPwd, 4);
		}
	}
	public Boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
		this.isUsedStr = this.isUsed ? "是" : "否";
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	public Integer getSubId() {
		return subId;
	}
	public void setSubId(Integer subId) {
		this.subId = subId;
	}
	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
	public Integer getGlId() {
		return glId;
	}
	public void setGlId(Integer glId) {
		this.glId = glId;
	}
	public String getProductSn() {
		return productSn;
	}
	public void setProductSn(String productSn) {
		this.productSn = productSn;
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
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getCardPwdHide() {
		return cardPwdHide;
	}
	public String getIsUsedStr() {
		return isUsedStr;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof ProductCard) {
			ProductCard productCard = (ProductCard) obj;
			if (this.subId.equals(productCard.getSubId()) && StringUtil.equals(this.cardNo, productCard.getCardNo()) && StringUtil.equals(this.cardPwd, productCard.getCardPwd())) {
				return true;
			}
		}
		
		return false;
	}
	
	private static String doHideCardPwd(String cardPwd, int length) {
        if (cardPwd == null || cardPwd.isEmpty() || length <= 0) {
            return cardPwd;
        }
        
        // >=length时，后length位*，<length时，全部*。
        StringBuilder builder = new StringBuilder(cardPwd);
        int sourceLength = builder.length();
        int deleteLength = sourceLength > length ? length : sourceLength;
        builder.delete(sourceLength - deleteLength, sourceLength);
        while (deleteLength-- > 0) {
            builder.append("*");
        }
        
        return builder.toString();
    }
	
}
