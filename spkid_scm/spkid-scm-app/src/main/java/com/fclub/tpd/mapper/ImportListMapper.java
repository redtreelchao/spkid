package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.Page;
import com.fclub.common.dal.interceptor.PageInterceptor;
import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ImportList;

public interface ImportListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ImportList record);

    ImportList selectByPrimaryKey(Integer id);

    List<ImportList> selectByBatchNo(String batchNo);

    int updateByPrimaryKeySelective(ImportList record);

    List<ImportList> getLastByBatchNo(@Param("type") String type, @Param("batchNo") String batchNo);

    ImportList getLastByAdmin(@Param("type") String type, @Param("adminId") Integer adminId);

    /**
     * 分页方法 mapper key = 'findPage'
     * 
     * @param page 分页参数
     * @param obj 查询条件参数
     */
    List<ImportList> findGalleryByPage(@Param(PageInterceptor.PAGE_KEY) Page<ImportList> page,
                                       @Param(BaseMapper.PARAM_KEY) Object obj);

    List<ImportList> getPageByBatchNo(@Param(PageInterceptor.PAGE_KEY) Page<ImportList> page,
                                      @Param("type") String type, @Param("batchNo") String batchNo);

}