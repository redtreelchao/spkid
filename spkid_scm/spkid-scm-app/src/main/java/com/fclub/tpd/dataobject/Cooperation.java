package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class Cooperation implements Serializable {
    private Integer coopId;

    private String coopCode;

    private String coopName;

    private Integer coopAid;

    private Date coopTime;

    private static final long serialVersionUID = 1L;

    public Integer getCoopId() {
        return coopId;
    }

    public void setCoopId(Integer coopId) {
        this.coopId = coopId;
    }

    public String getCoopCode() {
        return coopCode;
    }

    public void setCoopCode(String coopCode) {
        this.coopCode = coopCode == null ? null : coopCode.trim();
    }

    public String getCoopName() {
        return coopName;
    }

    public void setCoopName(String coopName) {
        this.coopName = coopName == null ? null : coopName.trim();
    }

    public Integer getCoopAid() {
        return coopAid;
    }

    public void setCoopAid(Integer integer) {
        this.coopAid = integer;
    }

    public Date getCoopTime() {
        return coopTime;
    }

    public void setCoopTime(Date coopTime) {
        this.coopTime = coopTime;
    }
}