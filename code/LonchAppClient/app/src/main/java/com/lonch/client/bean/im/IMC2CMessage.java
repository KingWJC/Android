package com.lonch.client.bean.im;

public class IMC2CMessage {
    private boolean isSelf = false;
    private String msgId;
    private String sender;
    private boolean isPeerRead = false;
    private boolean isRead = false;
    private long timestamp;
    private TextElem textElem;
    private int elemType = 1;
    private int status = 0;
    private String userId;
    private String groupId;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String nickName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setIsSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }
    public boolean getIsSelf() {
        return isSelf;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    public String getMsgId() {
        return msgId;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getSender() {
        return sender;
    }

    public void setIsPeerRead(boolean isPeerRead) {
        this.isPeerRead = isPeerRead;
    }
    public boolean getIsPeerRead() {
        return isPeerRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
    public boolean getIsRead() {
        return isRead;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public void setTextElem(TextElem textElem) {
        this.textElem = textElem;
    }
    public TextElem getTextElem() {
        return textElem;
    }

    public void setElemType(int elemType) {
        this.elemType = elemType;
    }
    public int getElemType() {
        return elemType;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
    public static class TextElem{
        private String text;
        public void setText(String text) {
            this.text = text;
        }
        public String getText() {
            return text;
        }
    }
}
