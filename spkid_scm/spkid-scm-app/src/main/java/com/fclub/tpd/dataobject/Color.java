package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class Color implements Serializable {
    private Integer colorId;

    private Integer groupCode;

    private String colorCode;

    private String colorName;

    private String colorImg;

    private String colorColor;

    private Date colorTime;

    private Integer colorAid;
    
    private Boolean isUse;

    private static final long serialVersionUID = 1L;

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Integer getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Integer groupCode) {
        this.groupCode = groupCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode == null ? null : colorCode.trim();
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName == null ? null : colorName.trim();
    }

    public String getColorImg() {
        return colorImg;
    }

    public void setColorImg(String colorImg) {
        this.colorImg = colorImg == null ? null : colorImg.trim();
    }

    public String getColorColor() {
        return colorColor;
    }

    public void setColorColor(String colorColor) {
        this.colorColor = colorColor == null ? null : colorColor.trim();
    }

    public Date getColorTime() {
        return colorTime;
    }

    public void setColorTime(Date colorTime) {
        this.colorTime = colorTime;
    }

    public Integer getColorAid() {
        return colorAid;
    }

    public void setColorAid(Integer colorAid) {
        this.colorAid = colorAid;
    }
    
    public Boolean getIsUse() {
		return isUse;
	}
    
    public void setIsUse(Boolean isUse) {
		this.isUse = isUse;
	}
}