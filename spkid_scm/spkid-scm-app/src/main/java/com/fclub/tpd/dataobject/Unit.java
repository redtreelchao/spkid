package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class Unit implements Serializable {
    private Integer unitId;

    private String unitCode;

    private String unitName;

    private Short unitAid;

    private Date unitTime;

    private static final long serialVersionUID = 1L;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public Short getUnitAid() {
        return unitAid;
    }

    public void setUnitAid(Short unitAid) {
        this.unitAid = unitAid;
    }

    public Date getUnitTime() {
        return unitTime;
    }

    public void setUnitTime(Date unitTime) {
        this.unitTime = unitTime;
    }
}