/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.lang.utils.SystemUtil;

/**
 * System variables initialization listener.
 * 
 * @author michael
 * @version $Id: SystemListener.java 7 2013-06-28 01:51:27Z zhangshixi $
 */
public class SystemListener implements ServletContextListener {
	
	/** System configuration file location */
	public static final String SYSTEM_CONFIG_LOCATION = "systemConfigLocation";
	/** Default system configuration file */
	public static final String DEFAULT_SYSTEM_CONFIG_LOCATION = "/system.properties";
	/** require property Definition */
	public static final Map<String, String> REQUIRE_PARAM=new HashMap<>();
	
	static{
	    REQUIRE_PARAM.put("fclub.javaback.loggingLevel", "info");
	    REQUIRE_PARAM.put("fclub.log.web.root.dir", SystemUtil.getUserInfo().getHomeDir());
        REQUIRE_PARAM.put("sys_host_name", SystemUtil.getHostInfo().getName());
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		String configLocation = event.getServletContext().
				getInitParameter(SYSTEM_CONFIG_LOCATION);
		if (configLocation == null) {
			configLocation = DEFAULT_SYSTEM_CONFIG_LOCATION;
		}
		
		try {
			initialize(configLocation);
			checkDefaultConfig();
		} catch (IOException e) {
			System.out.println("加载系统配置文件错误,系统启动失败！！！");
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("System listener initialized.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	    System.out.println("System listener destroyed.");
	}

	/**
	 * Initialize system configuration file.
	 * 
	 * @param configLocation configuration file location.
	 * 
	 * @throws IOException if I/O exception occurs while loading.
	 */
	private void initialize(String configLocation) throws IOException {
		InputStream input = null;
		Properties props = null;
		try {
			input = SystemListener.class.getResourceAsStream(configLocation);
			props = new Properties();
			props.load(input);			
		} finally {
			if (input != null) {
				input.close();
			}
		}
		System.out.println("System Configration initialize Details:");
		
		for (Entry<Object, Object> entry : props.entrySet()) {
		    String key=(String)entry.getKey();
		    String value=(String)entry.getValue();
		    System.out.println("|------ key["+key+"];value["+value+"]");
			System.setProperty(key , value);
		}
		System.out.println("-------finish");
	}
	
	private void checkDefaultConfig(){
	    System.out.println("System Configration initialize require properties:");
	    for (Iterator<String> iterator = REQUIRE_PARAM.keySet().iterator(); iterator.hasNext();) {
            String key =  iterator.next();
            String oldValue=System.getProperty(key);
            if(StringUtil.isBlank(oldValue)){
                String value=REQUIRE_PARAM.get(key);
                System.out.println("|------ key["+key+"];value["+value+"]");
                System.setProperty(key , value);
            }
        }
	    System.out.println("-------finish");
	}
	
}
