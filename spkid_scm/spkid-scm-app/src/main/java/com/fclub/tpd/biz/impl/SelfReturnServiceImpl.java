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
import com.fclub.tpd.biz.SelfReturnService;
import com.fclub.tpd.biz.SelfReturnSuggestService;
import com.fclub.tpd.dataobject.SelfReturn;
import com.fclub.tpd.dataobject.SelfReturnProduct;
import com.fclub.tpd.dataobject.SelfReturnSuggest;
import com.fclub.tpd.mapper.SelfReturnMapper;

/**
 * 
 * @author auto-gene
 * @version $Id: SelfReturnServiceImpl.java, v 0.1 2013-07-12 16:18:00 auto-gene Exp $
 */
@Service
public class SelfReturnServiceImpl implements SelfReturnService {

	@Autowired
    private SelfReturnMapper selfReturnMapper;
	@Autowired
	private SelfReturnSuggestService selfReturnSuggestService;
    
    @Override
    public SelfReturn get(Integer id) {
        return selfReturnMapper.get(id);
    }

    @Override
    public Page<SelfReturn> findPage(Page<SelfReturn> page, SelfReturn selfReturn) {
        
        page.setResult(selfReturnMapper.findPage(page, selfReturn));
        return page;
    }
    
    @Override
    @Transactional
    public void save(SelfReturn selfReturn) {
        selfReturnMapper.insert(selfReturn);
    }

    @Override
    @Transactional
    public void update(SelfReturn selfReturn) {
        selfReturnMapper.update(selfReturn);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        selfReturnMapper.delete(id);
    }

	@Override
	public List<SelfReturnProduct> getSelfReturnProduct(Integer id) {
		return selfReturnMapper.getSelfReturnProduct(id);
	}

	@Override
	public List<SelfReturnSuggest> getSelfReturnSuggest(Integer id) {
		return selfReturnMapper.getSelfReturnSuggest(id);
	}

    @Override
    public void saveSuggest(SelfReturnSuggest selfReturnSuggest) {
        
        SelfReturn selfReturn = new SelfReturn();
        selfReturn.setApplyId(selfReturnSuggest.getApplyId());
        selfReturn.setProviderStatus(1);
        update(selfReturn);
        selfReturnSuggestService.save(selfReturnSuggest);
    }
}