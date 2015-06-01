package com.fclub.tpd.mapper;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.erp.Admin;

public interface AdminMapper extends BaseMapper<Admin>{
    
    public Admin selectByName(String adminName);
    
}