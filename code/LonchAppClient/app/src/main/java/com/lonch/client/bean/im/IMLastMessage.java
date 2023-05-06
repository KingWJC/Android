package com.lonch.client.bean.im;

import com.alibaba.fastjson.annotation.JSONField;

import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.StandardCharsets;

public class IMLastMessage {
    private long timestamp;
    private IMTextElem textElem;
    private IMCustomElem customElem;
    private IMGroupTipsElem groupTipsElem;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public IMTextElem getTextElem() {
        return textElem;
    }

    public void setTextElem(IMTextElem textElem) {
        this.textElem = textElem;
    }

    public IMCustomElem getCustomElem() {
        return customElem;
    }

    public void setCustomElem(IMCustomElem customElem) {
        this.customElem = customElem;
    }

    public IMGroupTipsElem getGroupTipsElem() {
        return groupTipsElem;
    }

    public void setGroupTipsElem(IMGroupTipsElem groupTipsElem) {
        this.groupTipsElem = groupTipsElem;
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
