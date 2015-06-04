/**
 * fclub.cn
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.CategoryService;
import com.fclub.tpd.biz.ProductService;
import com.fclub.tpd.biz.ProductTypeLinkService;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dataobject.ProductGallery;
import com.fclub.tpd.dto.TypeLinkQueryDTO;
import com.fclub.tpd.mapper.ProductMapper;
import com.fclub.tpd.mapper.ProductTypeLinkMapper;

/**
 * 前台分类管理Service实现类
 * @version $Id: GoodsTypeLinkServiceImpl.java, v 0.1 2012-7-24 下午1:22:07 baolm Exp $
 */
@Service
public class ProductTypeLinkServiceImpl implements ProductTypeLinkService {

    @Autowired
    private ProductTypeLinkMapper productTypeLinkMapper;
    @Autowired
    private ProductMapper         productMapper;
    @Autowired
    private CategoryService       categoryService;
    @Autowired
    private ProductService        productService;

    @Override
    public Page<Product> findPage(Page<Product> page, TypeLinkQueryDTO queryDTO) {
        if (queryDTO.getCatId() != null && queryDTO.getCatId() != -1)
            queryDTO.setCatIds(categoryService.getAllSubCategoryById(queryDTO.getCatId(), true));
        List<Product> goodsList = productMapper.findGoodsTypePage(page, queryDTO);
        for (Product goods : goodsList) {
            ProductGallery goodsGallery = productService.getDefaultImg(goods.getGoodsId());
            if(goodsGallery != null)
                goods.setUrl120160(goodsGallery.getUrl120160());
        }
        page.setResult(goodsList);
        return page;
    }

    @Override
    @Transactional
    public void setGoodsType(Integer goodsId, String[] typeIds) {

        productTypeLinkMapper.deleteByGoodsId(goodsId);
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("goodsId", goodsId);
        for (String typeId : typeIds) {
            if (StringUtils.isBlank(typeId)) {
                continue;
            }
            param.put("typeId", Integer.valueOf(typeId));
            productTypeLinkMapper.insert(param);
        }
    }

    /** 
     * @see com.fclub.erp.ProductTypeLinkService.goods.GoodsTypeLinkService#confirmGoodsTypeExits(java.lang.Integer)
     */
    @Override
    public boolean confirmGoodsTypeExits(Integer goodsId) {
        return productTypeLinkMapper.confirmGoodsTypeExits(goodsId);
    }

    /** 
     * @see com.fclub.erp.ProductTypeLinkService.goods.GoodsTypeLinkService#deleteGoodsTypeLinkByGoodsId(java.lang.Integer)
     */
    @Override
    public void deleteGoodsTypeLinkByGoodsId(Integer goodsId) {
        productTypeLinkMapper.deleteByGoodsId(goodsId);
    }

    /** 
     * @see com.fclub.erp.ProductTypeLinkService.goods.GoodsTypeLinkService#selectTypesByGoodsId(java.lang.Integer)
     */
    @Override
    public List<Integer> selectTypesByGoodsId(Integer goodsId) {
        return productTypeLinkMapper.selectTypesByGoodsId(goodsId);
    }

}
