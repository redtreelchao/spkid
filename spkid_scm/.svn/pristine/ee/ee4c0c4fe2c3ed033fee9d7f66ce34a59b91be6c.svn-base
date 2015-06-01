/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.CooperationTipsService;
import com.fclub.tpd.dataobject.CooperationTips;
import com.fclub.tpd.mapper.CooperationTipsMapper;

/**
 * 
 * @author auto-gene
 * @version $Id: CooperationTipsServiceImpl.java, v 0.1 2013-06-28 15:30:48 auto-gene Exp $
 */
@Service
public class CooperationTipsServiceImpl implements CooperationTipsService {

	@Autowired
    private CooperationTipsMapper cooperationTipsMapper;
    
    @Override
    public CooperationTips get(Integer id) {
        return cooperationTipsMapper.get(id);
    }

    @Override
    public Page<CooperationTips> findPage(Page<CooperationTips> page, CooperationTips cooperationTips) {
        
        page.setResult(cooperationTipsMapper.findPage(page, cooperationTips));
        return page;
    }
    
    @Override
    @Transactional
    public void save(CooperationTips cooperationTips) {
        cooperationTipsMapper.insert(cooperationTips);
    }

    @Override
    @Transactional
    public void update(CooperationTips cooperationTips) {
        cooperationTipsMapper.update(cooperationTips);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        cooperationTipsMapper.delete(id);
    }
}