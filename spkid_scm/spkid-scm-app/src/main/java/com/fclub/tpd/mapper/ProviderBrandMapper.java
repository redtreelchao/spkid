package com.fclub.tpd.mapper;

import java.util.List;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.dataobject.ProviderBrand;

public interface ProviderBrandMapper extends BaseMapper<Provider>{
    
    public List<ProviderBrand> selectBrandsByProviderId(Integer id);
    
}