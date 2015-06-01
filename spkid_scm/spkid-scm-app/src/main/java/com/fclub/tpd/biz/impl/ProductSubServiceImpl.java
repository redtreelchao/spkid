package com.fclub.tpd.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.ProductSubService;
import com.fclub.tpd.dataobject.ProductSub;
import com.fclub.tpd.mapper.ProductSubMapper;

@Service
public class ProductSubServiceImpl implements ProductSubService {

	@Autowired
    private ProductSubMapper productSubMapper;
    
    @Override
    public ProductSub get(Integer id) {
        return productSubMapper.get(id);
    }

    @Override
    public Page<ProductSub> findPage(Page<ProductSub> page, ProductSub goodsLabor) {
        
        page.setResult(productSubMapper.findPage(page, goodsLabor));
        return page;
    }
    
    @Override
    @Transactional
    public void save(ProductSub goodsLabor) {
        productSubMapper.insert(goodsLabor);
    }

    @Override
    @Transactional
    public void update(ProductSub goodsLabor) {
        productSubMapper.update(goodsLabor);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        productSubMapper.delete(id);
    }

    @Override
    @Transactional
    public void batchUpdate(List<ProductSub> laborList) {
        productSubMapper.batchUpdate(laborList);
    }
}