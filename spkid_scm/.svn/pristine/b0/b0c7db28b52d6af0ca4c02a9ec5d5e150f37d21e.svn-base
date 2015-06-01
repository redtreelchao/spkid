/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.dto;


/**
 * @author michael
 * @version $Id: GoodsSku.java 257 2013-07-29 07:39:27Z zhangshixi $
 */
public class ProductCardDto extends GoodsConsign {

	private String cardNo;
	private String cardPwd;
	private String cardNoCrypt;
	private String cardPwdCrypt;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
		if (this.cardNo != null) {
			this.cardNoCrypt = this.cardNo;
		}
	}
	public String getCardPwd() {
		return cardPwd;
	}
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
		if (this.cardPwd != null) {
			this.cardPwdCrypt = this.cardPwd;
		}
	}
	public String getCardNoCrypt() {
		return cardNoCrypt;
	}
	public String getCardPwdCrypt() {
		return cardPwdCrypt;
	}
	
}
