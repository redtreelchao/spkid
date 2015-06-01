/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.BcsImpService;
import com.fclub.tpd.biz.ProductService;
import com.fclub.tpd.dataobject.BcsImp;
import com.fclub.tpd.mapper.BcsImpMapper;

/**
 * 
 * @author auto-gene
 * @version $Id: BcsImpServiceImpl.java, v 0.1 2013-08-20 14:56:57 auto-gene Exp $
 */
@Service
public class BcsImpServiceImpl implements BcsImpService {

	@Autowired
    private BcsImpMapper bcsImpMapper;
	@Autowired
    private ProductService goodsService;
    
    @Override
    public BcsImp get(Integer id) {
        return bcsImpMapper.get(id);
    }

    @Override
    public Page<BcsImp> findPage(Page<BcsImp> page, BcsImp bcsImp) {
        
        page.setResult(bcsImpMapper.findPage(page, bcsImp));
        return page;
    }
    
    @Override
    @Transactional
    public void save(BcsImp bcsImp) {
        bcsImpMapper.insert(bcsImp);
    }

    @Override
    @Transactional
    public void update(BcsImp bcsImp) {
        bcsImpMapper.update(bcsImp);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        bcsImpMapper.delete(id);
    }
    
    @Override
    @Transactional
    public void saveOpt(BcsImp bcsImp) {
    	if (bcsImp.getImpStatus() == 1) {
    		goodsService.batchUpdateBcsUrl(bcsImp);
    	}
    	save(bcsImp);
    }
    
    @Override
    @Transactional
    public void updateOpt(BcsImp bcsImp) {
    	if (bcsImp.getImpStatus() == 1) {
    		goodsService.batchUpdateBcsUrl(bcsImp);
    	}
    	update(bcsImp);
    }
    
    @Override
    @Transactional
    public void deleteOpt(BcsImp bcsImp) {
    	bcsImp.setImageUrl("");
    	goodsService.batchUpdateBcsUrl(bcsImp);
    	delete(bcsImp.getImpId());
    }
    
    @Override
    public Integer checkExists(BcsImp bcsImp) {
    	return bcsImpMapper.checkExists(bcsImp);
    }
}