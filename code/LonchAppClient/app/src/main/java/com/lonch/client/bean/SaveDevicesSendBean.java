package com.lonch.client.bean;

public class SaveDevicesSendBean {


    /**
     * android.os : string
     * deviceId : string
     * deviceBrand : string
     * deviceVersion : string
     * appClientId : string
     * appVersion : string
     */

    private String os;
    private String deviceId;
    private String deviceBrand;
    private String deviceVersion;
    private String appClientId;
    private String appVersion;


    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    private String appType;

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getAppClientId() {
        return appClientId;
    }

    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
