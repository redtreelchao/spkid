/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fclub.common.log.LogUtil;
import com.fclub.tpd.common.holder.AdminAuthority;
import com.fclub.tpd.common.holder.AdminAuthorityHolder;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.helper.LogParamHelper;
import com.fclub.tpd.helper.SessionHelper;

/**
 * 
 * @author likaiping
 * @version $Id: AuthorityFilter.java, v 0.1 Jul 11, 2013 8:02:01 PM likaiping Exp $
 */
public class AuthorityFilter extends AbstractHttpFilter {
    /** logger */
    protected static final LogUtil LOGGER = LogUtil.getLogger(AuthorityFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        if (isFilterPattern(request) || hasAuthority(request)) {
            // LOGGER.debug("Request authority: {0} : {1}", true, request.getRequestURI());
            // 权限认证成功,设置用户状态
            AdminAuthorityHolder.set(gen(request));
            chain.doFilter(request, response);
        } else {
            LOGGER.debug("Request authority: {0}: {1}", false, request.getRequestURI());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not have authority to visit: "
                                                                 + getVisitUri(request));
        }
    }

    /**
     * Check user has authority or not.
     * 
     * @param  request http request.
     * @return {@code true} if and only if user has authority; 
     *         {@code false} otherwise.
     */
    private boolean hasAuthority(HttpServletRequest request) {
        Provider provider = SessionHelper.getProvider(request);
        if (provider != null)
            return true;
        return false;
    }

    /**
     * 生存用户授权对象
     * @param request
     * @return
     */
    public static AdminAuthority gen(HttpServletRequest request) {
        AdminAuthority adminAuthority = new AdminAuthority();
        adminAuthority.setProvider(SessionHelper.getProvider(request));
        adminAuthority.setRemoteAddr(LogParamHelper.getRemoteAddr(request));
        adminAuthority.setRemoteHost(request.getRemoteHost());
        adminAuthority.setRemotePort(request.getRemotePort());
        adminAuthority.setRemoteUser(request.getRemoteUser());
        return adminAuthority;
    }

}
