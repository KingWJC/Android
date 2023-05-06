package com.lonch.client.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_msggroup_entity")
public class MsgGroupsEntity {

    @Id(autoincrement = true)
    private Long id;

    private String userID;//用户ID
    private String groupID;//群组ID
    private String msgID;//消息ID
    private String msgType;//消息类型
    private String text;//消息
    private boolean isSelf;//true 自己


    private String nickName;//名字
    private String faceUrl;//头像
    private long timMessage;//时间
    private String ownerId;//消息所有者
    private String json;
    @Generated(hash = 2131406098)
    public MsgGroupsEntity(Long id, String userID, String groupID, String msgID,
                           String msgType, String text, boolean isSelf, String nickName,
                           String faceUrl, long timMessage, String ownerId, String json) {
        this.id = id;
        this.userID = userID;
        this.groupID = groupID;
        this.msgID = msgID;
        this.msgType = msgType;
        this.text = text;
        this.isSelf = isSelf;
        this.nickName = nickName;
        this.faceUrl = faceUrl;
        this.timMessage = timMessage;
        this.ownerId = ownerId;
        this.json = json;
    }
    @Generated(hash = 182146026)
    public MsgGroupsEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserID() {
        return this.userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getGroupID() {
        return this.groupID;
    }
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
    public String getMsgID() {
        return this.msgID;
    }
    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }
    public String getMsgType() {
        return this.msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean getIsSelf() {
        return this.isSelf;
    }
    public void setIsSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getFaceUrl() {
        return this.faceUrl;
    }
    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }
    public long getTimMessage() {
        return this.timMessage;
    }
    public void setTimMessage(long timMessage) {
        this.timMessage = timMessage;
    }
    public String getOwnerId() {
        return this.ownerId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }


}
