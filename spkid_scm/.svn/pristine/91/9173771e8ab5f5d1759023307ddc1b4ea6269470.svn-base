/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.SelfReturn;
import com.fclub.tpd.dataobject.SelfReturnProduct;
import com.fclub.tpd.dataobject.SelfReturnSuggest;

/**
 * 
 * @author auto-gene
 * @version $Id: SelfReturnService.java, v 0.1 2013-07-12 16:18:00 auto-gene Exp $
 */
public interface SelfReturnService {

	/**
     * 根据Id查询SelfReturn
     */
    SelfReturn get(Integer id);
    
    /**
     * 分页查询SelfReturn
     */
    Page<SelfReturn> findPage(Page<SelfReturn> page, SelfReturn selfReturn);
    
    /**
     * 保存SelfReturn
     */
    void save(SelfReturn selfReturn);
    
    /**
     * 更新SelfReturn
     */
    void update(SelfReturn selfReturn);
    
    /**
     * 删除SelfReturn
     */
    void delete(Integer id);

	List<SelfReturnProduct> getSelfReturnProduct(Integer id);

	List<SelfReturnSuggest> getSelfReturnSuggest(Integer id);

	/**
	 * 审核意见
	 */
    void saveSuggest(SelfReturnSuggest selfReturnSuggest);
}