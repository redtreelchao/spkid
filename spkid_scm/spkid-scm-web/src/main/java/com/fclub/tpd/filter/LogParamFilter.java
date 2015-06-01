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

import com.fclub.tpd.helper.LogParamHelper;

/**
 * 日志参数处理过滤器
 * @author likaiping
 * @version $Id: LogParamFilter.java, v 0.1 Aug 7, 2012 2:01:09 PM likaiping Exp $
 */
public class LogParamFilter extends AbstractHttpFilter{

    /** 
     * @see com.fclub.tpd.filter.AbstractHttpFilter#doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        LogParamHelper.processLogParam(request);
        chain.doFilter(request, response);
        LogParamHelper.cleanLogParam();
    }

}
