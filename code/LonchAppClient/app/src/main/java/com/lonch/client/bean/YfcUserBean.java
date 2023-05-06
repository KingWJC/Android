package com.lonch.client.bean;

import java.io.Serializable;
import java.util.List;

public class YfcUserBean implements Serializable {


    /**
     * opFlag : true
     * serviceResult : {"id":"f51d19c4a7e046ca8671b9431f24f440","name":"白二鹏","marketingId":"fa9741283cfd4462967da43d994bf9e6","genderCode":"1","phone":"15711494546","portrait":"https://wework.qpic.cn/wwhead/duc2TvpEgSTPk74IwG7Bs9gJYHks5SSWB3tG2M6YL27RuYAEP1ZAicicD3UNjrlT4ZVMCuKkxwKZI/0","marketing":true,"linkman":false,"role":[{"id":"237","roleName":"yyy"},{"id":"14","roleName":"000050开发工程师"},{"id":"24","roleName":"000070员工"},{"id":"254","roleName":"DEC-关键业务关键节点"},{"id":"214","roleName":"朗致星"},{"id":"277","roleName":"三级业务员"}]}
     */

    private boolean opFlag;
    private ServiceResultBean serviceResult;

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }

    public ServiceResultBean getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(ServiceResultBean serviceResult) {
        this.serviceResult = serviceResult;
    }

    public static class ServiceResultBean implements Serializable {
        /**
         * id : f51d19c4a7e046ca8671b9431f24f440
         * name : 白二鹏
         * marketingId : fa9741283cfd4462967da43d994bf9e6
         * genderCode : 1
         * phone : 15711494546
         * portrait : https://wework.qpic.cn/wwhead/duc2TvpEgSTPk74IwG7Bs9gJYHks5SSWB3tG2M6YL27RuYAEP1ZAicicD3UNjrlT4ZVMCuKkxwKZI/0
         * marketing : true
         * linkman : false
         * role : [{"id":"237","roleName":"yyy"},{"id":"14","roleName":"000050开发工程师"},{"id":"24","roleName":"000070员工"},{"id":"254","roleName":"DEC-关键业务关键节点"},{"id":"214","roleName":"朗致星"},{"id":"277","roleName":"三级业务员"}]
         */

        private String id;
        private String name;
        private String marketingId;
        private String genderCode;
        private String phone;
        private String portrait;
        private boolean marketing;
        private boolean linkman;
        private List<RoleBean> role;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMarketingId() {
            return marketingId;
        }

        public void setMarketingId(String marketingId) {
            this.marketingId = marketingId;
        }

        public String getGenderCode() {
            return genderCode;
        }

        public void setGenderCode(String genderCode) {
            this.genderCode = genderCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public boolean isMarketing() {
            return marketing;
        }

        public void setMarketing(boolean marketing) {
            this.marketing = marketing;
        }

        public boolean isLinkman() {
            return linkman;
        }

        public void setLinkman(boolean linkman) {
            this.linkman = linkman;
        }

        public List<RoleBean> getRole() {
            return role;
        }

        public void setRole(List<RoleBean> role) {
            this.role = role;
        }

        public static class RoleBean {
            /**
             * id : 237
             * roleName : yyy
             */

            private String id;
            private String roleName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }
        }
    }
}
