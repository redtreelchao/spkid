/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fclub.tpd.biz.BrandService;
import com.fclub.tpd.dataobject.Brand;
import com.fclub.tpd.mapper.BrandMapper;

/**
 * Goods brand service implementation.
 * 
 * @author ZhangShixi
 * @version $Id: BrandServiceImpl.java 36 2013-07-08 06:56:32Z zhangshixi $
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Brand findBrand(Integer brandId) {
        return brandMapper.get(brandId);
    }

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

    @Override
    public List<Brand> findProviderBrands(Integer providerId) {
        return brandMapper.findBrandsByProviderId(providerId);
    }
    
}
