package com.lonch.client.bean;

import java.io.Serializable;

public class ApiResult<T> implements Serializable {

    private boolean opFlag = true;
    private String  error ="";
    private String  timestamp;
    private T serviceResult;

    public ApiResult() {
        long timeMillis = System.currentTimeMillis();
        String tMillis = String.valueOf(timeMillis);
        setTimestamp(tMillis);
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

    public T getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(T serviceResult) {
        this.serviceResult = serviceResult;
    }
}
