/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fclub.common.log.LoggerUtil;
import com.fclub.erp.dto.goods.BatchResultVo;
import com.fclub.erp.remote.domain.ProcessStatus;
import com.fclub.erp.remote.domain.SocketMessage;
import com.fclub.remote.domain.Server;
import com.fclub.socket.client.SocketClient;
import com.fclub.socket.exception.SocketException;
import com.fclub.tpd.batch.remote.socket.SocketClientUtil;

/**
 * 图片服务器管理器 基于Socket协议
 * 
 * @author "baolm"
 * @version $Id: ServerManager.java, v 0.1 2012-10-29 下午12:58:39 "baolm" Exp $
 */
public class SocketServerManager implements ServerManager {

    public static final String REQUEST_TEST_LINK = "ImageProcessService.testLink";
    public static final String REQUEST_PROCESS = "tpd.ImageProcessService.process";
    public static final String REQUEST_GET_STATUS = "tpd.ImageProcessService.getStatus";
    public static final String REQUEST_REMOVE_STATUS = "tpd.ImageProcessService.removeStatus";

    private final List<Server> servers;
    private final Map<String, SocketClient> clients;
    private final Map<String, ProcessStatus> serverStatus;
    private Integer adminId;
    private LoggerUtil logger;

    public SocketServerManager() {
        servers = new ArrayList<>();
        clients = new HashMap<>();
        serverStatus = new HashMap<>();
    }

    @Override
    public void init(List<Server> serverList, Integer adminId, LoggerUtil logger) {

        this.adminId = adminId;
        this.logger = logger;
        for (Server server : serverList) {

            try {
                SocketClient client = new SocketClient(server.getIp(), server.getPort());
                Object response = SocketClientUtil.getResponse(client, REQUEST_TEST_LINK, null);
                if (SUCCESS.equals(response)) {
                    server.setEnabled(true);
                    logger.info("linked socket server: {0}", server.getIp());
                    clients.put(server.getIp(), client);
                    servers.add((Server) server.clone());
                }
            } catch (Exception e) {
                logger.error(
                        "can not link to image server: {0}, please check the server is startup.",
                        server.getIp(), e);
            }
        }
    }

    @Override
    public void processImageDirs(Server server, String uuid, String channel, List<String> dirs)
            throws Exception {

        SocketClient client = clients.get(server.getIp());
        Object response = SocketClientUtil.getResponse(client, REQUEST_PROCESS, new SocketMessage(
                uuid, channel, dirs, adminId));
        if (!SUCCESS.equals(response)) {
            throw new SocketException("response data error");
        }
    }

    @Override
    public ProcessStatus getStatus(String uuid) {

        ProcessStatus result = new ProcessStatus();

        int processCount = 0;
        boolean isFinished = true;
        List<String> successDirs = new ArrayList<>(1000);
        List<String> processDirs = new ArrayList<>(1000);
        List<String> totalDirs = new ArrayList<>(1000);
        List<String> logback = new ArrayList<>(1000);
        List<BatchResultVo> resultList = new ArrayList<>(100);
        for (Server server : servers) {

            if(!server.isProcessing()) {
                logger.debug("skip server: {0}", server.getIp());
                continue;
            }
            ProcessStatus status = null;
            try {
                if (server.isEnabled()) {
                    logger.debug("get status from server: {0}, key: {1}", server.getIp(), uuid);
                    status = (ProcessStatus) SocketClientUtil.getResponse(clients.get(server.getIp()),
                        REQUEST_GET_STATUS, uuid);
                    if (status == null) {
                        isFinished = false;
                        logger.warn("can not get status from server:{0}, key: {1}", server.getIp(), uuid);
                    } else {
                        serverStatus.put(server.getIp(), status);
                    }
                } else {
                    status = serverStatus.get(server.getIp());
                }
            } catch (Exception e) {
                logger.error("获取图片服务器：" + server.getIp() + " 处理状态 错误：", e);
                ProcessStatus lastStatus = serverStatus.get(server.getIp());
                if (lastStatus != null && !lastStatus.isFinished()) {
                    //System.out.println("last process count: "+lastStatus.getProcessCount());
                    if (lastStatus.getLossTimes() < 3) {
                        lastStatus.increaseLossTimes();
                    } else {
                        logger.info("等待异常处理...");
                        server.setEnabled(false);
                        lastStatus.setErrorflag(true);
                        lastStatus.setFinished(true);
                    }
                    lastStatus.getLogback().clear();
                }
                status = lastStatus;
            }

            if (status != null) {
                processCount += status.getProcessCount();
                successDirs.addAll(status.getSuccessDirs());
                processDirs.addAll(status.getProcessDirs());
                totalDirs.addAll(status.getTotalDirs());
                logback.addAll(status.getLogback());
                resultList.addAll(status.getResults());
                if (!status.isFinished()) {
                    isFinished = false;
                }
            }
        }

        result.setProcessCount(processCount);
        result.setSuccessDirs(successDirs);
        result.setProcessDirs(processDirs);
        result.setTotalDirs(totalDirs);
        result.setFinished(isFinished);
        result.setLogback(logback);
        result.setResults(resultList);

        return result;
    }

    @Override
    public void removeStatus(String uuid) {

        for (Server server : servers) {

            if (!server.isEnabled() || !server.isProcessing()) {
                continue;
            }
            try {
                String response = (String) SocketClientUtil.getResponse(
                        clients.get(server.getIp()), REQUEST_REMOVE_STATUS, uuid);
                if (SUCCESS.equals(response)) {
                    logger.info("移除图片服务器：{0} 处理信息成功", server.getIp());
                }
            } catch (Exception e) {
                logger.error("移除图片服务器：{0} 处理信息错误：", server.getIp(), e);
            } finally {
                server.setProcessing(false);
            }
        }
    }

    @Override
    public List<Server> getServers() {
        List<Server> result = new ArrayList<>();
        for (Server server : servers) {
            if (server.isEnabled()) {
                result.add(server);
            }
        }
        return result;
    }

    @Override
    public List<String> getErrorDirs() {
        List<String> errorDirs = new ArrayList<>();
        for (Map.Entry<String, ProcessStatus> entry : serverStatus.entrySet()) {
            ProcessStatus temp = entry.getValue();
            if (temp != null && temp.isErrorflag()) {
                errorDirs.addAll(temp.getTotalDirs());
                errorDirs.removeAll(temp.getProcessDirs());
            }
        }
        //serverStatus.clear();
        return errorDirs;
    }
    
    @Override
    public void clearStatus() {
        this.serverStatus.clear();
    }

    /**
     * 清除服务器状态并关闭socket
     * 
     * @see com.fclub.erp.remote.ServerManager#destory()
     */
    @Override
    public void destory() {
        this.servers.clear();
        this.serverStatus.clear();
        for(Map.Entry<String, SocketClient> entry : clients.entrySet()) {
            try {
                entry.getValue().close();
                logger.debug("close socket: {0} success.", entry.getKey());
            } catch (IOException e) {
                logger.error("close socket error: ", e);
                continue;
            }
        }
        this.clients.clear();
    }

}
