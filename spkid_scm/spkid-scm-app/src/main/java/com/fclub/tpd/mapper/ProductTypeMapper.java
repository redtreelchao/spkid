package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.Page;
import com.fclub.common.dal.interceptor.PageInterceptor;
import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ProductType;

public interface ProductTypeMapper extends BaseMapper<ProductType> {

	ProductType selectL2ByCode(String typeCode);
	
	List<ProductType> getLevelOneTypes();

	List<ProductType> getLevelTwoTypes(Integer levelOneTypeId);

	List<ProductType> getLevelThreeTypes(Integer levelTwoTypeId);

	List<ProductType> getAllLevelThreeTypes();

	List<ProductType> getAllTypesByGoodsId(Integer goodsId);

	String getGoodsTypeByGoodsId(Integer goodsId);

	/**
	 * 分页方法 mapper key = 'findPage'
	 * 
	 * @param page
	 *            分页参数
	 * @param obj
	 *            查询条件参数
	 */
	List<ProductType> findPage(
			@Param(PageInterceptor.PAGE_KEY) Page<ProductType> page,
			@Param(PARAM_KEY) Object obj, @Param("keyWord") String keyWord,
			@Param("goods_type_1") int goods_type_1);

	ProductType selectByTypeCode(ProductType record);

	/**
	 * 
	 * @param typeId
	 * @return
	 */
	boolean confirmBeingUsed1(int typeId);

	/**
	 * 
	 * @param typeId
	 * @return
	 */
	boolean confirmBeingUsed2(int typeId);

	/**
	 * 
	 * @param page
	 * @param parentId
	 * @return
	 */
	List<ProductType> findListPage(
			@Param(PageInterceptor.PAGE_KEY) Page<ProductType> page,
			@Param("parentId") int parentId);

	// 查询
	List<Integer> selectOneTypeId(String[] strArray);

	/**
	 * 根据一级分类的ID，查询关联正在进行的显示抢购的二级分类列表。
	 * 
	 * @param typeId
	 *            一级分类ID。
	 * 
	 * @return 具有正在进行的显示抢购的二级分类列表。
	 */
	public List<ProductType> selectRushingGoodsTypeList(Integer typeId);
	
	public String findTypeNameById(Integer id);

	List<ProductType> getLevelOneTypesWithoutGift();
}