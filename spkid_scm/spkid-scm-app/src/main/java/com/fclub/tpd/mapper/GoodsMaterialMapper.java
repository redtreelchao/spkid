package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.GoodsMaterial;

public interface GoodsMaterialMapper extends BaseMapper<GoodsMaterial> {
	int deleteByPrimaryKey(Integer materId);

	int insertSelective(GoodsMaterial record);

	GoodsMaterial selectByPrimaryKey(Integer materId);

	int updateByPrimaryKeySelective(GoodsMaterial record);

	List<GoodsMaterial> query();

	boolean confirmBeingUsed(Integer materId);

	GoodsMaterial selectByMaterCode(GoodsMaterial record);

	GoodsMaterial selectByMaterName(GoodsMaterial record);

	GoodsMaterial selectCodeWithOutId(GoodsMaterial record);

	GoodsMaterial selectNameWithOutId(GoodsMaterial record);

	// 查询单个的materId
	Integer getMaterIdByName(String materCode);

	/**
	 * 根据Id查询出图片地址
	 * 
	 * @param materId
	 * @return
	 */
	String selecImgName(int materId);

	/**
	 * 更新图片地址
	 * 
	 * @param materId
	 * @return
	 */
	int updateImgName(int materId);
	
	/**
	 * 根据ID数组查询
	 */
	List<GoodsMaterial> findByIds(@Param("ids") String[] ids);
}