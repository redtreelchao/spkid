/**
 * fclub.cn
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dto.TypeLinkQueryDTO;

/**
 * 前台分类管理Service
 * @author baolm
 * @version $Id: GoodsTypeLinkService.java, v 0.1 2012-7-24 下午12:37:35 baolm Exp $
 */
public interface ProductTypeLinkService {

    /**
     * 分页查询Goods
     */
    Page<Product> findPage(Page<Product> page, TypeLinkQueryDTO queryDTO);
    
    void setGoodsType(Integer goodsId, String[] typeIds);
    
    boolean confirmGoodsTypeExits(Integer goodsId);
    
    void deleteGoodsTypeLinkByGoodsId(Integer goodsId);
    
    List<Integer> selectTypesByGoodsId(Integer goodsId);
}
