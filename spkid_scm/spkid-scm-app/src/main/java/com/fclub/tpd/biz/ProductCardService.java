/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.ProductCard;

/**
 * 
 * @author auto-gene
 * @version $Id: ProductCardService.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface ProductCardService {

	/**
     * 根据Id查询ProductCard
     */
	ProductCard get(Integer id);
    
    /**
     * 分页查询ProductCard
     */
    Page<ProductCard> findPage(Page<ProductCard> page, ProductCard productCard);
    
    /**
     * 保存ProductCard
     */
    void save(ProductCard productCard);
    
    /**
     * 更新ProductCard
     */
    void update(ProductCard productCard);
    
    /**
     * 删除ProductCard
     */
    void delete(Integer id);

    /**
     * 批量更新ProductCard
     */
    void batchUpdate(List<ProductCard> cardList);

	void batchUpdate(String ids);

	void batchDelete(String ids);

	void export(ProductCard productCard, HttpServletResponse response);
}