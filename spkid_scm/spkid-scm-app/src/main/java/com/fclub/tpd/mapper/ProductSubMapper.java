package com.fclub.tpd.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ProductCard;
import com.fclub.tpd.dataobject.ProductSub;

public interface ProductSubMapper extends BaseMapper<ProductSub> {

	List<ProductSub> selectByGoodsId(int goodsId);
	
	List<ProductSub> findByGoodsId(Integer goodsId);
	
	Integer findByGoodsIdColorIdSizeId(Map<String, Object> param);
	
	Integer findByGoodsIdWithOutColorId(Map<String, Object> param);
	
	ProductSub findByGoodsColorSizeId(@Param("goodsId") Integer goodsId, 
	                                  @Param("colorId") Integer colorId, 
	                                  @Param("sizeId") Integer sizeId);
	
	void clearConsignNum(List<Integer> goodsIdList);
	void clearConsignNumTpd(List<Integer> goodsIdList);
	
    void batchUpdate(List<ProductSub> laborList);
    void batchUpdateTpd(List<ProductSub> laborList);
    
    List<ProductSub> selectByGoodsIdGroupByColorId(Integer goodsId);
    
    void deleteByGoodsId(Integer goodsId);
    
    //----- 批量导图中使用 -----//
    Integer getCountByGoodsAndColor(@Param("goodsId")Integer goodsId, @Param("colorId")Integer colorId);

    
	void batchUpdateConsignNum(List<ProductSub> subList);

	void batchUpdateConsignNumByCard(List<ProductCard> cardList);
	
}
