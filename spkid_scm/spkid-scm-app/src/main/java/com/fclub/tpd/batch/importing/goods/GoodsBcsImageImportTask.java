/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.goods;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Layout;
import org.apache.log4j.SimpleLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.fclub.cache.CacheDriver;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.RandomUtils;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LoggerUtil;
import com.fclub.tpd.batch.importing.ImportService;
import com.fclub.tpd.batch.importing.ImportTask;
import com.fclub.tpd.batch.importing.TaskLifeCycle;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.enums.ImportType;
import com.fclub.tpd.batch.importing.helper.ImportTaskHelper;
import com.fclub.tpd.biz.ProductService;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.dataobject.ImportList;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.helper.ConstantsHelper;
import com.fclub.tpd.mapper.BatchImportMapper;
import com.fclub.tpd.mapper.ImportListMapper;
import com.fclub.tpd.mapper.ProductMapper;
import com.fclub.web.util.UploadUtil;

/**
 * @version $Id: GoodsBcsImageImportTask.java, v 0.1 Jul 19, 2013 2:09:35 PM likaiping Exp $
 */
public class GoodsBcsImageImportTask  implements ImportTask, TaskLifeCycle {

    /** 默认导入日志 */
    public static final String IMPORT_DETAULT_LOGGER = "IMPORT_DETAULT_LOGGER";
    /** 指定导入日志 */
    protected LoggerUtil       logger;
    /** 导入上下文 */
    protected ImportContext    importContext;
    /** 导入结果内容 */
    protected List<Object>     resultContent;
    /** 结果文件名称 */
    private String             resultFileName;
    /** 结果模板文件 */
    protected String           resultTamplatePath;
    /** 日志文件名称 */
    private String             resultLogName;

    @Autowired
    private BatchImportMapper  batchImportMapper;
    @Autowired
    private ImportListMapper   importListMapper;
    @Autowired
    private CacheDriver        cacheDriver;
    @Autowired
    private ProductService       goodsService;
    @Autowired
    private ProductMapper goodsMapper;
    
    /** 尺寸图宽度 */
    public static final int BCS_IMAGE_WIDTH = 700;

    /** 
     * @see com.fclub.erp.biz.importing.ImportTask#validation(com.fclub.erp.dto.importing.ImportContext)
     */
    @Override
    public boolean validation(ImportContext importContext) {
        this.importContext = importContext;
        if (importContext.getImportType() == ImportType.GOODS_BCS_IMAGE) {
            return true;
        }
        return false;
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
            genLogger();
            evolution(ImportStatus.RUNING);
            logger.info("批量导入-MAIN-初始化导入-end");
        } catch (Exception e) {
            logger.error("初始化导入批次异常,{0}", e, importContext);
            result = ImportTaskHelper.genResultError("初始化导入批次异常");
        }
        if (result == null) {
            try {
                logger.info("批量导入-MAIN-执行导入-begin");
                result = doProcess();
                logger.info("批量导入-MAIN-执行导入-end");
                logger.info("批量导入-MAIN-生成导入结果-begin");
                if (!ImportTaskHelper.generateHtml(result, resultContent,
                    getResultTemplateName(), getResultFilePath(), getResultFileName())) {
                    result = ImportTaskHelper.genResult(null, ImportStatus.WARN, "生成结果错误");
                }
                logger.info("批量导入-MAIN-生成导入结果-end");
            } catch (Exception e) {
                logger.error("执行导入任务，发生未知异常,{0}", e, importContext);
                result = ImportTaskHelper.genResultError(null, "执行导入任务，发生未知异常");
            }
        }
        logger.info("批量导入-MAIN-结束导入-begin");
        closure(result);
        logger.info("批量导入-结束导入-end");
        return result;
    }

    protected ImportResult doProcess() {
        initResultTamplatePath();
        String bcsDirPath = ConstantsHelper.getFtpRootPath();
        String batchNo = importContext.getBatchNo();
        String bcsString = ConstantsHelper.getPram(SystemConstant.GOODS_BCS_IMAGE_IN);
        // 根目录
        File dir = new File(new File(bcsDirPath, batchNo),bcsString);
        if (!dir.exists()) {
            logger.error("执行导入任务失败,请把图片放在指定目录下,{0}/{1}", bcsDirPath, batchNo);
            return ImportTaskHelper.genResultError(null, "执行导入任务失败,请把图片放在指定目录下");
        }
        File[] fileArr = dir.listFiles();
        if (fileArr == null || fileArr.length == 0) {
            logger.error("执行导入任务失败,请把图片放在指定目录下,{0}/{1}", bcsDirPath, batchNo);
            return ImportTaskHelper.genResultError(null, "执行导入任务失败,请把图片放在指定目录下");
        }
        for (int i = 0; i < fileArr.length; i++) {
            setProgress(fileArr.length, i + 1);
            File file = fileArr[i];
            String fileName = file.getName();
            try {
                String goodsSn = StringUtil.substringBeforeLast(fileName, ".");
                goodsSn = StringUtil.upperCase(goodsSn);
                
                BufferedImage bi = ImageIO.read(file);
                if (bi.getWidth() != BCS_IMAGE_WIDTH) {
                	throw new BizException("对应尺寸图宽度不等于 " + BCS_IMAGE_WIDTH + " ，款号:" + goodsSn);
                }
                
                Product goodsWithBLOBs = goodsMapper.findTpdGoodsBySn(goodsSn);
                if (goodsWithBLOBs == null) {
                	addResultLog("导入图片失败：商品款号[" + goodsSn +"]不存在！");
                	continue;
                    // throw new BizException("对应商品不存在，款号:" + goodsSn);
                }
                String imagePath = ConstantsHelper.getPicRootPath()
                                   + System.getProperty(SystemConstant.UPLOAD_IMAGE_BCS);
                String domainPath = genFile(file, imagePath, fileName.toLowerCase());
                String scDesc = "<img title=\"尺寸图\" src=\"" + domainPath + "\">";
                Product goods = new Product();
                goods.setGoodsId(goodsWithBLOBs.getGoodsId());
                goods.setScDesc(scDesc);
                goodsService.update(goods);
                addResultLog("导入图片成功,fileName:" + fileName);
                if(file.isFile()) {
                    file.delete();
                }
            } catch (BizException e) {
                logger.error("导入图片失败,fileName:{0}", e, fileName);
                addResultLog("导入图片失败," + e.getMessage());
                return ImportTaskHelper.genResult(null, ImportStatus.FAIL, "导入失败");
            } catch (Exception e) {
                logger.error("导入图片失败,fileName:{0}", e, fileName);
                addResultLog("导入图片失败,fileName:" + fileName);
                return ImportTaskHelper.genResult(null, ImportStatus.FAIL, "导入失败");
            }
        }
        return ImportTaskHelper.genResult(null, ImportStatus.SUCCESS, "导入完成");
    }

    public void initResultTamplatePath() {
        this.resultTamplatePath = "import/result/batchBcsTemplate.vm";
    }

    /**
     * 生成文件
     * @param file
     * @param localPath
     * @param fileName
     * @return
     * @throws Exception
     */
    private String genFile(File file, String localPath, String fileName) throws Exception {
        FileUtils.forceMkdir(new File(localPath));
        //保存文件
        UploadUtil.dumpAsset(file, fileName, localPath);
        //返回
        String remotePath = System.getProperty(SystemConstant.IMAGE_DOMAIN)
                            + System.getProperty(SystemConstant.UPLOAD_IMAGE_BCS);
        return remotePath + File.separator + fileName;
    }

    /**
     * 演进生命周期
     */
    protected void evolution(ImportStatus status) {
        logger.info("批量导入-演进生命周期-开始执行任务；ImportStatus：{0}", status);
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
        logger.info("批量导入-演进生命周期-记录任务记录成功；ListId：{0};details:{1}", importList.getId(), importList);
        //
//        BatchImport record = new BatchImport();
//        record.setImpBatchNo(importContext.getBatchNo());
//        record.setUptUser(importContext.getImportAdmin());
//        record.setUpImportType(importContext.getImportType());
//        batchImportMapper.activate(record);
//        logger.info("批量导入-演进生命周期-激活导入批次记录；BatchImport：{0}", record);
    }

    /**
     * 结束此次操作
     * @param result
     */
    protected void closure(ImportResult result) {
        logger.info("[导入完成][导入Result][{0}]", result);
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
                importList.setImportStatus(ImportStatus.FAIL);
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
        logger.info("[导入完成][导入记录更新]details:{0}", importList);
        //演化总体记录
//        BatchImport record = new BatchImport();
//        record.setImpBatchNo(importContext.getBatchNo());
//        record.setUptUser(importContext.getImportAdmin());
//        record.setUpImportType(importContext.getImportType());
//
//        if (importList.getImportStatus() == ImportStatus.SUCCESS) {
//            switch (importContext.getImportType()) {
//                case GOODS_MAIN_INFORMATION:
//                    record.setImpmain(true);
//                    break;
//                case GOODS_COLOR_SIZE:
//                    record.setImpcolorsize(true);
//                    break;
//                case GOODS_VERIFY:
//                    record.setAudit(true);
//                    record.setAuditId(importContext.getImportAdmin());
//                    break;
//                case GOODS_SUB_INFORMATION:
//                    record.setImpsecinfo(true);
//                    break;
//                case PURCHASE_ORDER:
//                    record.setImppurchase(true);
//                    break;
//                case GOODS_GALLERY:
//                    record.setImppic(true);
//                    break;
//                case GOODS_BCS_IMAGE:
//                    record.setImpbcsimg(true);
//                    break;
//                default:
//                    break;
//            }
//        }
//        record.setImpGoodsIds(importContext.getGoodsIds());
//        batchImportMapper.finish(record);
//
//        logger.info("[导入完成][导入批次更新]details：{0}", record);
    }

    /**
     * 生成日志文件-独立出console
     */
    private void genLogger() {
        try {
            String filePath = getResultLogFilePath() + "/" + genResultLogName();
            Layout layout = new SimpleLayout();
            logger.switchToLogFile(filePath, layout);
            logger.switchToCacheLogFile(filePath, cacheDriver, layout);
        } catch (Exception e) {
            logger.error("切换日志输出至指定console文件失败,{0}", e, importContext);
            this.resultLogName = "切换日志输出至指定console文件失败,fileName:" + this.resultLogName;
        }
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

    /**
     * 获取模板文件名称
     * @return
     */
    protected String getResultTemplateName() {
        return resultTamplatePath;
    }

    /**
     * 获取结果文件地址-全路径
     * @return
     */
    protected String getResultFilePath() {
        return ConstantsHelper.getStaticRootPath() + "/"
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
     * 增加结果记录
     * @param content
     */
    protected void addResultLog(Object content) {
        if (resultContent == null) {
            resultContent = new ArrayList<>();
        }
        resultContent.add(content);
    }

    public void setProgress(int total, int curr) {
        String key = ImportService.PROGRESS_PRE + importContext.getImportType().getCode() + "/"
                     + importContext.getBatchNo();
        cacheDriver.put(key, total + "/" + curr);
    }

    /** 
     * @see com.fclub.erp.biz.importing.TaskLifeCycle#getProgress()
     */
    @Override
    public short getProgress() {
        return 0;
    }

    /** 
     * @see com.fclub.erp.biz.importing.TaskLifeCycle#getState()
     */
    @Override
    public ImportStatus getState() {
        return null;
    }


}
