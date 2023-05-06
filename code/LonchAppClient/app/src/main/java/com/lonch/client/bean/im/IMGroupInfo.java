package com.lonch.client.bean.im;

import com.alibaba.fastjson.annotation.JSONField;

public class IMGroupInfo {
    private String resultMsg;
    private int resultCode;
    private GroupInfo info;
    @JSONField(name = "resultMsg")
    public String getResultMsg() {
        return resultMsg;
    }
    @JSONField(name = "resultMessage")
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
    @JSONField(name = "info")
    public GroupInfo getInfo() {
        return info;
    }
    @JSONField(name = "groupInfo")
    public void setInfo(GroupInfo info) {
        this.info = info;
    }

    public static class GroupInfo{
        private String groupId;
        private String groupType;
        private String groupName;
        private String notification;
        private String introduction;
        private String faceUrl;
        private boolean allMuted;
        private String owner;
        private long createTime;
        private int groupAddOpt;
        private long lastInfoTime;
        private long lastMessageTime;
        private long memberCount;
        private long onlineCount;
        private long memberMaxCount;
        private int recvOpt;
        private int role;
        private long muteUnit;
        private long joinTime;

        @JSONField(name = "groupId")
        public String getGroupId() {
            return groupId;
        }
        @JSONField(name = "groupID")
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupType() {
            return groupType;
        }

        public void setGroupType(String groupType) {
            this.groupType = groupType;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getNotification() {
            return notification;
        }

        public void setNotification(String notification) {
            this.notification = notification;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }


        public boolean isAllMuted() {
            return allMuted;
        }

        public void setAllMuted(boolean allMuted) {
            this.allMuted = allMuted;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getGroupAddOpt() {
            return groupAddOpt;
        }

        public void setGroupAddOpt(int groupAddOpt) {
            this.groupAddOpt = groupAddOpt;
        }

        public long getLastInfoTime() {
            return lastInfoTime;
        }

        public void setLastInfoTime(long lastInfoTime) {
            this.lastInfoTime = lastInfoTime;
        }

        public long getLastMessageTime() {
            return lastMessageTime;
        }

        public void setLastMessageTime(long lastMessageTime) {
            this.lastMessageTime = lastMessageTime;
        }

        public long getMemberMaxCount() {
            return memberMaxCount;
        }

        public void setMemberMaxCount(long memberMaxCount) {
            this.memberMaxCount = memberMaxCount;
        }

        public int getRecvOpt() {
            return recvOpt;
        }

        public void setRecvOpt(int recvOpt) {
            this.recvOpt = recvOpt;
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


        public String getFaceUrl() {
            return faceUrl;
        }

        public void setFaceUrl(String faceUrl) {
            this.faceUrl = faceUrl;
        }

        public long getMemberCount() {
            return memberCount;
        }

        public void setMemberCount(long memberCount) {
            this.memberCount = memberCount;
        }

        public long getOnlineCount() {
            return onlineCount;
        }

        public void setOnlineCount(long onlineCount) {
            this.onlineCount = onlineCount;
        }
    }

}
