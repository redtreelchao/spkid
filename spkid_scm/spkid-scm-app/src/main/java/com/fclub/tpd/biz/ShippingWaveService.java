/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.ShippingWave;
import com.fclub.tpd.dto.ShippingImportDTO;

/**
 * @version $Id: ShippingWaveService.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface ShippingWaveService {
    
	/**
     * 根据Id查询ShippingWave
     */
    ShippingWave get(Integer id);
    
    /**
     * 分页查询ShippingWave
     */
    Page<ShippingWave> findPage(Page<ShippingWave> page, ShippingWave shippingWave);
    
    /**
     * 保存ShippingWave
     */
    void save(ShippingWave shippingWave);
    
    /**
     * 更新ShippingWave
     */
    void update(ShippingWave shippingWave);
    
    /**
     * 删除ShippingWave
     */
    void delete(Integer id);

    /**
     * 生成波次
     */
    void generateWave(Integer providerId);

    /**
     * 批量发货
     */
    List<ShippingImportDTO> batchShipping(Integer providerId, String type, List<ShippingImportDTO> shippingList);
    
    /**
     * 更新波次打印状态
     */
    void updatePrintStatus(String waveSn);
}