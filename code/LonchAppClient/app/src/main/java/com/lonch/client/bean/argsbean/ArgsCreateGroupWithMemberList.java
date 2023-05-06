package com.lonch.client.bean.argsbean;

import java.util.List;

public class ArgsCreateGroupWithMemberList {


    /**
     * info : {"faceURL":"https://resources.lonch.com.cn/bi-chat/chat/2021/01/22/head20210122121146.png","groupID":"","groupType":"Work","groupName":",刘高飞,洪景泉,杨洋","Introduction":""}
     * memberList : [{"role":200,"userID":"129179145744814124"},{"role":200,"userID":"2145c3e2f4c94b20bb7b60db26ab8537"},{"role":200,"userID":"129179145744814111"}]
     */

    private InfoBean info;
    private List<MemberListBean> memberList;


    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<MemberListBean> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberListBean> memberList) {
        this.memberList = memberList;
    }

    public static class InfoBean {
        /**
         * faceURL : https://resources.lonch.com.cn/bi-chat/chat/2021/01/22/head20210122121146.png
         * groupID :
         * groupType : Work
         * groupName : ,刘高飞,洪景泉,杨洋
         * Introduction :
         */

        private String faceUrl;
        private String groupId;
        private String groupType;
        private String groupName;
        private String Introduction;

        public String getFaceUrl() {
            return faceUrl;
        }

        public void setFaceUrl(String faceUrl) {
            this.faceUrl = faceUrl;
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

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getIntroduction() {
            return Introduction;
        }

        public void setIntroduction(String introduction) {
            Introduction = introduction;
        }
    }

    public static class MemberListBean {
        /**
         * role : 200
         * userID : 129179145744814124
         */

        private int role;
        private String userId;

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
