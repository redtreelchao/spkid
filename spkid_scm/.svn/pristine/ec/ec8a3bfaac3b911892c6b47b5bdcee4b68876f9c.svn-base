package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fclub.common.lang.utils.StringUtil;

public class ShippingProduct implements Serializable {

    private static final long serialVersionUID = -4968526755528281871L;
    
    private Integer orderId;
    private String  orderSn;
    private String  productSn;
    private String  sku;
    private String  productName;
    private String  brandName;
    private String  providerProductcode;
    private String  colorName;
    private String  sizeName;
    private Integer productNum;
    private BigDecimal shopPrice;
    private String  providerBarcode;
    
    private String  oldProviderBarcode;
    
    private ShippingPacket shippingPacket;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProviderProductcode() {
        return providerProductcode;
    }

    public void setProviderGoods(String provideProductcode) {
        this.providerProductcode = provideProductcode;
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

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getProviderBarcode() {
        return StringUtil.isEmpty(this.providerBarcode) 
                ? this.oldProviderBarcode : this.providerBarcode;
    }

    public void setProviderBarcode(String providerBarcode) {
        this.providerBarcode = providerBarcode;
    }

    public ShippingPacket getShippingPacket() {
        return shippingPacket;
    }

    public void setShippingPacket(ShippingPacket shippingPacket) {
        this.shippingPacket = shippingPacket;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getOldProviderBarcode() {
        return oldProviderBarcode;
    }
    
    public void setOldProviderBarcode(String oldProviderBarcode) {
        this.oldProviderBarcode = oldProviderBarcode;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
