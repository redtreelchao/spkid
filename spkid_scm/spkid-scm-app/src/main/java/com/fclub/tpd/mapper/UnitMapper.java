package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.Page;
import com.fclub.common.dal.interceptor.PageInterceptor;
import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Unit;

public interface UnitMapper extends BaseMapper<Unit> {
	int deleteByPrimaryKey(Integer unitId);

	int insertSelective(Unit record);

	List<Unit> selectByPrimaryKey(Integer unitId);

	int updateByPrimaryKeySelective(Unit record);

	Unit selectUnitByPrimaryKey(Unit unit);

	Integer selectUnitCount(Unit unit);

	Unit selectByUnitCode(String unitCode);

	Unit selectByUnitName(String unitName);

	boolean confirmBeingUsed(Integer unitId);

	Unit selectUnitCodeWithOutId(Unit unit);

	Unit selectUnitNameWithOutId(Unit unit);

	// 查询兼分页
	List<Unit> selectUnitList(@Param(PageInterceptor.PAGE_KEY) Page<Unit> page,
			@Param(PARAM_KEY) Unit unit);

	List<Unit> queryAll();

	// 查询单个的unitId;
	Integer getUnitIdByCode(String unitCode);
}