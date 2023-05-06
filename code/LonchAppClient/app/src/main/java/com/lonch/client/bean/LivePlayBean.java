package com.lonch.client.bean;

public class LivePlayBean {
    private String error;
    private boolean opFlag;
    private String serviceResult;
    private String timestamp;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }

    public String getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(String serviceResult) {
        this.serviceResult = serviceResult;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
