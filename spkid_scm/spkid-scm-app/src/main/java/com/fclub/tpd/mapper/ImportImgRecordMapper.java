package com.fclub.tpd.mapper;

import java.util.List;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ImportImgRecord;
import com.fclub.tpd.dataobject.erp.Admin;

public interface ImportImgRecordMapper extends BaseMapper<ImportImgRecord> {
    /**
     * 查找批量导图记录表中用户信息列表
     * @return 用户信息列表（包括admin_id和user_name）
     */
    List<Admin> findBatchImportAdmin();
}
