/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import static com.fclub.tpd.helper.ConstantsHelper.getPicRootPath;
import static com.fclub.tpd.helper.ConstantsHelper.getWorkOrderUploadDir;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.WorkOrderService;
import com.fclub.tpd.dataobject.WorkOrder;
import com.fclub.tpd.dto.WorkOrderGoods;
import com.fclub.tpd.mapper.WorkOrderMapper;

/**
 * 
 * @author auto-gene
 * @version $Id: WorkOrderServiceImpl.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
@Service
public class WorkOrderServiceImpl implements WorkOrderService {

	@Autowired
    private WorkOrderMapper workOrderMapper;
    
    @Override
    public WorkOrder get(Integer id) {
        return workOrderMapper.get(id);
    }

    @Override
    public Page<WorkOrder> findPage(Page<WorkOrder> page, WorkOrder workOrder) {
        
        page.setResult(workOrderMapper.findPage(page, workOrder));
        return page;
    }
    
    @Override
    @Transactional
    public void save(WorkOrder workOrder) {
        workOrderMapper.insert(workOrder);
    }

    @Override
    @Transactional
    public void update(WorkOrder workOrder) {
        workOrderMapper.update(workOrder);
    }
    
    @Override
    @Transactional
	public void replyUpdate(WorkOrder workOrder) {
    	workOrderMapper.replyUpdate(workOrder);
	}
    
    @Override
    @Transactional
    public void delete(Integer id) {
        workOrderMapper.delete(id);
    }
    
    @Override
    @Transactional
    public void deleteImage(Integer id,Integer type) {
    	WorkOrder workOrder = get(id);
    	String file = null;
    	if(type == 1) {
    		file = workOrder.getWoFile();
    	} else {
    		file = workOrder.getReplyFile();
    	}
    	if(StringUtils.isNotBlank(file)) {
    		File f = new File(getPicRootPath() + getWorkOrderUploadDir(), file);
    		if(f.exists()) {
    			f.delete();
    		}
    	}
        workOrderMapper.deleteImage(id,type);
    }

	@Override
	public boolean checkWoNoUnique(String woNo) {
		return workOrderMapper.countByWoNo(woNo).intValue() == 0;
	}

	@Override
	public Integer checkOrderSnExist(String orderSn) {
		return workOrderMapper.checkOrderSnExist(orderSn);
	}
	
	@Override
    public List<WorkOrderGoods> getOrderGoods(String orderSn) {
        return workOrderMapper.getOrderGoods(orderSn);
    }
	
}