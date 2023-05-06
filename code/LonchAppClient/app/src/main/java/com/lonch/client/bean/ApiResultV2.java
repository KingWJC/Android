package com.lonch.client.bean;

import java.io.Serializable;

public class ApiResultV2<T> implements Serializable {

    private boolean opFlag = true;
    private String  errorMsg ="";

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String  errorCode = "";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    private String sid;

    private Long  timestamp;
    private T serviceResult;

    public ApiResultV2() {
        long timeMillis = System.currentTimeMillis();
        setTimestamp(timeMillis);
    }

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }


    public T getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(T serviceResult) {
        this.serviceResult = serviceResult;
    }
}
