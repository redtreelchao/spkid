package com.fclub.tpd.mapper;

import java.util.List;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Region;

public interface RegionMapper extends BaseMapper<Region> {

	public List<Region> selectAll();
	
}
