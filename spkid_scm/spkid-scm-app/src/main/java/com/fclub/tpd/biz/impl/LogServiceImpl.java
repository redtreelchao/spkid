/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.LogService;
import com.fclub.tpd.dataobject.Log;
import com.fclub.tpd.mapper.LogMapper;

/**
 * 
 * @author auto-gene
 * @version $Id: LogServiceImpl.java, v 0.1 2013-06-28 15:30:48 auto-gene Exp $
 */
@Service
public class LogServiceImpl implements LogService {

	@Autowired
    private LogMapper logMapper;
    
    @Override
    public Log get(Integer id) {
        return logMapper.get(id);
    }

    @Override
    public Page<Log> findPage(Page<Log> page, Log log) {
        
        page.setResult(logMapper.findPage(page, log));
        return page;
    }
    
    @Override
    @Transactional
    public void save(Log log) {
        logMapper.insert(log);
    }

    @Override
    @Transactional
    public void update(Log log) {
        logMapper.update(log);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        logMapper.delete(id);
    }
}