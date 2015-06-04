/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.List;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Brand;

/**
 * Goods brand mapper.
 * 
 * @author michael
 * @version $Id: BrandMapper.java, v 0.1 2012-8-1 下午7:43:09 michael Exp $
 */
public interface BrandMapper extends BaseMapper<Brand> {
    
    public void deleteById(int brandId);
    
    public Brand selectById(int brandId);

    public boolean isExistedName(Brand brand);

    public boolean isExistedShort(Brand brand);
    
    public boolean beingUsed(int brandId);
    
    public List<Brand> selectAllShowBrands();
    
    List<Brand> findAll();
    List<Brand> getBrandByName(String brandName);
    
    String findNameByid(Integer id);

    public List<Brand> findBrandsByProviderId(Integer providerId);
    
}
