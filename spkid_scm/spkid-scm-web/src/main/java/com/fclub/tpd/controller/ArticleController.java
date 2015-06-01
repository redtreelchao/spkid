/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author baolm
 * @version $Id: ArticleController.java, v 0.1 Aug 1, 2013 2:43:37 PM baolm Exp $
 */
@Controller
public class ArticleController {

    /**
     * 合作需知
     */
    @RequestMapping("/cooperation.htm")
    public String cooperation(){
        return "cooperation";
    }
    
    /**
     * 操作手册
     */
    @RequestMapping("/help.htm")
    public String help(){
        return "help";
    }
}
