package com.hong.entity;

import java.sql.Date;
import java.util.List;

/**
 * @Author LJH
 * @CreateTime 2020/07/13 14:17
 */
public class LogObject {
    private String logTime;
    private String happenAddress;
    private Object logInfo;
    private String ip;
    private String[] parms;

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getHappenAddress() {
        return happenAddress;
    }

    public void setHappenAddress(String happenAddress) {
        this.happenAddress = happenAddress;
    }

    public Object getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(Object logInfo) {
        this.logInfo = logInfo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String[] getParms() {
        return parms;
    }

    public void setParms(String[] parms) {
        this.parms = parms;
    }
}
