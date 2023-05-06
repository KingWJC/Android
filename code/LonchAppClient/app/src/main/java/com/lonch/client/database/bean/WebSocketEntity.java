package com.lonch.client.database.bean;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_websocket_entity")
public class WebSocketEntity {
    @Id
    @Expose
    private String id;
    @Expose
    private String appType;
    @Expose
    private String deviceId;
    @Expose
    private String os;
    @Expose(serialize = false,deserialize = false)
    private String deviceType;
    @Expose
    private String deviceVersion;
    @Expose
    private String deviceBrand;
    @Expose
    private int isSupport;
    @Expose
    private int pingTimes;
    @Expose
    private int pongTimes;
    @Expose
    private String testDate;
    @Expose
    private String ip;
    @Expose(serialize = false,deserialize = false)
    private Long time;

    @Expose(serialize = false,deserialize = false)
    private int isReported; // 0 未上报 1 已上报




    @Generated(hash = 1173188763)
    public WebSocketEntity(String id, String appType, String deviceId, String os,
            String deviceType, String deviceVersion, String deviceBrand,
            int isSupport, int pingTimes, int pongTimes, String testDate, String ip,
            Long time, int isReported) {
        this.id = id;
        this.appType = appType;
        this.deviceId = deviceId;
        this.os = os;
        this.deviceType = deviceType;
        this.deviceVersion = deviceVersion;
        this.deviceBrand = deviceBrand;
        this.isSupport = isSupport;
        this.pingTimes = pingTimes;
        this.pongTimes = pongTimes;
        this.testDate = testDate;
        this.ip = ip;
        this.time = time;
        this.isReported = isReported;
    }
    @Generated(hash = 2065670434)
    public WebSocketEntity() {
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAppType() {
        return this.appType;
    }
    public void setAppType(String appType) {
        this.appType = appType;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getOs() {
        return this.os;
    }
    public void setOs(String os) {
        this.os = os;
    }
    public String getDeviceType() {
        return this.deviceType;
    }
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public int getIsSupport() {
        return this.isSupport;
    }
    public void setIsSupport(int isSupport) {
        this.isSupport = isSupport;
    }
    public int getPingTimes() {
        return this.pingTimes;
    }
    public void setPingTimes(int pingTimes) {
        this.pingTimes = pingTimes;
    }
    public int getPongTimes() {
        return this.pongTimes;
    }
    public void setPongTimes(int pongTimes) {
        this.pongTimes = pongTimes;
    }
    public String getTestDate() {
        return this.testDate;
    }
    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public int getIsReported() {
        return this.isReported;
    }
    public void setIsReported(int isReported) {
        this.isReported = isReported;
    }

    public String getDeviceVersion() {
        return this.deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceBrand() {
        return this.deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
}
