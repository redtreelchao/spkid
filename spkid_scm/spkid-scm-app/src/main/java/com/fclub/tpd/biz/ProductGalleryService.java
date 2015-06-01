package com.fclub.tpd.biz;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.ProductGallery;

public interface ProductGalleryService {

	/**
     * 根据Id查询GoodsGallery
     */
    ProductGallery get(Integer id);
    
    /**
     * 分页查询GoodsGallery
     */
    Page<ProductGallery> findPage(Page<ProductGallery> page, ProductGallery goodsGallery);
    
    /**
     * 保存GoodsGallery
     */
    void save(ProductGallery goodsGallery);
    
    /**
     * 更新GoodsGallery
     */
    void update(ProductGallery goodsGallery);
    
    /**
     * 删除GoodsGallery
     */
    void delete(Integer id);
    /**
     * 获取商品默认图
     * @param goodsId
     * @return
     */
    public ProductGallery getDefaultGalleryImg(int goodsId);
    
    void deleteByGoodsId(Integer goodsId);
    
    
}