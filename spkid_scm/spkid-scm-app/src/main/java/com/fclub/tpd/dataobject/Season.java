package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

public class Season implements Serializable {

    private Integer           seasonId;

    private String            seasonCode;

    private String            seasonName;

    private Integer           seasonSort;

    private Short             seasonAid;

    private Date              seasonTime;

    private static final long serialVersionUID = 1L;

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public Integer getSeasonSort() {
        return seasonSort;
    }

    public void setSeasonSort(Integer seasonSort) {
        this.seasonSort = seasonSort;
    }

    public Short getSeasonAid() {
        return seasonAid;
    }

    public void setSeasonAid(Short seasonAid) {
        this.seasonAid = seasonAid;
    }

    public Date getSeasonTime() {
        return seasonTime;
    }

    public void setSeasonTime(Date seasonTime) {
        this.seasonTime = seasonTime;
    }

}
