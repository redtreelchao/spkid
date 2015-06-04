/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.ProductSub;

/**
 * 
 * @author auto-gene
 * @version $Id: GoodsLaborService.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface ProductSubService {

	/**
     * 根据Id查询GoodsLabor
     */
    ProductSub get(Integer id);
    
    /**
     * 分页查询GoodsLabor
     */
    Page<ProductSub> findPage(Page<ProductSub> page, ProductSub goodsLabor);
    
    /**
     * 保存GoodsLabor
     */
    void save(ProductSub goodsLabor);
    
    /**
     * 更新GoodsLabor
     */
    void update(ProductSub goodsLabor);
    
    /**
     * 删除GoodsLabor
     */
    void delete(Integer id);

    /**
     * 批量更新GoodsLaboe
     */
    void batchUpdate(List<ProductSub> laborList);
}