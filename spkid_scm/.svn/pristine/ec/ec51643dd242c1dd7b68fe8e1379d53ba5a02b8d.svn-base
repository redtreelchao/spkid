/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A abstract http filter class that convert the request/response to http
 * format. Child class need implements
 * {@link #doFilter(HttpServletRequest, HttpServletResponse, FilterChain)}
 * method to extends own logic.
 * 
 * @author michael
 * @version $Id: LoginFilter.java, v 0.1 2012-7-27 上午11:37:54 michael Exp $
 */
public abstract class AbstractHttpFilter implements Filter {

	/** filter configuration */
	private FilterConfig config;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param request
	 *            servlet request.
	 * @param response
	 *            servlet response.
	 * @param chain
	 *            filter chain.
	 * 
	 * @throws IOException
	 *             if an I/O exception occurs.
	 * @throws ServletException
	 *             if an servlet exception occurs.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		doFilter((HttpServletRequest) request, (HttpServletResponse) response,
				chain);
	}

	/**
	 * Gets the current filter configuration.
	 * 
	 * @return current filter configuration.
	 */
	protected FilterConfig getFilterConfig() {
		return config;
	}

	/**
	 * Actually do filter method over http protocol.
	 * 
	 * @param request
	 *            http servlet request.
	 * @param response
	 *            http servlet response.
	 * @param chain
	 *            filter chain.
	 * 
	 * @throws IOException
	 *             if an I/O exception occurs.
	 * @throws ServletException
	 *             if an http servlet exception occurs.
	 */
	protected abstract void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException;

	/**
	 * @param request
	 *            http request.
	 * 
	 * @return {@code true} if and only if request uri is match exclude pattern;
	 *         {@code false} otherwise.
	 */
	protected boolean isFilterPattern(HttpServletRequest request) {
		String visitUri = getVisitUri(request);
		if (!visitUri.endsWith(".htm") && !visitUri.endsWith(".json")) {
			return true;
		} else if (visitUri.startsWith("/login")
				|| visitUri.equals("/logout.htm")
				|| visitUri.equals("/provider/chongzhi/notify.htm")
				|| visitUri.equals("/provider/chongzhi/return.htm")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return current visit uri.
	 * 
	 * @param request
	 *            http request.
	 * 
	 * @return request uri.
	 */
	protected String getVisitUri(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String visitUri = requestUri.substring(contextPath.length());

		return visitUri;
	}

}
