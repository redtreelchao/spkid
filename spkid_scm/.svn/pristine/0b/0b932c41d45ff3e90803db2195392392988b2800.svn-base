/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.BcsImp;

/**
 * 
 * @author auto-gene
 * @version $Id: BcsImpService.java, v 0.1 2013-08-20 14:56:57 auto-gene Exp $
 */
public interface BcsImpService {

	/**
     * 根据Id查询BcsImp
     */
    BcsImp get(Integer id);
    
    /**
     * 分页查询BcsImp
     */
    Page<BcsImp> findPage(Page<BcsImp> page, BcsImp bcsImp);
    
    /**
     * 保存BcsImp
     */
    void save(BcsImp bcsImp);
    
    /**
     * 更新BcsImp
     */
    void update(BcsImp bcsImp);
    
    /**
     * 删除BcsImp
     */
    void delete(Integer id);

    /**
     * 保存BcsImp相关操作
     */
    void saveOpt(BcsImp bcsImp);

    /**
     * 更新BcsImp相关操作
     */
    void updateOpt(BcsImp bcsImp);

    /**
     * 删除BcsImp相关操作
     */
    void deleteOpt(BcsImp bcsImp);

	/**
	 * 检查尺寸图是否已导入
	 * @param bcsImp
	 */
    Integer checkExists(BcsImp bcsImp);
}