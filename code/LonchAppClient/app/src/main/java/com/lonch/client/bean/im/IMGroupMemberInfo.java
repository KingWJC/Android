package com.lonch.client.bean.im;

import com.alibaba.fastjson.annotation.JSONField;

public class IMGroupMemberInfo {
    private String userId;
    private String nickName;
    private String friendRemark;
    private String nameCard;
    private String faceUrl;
    private int role;
    private long muteUnit;
    private long joinTime;
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

    public String getFriendRemark() {
        return friendRemark;
    }

    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public long getMuteUnit() {
        return muteUnit;
    }

    public void setMuteUnit(long muteUnit) {
        this.muteUnit = muteUnit;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }
}
