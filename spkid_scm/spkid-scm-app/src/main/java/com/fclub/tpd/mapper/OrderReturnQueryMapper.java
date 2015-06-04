/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fclub.common.dal.mapper.BaseMapper;

/**
 * 
 * @author auto-gene
 * @version $Id: OrderReturnQueryMapper.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface OrderReturnQueryMapper extends BaseMapper<String> {

	List<String> queryReturnSnByOrderSn(@Param("orderSn") String orderSn, @Param("providerId") Integer providerId);
	
	String findOrderSnByReturnSn(@Param("returnSn") String returnSn, @Param("providerId") Integer providerId);
}
