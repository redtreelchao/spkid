/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.Log;

/**
 * 
 * @author auto-gene
 * @version $Id: LogService.java, v 0.1 2013-06-28 15:30:48 auto-gene Exp $
 */
public interface LogService {

	/**
     * 根据Id查询Log
     */
    Log get(Integer id);
    
    /**
     * 分页查询Log
     */
    Page<Log> findPage(Page<Log> page, Log log);
    
    /**
     * 保存Log
     */
    void save(Log log);
    
    /**
     * 更新Log
     */
    void update(Log log);
    
    /**
     * 删除Log
     */
    void delete(Integer id);
}