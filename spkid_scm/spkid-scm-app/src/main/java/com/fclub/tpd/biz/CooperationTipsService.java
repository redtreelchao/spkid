/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.CooperationTips;

/**
 * 
 * @author auto-gene
 * @version $Id: CooperationTipsService.java, v 0.1 2013-06-28 15:30:48 auto-gene Exp $
 */
public interface CooperationTipsService {

	/**
     * 根据Id查询CooperationTips
     */
    CooperationTips get(Integer id);
    
    /**
     * 分页查询CooperationTips
     */
    Page<CooperationTips> findPage(Page<CooperationTips> page, CooperationTips cooperationTips);
    
    /**
     * 保存CooperationTips
     */
    void save(CooperationTips cooperationTips);
    
    /**
     * 更新CooperationTips
     */
    void update(CooperationTips cooperationTips);
    
    /**
     * 删除CooperationTips
     */
    void delete(Integer id);
}