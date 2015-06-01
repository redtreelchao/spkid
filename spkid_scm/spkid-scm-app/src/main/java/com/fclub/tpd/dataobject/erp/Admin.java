/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.dataobject.erp;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Administrator.
 * 
 * @author michael
 * @version $Id: Test.java, v 0.1 2012-7-27 上午11:18:54 michael Exp $
 */
public class Admin implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 7602843867167232719L;

    private Integer adminId;       // admin id, primary key
    private String  userName;      // admin login name
    private String  password;      // admin password
    private String  email;         // admin email
    private String  deptName;      // department name

    private Integer userStatus;    // activity/locked
    private Integer groupIdJava;
    private String actionListJava;
    // private Integer actionGroupId; // PHP版，JAVA使用groupIdJava代替
    // private String  actionList;    // PHP版，JAVA使用actionListJava代替
    
    //gaotianliang these code is not null
    private String langType;
    private String navList;
    private Short agencyId;
    
    private Date    addTime;       // added time
    private Date    lastLoginTime; // last login time
    private String  lastLoginIp;   // last login ip address
    
    
    public boolean isEnable() {
		return userStatus != null && userStatus.intValue() == 1;
	}
    
    
    public Short getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Short agencyId) {
        this.agencyId = agencyId;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getNavList() {
        return navList;
    }

    public void setNavList(String navList) {
        this.navList = navList;
    }

    public Integer getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDeptName() {
        return deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    public Integer getUserStatus() {
        return userStatus;
    }
    
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
    
    public Date getAddTime() {
        return addTime;
    }
    
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    
    public Date getLastLoginTime() {
        return lastLoginTime;
    }
    
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public String getLastLoginIp() {
        return lastLoginIp;
    }
    
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
    
    public String getActionListJava() {
        return actionListJava;
    }

    public void setActionListJava(String actionListJava) {
        this.actionListJava = actionListJava;
    }
    
    public Integer getGroupIdJava() {
        return groupIdJava;
    }
    
    public void setGroupIdJava(Integer groupIdJava) {
        this.groupIdJava = groupIdJava;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,ToStringStyle.DEFAULT_STYLE);
    }

    /**
     * User status.
     * 
     * @author michael
     * @version $Id: AdminUser.java, v 0.1 2012-7-27 下午5:07:21 michael Exp $
     */
    public enum UserStatus {
        LOCKED(0),
        ACTIVITY(1);
        
        private int value;
        UserStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return this.value;
        }
    }
    
}