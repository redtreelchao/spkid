/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.Page;
import com.fclub.common.dal.interceptor.PageInterceptor;
import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.batch.importing.dto.GoodsData;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dto.TypeLinkQueryDTO;

/**
 * 
 * @author auto-gene
 * @version $Id: GoodsMapper.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface ProductMapper extends BaseMapper<Product> {
    
    Integer findByGoodsSn(String goodsSn);
    
    Integer findMaxGoodsSn(Map<String, Object> param);
    
    Product getExtraGoodsBySn(String goodsSn);
    /**
     * 分页查询前台分类关联
     */
    List<Product> findGoodsTypePage(@Param(PageInterceptor.PAGE_KEY) Page<Product> page,
                                  @Param(BaseMapper.PARAM_KEY) TypeLinkQueryDTO queryDTO);
    
    List<Product> queryGoodsByPage(@Param(PageInterceptor.PAGE_KEY) Page<Product> page,
            @Param(BaseMapper.PARAM_KEY) Product goods);
    
    Product selectExtGoodsById(Integer id);
    
    /**
     * 查询商品颜色尺寸信息
     * */
    List<GoodsData> getGoodsColorSizeDataList(String[] goodsArray);
    /**
     * 查询商品颜色尺寸信息
     * */
    List<GoodsData> getGoodsDataList(String[] goodsArray);
    
    Product findTpdGoodsBySn(String goodsSn);
    
    void deleteGoodsBcs(Integer goodsId);
    
    List<Product> queryExportGoodsData(Product goods);
    
	void updateByBcsImp(Product goods);

	void batchOnsale(@Param("ids") Integer[] ids, @Param("on") boolean on);
	
}
