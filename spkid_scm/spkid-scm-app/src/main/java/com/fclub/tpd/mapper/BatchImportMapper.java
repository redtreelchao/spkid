package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.Page;
import com.fclub.common.dal.interceptor.PageInterceptor;
import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.BatchImport;

public interface BatchImportMapper {
    /**
     * 分页方法 mapper key = 'findPage'
     * 
     * @param page 分页参数
     * @param obj 查询条件参数
     */
    List<BatchImport> findPage(@Param(PageInterceptor.PAGE_KEY) Page<BatchImport> page,
                    @Param(BaseMapper.PARAM_KEY) Object obj);
    
    int deleteByPrimaryKey(Integer id);

    int insert(BatchImport record);

    BatchImport selectByBatchNo(String batchNo);

    boolean checkBatchNoExsits(String batchNo);
    
    void activate(BatchImport record);
    
    void finish(BatchImport record);
}