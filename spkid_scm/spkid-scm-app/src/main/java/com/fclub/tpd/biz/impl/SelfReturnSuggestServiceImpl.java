/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.SelfReturnSuggestService;
import com.fclub.tpd.dataobject.SelfReturnSuggest;
import com.fclub.tpd.mapper.SelfReturnSuggestMapper;

/**
 * 
 * @author auto-gene
 * @version $Id: SelfReturnSuggestServiceImpl.java, v 0.1 2013-07-24 19:11:11 auto-gene Exp $
 */
@Service
public class SelfReturnSuggestServiceImpl implements SelfReturnSuggestService {

	@Autowired
    private SelfReturnSuggestMapper selfReturnSuggestMapper;
    
    @Override
    public SelfReturnSuggest get(Integer id) {
        return selfReturnSuggestMapper.get(id);
    }

    @Override
    public Page<SelfReturnSuggest> findPage(Page<SelfReturnSuggest> page, SelfReturnSuggest selfReturnSuggest) {
        
        page.setResult(selfReturnSuggestMapper.findPage(page, selfReturnSuggest));
        return page;
    }
    
    @Override
    @Transactional
    public void save(SelfReturnSuggest selfReturnSuggest) {
        selfReturnSuggestMapper.insert(selfReturnSuggest);
    }

    @Override
    @Transactional
    public void update(SelfReturnSuggest selfReturnSuggest) {
        selfReturnSuggestMapper.update(selfReturnSuggest);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        selfReturnSuggestMapper.delete(id);
    }
}