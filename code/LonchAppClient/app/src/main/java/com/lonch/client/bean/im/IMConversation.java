package com.lonch.client.bean.im;

import com.alibaba.fastjson.annotation.JSONField;


public class IMConversation{
    private int type;
    private String conversationId;
    private String userId;
    private String groupId;
    private String groupType;
    private String showName;
    private String faceUrl;
    private int unreadCount;
    private boolean isPinned;
    private IMLastMessage lastMessage;

    public IMLastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(IMLastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    @JSONField(name = "conversationId")
    public String getConversationId() {
        return conversationId;
    }
    @JSONField(name = "conversationID")
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public boolean getIsPinned() {
        return isPinned;
    }
    @JSONField(name="pinned")
    public void setIsPinned(boolean pinned) {
        isPinned = pinned;
    }

}
