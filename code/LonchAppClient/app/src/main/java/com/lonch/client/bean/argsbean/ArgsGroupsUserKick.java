package com.lonch.client.bean.argsbean;

import java.util.List;

public class ArgsGroupsUserKick {


    /**
     * groupID : xxx
     * userList : ["xxx","xxx"]
     */
    private String groupId;
    private String reason;
    private List<String> memberList;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }
}
