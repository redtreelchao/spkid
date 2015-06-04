package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ProductCard;

public interface ProductCardMapper extends BaseMapper<ProductCard> {

	void batchInsert(List<ProductCard> cardList);

	int getCountBySubNoPwd(@Param("subId") Integer glId, @Param("cardNo") String cardNo, @Param("cardPwd") String cardPwd);

	void batchUpdate(List<ProductCard> cardList);

	List<ProductCard> queryExportCardData(ProductCard productCard);

}
