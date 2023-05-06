package com.lonch.client.bean;

public class RefreshTokenBean {


    /**
     * opFlag : true
     * error :
     * serviceResult : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhT3duZXJPcmdJZCI6IjEyOTE3OTE0NTY5MDI4ODEyOCIsImFjY291bnRJZCI6IjEyOTE3OTE0NTc0NDgxNDEyNiIsImV4cCI6MTYwODc0NDMwNiwidXNlcklkIjoiMTI5MTc5MTQ1NzQ0ODE0MTI2IiwiaWF0IjoxNjA4NTI4MzA2LCJ1c2VybmFtZSI6IjE4NjE4MjY0NjE1In0.1e_YXOPbpEt4mIo7U3xx9UY1CrCb07FuHoFhDEyk4pc
     * timestamp :
     */

    private boolean opFlag;
    private String error;
    private String serviceResult;
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

    @Override
    public String toString() {
        return "RefreshTokenBean{" +
                "opFlag=" + opFlag +
                ", error='" + error + '\'' +
                ", serviceResult='" + serviceResult + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
