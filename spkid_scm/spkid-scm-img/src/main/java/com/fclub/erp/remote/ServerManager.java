/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.erp.remote;

import java.util.List;

import com.fclub.common.log.LoggerUtil;
import com.fclub.erp.remote.domain.ProcessStatus;
import com.fclub.remote.domain.Server;

/**
 * 图片服务器管理器
 * 
 * @author "baolm"
 * @version $Id: ServerManager.java, v 0.1 2012-10-29 下午12:58:39 "baolm" Exp $
 */
public interface ServerManager {

    String SUCCESS = "success";
    String FAILTRUE = "failtrue";

    void init(List<Server> serverList, Integer adminId, LoggerUtil logger);

    void processImageDirs(Server server, String uuid, String channel, List<String> dirs)
                                                                                        throws Exception;

    ProcessStatus getStatus(String uuid);

    void removeStatus(String uuid);

    List<Server> getServers();
    
    List<String> getErrorDirs();
    
    void clearStatus();
    
    /**
     * 销毁并释放资源
     */
    void destory();
}
