package com.lonch.client.bean;

public class SaveClientDevicesBean {


    /**
     * code : null
     * opFlag : true
     * error :
     * timestamp : 2021-04-20 14:19:54
     */

    private Object code;
    private boolean opFlag;
    private String error;
    private String timestamp;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
