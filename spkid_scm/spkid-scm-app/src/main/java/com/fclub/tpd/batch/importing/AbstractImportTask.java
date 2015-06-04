/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.fclub.cache.CacheDriver;
import com.fclub.common.lang.utils.RandomUtils;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LoggerUtil;
import com.fclub.thread.ThreadPoolManager;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.enums.ImportType;
import com.fclub.tpd.batch.importing.helper.ImportTaskHelper;
import com.fclub.tpd.biz.ProviderService;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.dataobject.BatchImport;
import com.fclub.tpd.dataobject.ImportList;
import com.fclub.tpd.helper.ConstantsHelper;
import com.fclub.tpd.mapper.BatchImportMapper;
import com.fclub.tpd.mapper.ImportListMapper;

/**
 * 抽象继承封装类
 * @version $Id: AbstractImportTask.java, v 0.1 Oct 26, 2012 9:33:09 AM likaiping Exp $
 */
public abstract class AbstractImportTask implements ImportTask, TaskLifeCycle {

    /** 默认导入日志 */
    public static final String         IMPORT_DETAULT_LOGGER      = "IMPORT_DETAULT_LOGGER";
    /** 默认日志模板 */
    public static final String         DEFAULT_CONVERSION_PATTERN = "%d{yyyy-MM-dd HH:mm:ss}[%-5p] -%m%n";
    protected static ThreadPoolManager THREAD_POOL                = new ThreadPoolManager(30);
    /** 指定导入日志 */
    protected LoggerUtil               logger;
    /** 导入上下文 */
    protected ImportContext            importContext;
    /** 导入结果内容 */
    protected List<Object>             resultContent;
    /** 结果文件名称 */
    private String                     resultFileName;
    /** 结果模板文件 
    protected String            resultTamplatePath;*/
    /** 日志文件名称 */
    private String                     resultLogName;

    @Autowired
    protected BatchImportMapper        batchImportMapper;
    @Autowired
    protected ImportListMapper         importListMapper;
    @Autowired
    protected CacheDriver              cacheDriver;
    @Autowired
    protected ProviderService          providerService;

    /** 
     * @see com.fclub.erp.biz.importing.TaskLifeCycle#getProgress()
     */
    @Override
    public short getProgress() {
        return 0;
    }

    public void setProgress(int total, int curr) {
        String key = ImportService.PROGRESS_PRE + importContext.getImportType().getCode() + "/"
                     + importContext.getBatchNo();
        cacheDriver.put(key, total + "/" + curr);
    }

    public void removeProgress() {
        String key = ImportService.PROGRESS_PRE + importContext.getImportType().getCode() + "/"
                     + importContext.getBatchNo();
        cacheDriver.remove(key);
    }

    /** 
     * @see com.fclub.erp.biz.importing.TaskLifeCycle#getState()
     */
    @Override
    public ImportStatus getState() {
        return null;
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportTask#validation(com.fclub.erp.dto.importing.ImportContext)
     */
    @Override
    public boolean validation(ImportContext importContext) {
        this.importContext = importContext;
        return doValidation(importContext);
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportTask#process()
     */
    @Override
    public ImportResult process() {
        logger = null;
        logger = LoggerUtil.getLogger(IMPORT_DETAULT_LOGGER);
        ImportResult result = null;
        try {
            setProgress(100, 1);
            init();
            genLogger();
            logger.info("初始化开始");
            initImport();
            storageOfHistorical();
            evolution(ImportStatus.RUNING);
            logger.info("初始化完成");
            
            THREAD_POOL.submit(new ImportRunnable(importContext));
            result = ImportTaskHelper.genResult(null, ImportStatus.SUCCESS, "文件上传成功，转入后台处理，请关闭本页面");
        } catch (Exception e) {
            logger.error("初始化出错,{0}", e, importContext);
            result = ImportTaskHelper.genResultError("初始化出错");
        }
        return result;
    }

    /**
     * 生成日志文件-独立出console
     */
    private void genLogger() {
        try {
            String filePath = getResultLogFilePath() + "/" + genResultLogName();
            Layout layout = new PatternLayout(DEFAULT_CONVERSION_PATTERN);
            logger.switchToLogFile(filePath, layout);
            logger.switchToCacheLogFile(filePath, cacheDriver, layout);
        } catch (Exception e) {
            logger.error("切换日志输出至指定console文件失败,{0}", e, importContext);
            this.resultLogName = "切换日志输出至指定console文件失败,fileName:" + this.resultLogName;
        }
    }

    /**
     * 第一次进入-初始化批次号-用作日志记录
     */
    protected void init() {
        resultContent = null;
        resultFileName = null;
        resultLogName = null;
        if (StringUtil.isBlank(importContext.getBatchNo())) {
            String batchNo = genImportBatchNo();
            importContext.setBatchNo(batchNo);
            importContext.setFirst(true);
        }
    }

    /**
     * 获取导入批次号
     * @return
     */
    protected String genImportBatchNo() {
        String impBatchNo = ImportTaskHelper.genBatchNo();
        if (batchImportMapper.checkBatchNoExsits(impBatchNo)) {
            while (true) {
                impBatchNo = ImportTaskHelper.genBatchNo();
                if (!batchImportMapper.checkBatchNoExsits(impBatchNo)) {
                    break;
                }
            }
        }
        return impBatchNo;
    }

    /**
     * 初始化导入动作
     * @throws Exception 
     * @throws  
     */
    protected void initImport() throws Exception {
        logger.info("开始批量导入操作，批次号【{0}】，操作人【{1}】", importContext.getBatchNo(), providerService
            .findById(importContext.getImportAdmin()).getProviderCode());
        if (importContext.isFirst()) {
            BatchImport record = new BatchImport();
            record.setImpBatchNo(importContext.getBatchNo());
            logger.info("开始备份上传文件至历史记录");
            String maindataFilename = ImportTaskHelper.storageOfHistorical(importContext);
            record.setMaindataFilename(maindataFilename);
            logger.info("备份上传文件成功-备份文件名【{0}】", maindataFilename);
            record.setCrtUser(importContext.getImportAdmin());
            record.setProviderId(importContext.getProviderId());
            record.setUptUser(importContext.getImportAdmin());
            record.setUpImportType(ImportType.GOODS_MAIN_INFORMATION);
            batchImportMapper.insert(record);
            importContext.setId(record.getId());
            importContext.setHistoricalFileName(maindataFilename);
            importContext.setBatchNo(importContext.getBatchNo());
        }
    }

    protected void storageOfHistorical() throws Exception {
        if (!importContext.isFirst()) {
            logger.info("开始备份上传文件至历史记录");
            String historicalFileName = ImportTaskHelper.storageOfHistorical(importContext);
            importContext.setHistoricalFileName(historicalFileName);
            logger.info("备份上传文件成功-备份文件名【{0}】", historicalFileName);
        }
    }

    /**
     * 增加结果记录
     * @param content
     */
    protected void addResultLog(Object content) {
        if (resultContent == null) {
            resultContent = new ArrayList<>();
        }
        resultContent.add(content);
    }

    /**
     * 演进生命周期
     */
    protected void evolution(ImportStatus status) {
        ImportList importList = new ImportList();
        importList.setFileName(importContext.getHistoricalFileName());
        importList.setImpBatchNo(importContext.getBatchNo());
        importList.setImportType(importContext.getImportType());
        importList.setLogFile(this.resultLogName);
        importList.setResultFile(this.resultFileName);
        importList.setImportStatus(status);
        importList.setImpAid(importContext.getImportAdmin());
        importListMapper.insert(importList);
        importContext.setListId(importList.getId());
        //
//        BatchImport record = new BatchImport();
//        record.setImpBatchNo(importContext.getBatchNo());
//        record.setUptUser(importContext.getImportAdmin());
//        record.setUpImportType(importContext.getImportType());
//        batchImportMapper.activate(record);
    }

    /**
     * 结束此次操作
     * @param result
     */
    protected void closure(ImportResult result) {
        //设置此次导入操作记录
        ImportList importList = new ImportList();
        importList.setId(importContext.getListId());
        importList.setFileName(importContext.getHistoricalFileName());
        importList.setImpBatchNo(importContext.getBatchNo());
        importList.setResultFile(this.resultFileName);

        switch (result.getImportStatus()) {
            case NONE:
                importList.setImportStatus(ImportStatus.FAIL);
                break;
            case INIT:
                importList.setImportStatus(ImportStatus.RUNING);
                break;
            case RUNING:
                importList.setImportStatus(ImportStatus.RUNING);
                break;
            case FAIL:
                importList.setImportStatus(ImportStatus.FAIL);
                break;
            case WARN:
                importList.setImportStatus(ImportStatus.SUCCESS);
                break;
            case ERROR:
                importList.setImportStatus(ImportStatus.FAIL);
                break;
            case SUCCESS:
                importList.setImportStatus(ImportStatus.SUCCESS);
                break;
            default:
                importList.setImportStatus(ImportStatus.SUCCESS);
                break;
        }

        importList.setImpAid(importContext.getImportAdmin());
        importListMapper.updateByPrimaryKeySelective(importList);
        //演化总体记录
        if (importContext.getId() != null) {
	        BatchImport record = new BatchImport();
	        record.setId(importContext.getId());
	        record.setImpBatchNo(importContext.getBatchNo());
	        record.setUptUser(importContext.getImportAdmin());
	        record.setUpImportType(importContext.getImportType());
	
	        if (importList.getImportStatus() == ImportStatus.SUCCESS) {
	            switch (importContext.getImportType()) {
	                case GOODS_MAIN_INFORMATION:
	                    record.setImpmain(true);
	                    break;
	                case GOODS_COLOR_SIZE:
	                    record.setImpcolorsize(true);
	                    break;
	                case GOODS_VERIFY:
	                    record.setAudit(true);
	                    record.setAuditId(importContext.getImportAdmin());
	                    break;
	                case GOODS_SUB_INFORMATION:
	                    record.setImpsecinfo(true);
	                    break;
	                case PURCHASE_ORDER:
	                    record.setImppurchase(true);
	                    break;
	                case GOODS_GALLERY:
	                    record.setImppic(true);
	                    break;
	                case GOODS_BCS_IMAGE:
	                    record.setImpbcsimg(true);
	                    break;
	                default:
	                    break;
	            }
	        }
	        record.setImpGoodsIds(importContext.getGoodsIds());
	        batchImportMapper.finish(record);
        }
    }

    /**
     * 获取结果文件地址-全路径
     * @return
     */
    protected String getResultFilePath() {
        return ConstantsHelper.getStaticRootPath()
               + ConstantsHelper.getPram(SystemConstant.BATCH_RESULT) + "/"
               + importContext.getBatchNo();
    }

    /**
     * 获取结果文件名称
     * @return
     */
    protected String getResultFileName() {
        if (StringUtil.isBlank(this.resultFileName)) {
            this.resultFileName = importContext.getImportType().getCode() + "_"
                                  + RandomUtils.generateString(4) + "_"
                                  + importContext.getImportAdmin() + "_" + new Date().getTime()
                                  + ".html";
        }
        return resultFileName;
    }

    /**
     * 获取批次日志记录路径
     * @return
     */
    protected String getResultLogFilePath() {
        return ConstantsHelper.getStaticRootPath()
               + ConstantsHelper.getPram(SystemConstant.BATCH_CONSOLE_OUT) + "/"
               + importContext.getBatchNo();
    }

    /**
     * 生成批次日志记录文件名称
     * @return
     */
    protected String genResultLogName() {
        if (StringUtil.isBlank(this.resultLogName)) {
            this.resultLogName = importContext.getImportType().getCode() + "_"
                                 + RandomUtils.generateString(4) + "_"
                                 + importContext.getImportAdmin() + ".log";
        }
        return resultLogName;
    }

    public ImportContext getImportContext() {
        return importContext;
    }

    protected abstract boolean doValidation(ImportContext importContext);

    protected abstract ImportResult doProcess();

    protected abstract String getResultTemplateName();

    public class ImportRunnable implements Runnable {

        ImportContext importContext;

        /**
         * @param importContext
         */
        public ImportRunnable(ImportContext importContext) {
            this.importContext = importContext;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            ImportResult result = doProcess();
            logger.info("开始生成导入结果报告");
            if (!ImportTaskHelper.generateHtml(result, resultContent, getResultTemplateName(),
                getResultFilePath(), getResultFileName())) {
                logger.info("生成结果错误");
                result = ImportTaskHelper.genResult(null, ImportStatus.WARN, "生成结果错误");
            }
            logger.info("导入结果报告文件名:{0}", getResultFileName());
            try {
            	closure(result);
            } catch (Exception e) {
            	logger.error("结束此次操作错误", e);
            }
            removeProgress();
            logger.info("完成此次导入");
        }
    }
}
