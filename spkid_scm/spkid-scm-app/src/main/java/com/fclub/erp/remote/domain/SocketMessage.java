package com.fclub.erp.remote.domain;

import java.io.Serializable;
import java.util.List;

public class SocketMessage implements Serializable {

    /**  */
    private static final long serialVersionUID = -3432168814803758443L;
    
    private String       uuid;
    private String       channel;
    private List<String> dirs;
    private Integer      adminId;

    public SocketMessage() {
        
    }

    public SocketMessage(String uuid, String channel, List<String> dirs, Integer adminId) {
        this.uuid = uuid;
        this.channel = channel;
        this.dirs = dirs;
        this.adminId = adminId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}
