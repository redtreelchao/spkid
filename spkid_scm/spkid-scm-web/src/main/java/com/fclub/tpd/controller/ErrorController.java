/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author likaiping
 * @version $Id: ErrorController.java 11 2013-06-28 02:47:21Z zhangshixi $
 */
@Controller
public class ErrorController {
    
    @RequestMapping("/error.htm")
    public String errorPage(){
        return "error";
    }

    @RequestMapping("/404.htm")
    public String notFundPage(){
        return "404";
    }
    
    @RequestMapping("/bizError.htm")
    public String bizError(){
        return "404";
    }
    
    @RequestMapping("/403.htm")
    public String notAuthority(){
        return "403";
    }
    
}
