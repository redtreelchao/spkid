/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.helper;

import static com.fclub.tpd.common.SystemConstant.COOKIE_DOMAIN;
import static com.fclub.tpd.common.SystemConstant.COOKIE_PASSWORD;
import static com.fclub.tpd.common.SystemConstant.COOKIE_PATH;
import static com.fclub.tpd.common.SystemConstant.COOKIE_PROVIDER_ID;
import static java.lang.System.getProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LogUtil;
import com.fclub.web.util.WebUtil;

/**
 * Cookie操作帮助类。
 * 
 * @author michael
 * @version $Id: CookieHelper.java 7 2013-06-28 01:51:27Z zhangshixi $
 */
public class CookieHelper {

    public static final String ADMIN_LOGIN_KEY = "_alogin";
    
    private CookieHelper() {
    }
    
    /**
     * Add login username and encoded password cookie to client browser.
     *  
     * @param adminId  login admin id.
     * @param password login password.
     * @param request  http request.
     * @param response http response.
     */
    public static void addCookies(String adminId, String password, String adminLogin, int cookieMaxAge,
                            HttpServletRequest request, HttpServletResponse response) {
        String cookieProviderId = getProperty(COOKIE_PROVIDER_ID);
        String cookiePassword = getProperty(COOKIE_PASSWORD);
        String cookieDomain = getProperty(COOKIE_DOMAIN);
        String cookiePath = getProperty(COOKIE_PATH);

        String md5Password = md5(password);
        
        if (adminId != null) {
        	WebUtil.addCookie(cookieProviderId, adminId, cookieMaxAge, cookieDomain, cookiePath, request, response);
        }
        if (password != null) {
        	WebUtil.addCookie(cookiePassword, md5Password, cookieMaxAge, cookieDomain, cookiePath, request, response);
        }
        if (adminLogin != null) {
        	WebUtil.addCookie(ADMIN_LOGIN_KEY, adminLogin, cookieMaxAge, cookieDomain, cookiePath, request, response);
        }

        LogUtil.getLogger(CookieHelper.class).info(
            "Added providerId:{0} and password:{1} cookie to client browser.", adminId, md5Password);
    }
    
    /**
     * 判断用于登录的Cookie信息是否存在。
     */
    public static boolean isLoginCookieExist(HttpServletRequest request) {
        String cookieProviderId = getProperty(COOKIE_PROVIDER_ID);
        String cookiePassword = getProperty(COOKIE_PASSWORD);

        String providerId = WebUtil.getCookieValue(cookieProviderId, request);
        String password = WebUtil.getCookieValue(cookiePassword, request);
        
        return StringUtil.isNotBlank(providerId) && StringUtil.isNotBlank(password);
    }
    
    public static String md5(String value) {
    	return DigestUtils.md5Hex(value);
    }
    
}
