package com.lonch.client.database.bean;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_api_entity")
public class ApiResponseEntity {
    @Id(autoincrement = true)
    @Expose(serialize = false,deserialize = false)
    private Long id;
    @Expose
    private String url;
    @Expose
    private int success; //1 成功 0 失败
    @Expose
    private Long createTime;
    @Expose
    private Long responseTime;
    @Expose(serialize = false,deserialize = false)
    private Long time;
    @Generated(hash = 926772442)
    public ApiResponseEntity(Long id, String url, int success, Long createTime,
                             Long responseTime, Long time) {
        this.id = id;
        this.url = url;
        this.success = success;
        this.createTime = createTime;
        this.responseTime = responseTime;
        this.time = time;
    }
    @Generated(hash = 1559800739)
    public ApiResponseEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getSuccess() {
        return this.success;
    }
    public void setSuccess(int success) {
        this.success = success;
    }
    public Long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public Long getResponseTime() {
        return this.responseTime;
    }
    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
}
