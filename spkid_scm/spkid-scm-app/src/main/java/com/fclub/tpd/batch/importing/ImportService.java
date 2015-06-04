/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing;

import java.util.List;
import java.util.Set;

import com.fclub.common.dal.Page;
import com.fclub.tpd.batch.importing.dto.BatchImportSeach;
import com.fclub.tpd.batch.importing.dto.ConsoleOut;
import com.fclub.tpd.batch.importing.dto.GoodsData;
import com.fclub.tpd.batch.importing.dto.ImportBatchDetail;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.dataobject.BatchImport;
import com.fclub.tpd.dataobject.ImportList;
import com.fclub.tpd.dataobject.ProductGallery;

/**
 * 导入接口
 * @author likaiping
 * @version $Id: ImportService.java, v 0.1 Oct 25, 2012 3:43:46 PM likaiping Exp $
 */
public interface ImportService {

    public static final String PROGRESS_PRE = "FCLUB_PROGRESS_PRE_";

    /**
     * 查询导入操作批次结果-分页
     * @param page
     * @param seach
     * @return
     */
    Page<BatchImport> findPage(Page<BatchImport> page, BatchImportSeach seach);

    /**
     * 查询导入商品图导入记录-分页
     * @param page
     * @param seach
     * @return
     */
    Page<ImportList> findGalleryByPage(Page<ImportList> page, BatchImportSeach seach);
    
    
    /**
     * 获取执行导入记录列表
     * @param type
     * @param batchNo
     * @return
     */
    Page<ImportList> getPageByBatchNo(Page<ImportList> page,String type ,String batchNo);

    /**
     * 执行导入操作
     */
    ImportResult importProcess(ImportContext importContext);

    /**
     * 重试导入操作
     */
    void retry(ImportContext importContext);

    /**
     * 删除导入
     */
    void delete(String batchNo);

    /**
     * 根据导入批次号获取结果
     */
    ImportBatchDetail getByBatchNo(String batchNo);

    /**
     * 获取商品颜色尺寸数据
     */
    List<GoodsData> getGoodsColorSizeData(String batchNo);

    /**
     * 获取执行进度
     */
    String getProgress(String type);

    /**
     * 获取执行记录
     */
    ConsoleOut getConsoleOut(int listId);

    /**
     * 获取执行结果
     * @param listId
     * @return
     */
    String getResultData(int listId);

    /**
     * 获取执行状态
     */
    ImportStatus getState(String type, String batchNo);
    
    /**
     * 获取最后执行记录
     * @param type
     * @param batchNo
     * @return
     */
    List<ImportList> getLastByBatchNo(String type, String batchNo);
   
    /**
     * 通过导入批次号获取执行状态
     * @param batchNo
     * @return
     */
    BatchImport getBatchImportByNo(String batchNo);
    
    void stopImportGoodsGallery(int listId);
    
    /**
     * 获取执行进度
     */
    String getProgressBar(String batchNo);

    
	List<ProductGallery> queryGallerys(Set<Integer> goodsIds);

}
