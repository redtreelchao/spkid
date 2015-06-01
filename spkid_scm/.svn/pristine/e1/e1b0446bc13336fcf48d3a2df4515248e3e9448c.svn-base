/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.erp.remote.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fclub.common.log.LogUtil;
import com.fclub.erp.remote.SocketServerManager;
import com.fclub.erp.remote.domain.ProcessStatus;
import com.fclub.erp.remote.domain.SocketMessage;
import com.fclub.erp.service.ImageProcessTPD;
import com.fclub.socket.SocketRequest;
import com.fclub.socket.SocketResponse;
import com.fclub.socket.server.NioServerEventAdapter;

/**
 * @version $Id: ImageServiceHandler.java, v 0.1 2012-10-31 下午2:02:20 "baolm" Exp $
 */
public class ImageServiceHandler extends NioServerEventAdapter {

    public static final String TPD_REQUEST_TEST_LINK = "tpd.ImageProcessService.testLink";
    public static final String TPD_REQUEST_PROCESS = "tpd.ImageProcessService.process";
    public static final String TPD_REQUEST_GET_STATUS = "tpd.ImageProcessService.getStatus";
    public static final String TPD_REQUEST_REMOVE_STATUS = "tpd.ImageProcessService.removeStatus";
    
    private static final LogUtil         logger     = LogUtil.getLogger(ImageServiceHandler.class);

    private static final ExecutorService threadPool = Executors.newSingleThreadExecutor();


    private final ImageProcessTPD        imageProcessTPD;

    public ImageServiceHandler(ImageProcessTPD imageProcessTPD) {
        this.imageProcessTPD = imageProcessTPD;
    }

    @Override
    public void onAccept() {
        //logger.debug("onAccept ...");
    }

    @Override
    public void onAccepted(SocketRequest request) {
        //logger.debug("onAccepted ...");
    }

    @Override
    public void onReaded(SocketRequest request) {
        //logger.debug("onReaded ...");
    }

    @Override
    public void onWrite(SocketRequest request, SocketResponse response) {
        logger.debug("onWrite ...");
        try {
            //System.out.println(request.getRequestId());
            if (SocketServerManager.REQUEST_TEST_LINK.equals(request.getRequestId())) {
                response.setResponseData(SocketServerManager.SUCCESS);
            // .........TPD...........
            } else if (TPD_REQUEST_PROCESS.equals(request.getRequestId())) {
                threadPool.submit(new ServerThread(TPD_REQUEST_PROCESS, (SocketMessage) request.getRequestData()));
                //new Thread(new ServerThread((SocketMessage) request.getRequestData())).start();
                response.setResponseData(SocketServerManager.SUCCESS);
            } else if (TPD_REQUEST_GET_STATUS.equals(request.getRequestId())) {
                ProcessStatus status = imageProcessTPD.getStatus((String) request.getRequestData());
                response.setResponseData(status);
            } else if (TPD_REQUEST_REMOVE_STATUS.equals(request.getRequestId())) {
                imageProcessTPD.removeStatus((String) request.getRequestData());
                response.setResponseData(SocketServerManager.SUCCESS);
            } else {
                //...
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("image server error: ", e);
            response.setResponseData(SocketServerManager.FAILTRUE);
        }
    }

    @Override
    public void onClosed(SocketRequest request) {
        //logger.debug("onClosed ...");
    }

    @Override
    public void onError(Exception exception) {
        //logger.debug("onError ...");
    }

    private class ServerThread implements Runnable {

        private final String requestId;
        private final SocketMessage message;

        public ServerThread(String requestId, SocketMessage message) {
            this.requestId = requestId;
            this.message = message;
        }

        @Override
        public void run() {
            try {
                if(TPD_REQUEST_PROCESS.equals(requestId)) {
                    imageProcessTPD.process(message.getUuid(), message.getChannel(), message.getDirs(),
                        message.getAdminId());
                }
            } catch (Exception e) {
                logger.error("image process Thread error:", e);
            }
        }

    }
}
