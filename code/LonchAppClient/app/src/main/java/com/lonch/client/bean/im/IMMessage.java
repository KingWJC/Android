package com.lonch.client.bean.im;


import com.alibaba.fastjson.annotation.JSONField;

import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.StandardCharsets;

public class IMMessage {
    private String msgId;
    private long timestamp;
    private String sender;
    private String nickName;
    private String friendRemark;
    private String nameCard;
    private String faceURL;
    private String groupId;
    private String userId;
    private long seq;
    private int status;
    private boolean isSelf;
    private boolean isRead;
    private boolean isPeerRead;
    private IMTextElem textElem;
    private IMCustomElem customElem;
    private IMGroupTipsElem groupTipsElem;

    public IMGroupTipsElem getGroupTipsElem() {
        return groupTipsElem;
    }

    public void setGroupTipsElem(IMGroupTipsElem groupTipsElem) {
        this.groupTipsElem = groupTipsElem;
    }

    public boolean getIsSelf() {
        return isSelf;
    }
    @JSONField(name="self")
    public void setIsSelf(boolean self) {
        isSelf = self;
    }

    public boolean getIsRead() {
        return isRead;
    }
    @JSONField(name="read")
    public void setIsRead(boolean read) {
        isRead = read;
    }

    public boolean getIsPeerRead() {
        return isPeerRead;
    }
    @JSONField(name="peerRead")
    public void setIsPeerRead(boolean peerRead) {
        isPeerRead = peerRead;
    }

    public IMCustomElem getCustomElem() {
        return customElem;
    }
    public void setCustomElem(IMCustomElem customElem) {
        this.customElem = customElem;
    }
    public IMTextElem getTextElem() {
        return textElem;
    }

    public void setTextElem(IMTextElem textElem) {
        this.textElem = textElem;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private int elemType;

    @JSONField(name = "msgId")
    public String getMsgId() {
        return msgId;
    }

    @JSONField(name = "msgID")
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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

    public String getFaceURL() {
        return faceURL;
    }

    public void setFaceURL(String faceURL) {
        this.faceURL = faceURL;
    }

    @JSONField(name = "groupId")
    public String getGroupId() {
        return groupId;
    }
    @JSONField(name = "groupID")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    @JSONField(name = "userId")
    public String getUserId() {
        return userId;
    }
    @JSONField(name = "userID")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public int getElemType() {
        return elemType;
    }

    public void setElemType(int elemType) {
        this.elemType = elemType;
    }
    public static class IMTextElem{
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private String text;
    }
    public static class IMCustomElem{

        private String desc;
        private String extension;
        private String data;
        @JSONField(name = "desc")
        public String getDesc() {
            return desc;
        }
        @JSONField(name = "description")
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getData() {
            return new String(Base64.decode(this.data.getBytes()), StandardCharsets.UTF_8);
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static class IMGroupTipsElem{
        private String groupId;
        private int type;
        private int memberCount;
        private IMGroupMemberInfo opMember;


        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMemberCount() {
            return memberCount;
        }

        public void setMemberCount(int memberCount) {
            this.memberCount = memberCount;
        }

        public IMGroupMemberInfo getOpMember() {
            return opMember;
        }

        public void setOpMember(IMGroupMemberInfo opMember) {
            this.opMember = opMember;
        }
    }


}
