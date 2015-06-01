package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class GoodsMaterial implements Serializable {
    private Integer materId;

//    private String materCode;

    private String materName;

    private String materImg;

    private Date materTime;

    private Integer materAid;

    private static final long serialVersionUID = 1L;

    public Integer getMaterId() {
        return materId;
    }

    public void setMaterId(Integer materId) {
        this.materId = materId;
    }

//    public String getMaterCode() {
//        return materCode;
//    }
//
//    public void setMaterCode(String materCode) {
//        this.materCode = materCode == null ? null : materCode.trim();
//    }

    public String getMaterName() {
        return materName;
    }

    public void setMaterName(String materName) {
        this.materName = materName == null ? null : materName.trim();
    }

    public String getMaterImg() {
        return materImg;
    }

    public void setMaterImg(String materImg) {
        this.materImg = materImg == null ? null : materImg.trim();
    }

    public Date getMaterTime() {
        return materTime;
    }

    public void setMaterTime(Date materTime) {
        this.materTime = materTime;
    }

    public Integer getMaterAid() {
        return materAid;
    }

    public void setMaterAid(Integer materAid) {
        this.materAid = materAid;
    }
}