package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Style implements Serializable {
    private Integer styleId;

    private String styleCode;

    private String styleName;

    private Integer styleAid;

    private Date styleTime;

    private static final long serialVersionUID = 1L;

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode == null ? null : styleCode.trim();
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName == null ? null : styleName.trim();
    }

    public Integer getStyleAid() {
        return styleAid;
    }

    public void setStyleAid(Integer styleAid) {
        this.styleAid = styleAid;
    }

    public Date getStyleTime() {
        return styleTime;
    }

    public void setStyleTime(Date styleTime) {
        this.styleTime = styleTime;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,ToStringStyle.DEFAULT_STYLE);
    }
    
}