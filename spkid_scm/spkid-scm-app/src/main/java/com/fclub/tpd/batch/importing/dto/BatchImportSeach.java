/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.dto;

/**
 * 
 * @author likaiping
 * @version $Id: BatchImportSeach.java, v 0.1 Oct 29, 2012 1:20:15 PM likaiping Exp $
 */
public class BatchImportSeach {

    private String importDateBegin;
    private String importDateEnd;

    private boolean owner;
    /** 主要数据导入人 */
    private Integer           crtUser;

    public String getImportDateBegin() {
        return importDateBegin;
    }

    public void setImportDateBegin(String importDateBegin) {
        this.importDateBegin = importDateBegin;
    }

    public String getImportDateEnd() {
        return importDateEnd;
    }

    public void setImportDateEnd(String importDateEnd) {
        this.importDateEnd = importDateEnd;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public Integer getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(Integer crtUser) {
        this.crtUser = crtUser;
    }

}
