package com.fclub.erp.service;

import java.util.List;

import com.fclub.erp.remote.domain.ProcessStatus;

/**
 * 图片具体处理接口
 */
public interface ImageProcess {

    void process(String batchNo, String channel, List<String> dirs, Integer adminId);
    
    ProcessStatus getStatus(String batchNo);

    void removeStatus(String batchNo);
    
    void processByHand(String batchNo, String channel, Integer adminId);
    
}
