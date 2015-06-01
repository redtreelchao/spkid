/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.ShippingPacketService;
import com.fclub.tpd.dataobject.ShippingProduct;
import com.fclub.tpd.dataobject.ShippingPacket;
import com.fclub.tpd.dto.ShippingStatDTO;
import com.fclub.tpd.mapper.ShippingPacketMapper;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingPacketServiceImpl.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
@Service
public class ShippingPacketServiceImpl implements ShippingPacketService {

    @Autowired
    private ShippingPacketMapper shippingPacketMapper;

    @Override
    public ShippingPacket get(Integer id) {
        return shippingPacketMapper.get(id);
    }

    @Override
    public Page<ShippingPacket> findPage(Page<ShippingPacket> page, ShippingPacket shippingPacket) {

        List<ShippingPacket> shippingPackets = shippingPacketMapper.findPage(page, shippingPacket);
        if (shippingPackets.size() == 0) {
            return page;
        }

        List<Integer> orderIds = new ArrayList<>(shippingPackets.size());
        for (ShippingPacket sp : shippingPackets) {
            orderIds.add(sp.getOrderId());
        }

        List<ShippingProduct> shippingGoodsList = shippingPacketMapper.getShippingGoods(
            shippingPacket.getProviderId(), orderIds);
        for (ShippingPacket sp : shippingPackets) {
            List<ShippingProduct> goodsList = new ArrayList<>();
            for (ShippingProduct sg : shippingGoodsList) {
                if (sg.getOrderId().equals(sp.getOrderId())) {
                    goodsList.add(sg);
                }
            }
            sp.setProductList(goodsList);
        }

        page.setResult(shippingPackets);
        return page;
    }

    @Override
    @Transactional
    public void save(ShippingPacket shippingPacket) {
        shippingPacketMapper.insert(shippingPacket);
    }

    @Override
    @Transactional
    public void update(ShippingPacket shippingPacket) {
        shippingPacketMapper.update(shippingPacket);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        shippingPacketMapper.delete(id);
    }

    @Override
    public List<ShippingPacket> getShippingPacket(ShippingPacket shippingPacket) {

        List<ShippingPacket> shippingPackets = shippingPacketMapper
            .getShippingPacket(shippingPacket);

        if (shippingPackets.size() == 0) {
            return shippingPackets;
        }

        List<Integer> orderIds = new ArrayList<>(shippingPackets.size());
        for (ShippingPacket sp : shippingPackets) {
            orderIds.add(sp.getOrderId());
        }

        List<ShippingProduct> shippingGoodsList = shippingPacketMapper.getShippingGoods(
            shippingPacket.getProviderId(), orderIds);
        for (ShippingPacket sp : shippingPackets) {
            List<ShippingProduct> goodsList = new ArrayList<>();
            for (ShippingProduct sg : shippingGoodsList) {
                if (sg.getOrderId().equals(sp.getOrderId())) {
                    goodsList.add(sg);
                }
            }
            sp.setProductList(goodsList);
        }

        return shippingPackets;
    }

    @Override
    public List<ShippingPacket> getExportOrder(Integer providerId) {
        return shippingPacketMapper.getExportOrder(providerId);
    }

    @Override
    public Integer getExportOrderNum(Integer providerId) {
        return shippingPacketMapper.getExportOrderNum(providerId);
    }

    @Override
    public List<ShippingPacket> getProviderPacket(Integer providerId, String orderSn) {
        return shippingPacketMapper.getProviderPacket(providerId, orderSn);
    }

    @Override
    public void updatePacketShortage(Integer providerId, List<Integer> orderIds) {
        shippingPacketMapper.updatePacketShortage(providerId, orderIds);
    }

    @Override
    public void updatePacketShipping(ShippingPacket shippingPacket) {
        shippingPacketMapper.updatePacketShipping(shippingPacket);
    }

    @Override
    public List<ShippingPacket> findExportOrder(ShippingPacket shippingPacket) {
        List<ShippingPacket> shippingPackets = shippingPacketMapper.findPage(null, shippingPacket);
        if (shippingPackets.size() == 0) {
            return null;
        }

        List<Integer> orderIds = new ArrayList<>(shippingPackets.size());
        for (ShippingPacket sp : shippingPackets) {
            orderIds.add(sp.getOrderId());
        }

        List<ShippingProduct> shippingGoodsList = shippingPacketMapper.getShippingGoods(
            shippingPacket.getProviderId(), orderIds);
        for (ShippingPacket sp : shippingPackets) {
            List<ShippingProduct> goodsList = new ArrayList<>();
            for (ShippingProduct sg : shippingGoodsList) {
                if (sg.getOrderId().equals(sp.getOrderId())) {
                    goodsList.add(sg);
                }
            }
            sp.setProductList(goodsList);
        }
        return shippingPackets;
    }

    @Override
    public List<ShippingProduct> findExportGoods(ShippingPacket shippingPacket) {

        List<ShippingProduct> result = new ArrayList<>();
        List<ShippingPacket> shippingPackets = shippingPacketMapper.findPage(null, shippingPacket);
        if (shippingPackets.size() == 0) {
            return null;
        }

        List<Integer> orderIds = new ArrayList<>(shippingPackets.size());
        for (ShippingPacket sp : shippingPackets) {
            orderIds.add(sp.getOrderId());
        }

        List<ShippingProduct> shippingGoodsList = shippingPacketMapper.getShippingGoods(
            shippingPacket.getProviderId(), orderIds);
        result.addAll(shippingGoodsList);

        return result;
    }

    @Override
    public ShippingStatDTO statOrder(ShippingPacket shippingPacket) {

        ShippingStatDTO dto = shippingPacketMapper.statOrder(shippingPacket);
        if(shippingPacket.getStatus() == null) {
            ShippingStatDTO validDto = shippingPacketMapper.statValidOrder(shippingPacket);
            dto.setValidOrderNumber(validDto.getValidOrderNumber());
            dto.setValidOrderAmount(validDto.getValidOrderAmount());
        } else if(shippingPacket.getStatus() == 1) {
            dto.setValidOrderNumber(dto.getTotalOrderNumber());
            dto.setValidOrderAmount(dto.getTotalOrderAmount());
        } else {
            dto.setValidOrderNumber(0);
            dto.setValidOrderAmount(BigDecimal.ZERO);
        }
        return dto;
    }

    @Override
    public List<ShippingPacket> getPacketCount(List<String> waveSnList) {
        return shippingPacketMapper.getPacketCount(waveSnList);
    }

    @Override
    public List<ShippingProduct> findExport(ShippingPacket shippingPacket) {
        return shippingPacketMapper.findExport(shippingPacket);
    }

    @Override
    public boolean existsInvoiceNo(String orderSn, String packetSn) {
        int count = shippingPacketMapper.getCountByInvoiceNo(orderSn, packetSn);
        return count > 0;
    }

	@Override
	public int batchInsertNotExists(Integer providerId, String waveSn, List<Integer> orderIdList) {
		if (orderIdList == null || orderIdList.isEmpty()) {
			return 0;
		}
		
		return shippingPacketMapper.batchInsertNotExists(providerId, waveSn, orderIdList);
	}
	
}