package com.lonch.client.bean.im;

public class IMCustomMessage {
    private boolean isSelf = false;
    private String msgId;
    private String sender;
    private boolean isPeerRead = false;
    private boolean isRead = false;
    private long timestamp;
    private CustomElem customElem;
    private int elemType = 2;
    private int status = 0;
    private String userId;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    private int seq;


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

    public CustomElem getCustomElem() {
        return customElem;
    }

    public void setCustomElem(CustomElem customElem) {
        this.customElem = customElem;
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
    public static class CustomElem{
        private String desc;
        private String data;
        private String extension;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }
}
