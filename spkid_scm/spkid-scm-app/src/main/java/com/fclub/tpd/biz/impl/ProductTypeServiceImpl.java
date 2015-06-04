/**
 * fclub.cn
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.ProductTypeService;
import com.fclub.tpd.dataobject.ProductType;
import com.fclub.tpd.mapper.ProductTypeMapper;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeMapper goodsTypeMapper;

    @Override
    public Page<ProductType> queryGoodsTypeByPage(Page<ProductType> page, 
    		ProductType goodsTyp, String keyWord, int goods_type_1) {
        page.setResult(goodsTypeMapper.findPage(page, goodsTyp, keyWord, goods_type_1));
        return page;
    }

    @Override
    public Page<ProductType> getListByPage(Page<ProductType> page, int parentId) {
        page.setResult(goodsTypeMapper.findListPage(page, parentId));
        return page;
    }

    @Override
    public List<ProductType> getLevelOneTypes() {
        return goodsTypeMapper.getLevelOneTypes();
    }

    @Override
    public List<ProductType> getLevelTwoTypes(Integer levelOneTypeId) {
        return goodsTypeMapper.getLevelTwoTypes(levelOneTypeId);
    }

    @Override
    public List<ProductType> getLevelThreeTypes(Integer levelTwoTypeId) {
        return goodsTypeMapper.getLevelThreeTypes(levelTwoTypeId);
    }

}
