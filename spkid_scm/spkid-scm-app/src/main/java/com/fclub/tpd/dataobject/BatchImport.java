package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fclub.tpd.batch.importing.enums.ImportType;

/**
 * 批量导入-主要信息表
 * @author likaiping
 * @version $Id: BatchImport.java, v 0.1 Oct 26, 2012 10:25:11 AM likaiping Exp $
 */
public class BatchImport implements Serializable {
    private Integer           id;
    /** 导入批次号 */
    private String            impBatchNo;
    /** 主要数据导入文件名 */
    private String            maindataFilename;
    /** 主要数据导入状态 */
    private Boolean            isImpmain;
    /** 是否导入颜色尺寸 */
    private Boolean           isImpcolorsize;
    /** 是否已经审核0-未，1-已 */
    private Boolean           isAudit;
    /** 审核人 */
    private Integer           auditId;
    /** 审核时间 */
    private Date              auditTime;
    /** 是否导入采购单 */
    private Boolean           isImppurchase;
    /** 是否导入次要信息 */
    private Boolean           isImpsecinfo;
    /** 是否导入尺寸对照图 */
    private Boolean           isImpbcsimg;
    /** 是否导入图片 */
    private Boolean           isImppic;
    /** 主要数据导入人 */
    private Integer           crtUser;
    /** 主要数据导入时间 */
    private Date              crtTime;
    /** 最后更新人 */
    private Integer           uptUser;
    /** 最后更新时间 */
    private Date              uptTime;
    /** 最后更新类型 */
    private String            uptType;
    /** 导入的商品ids */
    private String            impGoodsIds;
    //～=================EXT
    private ImportType        upImportType;
    private Integer providerId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImpBatchNo() {
        return impBatchNo;
    }

    public void setImpBatchNo(String impBatchNo) {
        this.impBatchNo = impBatchNo == null ? null : impBatchNo.trim();
    }

    public String getMaindataFilename() {
        return maindataFilename;
    }

    public void setMaindataFilename(String maindataFilename) {
        this.maindataFilename = maindataFilename == null ? null : maindataFilename.trim();
    }

    public Boolean isImpmain() {
        return isImpmain;
    }

    public void setImpmain(Boolean isImpmain) {
        this.isImpmain = isImpmain;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Boolean isImpcolorsize() {
        return isImpcolorsize;
    }

    public void setImpcolorsize(Boolean isImpcolorsize) {
        this.isImpcolorsize = isImpcolorsize;
    }

    public Boolean isAudit() {
        return isAudit;
    }

    public void setAudit(Boolean isAudit) {
        this.isAudit = isAudit;
    }

    public Boolean isImppurchase() {
        return isImppurchase;
    }

    public void setImppurchase(Boolean isImppurchase) {
        this.isImppurchase = isImppurchase;
    }

    public Boolean isImpsecinfo() {
        return isImpsecinfo;
    }

    public void setImpsecinfo(Boolean isImpsecinfo) {
        this.isImpsecinfo = isImpsecinfo;
    }

    public Boolean isImpbcsimg() {
        return isImpbcsimg;
    }

    public void setImpbcsimg(Boolean isImpbcsimg) {
        this.isImpbcsimg = isImpbcsimg;
    }

    public Boolean isImppic() {
        return isImppic;
    }

    public void setImppic(Boolean isImppic) {
        this.isImppic = isImppic;
    }

    public Integer getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(Integer crtUser) {
        this.crtUser = crtUser;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Integer getUptUser() {
        return uptUser;
    }

    public void setUptUser(Integer uptUser) {
        this.uptUser = uptUser;
    }

    public Date getUptTime() {
        return uptTime;
    }

    public void setUptTime(Date uptTime) {
        this.uptTime = uptTime;
    }

    public String getUptType() {
        return uptType;
    }

    public void setUptType(String uptType) {
        this.uptType = uptType == null ? null : uptType.trim();
        this.upImportType = ImportType.valueOfCode(uptType);
    }

    public String getImpGoodsIds() {
        return impGoodsIds;
    }

    public void setImpGoodsIds(String impGoodsIds) {
        this.impGoodsIds = impGoodsIds == null ? null : impGoodsIds.trim();
    }

    public ImportType getUpImportType() {
        return upImportType;
    }

    public void setUpImportType(ImportType upImportType) {
        this.upImportType = upImportType;
        this.uptType = upImportType.getCode();
    }
    
    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,ToStringStyle.DEFAULT_STYLE);
    }
    
}