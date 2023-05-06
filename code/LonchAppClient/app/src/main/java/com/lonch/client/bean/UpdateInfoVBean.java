package com.lonch.client.bean;

public class UpdateInfoVBean {

    /**
     * opFlag : true
     * error :
     * timestamp : 2020-12-28 00:07:04
     */

    private boolean opFlag;
    private String error;
    private String timestamp;

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
