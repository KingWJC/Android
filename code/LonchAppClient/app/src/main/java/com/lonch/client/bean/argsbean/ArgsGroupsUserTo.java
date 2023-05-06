package com.lonch.client.bean.argsbean;

import java.util.List;

public class ArgsGroupsUserTo {


    /**
     * groupID : xxx
     * userList : ["xxx","xxx"]
     */
    private String groupId;
    private List<String> userList;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }
}
