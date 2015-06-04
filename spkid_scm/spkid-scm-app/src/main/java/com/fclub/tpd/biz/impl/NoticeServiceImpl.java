/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.NoticeService;
import com.fclub.tpd.dataobject.Notice;
import com.fclub.tpd.mapper.NoticeMapper;

/**
 * @version $Id: NoticeServiceImpl.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
    private NoticeMapper noticeMapper;
    
    @Override
    public Notice get(Integer id) {
        return noticeMapper.get(id);
    }

    @Override
    public Page<Notice> findPage(Page<Notice> page, Notice notice) {
        page.setResult(noticeMapper.findPage(page, notice));
        return page;
    }
    
    @Override
    @Transactional
    public void save(Notice notice) {
        noticeMapper.insert(notice);
    }

    @Override
    @Transactional
    public void update(Notice notice) {
        noticeMapper.update(notice);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        noticeMapper.delete(id);
    }

	@Override
	public List<Notice> queryTop() {
		return noticeMapper.queryTop();
	}
}