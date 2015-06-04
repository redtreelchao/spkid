/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.remote;

/**
 * 
 * @author "baolm"
 * @version $Id: ServerManagerFactory.java, v 0.1 2012-10-31 下午12:46:06 "baolm" Exp $
 */
public class ServerManagerFactory {

    public static final String RMI        = "rmi";
    public static final String SOCKET     = "socket";
    public static final String WEBSERVICE = "webservice";

    public static ServerManager generateServerManager(String type) {
        ServerManager manager = null;
        if (SOCKET.equalsIgnoreCase(type)) {
            manager = new SocketServerManager();
        } else if (WEBSERVICE.equalsIgnoreCase(type)) {
            //...
        } else {
            throw new RuntimeException("not supported server manager type.");
        }
        return manager;
    }

}
