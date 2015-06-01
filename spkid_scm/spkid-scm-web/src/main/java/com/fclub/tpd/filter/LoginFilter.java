/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LogUtil;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.helper.CookieHelper;
import com.fclub.tpd.helper.SessionHelper;

/**
 * Login filter.
 * 
 * @author michael
 * @version $Id: LoginFilter.java, v 0.1 2012-7-27 上午11:37:54 michael Exp $
 */
public class LoginFilter extends AbstractHttpFilter {
    
    /** logger */
    protected static final LogUtil LOGGER = LogUtil.getLogger(LoginFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        if (isFilterPattern(request) || hasLogin(request, response)) {
            chain.doFilter(request, response);
        } else {
            String uri = request.getRequestURI();
            if (StringUtil.isNotBlank(uri) && !StringUtil.equals(uri, "/")
                && StringUtil.contains(uri, "/main.htm")) {
                request.getSession().setAttribute("goto", uri);
            } 
            response.sendRedirect("/login.htm");
        }
    }

    /**
     * Check user has login or not.
     * 
     * @param  request http request.
     * @return {@code true} if and only if user logged in; 
     *         {@code false} otherwise.
     */
    private boolean hasLogin(HttpServletRequest request, HttpServletResponse response) {
    	String adminLogin = request.getParameter("adminLogin");
        Provider provider = SessionHelper.getProvider(request);
        if (provider != null) {
            if (!CookieHelper.isLoginCookieExist(request)) {
                // 当用户Session存在，cookie不存在时，补种cookie。
            	Integer id = provider.isAdmin() ? provider.getAdminId() : provider.getProviderId();
                CookieHelper.addCookies(id.toString(), provider.getPassword(), adminLogin, -1, request, response);
            }
            return true;
        } else {
            return false;
        }
    }

}
