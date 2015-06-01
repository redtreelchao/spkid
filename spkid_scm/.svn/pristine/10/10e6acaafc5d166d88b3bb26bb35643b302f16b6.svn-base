/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.remote.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.fclub.common.lang.BizException;
import com.fclub.common.log.LoggerUtil;
import com.fclub.remote.domain.Server;
import com.fclub.tpd.batch.remote.DispatchStragety;
import com.fclub.tpd.batch.remote.ServerManager;

/**
 * CPU分发策略
 * @author likaiping
 * @version $Id: CpuDispatchStragety.java, v 0.1 Jul 17, 2013 12:07:54 PM likaiping Exp $
 */
@Service
public class CpuDispatchStragety implements DispatchStragety {

    //private static final LogUtil logger = LogUtil.getLogger(CpuDispatchStragety.class);

    @Override
    public List<String> dispatchTask(ServerManager manager, String batchNo, String channel, List<String> dirs, LoggerUtil logger) {

        logger.info("分发策略：基于CPU数分发");
        
        List<Server> servers = manager.getServers();
        if(servers.size() == 0) {
            throw new BizException("无可用图片服务器");
        }
        
        int serverNum = servers.size();
        int cpuNum = 0;
        for (Server server : servers) {
            cpuNum += server.getCpu();
        }

        logger.info("需分发目录数：{0} ，现有图片服务器数：{1} ，总CPU数：{2}", dirs.size(), servers.size(), cpuNum);
        Integer cpuAvg = dirs.size() / cpuNum;

        int j = 1;
        List<String> errorDirs = new ArrayList<>(dirs.size());
        List<String> subDirs = new ArrayList<>(dirs.size());
        for (int i = 0; i < dirs.size(); i++) {
            logger.debug("num:" + i + ", name: " + dirs.get(i));
            subDirs.add(dirs.get(i));
            Server server = servers.get(j - 1);
            if (subDirs.size() == cpuAvg * server.getCpu() && j < serverNum) {
                dispatch(manager, batchNo, channel, errorDirs, subDirs, server, logger);
                subDirs.clear();
                j++;
            }
        }
        dispatch(manager, batchNo, channel, errorDirs, subDirs, servers.get(j - 1), logger);
        if(errorDirs.size() == dirs.size()) {
            logger.warn("分发不成功");
            throw new BizException("分发不成功");
        }
        return errorDirs;
    }

    private void dispatch(ServerManager manager, String batchNo, String channel,
                          List<String> errorDirs, List<String> subDirs, Server server, LoggerUtil logger) {
        try {
            manager.processImageDirs(server, batchNo, channel, subDirs);
            server.setProcessing(true);
            logger.info("分发到图片服务器：{0} ，{1}个目录", server.getIp(), subDirs.size());
        } catch (Exception e) {
            try {
                TimeUnit.SECONDS.sleep(3L);
                manager.processImageDirs(server, batchNo, channel, subDirs);
            } catch (Exception e1) {
                logger.error("分发到图片服务器：{0} 出错。等待异常处理...", server.getIp());
                server.setEnabled(false);
                errorDirs.addAll(subDirs);
            }
            
        }
    }

}
