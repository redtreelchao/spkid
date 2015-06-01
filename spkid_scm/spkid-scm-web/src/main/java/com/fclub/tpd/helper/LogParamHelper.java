/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.helper;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;

import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LogParamEmun;

/**
 * 日志参数帮助类
 * @author likaiping
 * @version $Id: LogParamHelper.java, v 0.1 Aug 7, 2012 1:39:44 PM likaiping Exp $
 */
public abstract class LogParamHelper {
    /**
     * 处理日志参数
     * @param request
     */
    public static void processLogParam(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (StringUtil.isNotBlank(requestURI)) {
            MDC.put(LogParamEmun.REQ_URI_STR.getParam(), requestURI);
        }
        String remoteAddr=getRemoteAddr(request);
        if(StringUtil.isNotBlank(remoteAddr)){
            MDC.put(LogParamEmun.REQ_CLIENT_IP.getParam(), remoteAddr);
        }
//        AdminUser adminUser = SessionHelper.getAdminUser(request);
//        if (adminUser != null) {
//            MDC.put(LogParamEmun.LOGIN_USER_ID.getParam(), adminUser.getAdminId());
//            MDC.put(LogParamEmun.LOGIN_USER_EMAIL.getParam(), adminUser.getEmail());
//        }

    }
    
    /**
     * 清除日志参数
     */
    public static void cleanLogParam(){
        MDC.remove(LogParamEmun.REQ_URI_STR.getParam());
        MDC.remove(LogParamEmun.REQ_CLIENT_IP.getParam());
        MDC.remove(LogParamEmun.LOGIN_USER_ID.getParam());
        MDC.remove(LogParamEmun.LOGIN_USER_EMAIL.getParam());
    }

    /**
     * 获取客户端IP地址，过滤反向代理造成的影响
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
