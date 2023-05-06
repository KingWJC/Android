package com.lonch.client.bean.argsbean;

import java.util.List;

public class ArgsGetCtcMsg {


    /**
     * conversationID : c2c_c73f6761ae3642da8d9535d5ca038525
     * userID : c73f6761ae3642da8d9535d5ca038525
     * count : 20
     * lastMsg : {"customElem":null,"elemType":1,"faceElem":null,"faceUrl":"https://wework.qpic.cn/wwhead/duc2TvpEgSTPk74IwG7Bs9gJYHks5SSWB3tG2M6YL27RuYAEP1ZAicicD3UNjrlT4ZVMCuKkxwKZI/0","fileElem":null,"friendRemark":null,"groupAtUserList":[],"groupID":null,"groupTipsElem":null,"imageElem":null,"localCustomData":"","localCustomInt":0,"locationElem":null,"msgID":"144115231917211572-1616431225-4004862854","nameCard":null,"nickName":"白二鹏","offlinePushInfo":{"desc":null,"disablePush":false,"title":null},"peerRead":true,"priority":2,"read":true,"self":true,"sender":"f51d19c4a7e046ca8671b9431f24f440","seq":1548579854,"soundElem":null,"status":2,"textElem":{"nextElem":null,"text":"2"},"timestamp":1616431225,"userID":"c73f6761ae3642da8d9535d5ca038525","videoElem":null}
     */

    private String conversationId;
    private String userId;
    private int count;
    private LastMsgBean lastMsg;


    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LastMsgBean getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(LastMsgBean lastMsg) {
        this.lastMsg = lastMsg;
    }

    public static class LastMsgBean {
        /**
         * customElem : null
         * elemType : 1
         * faceElem : null
         * faceUrl : https://wework.qpic.cn/wwhead/duc2TvpEgSTPk74IwG7Bs9gJYHks5SSWB3tG2M6YL27RuYAEP1ZAicicD3UNjrlT4ZVMCuKkxwKZI/0
         * fileElem : null
         * friendRemark : null
         * groupAtUserList : []
         * groupID : null
         * groupTipsElem : null
         * imageElem : null
         * localCustomData :
         * localCustomInt : 0
         * locationElem : null
         * msgID : 144115231917211572-1616431225-4004862854
         * nameCard : null
         * nickName : 白二鹏
         * offlinePushInfo : {"desc":null,"disablePush":false,"title":null}
         * peerRead : true
         * priority : 2
         * read : true
         * self : true
         * sender : f51d19c4a7e046ca8671b9431f24f440
         * seq : 1548579854
         * soundElem : null
         * status : 2
         * textElem : {"nextElem":null,"text":"2"}
         * timestamp : 1616431225
         * userID : c73f6761ae3642da8d9535d5ca038525
         * videoElem : null
         */

        private Object customElem;
        private int elemType;
        private Object faceElem;
        private String faceUrl;
        private Object fileElem;
        private Object friendRemark;
        private Object groupID;
        private Object groupTipsElem;
        private Object imageElem;
        private String localCustomData;
        private int localCustomInt;
        private Object locationElem;
        private String msgId;
        private Object nameCard;
        private String nickName;
        private OfflinePushInfoBean offlinePushInfo;
        private boolean peerRead;
        private int priority;
        private boolean read;
        private boolean self;
        private String sender;
        private int seq;
        private Object soundElem;
        private int status;
        private TextElemBean textElem;
        private int timestamp;
        private String userID;
        private Object videoElem;
        private List<?> groupAtUserList;

        public Object getCustomElem() {
            return customElem;
        }

        public void setCustomElem(Object customElem) {
            this.customElem = customElem;
        }

        public int getElemType() {
            return elemType;
        }

        public void setElemType(int elemType) {
            this.elemType = elemType;
        }

        public Object getFaceElem() {
            return faceElem;
        }

        public void setFaceElem(Object faceElem) {
            this.faceElem = faceElem;
        }

        public String getFaceUrl() {
            return faceUrl;
        }

        public void setFaceUrl(String faceUrl) {
            this.faceUrl = faceUrl;
        }

        public Object getFileElem() {
            return fileElem;
        }

        public void setFileElem(Object fileElem) {
            this.fileElem = fileElem;
        }

        public Object getFriendRemark() {
            return friendRemark;
        }

        public void setFriendRemark(Object friendRemark) {
            this.friendRemark = friendRemark;
        }

        public Object getGroupID() {
            return groupID;
        }

        public void setGroupID(Object groupID) {
            this.groupID = groupID;
        }

        public Object getGroupTipsElem() {
            return groupTipsElem;
        }

        public void setGroupTipsElem(Object groupTipsElem) {
            this.groupTipsElem = groupTipsElem;
        }

        public Object getImageElem() {
            return imageElem;
        }

        public void setImageElem(Object imageElem) {
            this.imageElem = imageElem;
        }

        public String getLocalCustomData() {
            return localCustomData;
        }

        public void setLocalCustomData(String localCustomData) {
            this.localCustomData = localCustomData;
        }

        public int getLocalCustomInt() {
            return localCustomInt;
        }

        public void setLocalCustomInt(int localCustomInt) {
            this.localCustomInt = localCustomInt;
        }

        public Object getLocationElem() {
            return locationElem;
        }

        public void setLocationElem(Object locationElem) {
            this.locationElem = locationElem;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public Object getNameCard() {
            return nameCard;
        }

        public void setNameCard(Object nameCard) {
            this.nameCard = nameCard;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public OfflinePushInfoBean getOfflinePushInfo() {
            return offlinePushInfo;
        }

        public void setOfflinePushInfo(OfflinePushInfoBean offlinePushInfo) {
            this.offlinePushInfo = offlinePushInfo;
        }

        public boolean isPeerRead() {
            return peerRead;
        }

        public void setPeerRead(boolean peerRead) {
            this.peerRead = peerRead;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }

        public boolean isSelf() {
            return self;
        }

        public void setSelf(boolean self) {
            this.self = self;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public Object getSoundElem() {
            return soundElem;
        }

        public void setSoundElem(Object soundElem) {
            this.soundElem = soundElem;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public TextElemBean getTextElem() {
            return textElem;
        }

        public void setTextElem(TextElemBean textElem) {
            this.textElem = textElem;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public Object getVideoElem() {
            return videoElem;
        }

        public void setVideoElem(Object videoElem) {
            this.videoElem = videoElem;
        }

        public List<?> getGroupAtUserList() {
            return groupAtUserList;
        }

        public void setGroupAtUserList(List<?> groupAtUserList) {
            this.groupAtUserList = groupAtUserList;
        }

        public static class OfflinePushInfoBean {
            /**
             * desc : null
             * disablePush : false
             * title : null
             */

            private Object desc;
            private boolean disablePush;
            private Object title;

            public Object getDesc() {
                return desc;
            }

            public void setDesc(Object desc) {
                this.desc = desc;
            }

            public boolean isDisablePush() {
                return disablePush;
            }

            public void setDisablePush(boolean disablePush) {
                this.disablePush = disablePush;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }
        }

        public static class TextElemBean {
            /**
             * nextElem : null
             * text : 2
             */

            private Object nextElem;
            private String text;

            public Object getNextElem() {
                return nextElem;
            }

            public void setNextElem(Object nextElem) {
                this.nextElem = nextElem;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
