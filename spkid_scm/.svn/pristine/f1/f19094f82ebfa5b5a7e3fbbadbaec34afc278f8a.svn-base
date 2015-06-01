/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.Brand;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.dataobject.ProviderAccountLog;
import com.fclub.tpd.dataobject.ProviderBrand;
import com.fclub.tpd.dto.ProviderAccountLogSearch;

public interface ProviderService {

    public void update(Provider provider);
    
    public Provider findById(Integer providerId);
    
    public Provider findByUserName(String userName);
    
    public List<ProviderBrand> queryBrandListByProviderId(Integer id);
    
    public void editProvider(Provider provider, List<Brand> brandList);    
   
    public Page<ProviderAccountLog> queryAccountLogListByPage(Page<ProviderAccountLog> page, ProviderAccountLogSearch search);
    
    public boolean insertAccountLog(ProviderAccountLog providerAccountLog);
    
    public void updateAccountLog(ProviderAccountLog providerAccountLog);
    
    public ProviderAccountLog findAccountLogByLogId(Integer logId);
    
    public String aliPay(Integer providerId,Integer orderId,Double money);
}
