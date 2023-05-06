package com.lonch.client.database.bean;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity(nameInDb = "lonch_net_entity")
public class NetLinkEntity {
    @Id
    @Expose
    private String id;
    @Expose
    private String linkId;
    @Expose
    private String apiId;
    @Expose
    private int paramValue;
    @Expose
    private int resposeTime;
    @Expose
    private int tranferTime;
    @Expose
    private int netType; //1 wifi 2 流量
    @Expose
    private double lossRate = 0;
    @Expose
    private String reportTime;
    @Expose(serialize = false,deserialize = false)
    private  int type; // 1 成功 0失败
    @Expose
    private String ip;
    @Expose(serialize = false,deserialize = false)
    private Long time;


    @Generated(hash = 1415854698)
    public NetLinkEntity(String id, String linkId, String apiId, int paramValue,
                         int resposeTime, int tranferTime, int netType, double lossRate,
                         String reportTime, int type, String ip, Long time) {
        this.id = id;
        this.linkId = linkId;
        this.apiId = apiId;
        this.paramValue = paramValue;
        this.resposeTime = resposeTime;
        this.tranferTime = tranferTime;
        this.netType = netType;
        this.lossRate = lossRate;
        this.reportTime = reportTime;
        this.type = type;
        this.ip = ip;
        this.time = time;
    }
    @Generated(hash = 214023247)
    public NetLinkEntity() {
    }


    public String getLinkId() {
        return this.linkId;
    }
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
    public String getApiId() {
        return this.apiId;
    }
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }
    public int getParamValue() {
        return this.paramValue;
    }
    public void setParamValue(int paramValue) {
        this.paramValue = paramValue;
    }
    public int getResposeTime() {
        return this.resposeTime;
    }
    public void setResposeTime(int resposeTime) {
        this.resposeTime = resposeTime;
    }
    public int getTranferTime() {
        return this.tranferTime;
    }
    public void setTranferTime(int tranferTime) {
        this.tranferTime = tranferTime;
    }
    public int getNetType() {
        return this.netType;
    }
    public void setNetType(int netType) {
        this.netType = netType;
    }
    
    public String getReportTime() {
        return this.reportTime;
    }
    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getLossRate() {
        return this.lossRate;
    }
    public void setLossRate(double lossRate) {
        this.lossRate = lossRate;
    }

}
