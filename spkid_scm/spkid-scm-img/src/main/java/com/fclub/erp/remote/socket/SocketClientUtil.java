/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.erp.remote.socket;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.fclub.common.log.LogUtil;
import com.fclub.socket.SocketRequest;
import com.fclub.socket.SocketResponse;
import com.fclub.socket.client.SocketClient;

/**
 * SOCKET服务工具类
 * 
 * @author "baolm"
 * @version $Id: SocketClientUtil.java, v 0.1 2012-10-26 上午9:16:23 "baolm" Exp $
 */
public class SocketClientUtil {

    private static final LogUtil         logger         = LogUtil.getLogger(SocketClient.class);
    private static final ExecutorService socket_pool    = Executors.newFixedThreadPool(20);
    /**
     * 默认通讯超时时间
     */
    private static final long            defaultTimeout = 5L;

    public static Object getResponse(final SocketClient client, final String head,
                                     final Object message) throws Exception {

        Callable<Object> call = new Callable<Object>() {
            public Object call() throws Exception {

                //开始执行SOCKET通讯
                SocketRequest request = new SocketRequest(head);
                request.setRequestData(message);
                client.send(request);
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    logger.error("client socket [head={0}] receive sleep exception: ", head, e);
                }
                final SocketResponse response = client.receive(2L, TimeUnit.SECONDS);
                if (response == null) {
                    return null;
                }
                if (head.equals(response.getRequestId())) {
                    return response.getResponseData();
                }
                return null;
            }
        };

        Future<Object> future = socket_pool.submit(call);
        try {
            return future.get(defaultTimeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            logger.error("communicate timeout when {0}: ", head, e);
            throw new TimeoutException("Timeout");
        }
    }

}
