/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.WorkOrder;
import com.fclub.tpd.dto.WorkOrderGoods;

/**
 * 
 * @author auto-gene
 * @version $Id: WorkOrderService.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
public interface WorkOrderService {

	/**
     * 根据Id查询WorkOrder
     */
    WorkOrder get(Integer id);
    
    /**
     * 分页查询WorkOrder
     */
    Page<WorkOrder> findPage(Page<WorkOrder> page, WorkOrder workOrder);
    
    /**
     * 保存WorkOrder
     */
    void save(WorkOrder workOrder);
    
    /**
     * 更新WorkOrder
     */
    void update(WorkOrder workOrder);
    
    /**
     * 删除WorkOrder
     */
    void delete(Integer id);
    
    /**
     * 更新回复内容，回复人，回复时间
     * @param workOrder
     */
	void replyUpdate(WorkOrder workOrder);

	/**
     * 删除WorkOrder里面的图片
     */
    void deleteImage(Integer id,Integer type);

    /**
     * 检查工单号是否为唯一
     * @param woNo
     * @return 
     */
    boolean checkWoNoUnique(String woNo);

	Integer checkOrderSnExist(String orderSn);
	
	/**
	 * 根据订单号查询相关商品，并带出供应商
	 */
    List<WorkOrderGoods> getOrderGoods(String orderSn);

}