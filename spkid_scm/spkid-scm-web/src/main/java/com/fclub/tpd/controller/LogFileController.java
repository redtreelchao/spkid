package com.fclub.tpd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fclub.common.lang.BizException;
import com.fclub.common.lang.SystemException;

@Controller
@RequestMapping("/logfile")
public class LogFileController {

	@RequestMapping("/main.htm")
    public String main(String key) {
        checkPermission(key);
		return "tpd/logfile";
    }
	
	@RequestMapping(value="/download.htm", method=RequestMethod.GET)
    public void downloadLog(String name, HttpServletResponse response) {
		File logFile = doGetLogFile(name);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;fileName=" + name);
        
        InputStream input = null;
        try {
            input = new FileInputStream(logFile);
            IOUtils.copy(input, response.getOutputStream());
        } catch (FileNotFoundException e) {
            throw new SystemException("Log file is not exist: " + logFile.getAbsolutePath());
        } catch (IOException e) {
            throw new SystemException("Read log file error: " + logFile.getAbsolutePath());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                throw new SystemException("Close log file error: " + logFile.getAbsolutePath());
            }
        }
    }
    
    /* ---- private methods ---- */
    private File doGetLogFile(String name) {
    	if (name == null || name.isEmpty()) {
    		throw new NullPointerException("logname");
    	} else {
    		if (!name.startsWith("/")) {
    			name = "/" + name;
    		}
    		String logFileName = getLogHome() + name;
    		return new File(logFileName);
    	}
    }

	private String getLogHome() {
		return System.getProperty("fclub.log.web.root.dir");
	}
	
	private void checkPermission(String key) {
		if (!"fclub2010".equals(key)) {
			throw new BizException("您无权访问!");
		}
	}
    
}