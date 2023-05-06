package com.lonch.client.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_userinfo_entity")
public class InfoUserEntity {

    @Id(autoincrement = true)
    private Long id;
    private String userId;//用户Id
    private String faceUrl;//头像
    private String nickName;//名称
    private long timMessage;//时间
    @Generated(hash = 1896181439)
    public InfoUserEntity(Long id, String userId, String faceUrl, String nickName,
                          long timMessage) {
        this.id = id;
        this.userId = userId;
        this.faceUrl = faceUrl;
        this.nickName = nickName;
        this.timMessage = timMessage;
    }
    @Generated(hash = 1164806521)
    public InfoUserEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFaceUrl() {
        return this.faceUrl;
    }
    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public long getTimMessage() {
        return this.timMessage;
    }
    public void setTimMessage(long timMessage) {
        this.timMessage = timMessage;
    }

}
