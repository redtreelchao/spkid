package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class Size implements Serializable {
    private Integer           sizeId;

    private String            sizeCode;

    private String            sizeName;

    private Short             sortOrder;

    private Short             sizeAid;

    private Date              sizeTime;

    private static final long serialVersionUID = 1L;

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode == null ? null : sizeCode.trim();
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName == null ? null : sizeName.trim();
    }

    public Short getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Short sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Short getSizeAid() {
        return sizeAid;
    }

    public void setSizeAid(Short sizeAid) {
        this.sizeAid = sizeAid;
    }

    public Date getSizeTime() {
        return sizeTime;
    }

    public void setSizeTime(Date sizeTime) {
        this.sizeTime = sizeTime;
    }

    @Override
    public String toString() {
        return "Size [sizeId=" + sizeId + ", sizeCode=" + sizeCode + ", sizeName=" + sizeName
               + ", sortOrder=" + sortOrder + ", sizeAid=" + sizeAid + ", sizeTime=" + sizeTime
               + "]";
    }

}