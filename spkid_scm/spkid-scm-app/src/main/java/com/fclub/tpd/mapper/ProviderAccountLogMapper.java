package com.fclub.tpd.mapper;

import java.util.List;
import java.util.Map;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ProviderAccountLog;

public interface ProviderAccountLogMapper extends
		BaseMapper<ProviderAccountLog> {
	public List<ProviderAccountLog> queryAccountLog(Map<String, Object> map);
}