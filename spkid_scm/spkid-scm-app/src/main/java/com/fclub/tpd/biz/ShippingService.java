/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.Shipping;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingService.java, v 0.1 2013-07-03 15:58:45 auto-gene Exp $
 */
public interface ShippingService {

	/**
     * 根据Id查询Shipping
     */
    Shipping get(Integer id);
    
    /**
     * 分页查询Shipping
     */
    Page<Shipping> findPage(Page<Shipping> page, Shipping shipping);
    
    /**
     * 查询所有
     */
    List<Shipping> findAll();
}