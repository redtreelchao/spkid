/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.erp.run;

import java.io.IOException;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fclub.erp.remote.socket.ImageServiceHandler;
import com.fclub.erp.service.ImageProcessTPD;
import com.fclub.socket.server.NioSocketServer;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.helper.ConstantsHelper;

/**
 * Image Server Startup
 * @version $Id: Startup.java, v 0.1 Nov 12, 2012 9:03:47 AM baolm Exp $
 */
public class Startup {

    /** spring configuration file location */
    private static final String  CONFIG_LOCATION = "classpath*:META-INF/spring/**/*.xml";

    public static void main(String[] args) throws IOException {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_LOCATION);

        String ip = ConstantsHelper.getImageServerIp();
        Integer port = Integer.valueOf(System.getProperty(SystemConstant.REMOTE_IMAGESERVICE_PORT));
        ImageProcessTPD processTPD = context.getBean(ImageProcessTPD.class);
        NioSocketServer server = new NioSocketServer(port, 10);
        server.getServerEventNotifier().registerListener(new ImageServiceHandler(processTPD));
        server.startup();

        System.out.println("[ image server: " + ip
                           + " ] startup on port " + port + ", root process dir is: "
                           + ConstantsHelper.getFtpRootPath());
    }
}
