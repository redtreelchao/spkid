/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.tpd.dataobject.Brand;

/**
 * Goods brand service interface.
 * 
 * @author ZhangShixi
 * @version $Id: BrandService.java 37 2013-07-08 06:57:51Z zhangshixi $
 */
public interface BrandService {
    
    public Brand findBrand(Integer brandId);
    
    public List<Brand> findAll();

    public List<Brand> findProviderBrands(Integer providerId);

}
