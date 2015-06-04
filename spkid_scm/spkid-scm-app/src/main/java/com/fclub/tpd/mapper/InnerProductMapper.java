package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.tpd.dataobject.Batch;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dataobject.ProductCard;
import com.fclub.tpd.dataobject.ProductSub;

/**
 * @version $Id: GoodsMapper.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface InnerProductMapper {

	void copyProductInfo(@Param("productId") Integer productId, 
						 @Param("adminId") Integer adminId);

	void copyProductType(Integer productId);

	void copyProductSub(@Param("productId") Integer productId, 
						@Param("adminId") Integer adminId);

	void copyProductGallery(@Param("productId") Integer productId, 
							@Param("adminId") Integer adminId);

	void batchOnsale(@Param("ids") Integer[] ids, @Param("on") boolean on);

	void batchInsertOnsaleRecord(@Param("ids") Integer[] ids, 
								 @Param("on") boolean on, 
								 @Param("adminId") Integer adminId);

	
	void copyProductCost(@Param("productId") Integer productId, 
						 @Param("batchId") Integer batchId, 
						 @Param("providerId") Integer providerId,
						 @Param("adminId") Integer adminId);
	
	
	
	Integer selectBatchId(@Param("providerId") Integer providerId, 
						  @Param("brandId") Integer brandId);

	void insertBatch(Batch batch);
    
	
	
	Product selectProductBySn(String productSn);

	Integer selectColorIdByName(String colorName);

	Integer selectSizeIdByName(String sizeName);

	ProductSub selectByPCSId(@Param("productId") Integer productId, 
							 @Param("colorId") Integer colorId, 
							 @Param("sizeId") Integer sizeId);
	
	void batchUpdateConsignNum(List<ProductSub> subList);

	void updateByScmProdutId(Product product);
	
	
	ProductSub selectProductSub(Integer glId);

	void batchUpdateConsignNumByCard(List<ProductCard> cardList);
	
	String selectProductSnById(Integer productId);

}
