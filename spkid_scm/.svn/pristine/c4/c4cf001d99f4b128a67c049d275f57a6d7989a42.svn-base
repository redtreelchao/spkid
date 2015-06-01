/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.Notice;

/**
 * 
 * @author auto-gene
 * @version $Id: NoticeService.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface NoticeService {

	/**
     * 根据Id查询Notice
     */
    Notice get(Integer id);
    
    /**
     * 分页查询Notice
     */
    Page<Notice> findPage(Page<Notice> page, Notice notice);
    
    /**
     * 保存Notice
     */
    void save(Notice notice);
    
    /**
     * 更新Notice
     */
    void update(Notice notice);
    
    /**
     * 删除Notice
     */
    void delete(Integer id);
    
    List<Notice> queryTop();
}