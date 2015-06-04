/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.common.holder;


/**
 * 用户信息Holder
 * @author likaiping
 * @version $Id: AdminAuthorityHolder.java, v 0.1 Aug 23, 2012 9:05:25 AM likaiping Exp $
 */
public abstract class AdminAuthorityHolder {

    private static ThreadLocal<AdminAuthority> authorityLocal=new ThreadLocal<>();
    
    /**
     * 设置用户信息
     * @param adminUser
     */
    public static void set(AdminAuthority authority){
        authorityLocal.set(authority);
    }
    
    /**
     * 获取用户信息
     * @return AdminUser
     */
    public static AdminAuthority get(){
        return authorityLocal.get();
    }
    
    /**
     * 清楚信息
     */
    public static void clear(){
        authorityLocal.remove();
    }
}
