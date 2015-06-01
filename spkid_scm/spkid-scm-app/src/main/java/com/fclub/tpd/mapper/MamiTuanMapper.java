package com.fclub.tpd.mapper;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.MamiTuan;

public interface MamiTuanMapper extends BaseMapper<MamiTuan> {

	int getCountByProductId(Integer productId);

}
