/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.helper;

import static com.fclub.tpd.common.SystemConstant.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fclub.tpd.dataobject.Provider;

/**
 * Session operation helper.
 * 
 * @author michael
 * @version $Id: SessionHelper.java 7 2013-06-28 01:51:27Z zhangshixi $
 */
public class SessionHelper {
    
    private SessionHelper() {
    }
    
    // ---- public methods ----------------------------------------------------------------
    public static void invalidateSession(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    public static String getCaptcha(HttpServletRequest request) {
        return getSessionValue(SESSION_CAPTCHA, request);
    }
    
    public static void setCaptcha(String captcha, HttpServletRequest request) {
        setSessionValue(SESSION_CAPTCHA, captcha, request);
    }
    
    public static Provider getProvider() {
        return getSessionValue(SESSION_ADMIN_USER, null);
    }
    
    public static Provider getProvider(HttpServletRequest request) {
        return getSessionValue(SESSION_ADMIN_USER, request);
    }
    
    public static void setProvider(Provider adminUser, HttpServletRequest request) {
        setSessionValue(SESSION_ADMIN_USER, adminUser, request);
    }
    
    // ---- private methods ------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    private static <T> T getSessionValue(String propName, HttpServletRequest request) {
        if(request == null) {
            request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        } else {
            String key = System.getProperty(propName);
            return (T) session.getAttribute(key);
        }
    }
    
    private static void setSessionValue(String propName, Object value, HttpServletRequest request) {
        if (value == null) {
            return;
        }
        
        String key = System.getProperty(propName);
        request.getSession().setAttribute(key, value);
    }

}
