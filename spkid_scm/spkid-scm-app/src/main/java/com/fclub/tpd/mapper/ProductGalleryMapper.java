/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ProductGallery;

/**
 * @version $Id: GoodsGalleryMapper.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface ProductGalleryMapper extends BaseMapper<ProductGallery> {
    ProductGallery getDefaultGallery(int goodsId);
    
    List<ProductGallery> selectByGoodsId(Map<String, Object> param);
    
    void deleteByGoodsId(Integer goodsId);
    
    //----- 批量导图中使用 -----//
    Integer getCountByGoodsColorImg(@Param("goodsId") Integer goodsId,
                                    @Param("colorId") Integer colorId,
                                    @Param("imgDefault") String imgDefault);
    
    List<ProductGallery> getByGoodsColor(@Param("goodsId") Integer goodsId,
            @Param("colorId") Integer colorId);

    void deleteByGoodsColor(@Param("goodsId") Integer goodsId, @Param("colorId") Integer colorId);

	List<ProductGallery> selectGalleryByGoodsId(Integer goodsId);
    
}
