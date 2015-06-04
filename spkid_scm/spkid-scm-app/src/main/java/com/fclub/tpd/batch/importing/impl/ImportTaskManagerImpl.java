/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.Assert;
import com.fclub.common.spring.SpringContextHolder;
import com.fclub.tpd.batch.importing.ImportTask;
import com.fclub.tpd.batch.importing.ImportTaskManager;
import com.fclub.tpd.batch.importing.MutiImportTask;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportPolicy;

/**
 * 
 * @author likaiping
 * @version $Id: ImportTaskManagerImpl.java, v 0.1 Oct 25, 2012 4:35:10 PM likaiping Exp $
 */
@Service
public class ImportTaskManagerImpl implements ImportTaskManager {

    @Resource(name = "importTask")
    private Map<String, String> resources;

    private ImportResult singleProcess(final ImportContext importContext) {
        Assert.isTrue(
            resources != null && resources.containsKey(importContext.getImportType().name()),
            "没有发现对应的导入任务处理器");
        String taskName = resources.get(importContext.getImportType().name());
        final ImportTask importTask = SpringContextHolder.getBean(taskName);
        if (importTask.validation(importContext)) {
            return importTask.process();
        } else {
            throw new BizException("验证失败，无法执行此导入任务");
        }
    }

    private ImportResult mutiProcess(ImportContext importContext) {
        Assert.isTrue(
            resources != null && resources.containsKey(importContext.getImportType().name()),
            "没有发现对应的导入任务处理器");
        String taskName = resources.get(importContext.getImportType().name());
        ImportTask importTask = SpringContextHolder.getBean(taskName);
        if (importTask instanceof MutiImportTask) {
            if (importTask.validation(importContext)) {
                return ((MutiImportTask) importTask).dispense();
            } else {
                throw new BizException("验证失败，无法拆解此导入任务");
            }
        } else {
            throw new BizException("验证失败，无法执行此导入任务");
        }
    }

    /** 
     * @see com.fclub.erp.biz.importing.ImportTaskManager#execute(com.fclub.erp.dto.importing.ImportContext)
     */
    @Override
    public ImportResult execute(ImportContext importContext) {
        if (importContext.getImportPolicy() == ImportPolicy.SINGLE_PROCESSING) {
            return singleProcess(importContext);
        } else {
            return mutiProcess(importContext);
        }
    }

}
