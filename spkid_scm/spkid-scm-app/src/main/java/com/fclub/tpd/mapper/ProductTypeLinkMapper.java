package com.fclub.tpd.mapper;

import java.util.List;
import java.util.Map;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ProductTypeLink;

public interface ProductTypeLinkMapper extends BaseMapper<ProductTypeLink> {

	void deleteByGoodsId(Integer goodsId);

	void insert(Map<String, Integer> param);

	boolean confirmGoodsTypeExits(Integer goodsId);

	List<Integer> selectTypesByGoodsId(Integer goodsId);
	
	List<String> selectGoodsTypeNamesByGoodsId(Integer goodsId);

	/*
	 * // 查询 Integer getOneTypeLinkId(Integer goodsId);
	 * 
	 * // 更新 Integer updateTypeLinkById(Map map);
	 * 
	 * // 增加 In teger insertTypeLink(String[] strArr);
	 */
}