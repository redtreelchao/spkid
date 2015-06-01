package com.fclub.tpd.mapper;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Provider;

public interface ProviderMapper extends BaseMapper<Provider>{
    
    public Provider selectByUserName(String userName);
    
}