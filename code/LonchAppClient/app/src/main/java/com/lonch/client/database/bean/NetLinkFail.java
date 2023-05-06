package com.lonch.client.database.bean;

import com.google.gson.annotations.Expose;

public class NetLinkFail {
    @Expose
    private String apiId;
    @Expose
    private String linkId;
    @Expose
    private String ip;
    @Expose
    private int failAccumulate;
    @Expose
    private String id;

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    @Expose
    private int allCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    @Expose
    private String reportTime;

    public int getFailAccumulate() {
        return failAccumulate;
    }

    public void setFailAccumulate(int failAccumulate) {
        this.failAccumulate = failAccumulate;
    }

    @Expose
    private double failAverage;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getFailAverage() {
        return failAverage;
    }

    public void setFailAverage(double failAverage) {
        this.failAverage = failAverage;
    }
}
