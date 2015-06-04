/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.List;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Shipping;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingMapper.java, v 0.1 2013-07-03 15:58:45 auto-gene Exp $
 */
public interface ShippingMapper extends BaseMapper<Shipping> {

    List<Shipping> findAll();

}
