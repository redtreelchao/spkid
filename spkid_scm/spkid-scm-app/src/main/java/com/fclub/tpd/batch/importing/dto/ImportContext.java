/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.dto;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.web.multipart.MultipartFile;

import com.fclub.tpd.batch.importing.enums.ImportPolicy;
import com.fclub.tpd.batch.importing.enums.ImportType;

/**
 * 导入上下文
 * @author likaiping
 * @version $Id: ImportContent.java, v 0.1 Oct 25, 2012 3:41:31 PM likaiping Exp $
 */
public class ImportContext {

    /** 导入批次号 */
    private String        batchNo;

    private Integer       id;

    private String        goodsIds;
    /** 导入类型 */
    private ImportType    importType;
    /** 导入策略 */
    private ImportPolicy  importPolicy = ImportPolicy.SINGLE_PROCESSING;
    /** 导入人 */
    private Integer       importAdmin;
    /** 文件导入内容 */
    private MultipartFile file;
    /** 供应商编号 */
    private int           providerId;
    /** 归档记录文件名称 */
    private String        historicalFileName;
    /** 导入记录ID */
    private int           listId;
    /** 是否第一次导入 */
    private boolean       first;

    private Object        content;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public ImportType getImportType() {
        return importType;
    }

    public void setImportType(ImportType importType) {
        this.importType = importType;
    }

    public ImportPolicy getImportPolicy() {
        return importPolicy;
    }

    public void setImportPolicy(ImportPolicy importPolicy) {
        this.importPolicy = importPolicy;
    }

    public Integer getImportAdmin() {
        return importAdmin;
    }

    public void setImportAdmin(Integer importAdmin) {
        this.importAdmin = importAdmin;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getHistoricalFileName() {
        return historicalFileName;
    }

    public void setHistoricalFileName(String historicalFileName) {
        this.historicalFileName = historicalFileName;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(String goodsIds) {
        this.goodsIds = goodsIds;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
