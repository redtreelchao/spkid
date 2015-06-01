/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fclub.common.lang.utils.Assert;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.helper.ImportTaskHelper;

/**
 * 
 * @author likaiping
 * @version $Id: AstractUploadImportTask.java, v 0.1 Oct 26, 2012 4:29:12 PM likaiping Exp $
 */
public abstract class AbstractUploadImportTask extends AbstractImportTask {

    @Autowired
    private PlatformTransactionManager transactionManager;

    /** 
     * @see com.fclub.erp.biz.importing.AbstractImportTask#doProcess()
     */
    @Override
    protected ImportResult doProcess() {
        // 检查文件是否存在
        File file = ImportTaskHelper.getHistoricalFile(importContext);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Assert.notNull(inputStream);
        } catch (IOException e) {
            logger.error("文件打开失败", e);
            return ImportTaskHelper.genResultError("文件打开失败");
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        ImportResult result = null;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        logger.info("开始导入流程");
        try {
            result = execute();
            if (result.getImportStatus() == ImportStatus.SUCCESS) {
                transactionManager.commit(status);
                logger.info("完成入库");
                result.setMsg("导入成功");
            } else {
                logger.error("导入流程出错,数据回滚，此次操作对数据库无任何影响，请重新导入");
                transactionManager.rollback(status);
            }
        } catch (RuntimeException e) {
            logger.error("导入流程出错,数据回滚，请联系管理员", e);
            transactionManager.rollback(status);
            result = ImportTaskHelper.genResult(null, ImportStatus.FAIL, "导入失败");
        }
        return result;
    }

    protected abstract ImportResult execute();

}
