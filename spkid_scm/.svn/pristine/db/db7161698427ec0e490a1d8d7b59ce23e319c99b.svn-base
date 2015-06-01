/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.WorkOrder;
import com.fclub.tpd.dto.WorkOrderGoods;

/**
 * 
 * @author auto-gene
 * @version $Id: WorkOrderMapper.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {

	void replyUpdate(WorkOrder workOrder);

	void deleteImage(@Param("woId") Integer id, @Param("type")Integer type);

	Integer countByWoNo(String woNo);

	Integer checkOrderSnExist(String orderSn);
	
	List<WorkOrderGoods> getOrderGoods(String orderSn);
	
}
