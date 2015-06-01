/**
 * fclub.cn
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.ProductType;

/**
 * 前台分类Service
 * @author baolm
 * @version $Id: GoodsTypeService.java, v 0.1 2012-7-24 下午12:37:35 baolm Exp $
 */
public interface ProductTypeService {

    /**
     * 分页查询GoodsType
     */
    public Page<ProductType> queryGoodsTypeByPage(Page<ProductType> page,ProductType goodsType,String keyWord,int goods_type_1);

    /**
     * 查询满足parentId = typeId Or parentId2=typeId 的分类
     * @param page
     * @param parentId
     * @return
     */
    Page<ProductType> getListByPage(Page<ProductType> page, int parentId);
    
    List<ProductType> getLevelOneTypes();

    List<ProductType> getLevelTwoTypes(Integer levelOneTypeId);
    
    List<ProductType> getLevelThreeTypes(Integer levelTwoTypeId);
    
    
}
