/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import static com.fclub.tpd.common.SystemConstant.COOKIE_DOMAIN;
import static com.fclub.tpd.common.SystemConstant.COOKIE_MAX_AGE;
import static com.fclub.tpd.common.SystemConstant.COOKIE_PASSWORD;
import static com.fclub.tpd.common.SystemConstant.COOKIE_PATH;
import static com.fclub.tpd.common.SystemConstant.COOKIE_PROVIDER_ID;
import static java.lang.System.getProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fclub.common.log.LogUtil;
import com.fclub.tpd.biz.LogService;
import com.fclub.tpd.biz.ProviderService;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.dataobject.erp.Admin;
import com.fclub.tpd.helper.CookieHelper;
import com.fclub.tpd.helper.SessionHelper;
import com.fclub.tpd.mapper.AdminMapper;
import com.fclub.web.util.WebUtil;

/**
 * Login/Logout controller.
 * 
 * @author michael
 * @version $Id: LoginController.java 358 2013-08-16 08:04:06Z zhangshixi $
 */
@Controller
public class LoginController {

    @Autowired
    private ProviderService     providerService;
    @Autowired
    private LogService          logService;
    @Autowired
    private	AdminMapper			adminMapper;
    

    /** logger */
    private static final LogUtil LOGGER             = LogUtil.getLogger(LoginController.class);

    // ---- public methods -------------------------------------------------------------
    @RequestMapping("/login.htm")
    public String login(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
//        boolean login = autoLoginByCookie(request);
//        if (!login) {
          boolean login = manualLogin(request, response);
//        }
        return login ? "redirect:/index.htm" : "login";
    }

    @RequestMapping("/logout.htm")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        removeCookies(request, response);
        SessionHelper.invalidateSession(request);

        return "redirect:/login.htm";
    }

    @ResponseBody
    @RequestMapping("/login/check.htm")
    public String checkLogin(String userName, String password, String captcha, String adminLogin, HttpServletRequest request) {
        assert userName != null;
        assert password != null;
        assert captcha != null;
        
        Provider provider = null;
        if ("1".equals(adminLogin)) {
        	Admin loginAdmin = adminMapper.selectByName(userName);
        	provider = newProviderByAdmin(loginAdmin);
        } else {
        	provider = providerService.findByUserName(userName);
        }
        
        password = CookieHelper.md5(password);
        
        if (provider == null) {
            return "用户名不存在！";
        }
        if (!provider.getPassword().equals(password)) {
            return "密码有误！";
        }
        if (!validateCaptcha(captcha, request)) {
            return "验证码有误！";
        }
        Boolean loginStatus = provider.getProviderStatus();
        if (loginStatus != null && loginStatus.booleanValue()) {
        	return "账号被锁定，不能登录！";
        }
        
        // 登录合作方式校验
//	    if (!"1".equals(adminLogin)) {
//	    	Integer coopId = provider.getProviderCooperation();
//	    	if (coopId == null || coopId.intValue() != 4) {
//	    		return "不是第三方直发供应商，不能登录！";
//	    	}
//	    }

        return "success";
    }
    
    // ---- 密码修改 
    @RequestMapping("/passwordTo.htm")
    public String toUpdatePassword(ModelMap modelMap) {
        return "password";
    }
    
    @ResponseBody
    @RequestMapping("/password.htm")
    public String password(ModelMap modelMap, String oldPassword, String newPassword, HttpServletRequest request) {
        Provider provider = SessionHelper.getProvider(request);
        oldPassword = CookieHelper.md5(oldPassword);
        if (!provider.getPassword().equals(oldPassword)) {
            return "旧密码错误，请重新输入！";
        } else {
            String newPwd = CookieHelper.md5(newPassword);
            
            Provider newProvider = new Provider();
            newProvider.setProviderId(provider.getProviderId());
            newProvider.setPassword(newPwd);
            try {
                providerService.update(newProvider);
                //recordEditAccountLog();
            } catch (Exception e) {
                LOGGER.error("修改供应商密码失败！", e);
                return "修改密码失败！";
            }
            
            // 更新SESSION
            provider.setPassword(newPwd);
            SessionHelper.setProvider(provider, request);
        }
        
        return "success";
    }

    // ---- private methods ------------------------------------------------------------
//    private boolean autoLoginByCookie(HttpServletRequest request) {
//        String cookieProviderId = getProperty(COOKIE_PROVIDER_ID);
//        String cookiePassword = getProperty(COOKIE_PASSWORD);
//
//        String cookiePidValue = WebUtil.getCookieValue(cookieProviderId, request);
//        String cookiePsdValue = WebUtil.getCookieValue(cookiePassword, request);
//        String cookieALoginValue = WebUtil.getCookieValue(CookieHelper.ADMIN_LOGIN_KEY, request);
//        
//        if (cookiePidValue == null || cookiePsdValue == null) {
//            LOGGER.info("Could not auto login by client cookies.");
//            return false;
//        }
//        
//        Provider provider = null;
//        if ("1".equals(cookieALoginValue)) {
//        	// 按管理员登录
//        	Admin loginAdmin = adminMapper.get(cookiePidValue);
//        	if (loginAdmin == null) {
//        		LOGGER.warn("Could not auto login by admin with admin id: {0}", cookiePidValue);
//        		return false;
//        	} else {
//        		provider = newProviderByAdmin(loginAdmin);
//        	}
//        	
//        } else {
//        	
//        	// 按供应商登录
//	        Integer providerId;
//	        try {
//	            providerId = Integer.parseInt(cookieProviderId);
//	        } catch (NumberFormatException e) {
//	            LOGGER.warn("value of username in cookie is not a number: {0}", cookiePidValue);
//	            return false;
//	        }
//	
//	        provider = providerService.findById(providerId);
//	        if (provider == null) {
//	            LOGGER.debug("not found admin user from database by id [{0}]", providerId);
//	            return false;
//	        }
//	        
//	        provider.setProviderId(providerId);
//        }
//        
//        return doLogin(provider, cookiePsdValue, true, request);
//    }

    private boolean manualLogin(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        if (userName == null || password == null || !validateCaptcha(captcha, request)) {
            return false;
        }
        
        String adminLogin = request.getParameter("adminLogin");
        Provider provider = null;
        if ("1".equals(adminLogin)) {
        	Admin loginAdmin = adminMapper.selectByName(userName);
        	if (loginAdmin == null) {
        		LOGGER.warn("not found admin user from database by name [{0}]", userName);
        		return false;
        	}
        	
        	provider = newProviderByAdmin(loginAdmin);
        } else {
        	
        	provider = providerService.findByUserName(userName);
        	if (provider == null) {
        		LOGGER.debug("not found provider user from database by name [{0}]", userName);
        		return false;
        	}
        	provider.setUserName(userName);
        }

        password = CookieHelper.md5(password);
        boolean login = doLogin(provider, password, false, request);
        
        if (login) {
            int cookieMaxAge = -1; // cookies auto-expire
            if (Boolean.parseBoolean(request.getParameter("autoLogin"))) {
                cookieMaxAge = Integer.parseInt(getProperty(COOKIE_MAX_AGE));
                CookieHelper.addCookies(provider.getProviderId().toString(), password, adminLogin, cookieMaxAge, request, response);
            }
        }

        return login;
    }

    private boolean doLogin(Provider provider, String password, boolean pwdEncoded, HttpServletRequest request) {
        // validate user status
        if (Boolean.TRUE.equals(provider.getProviderStatus())) {
            LOGGER.debug("provider [{0}] is locked", provider.getUserName());
            return false;
        }

        // validate password
        String dbPassword = provider.getPassword();
        if (pwdEncoded) {
            dbPassword = CookieHelper.md5(dbPassword);
        }
        if (!password.equals(dbPassword)) {
            LOGGER.debug("login password [{0}] error", password);
            return false;
        }

        // save to session
        SessionHelper.setProvider(provider, request);

        return true;
    }

    private void removeCookies(HttpServletRequest request, HttpServletResponse response) {
        String cookieUserName = getProperty(COOKIE_PROVIDER_ID);
        String cookiePassword = getProperty(COOKIE_PASSWORD);
        String cookieDomain = getProperty(COOKIE_DOMAIN);
        String cookiePath = getProperty(COOKIE_PATH);

        WebUtil.removeCookie(cookieUserName, cookieDomain, cookiePath, request, response);
        WebUtil.removeCookie(cookiePassword, cookieDomain, cookiePath, request, response);

        LOGGER.info("Removed providerId and password cookie from client browser.");
    }

    private boolean validateCaptcha(String captcha, HttpServletRequest request) {
        return captcha.equalsIgnoreCase(SessionHelper.getCaptcha(request));
    }
    
    private Provider newProviderByAdmin(Admin admin) {
    	if (admin == null) {
    		return null;
    	}
    	
    	Provider provider = new Provider();
		provider.setAdminId(admin.getAdminId());
		provider.setUserName(admin.getUserName());
		provider.setPassword(admin.getPassword());
		provider.setProviderStatus(Integer.valueOf(1).equals(admin.getUserStatus()) ? Boolean.FALSE : Boolean.TRUE);
		
		return provider;
    }

}