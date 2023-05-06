package com.lonch.client.bean;

public class RefreshOrgBen {

    /**
     * opFlag : true
     * error :
     * serviceResult : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhT3duZXJPcmdJZCI6IjNiOWQ4MmJjZTRkZjRkMWFiMTExZjgyNWVlNDBkNTg1IiwiYWNjb3VudElkIjoiYzM5Zjk0ZWYxZjI0NDkxYmEzYmY2MmIzZjRhZDU2ODQiLCJleHAiOjE2MTQwNDMxNjQsInVzZXJJZCI6ImMzOWY5NGVmMWYyNDQ5MWJhM2JmNjJiM2Y0YWQ1Njg0IiwiaWF0IjoxNjEzODI3MTY0LCJ1c2VybmFtZSI6IjE1NzExNDk0NTQ2In0.NdhEy_W-OGui40eUJOeV66-xY2-UxXtmD3996gN9fts
     * timestamp : 2021-02-20 21:19:24
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
}
