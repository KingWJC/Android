package com.lonch.client.bean.im;

import com.alibaba.fastjson.annotation.JSONField;

public class IMUserInfo {
    private String userId;
    private String nickName;
    private String faceUrl;
    private String selfSignature;
    private int gender;
    private int allowTYpe;
    @JSONField(name = "userId")
    public String getUserId() {
        return userId;
    }
    @JSONField(name = "userID")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getSelfSignature() {
        return selfSignature;
    }

    public void setSelfSignature(String selfSignature) {
        this.selfSignature = selfSignature;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAllowTYpe() {
        return allowTYpe;
    }

    public void setAllowTYpe(int allowTYpe) {
        this.allowTYpe = allowTYpe;
    }
}
