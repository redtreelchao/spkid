/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.dto;

import java.util.List;
import java.util.Map;

import com.fclub.tpd.dataobject.BatchImport;
import com.fclub.tpd.dataobject.ImportList;

/**
 * 
 * @author likaiping
 * @version $Id: ImportBatchDetail.java, v 0.1 Oct 30, 2012 1:39:50 PM likaiping Exp $
 */
public class ImportBatchDetail {
    /** 批次主要信息 */
    BatchImport          batchImport;
    /** 详细批次记录 */
    List<ImportList>     importLists;
    /** 控制对象 操作标识:0-不可操作；1-可操作； */
    Map<String, Integer> optionMap;
    /** 状态对象 提示标识 0-未完成;1-正在执行中;2-已完成;3-出错; */
    Map<String, Integer> stateMap;

    public BatchImport getBatchImport() {
        return batchImport;
    }

    public void setBatchImport(BatchImport batchImport) {
        this.batchImport = batchImport;
    }

    public List<ImportList> getImportLists() {
        return importLists;
    }

    public void setImportLists(List<ImportList> importLists) {
        this.importLists = importLists;
    }

    public Map<String, Integer> getOptionMap() {
        return optionMap;
    }

    public void setOptionMap(Map<String, Integer> optionMap) {
        this.optionMap = optionMap;
    }

    public Map<String, Integer> getStateMap() {
        return stateMap;
    }

    public void setStateMap(Map<String, Integer> stateMap) {
        this.stateMap = stateMap;
    }

    //~=================

    /**
     * 获取操作权限值
     * @param option
     * @return
     */
    public boolean getOption(String option) {
        if (optionMap == null) {
            return false;
        }
        if (!optionMap.containsKey(option)) {
            return false;
        }
        Integer integer = optionMap.get(option);
        if (integer == null || integer == 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取实时状态标识
     * 0-未完成;1-正在执行中;2-已完成;3-出错;
     * @param option
     * @return
     */
    public int getState(String option) {
        if (stateMap == null) {
            return 0;
        }
        if (!stateMap.containsKey(option)) {
            return 0;
        }
        Integer integer = stateMap.get(option);
        if (integer == null) {
            return 0;
        } else {
            return integer;
        }
    }
}
