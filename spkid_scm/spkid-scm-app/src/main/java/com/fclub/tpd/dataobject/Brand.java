/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * @author michael
 * @version $Id: Brand.java, v 0.1 2012-8-8 上午10:23:09 michael Exp $
 */
public class Brand implements Serializable {

    private static final long serialVersionUID = 2727349031348080755L;

    // basic informations
    private Integer           brandId;                                // 品牌ID，主键
    private String            brandName;                              // 品牌名称
    private String            brandShort;                             // 品牌简称
    private String            brandInitial;                           // 品牌首字母
    private String            siteUrl;                                // 品牌网址
    private Integer           sortOrder;                              // 品牌排序值
    private String            brandDesc;                              // 品牌描述
    private String            keywords;                               // 搜索关键词
    private Integer           hits;                                   // 品牌点击数

    private String            brandBannerUrl;                         // 品牌Banner URL地址
    private String            brandStoryLink;                         // 品牌故事链接地址

    private String            brandStory;                             // 品牌故事
    private String            brandAddress;                           // 专卖店地址
    private String            brandCustom;                            // 自定义头部风格
    private String            brandActivity;                          // 品牌活动

    private Boolean           isFocus;                                // 是否设为焦点
    private Boolean           isShow;                                 // 是否显示
    private Boolean           isEmpty;                                // 售空是否显示

    private Short             areaId;                                 // 所属国家ID {@link Area}

    // images
    private String            brandLogo;                              // 品牌LOGO图
    private String            brandLogobig;                           // 品牌大LOGO图
    private String            brandLogoSmall;                         // 品牌小LOGO图
    private String            brandPlayimg;                           // 品牌活动图
    private String            brandFocus;                             // 品牌焦点图
    private String            brandVideo;                             // 品牌视频图
    private String            brandBanner;                            // 品牌Banner图     

    // operation informations
    private Short             brandAid;                               // 添加管理员ID {@link AdminUser}
    private Date              brandTime;                              // 添加时间

    // WMS informations
    private Boolean           synchStatus;                            // 是否同步到WMS
    private Date              synchTime;                              // WMS同步时间

    // 首页改版：http://jira.f-club.cn/browse/BACKENHANCE-210
    private String            logo7550Url;                            // 首页改版新logo图
    private Integer           uptUser;                                // 更新人ID
    private Date              uptTime;                                // 更新时间

    public Integer getBrandId() {
        return brandId;
    }
    
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getBrandShort() {
        return brandShort;
    }

    public String getBrandInitial() {
        return brandInitial;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public String getKeywords() {
        return keywords;
    }

    public Integer getHits() {
        return hits;
    }

    public String getBrandBannerUrl() {
        return brandBannerUrl;
    }

    public String getBrandStoryLink() {
        return brandStoryLink;
    }

    public String getBrandStory() {
        return brandStory;
    }

    public String getBrandAddress() {
        return brandAddress;
    }

    public String getBrandCustom() {
        return brandCustom;
    }

    public String getBrandActivity() {
        return brandActivity;
    }

    public Boolean getIsFocus() {
        return isFocus;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public Boolean getIsEmpty() {
        return isEmpty;
    }

    public Short getAreaId() {
        return areaId;
    }

    public String getBrandLogo() {
        return brandLogo;
    }
    
    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getBrandLogobig() {
        return brandLogobig;
    }

    public String getBrandLogoSmall() {
        return brandLogoSmall;
    }

    public String getBrandPlayimg() {
        return brandPlayimg;
    }

    public String getBrandFocus() {
        return brandFocus;
    }

    public String getBrandVideo() {
        return brandVideo;
    }

    public String getBrandBanner() {
        return brandBanner;
    }

    public Short getBrandAid() {
        return brandAid;
    }

    public Date getBrandTime() {
        return brandTime;
    }

    public Boolean getSynchStatus() {
        return synchStatus;
    }

    public Date getSynchTime() {
        return synchTime;
    }

    public String getLogo7550Url() {
        return logo7550Url;
    }

    public Integer getUptUser() {
        return uptUser;
    }

    public Date getUptTime() {
        return uptTime;
    }
    
    public String getUrl7638() {
    	return genLogoUrl(new int[]{76, 38});
    }
    
    private String genLogoUrl(int[] size) {
    	if (brandLogo == null || brandLogo.isEmpty()) {
    		return null;
    	} else {
    		String suffix = brandLogo.substring(brandLogo.lastIndexOf("."));
    		return brandLogo + "." + size[0] + "x" + size[1] + suffix;
    	}
    }

}