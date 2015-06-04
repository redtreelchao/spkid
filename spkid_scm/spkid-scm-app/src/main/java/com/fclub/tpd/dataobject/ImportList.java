package com.fclub.tpd.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.enums.ImportType;

/**
 * 导入附属记录
 * @author likaiping
 * @version $Id: ImportList.java, v 0.1 Oct 26, 2012 10:28:45 AM likaiping Exp $
 */
public class ImportList implements Serializable {
    private Integer           id;
    /** 导入批次号 */
    private String            impBatchNo;
    /** 导入文件名 */
    private String            fileName;
    /** 01主要数据;02颜色尺寸;03统一审核;04次要信息;05采购单;06:图片;07:尺寸对照图 */
    private String            impType;
    /** 导入时间 */
    private Date              impTime;
    /** 是否成功 */
    private String            status;
    /** 导入人 */
    private Integer           impAid;
    /** 操作日志文件 */
    private String            logFile;
    /** 结果报告文件 */
    private String            resultFile;
    /** 执行结果-消息 */
    private String            msg;
    //～=============EXT

    private ImportType        importType;
    private ImportStatus      importStatus;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImpType() {
        return impType;
    }

    public void setImpType(String impType) {
        this.impType = impType == null ? null : impType.trim();
        this.importType = ImportType.valueOfCode(impType);
    }

    public Date getImpTime() {
        return impTime;
    }

    public void setImpTime(Date impTime) {
        this.impTime = impTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.importStatus =ImportStatus.valueOfCode(status);
    }

    public Integer getImpAid() {
        return impAid;
    }

    public void setImpAid(Integer impAid) {
        this.impAid = impAid;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile == null ? null : logFile.trim();
    }

    public String getResultFile() {
        return resultFile;
    }

    public void setResultFile(String resultFile) {
        this.resultFile = resultFile == null ? null : resultFile.trim();
    }

    public ImportType getImportType() {
        return importType;
    }

    public void setImportType(ImportType importType) {
        this.importType = importType;
        this.impType = importType.getCode();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ImportStatus getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(ImportStatus importStatus) {
        this.importStatus = importStatus;
        this.status=importStatus.getCode();
    }
    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,ToStringStyle.DEFAULT_STYLE);
    }
    

}