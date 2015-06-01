/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.remote;

import java.util.List;

import com.fclub.erp.remote.domain.ProcessStatus;

/**
 * 图片处理接口
 * 
 * @author "baolm"
 * @version $Id: ImageProcessService.java, v 0.1 2012-10-29 下午12:57:48 "baolm" Exp $
 */
public interface ImageProcessService {

    /**
     * 图片处理
     * 
     * @param uuid 唯一批次号
     * @param channel 通道
     * @param dirs 目录列表
     * @param adminId 管理员
     * @return 成功返回"success"
     */
    String process(String uuid, String channel, List<String> dirs, Integer adminId);

    /**
     * 获取处理状态
     * 
     * @return ProcessStatus 状态对象
     */
    ProcessStatus getStatus(String uuid);
    
    /**
     * 清除图片服务器处理状态
     * 
     * @return 成功返回"success"
     */
    String removeStatus(String uuid);
    
    /**
     * 服务测试
     * 
     * @return 成功返回"success"
     */
    String testLink();
}
