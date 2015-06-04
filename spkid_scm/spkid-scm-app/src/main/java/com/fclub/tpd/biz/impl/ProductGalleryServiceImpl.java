/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.ProductGalleryService;
import com.fclub.tpd.dataobject.ProductGallery;
import com.fclub.tpd.mapper.ProductGalleryMapper;

@Service
public class ProductGalleryServiceImpl implements ProductGalleryService {

	@Autowired
    private ProductGalleryMapper productGalleryMapper;
    
    @Override
    public ProductGallery get(Integer id) {
        return productGalleryMapper.get(id);
    }

    @Override
    public Page<ProductGallery> findPage(Page<ProductGallery> page, ProductGallery goodsGallery) {
        
        page.setResult(productGalleryMapper.findPage(page, goodsGallery));
        return page;
    }
    
    @Override
    @Transactional
    public void save(ProductGallery goodsGallery) {
        productGalleryMapper.insert(goodsGallery);
    }

    @Override
    @Transactional
    public void update(ProductGallery goodsGallery) {
        productGalleryMapper.update(goodsGallery);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        productGalleryMapper.delete(id);
    }
    @Override
    public ProductGallery getDefaultGalleryImg(int goodsId) {
        return productGalleryMapper.getDefaultGallery(goodsId);
    }
    
    @Override
    public void deleteByGoodsId(Integer goodsId) {
    	productGalleryMapper.deleteByGoodsId(goodsId);
    }
}