/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.BcsImp;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dataobject.ProductGallery;
import com.fclub.tpd.dataobject.ProductSub;

/**
 * 
 * @author auto-gene
 * @version $Id: GoodsService.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface ProductService {

	/**
     * 根据Id查询Goods
     */
    Product get(Integer id);
    
    /**
     * 分页查询Goods
     */
    Page<Product> findPage(Page<Product> page, Product goods);
    
    /**
     * 保存Goods
     */
    void save(Product goods);
    
    /**
     * 更新Goods
     */
    void update(Product goods);
    
    /**
     * 删除Goods
     */
    void delete(Integer id);
    
    /**
     * 获取默认商品图，用作列表页展示
     * @param goodsId
     * @return
     */
    ProductGallery getDefaultImg(int goodsId);
    
    /**
     * 查询商品列表
     * @param page
     * @param goods
     * @return
     */
    Page<Product> queryGoodsByPage(Page<Product> page, Product goods);
    
    /**
     * 针对商品信息做验证,提供给前端JS
     * @param goods
     */
    void validation(Product goods);
    
    /**
     * 根据ID查询ExtGoods
     * @param id
     * @return
     */
    Product getExtGoodsById(Integer id);
    
    /**
     * 批量提交审核
     * @param ids
     */
    @Transactional
    void batchSubmit(String ids);
    
    @Transactional
    void deleteGoods(Integer goodsId);
    
    @Transactional
    void batchDelete(String ids);
    
    void exportGoodsData(HttpServletResponse response, Product goods);
    
    /**
     * 尺寸图批量导入
     * @param bcsImp
     */
    void batchUpdateBcsUrl(BcsImp bcsImp);
    
    /**
     * 批量审核
     */
    @Transactional
    void check(Integer adminId, Integer... ids);
    
    /**
     * 批量驳回
     */
    @Transactional
    void reject(Integer... ids);

    @Transactional
	void online(Integer adminId, Integer... ids);
    
    @Transactional
	void offline(Integer adminId, Integer... ids);

    @Transactional
	void updateConsignNum(ProductSub sub);

    @Transactional
	void updateByScmProductId(Product product);
    
}