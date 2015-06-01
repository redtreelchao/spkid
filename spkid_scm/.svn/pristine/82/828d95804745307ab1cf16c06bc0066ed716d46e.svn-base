/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.goods;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.fclub.cache.CacheDriver;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.FileUtil;
import com.fclub.common.lang.utils.RandomUtils;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LoggerUtil;
import com.fclub.erp.dto.goods.BatchResultVo;
import com.fclub.erp.remote.domain.ProcessStatus;
import com.fclub.remote.domain.Server;
import com.fclub.remote.domain.ServerCollection;
import com.fclub.tpd.batch.importing.AbstractImportTask;
import com.fclub.tpd.batch.importing.ImportService;
import com.fclub.tpd.batch.importing.MutiImportTask;
import com.fclub.tpd.batch.importing.TaskLifeCycle;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.enums.ImportType;
import com.fclub.tpd.batch.importing.helper.ImportTaskHelper;
import com.fclub.tpd.batch.remote.DispatchStragety;
import com.fclub.tpd.batch.remote.ServerManager;
import com.fclub.tpd.batch.remote.ServerManagerFactory;
import com.fclub.tpd.common.PropertyUtil;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.dataobject.ImportList;
import com.fclub.tpd.helper.ConstantsHelper;
import com.fclub.tpd.mapper.BatchImportMapper;
import com.fclub.tpd.mapper.ImportListMapper;

/**
 * @version $Id: GoodsGalleryImportTask.java, v 0.1 Jul 17, 2013 12:04:20 PM likaiping Exp $
 */
public class GoodsGalleryImportTask  implements MutiImportTask, TaskLifeCycle {
    /** 默认导入日志 */
    public static final String           IMPORT_DETAULT_LOGGER = "IMPORT_DETAULT_LOGGER";
    /** 指定导入日志 */
    protected LoggerUtil                 logger;
    /** 导入上下文 */
    protected ImportContext              importContext;
    /** 导入结果内容 */
    protected List<Object>               resultContent;
    /** 结果文件名称 */
    private String                       resultFileName;
    /** 结果模板文件 */
    protected String                     resultTamplatePath    = "import/result/batchImageTemplate.vm";
    /** 日志文件名称 */
    private String                       resultLogName;

    @Autowired
    private BatchImportMapper            batchImportMapper;
    @Autowired
    private ImportListMapper             importListMapper;
    @Autowired
    private CacheDriver                  cacheDriver;
    @Autowired
    private DispatchStragety             dispatchStragety;

    private static final ExecutorService threadPool            = Executors.newCachedThreadPool();

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

    /**
     * @see com.fclub.erp.biz.importing.ImportTask#validation(com.fclub.erp.dto.importing.ImportContext)
     */
    @Override
    public boolean validation(ImportContext importContext) {
        this.importContext = importContext;
        if (importContext.getImportType() == ImportType.GOODS_GALLERY) {
            return true;
        }
        return false;
    }

    /**
     * @see com.fclub.erp.biz.importing.ImportTask#process()
     */
    @Override
    public ImportResult process() {

        String tmp = importContext.getBatchNo();
        //String zip = "0";
        String channel = tmp;
        String batchNo = tmp + RandomUtils.generateString(16);
//        String path = ConstantsHelper.getFtpRootPath();
        String path = "/var/www/remote_data/ftp";
        String image = ConstantsHelper.getImageInPath();

        /*if (StringUtils.equals(zip, "1")) {
            String zipFileName = channel + ".zip";
            File zipFile = new File(path, zipFileName);
            if (!zipFile.exists()) {
                throw new BizException("导入失败,请把图片ZIP压缩包放在" + path + "目录下,文件名应为" + zipFileName);
            }
            try {
                FileUtil.unzip(path, zipFile);
            } catch (RuntimeException e) {
                throw new BizException("解压缩失败");
            }
        }*/

        // 根目录
        File dir = new File(new File(path,channel), image);
        
        File[] fileArr = dir.listFiles();
        if (fileArr == null || fileArr.length == 0) {
        	logger.error("导入失败,请把图片放在" + dir.getAbsolutePath() + "目录下");
            throw new BizException("导入失败,请把图片放在FTP根目录的[" + image + "]目录下！");
        }

        // 解压
        boolean zip = false;
        for (File file : fileArr) {
            if (file.getName().endsWith(".zip")) {
                zip = true;
                break;
            }
        }
        if (zip) {
            if (fileArr.length > 1) {
                throw new BizException("请先删除" + path + "目录下的其它目录");
            } else {
                try {
                    FileUtil.unzip(path, fileArr[0]);
                } catch (RuntimeException e) {
                    throw new BizException("解压缩失败");
                }
            }
        }

        // 重新读取目录
        fileArr = dir.listFiles();
        if (fileArr == null || fileArr.length == 0) {
        	logger.error("导入失败,请把图片放在" + path + "目录下");
            throw new BizException("导入失败,请把图片放在" + path + "目录下");
        }
        List<String> dirs = new ArrayList<>(fileArr.length);
        for (int i = 0; i < fileArr.length; i++) {

            if (fileArr[i].exists() && fileArr[i].isDirectory()) {
                dirs.add(fileArr[i].getName());
            }
        }
        if (dirs.size() == 0) {
            throw new BizException("导入失败,请把图片放在" + path + "目录下");
        }

        return processMain(batchNo, channel, dirs);
        //是否启用线程
        //threadPool.submit(new GalleryThread(batchNo, channel, dirs));

        //return ImportTaskHelper.genResult(null, ImportStatus.SUCCESS, "导入完成");
    }

    private ProcessStatus reProcess(ServerManager manager, String batchNo, String channel,
                                    List<String> dirs, int total, int process) {

        batchNo = batchNo + "-REP";
        ProcessStatus statusREP = null;

        List<String> errorDirs = dispatchStragety.dispatchTask(manager, batchNo, channel, dirs,
            logger);

        if (errorDirs.size() > 0) {
            //
        }

        while (true) {

            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                logger.error("main thread sleep exception: ", e);
            }

            statusREP = manager.getStatus(batchNo);

            //TODO 进度控制 BY LKP
            setProgress(total, process + statusREP.getProcessCount());

            for (String log : statusREP.getLogback()) {
                //System.out.println(log);
                logger.debug(log);
            }

            if (statusREP.isFinished()) {
                logger.info("exception process finish...");
                manager.removeStatus(batchNo);
                break;
            }
        }

        if (manager.getErrorDirs().size() > 0) {
            // TODO 启用故障处理方案 -REP 方式
            // logger.info("启动故障处理...");
        }

        return statusREP;
    }

    private void backDirs(List<String> dirs, String channel) {
        // 源文件处理方案
        final boolean isBackFile = false;
        String srcPath = ConstantsHelper.getImageInPath(channel);
        String destPath = ConstantsHelper.getStaticRootPath()
                          + System.getProperty(SystemConstant.BATCH_IMAGE_BACK) + File.separator
                          + channel + File.separator;

        logger.info("备份图片开始，root目录：{0}", srcPath);

        for (String dir : dirs) {
            if (StringUtils.isBlank(dir)) {
                continue;
            }
            try {
                if (!isBackFile) {
                    File directory = new File(srcPath, dir);
                    if (!directory.exists()) {
                        logger.warn("delete dir {1} failed: dir not exists.",
                            directory.getAbsolutePath());
                    }
                    FileUtils.forceDelete(directory);
                    logger.debug("delete dir: {0}", dir);
                } else {
                    FileUtils.deleteDirectory(new File(destPath, dir));
                    FileUtils.moveDirectoryToDirectory(new File(srcPath, dir), new File(destPath),
                        true);
                    logger.debug("backup dir: {0}", dir);
                }
            } catch (IOException e) {
                logger.error("{0} dir {1} failed: {2}", isBackFile ? "backup" : "delete", dir, e);
            }
        }
        logger.info("备份图片已完成");
    }

    /**
     * 处理主线程
     */
    private ImportResult processMain(String batchNo, String channel, List<String> dirs) {

        ImportResult result = null;
        int total = dirs.size();
        int processCount = 0;
        setProgress(total, processCount);
        final List<String> processDir = new ArrayList<>();
        final List<String> successDir = new ArrayList<>();
        final List<String> errorDirs = new ArrayList<>();

        List<Server> serverList = ServerCollection.getServerList();

        ServerManager manager = ServerManagerFactory.generateServerManager(System
            .getProperty(SystemConstant.REMOTE_PROTOCOL));
        manager.init(serverList, importContext.getImportAdmin(), logger);

        try {
            errorDirs
                .addAll(dispatchStragety.dispatchTask(manager, batchNo, channel, dirs, logger));

            ProcessStatus status = null;

            while (true) {

                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    logger.error("main thread sleep exception: ", e);
                }

                status = manager.getStatus(batchNo);

                //TODO 进度控制 BY LKP
                setProgress(total, status.getProcessCount());
                logger.info("process bar: {0}/{1}", total, status.getProcessCount());

                for (String log : status.getLogback()) {
                    //System.out.println(log);
                    logger.debug(log);
                }

                if (status.getFinishNum() > 0) {

                }

                if (status.isFinished()) {
                    logger.info("main process finish...");
                    manager.removeStatus(batchNo);
                    break;
                }
            }

            processCount = status.getProcessCount();
            successDir.addAll(status.getSuccessDirs());
            processDir.addAll(status.getProcessDirs());

            for (BatchResultVo vo : status.getResults()) {
                logger.info("import result: goodSn={0}, colorCode={1}, msg={2}", vo.getGoodsSn(),
                    vo.getColorCode(), vo.getMessage());
            }
            if (resultContent == null) {
                resultContent = new ArrayList<>(status.getResults().size());
            }
            resultContent.addAll(status.getResults());

            errorDirs.addAll(manager.getErrorDirs());
            manager.clearStatus();

            ProcessStatus statusREP = null;
            try {
                if (errorDirs.size() > 0) {
                    // TODO 启用故障处理方案  是否-REP方式
                    logger.info("启动异常处理...");
                    statusREP = reProcess(manager, batchNo, channel, errorDirs, total,
                        status.getProcessCount());
                    errorDirs.clear();
                }
            } catch (Exception e) {
                logger.error("异常处理出错：{0}", e.getMessage());
            }

            if (statusREP != null) {
                for (BatchResultVo vo : statusREP.getResults()) {
                    logger.debug("import result: goodSn={0}, colorCode={1}, msg={2}",
                        vo.getGoodsSn(), vo.getColorCode(), vo.getMessage());
                }
                resultContent.addAll(statusREP.getResults());
                if (statusREP.getProcessCount() > 0) {
                    processCount += statusREP.getProcessCount();
                }
                successDir.addAll(statusREP.getSuccessDirs());
                processDir.addAll(statusREP.getProcessDirs());
            }

            // 未完全处理
            if (total > processCount) {

                result = ImportTaskHelper.genResult(null, ImportStatus.WARN, "未完全导入");

                @SuppressWarnings("unchecked")
                List<String> unProcessDir = ListUtils.removeAll(dirs, processDir);
                //System.out.println(dirs.size() + " : " + processDir.size());

                for (String dir : unProcessDir) {
                    logger.warn("未处理目录：" + dir);
                    String[] tmp = dir.split("-");
                    BatchResultVo resultVo = new BatchResultVo();
                    resultVo.setGoodsSn(tmp[0]);
                    resultVo.setColorCode(tmp[1]);
                    resultVo.setMessage("未处理");
                    resultContent.add(resultVo);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.destory();
            manager = null;
        }

        backDirs(successDir, channel);
        logger.info("image process end.");
        //System.out.println("==============the end================");
        if (total > successDir.size()) {
            result = ImportTaskHelper.genResult(null, ImportStatus.FAIL, "导入部分失败");
        }
        if (result == null) {
            result = ImportTaskHelper.genResult(null, ImportStatus.SUCCESS, "导入完成");
        }
        return result;
    }

    /**
     * @see com.fclub.erp.biz.importing.ImportTask#dispense()
     */
    @Override
    public ImportResult dispense() {
        logger = null;
        logger = LoggerUtil.getLogger(IMPORT_DETAULT_LOGGER);
        ImportResult result = null;
        try {
            genLogger();
            evolution(ImportStatus.RUNING);
            logger.info("批量导入-MAIN-初始化导入-end");
        } catch (Exception e) {
            logger.error("初始化导入批次异常,{0}", e, importContext);
            return ImportTaskHelper.genResultError("初始化导入批次异常");
        }

        resultContent = new ArrayList<>();

        threadPool.submit(new ProcessThread());

        return result;
    }

    private class ProcessThread implements Runnable {

        @Override
        public void run() {
            try {
                ImportResult result = null;
                setProgress(100, 0);
                try {
                    logger.info("批量导入-MAIN-执行导入-begin");
                    result = process();
                    logger.info("批量导入-MAIN-执行导入-end");
                } catch (BizException e) {
                    logger.error("执行导入任务，发生异常,{0}", e, importContext);
                    result = ImportTaskHelper.genResultError(null, e.getMessage());
                } catch (Exception e) {
                    logger.error("执行导入任务，发生未知异常,{0}", e, importContext);
                    result = ImportTaskHelper.genResultError(null, "执行导入任务，发生未知异常");
                } finally {
                    logger.info("批量导入-MAIN-生成导入结果-begin");
                    if (!ImportTaskHelper.generateHtml(result, resultContent,
                        getResultTemplateName(), getResultFilePath(), getResultFileName())) {
                        result = ImportTaskHelper.genResult(null, ImportStatus.WARN, "生成结果错误");
                    }
                    logger.info("批量导入-MAIN-生成导入结果-end");
                }
                logger.info("批量导入-MAIN-结束导入-begin");
                closure(result);
                logger.info("批量导入-结束导入-end");
                removeProgress();
            } catch (Exception e) {
                logger.error("Thread Error", e);
            }
        }

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
     * 
     * @param result
     */
    protected void closure(ImportResult result) {
        logger.info("[导入完成][导入Result][{0}]", result);
        // 设置此次导入操作记录
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
        // 演化总体记录
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
            Layout layout = new PatternLayout(AbstractImportTask.DEFAULT_CONVERSION_PATTERN);
            logger.switchToLogFile(filePath, layout);
            logger.switchToCacheLogFile(filePath, cacheDriver, layout);
        } catch (Exception e) {
            logger.error("切换日志输出至指定console文件失败,{0}", e, importContext);
            this.resultLogName = "切换日志输出至指定console文件失败,fileName:" + this.resultLogName;
        }
    }

    /**
     * 获取批次日志记录路径
     * 
     * @return
     */
    protected String getResultLogFilePath() {
        return ConstantsHelper.getStaticRootPath()
               + ConstantsHelper.getPram(SystemConstant.BATCH_CONSOLE_OUT) + "/"
               + importContext.getBatchNo();
    }

    /**
     * 生成批次日志记录文件名称
     * 
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
     * 
     * @return
     */
    protected String getResultTemplateName() {
        return resultTamplatePath;
    }

    /**
     * 获取结果文件地址-全路径
     * 
     * @return
     */
    protected String getResultFilePath() {
        return ConstantsHelper.getStaticRootPath() + "/"
               + ConstantsHelper.getPram(SystemConstant.BATCH_RESULT) + "/"
               + importContext.getBatchNo();
    }

    /**
     * 获取结果文件名称
     * 
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

    public void setProgress(int total, int curr) {
        String key = ImportService.PROGRESS_PRE + importContext.getImportType().getCode() + "/"
                     + importContext.getBatchNo();
//        cacheDriver.put(key, total + "/" + curr);
        PropertyUtil.setProgressValue(key, total + "/" + curr);
    }

    public void removeProgress() {
        String key = ImportService.PROGRESS_PRE + importContext.getImportType().getCode() + "/"
                     + importContext.getBatchNo();
//        cacheDriver.remove(key);
        PropertyUtil.removeProgressValue(key);
    }

}
