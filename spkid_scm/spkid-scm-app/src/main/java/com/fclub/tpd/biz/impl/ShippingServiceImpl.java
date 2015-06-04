package com.fclub.tpd.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.ShippingService;
import com.fclub.tpd.dataobject.Shipping;
import com.fclub.tpd.mapper.ShippingMapper;

@Service
public class ShippingServiceImpl implements ShippingService {

	@Autowired
    private ShippingMapper shippingMapper;
    
    @Override
    public Shipping get(Integer id) {
        return shippingMapper.get(id);
    }

    @Override
    public Page<Shipping> findPage(Page<Shipping> page, Shipping shipping) {
        
        page.setResult(shippingMapper.findPage(page, shipping));
        return page;
    }

    @Override
    public List<Shipping> findAll() {
        
        return shippingMapper.findAll();
    }
}