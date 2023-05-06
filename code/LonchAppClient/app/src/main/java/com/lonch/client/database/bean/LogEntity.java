package com.lonch.client.database.bean;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_log_entity")
public class LogEntity {
    @Id(autoincrement = true)
    @Expose(serialize = false,deserialize = false)
    private Long id;
    @Expose(serialize = false,deserialize = false)
    private String operation;
    @Expose(serialize = false,deserialize = false)
    private String startTime;
    @Expose(serialize = false,deserialize = false)
    private String endTime;
    @Expose(serialize = false,deserialize = false)
    private String fromType;
    @Expose(serialize = false,deserialize = false)
    private Long time;
    @Expose(serialize = false,deserialize = false)
    private String json;
    @Expose
    private String args;
    @Generated(hash = 995898438)
    public LogEntity(Long id, String operation, String startTime, String endTime,
                     String fromType, Long time, String json, String args) {
        this.id = id;
        this.operation = operation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fromType = fromType;
        this.time = time;
        this.json = json;
        this.args = args;
    }
    @Generated(hash = 1472642729)
    public LogEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOperation() {
        return this.operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getFromType() {
        return this.fromType;
    }
    public void setFromType(String fromType) {
        this.fromType = fromType;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    public String getArgs() {
        return this.args;
    }
    public void setArgs(String args) {
        this.args = args;
    }
}
