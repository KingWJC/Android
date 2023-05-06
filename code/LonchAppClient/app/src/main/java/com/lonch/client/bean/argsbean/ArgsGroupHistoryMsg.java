package com.lonch.client.bean.argsbean;

public class ArgsGroupHistoryMsg {


    /**
     * groupID : @TGS#2BPBRK5GM
     * count : 20
     * lastMsg : {}
     */

    private String groupId;
    private int count;
    private LastMsgBean lastMsg;

    public LastMsgBean getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(LastMsgBean lastMsg) {
        this.lastMsg = lastMsg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public static class LastMsgBean{

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        private String msgId;

    }




}
