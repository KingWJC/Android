package com.lonch.client.bean.login;

public class LoginSmsBean {


    /**
     * opFlag : true
     * error : 成功
     * serviceResult : {"caid":"c39f94ef1f24491ba3bf62b3f4ad5684","code":"0000","msg":"登录成功","accountId":null,"userId":null,"dataOwnerOrgId":"129179145690288128","userName":null,"roleName":"000050开发工程师","realName":"白二鹏","unionid":null,"phone":null,"password":null,"deleted":false,"smsCode":null,"createTime":null,"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhT3duZXJPcmdJZCI6IjEyOTE3OTE0NTY5MDI4ODEyOCIsImFjY291bnRJZCI6ImMzOWY5NGVmMWYyNDQ5MWJhM2JmNjJiM2Y0YWQ1Njg0IiwiZXhwIjoxNjEyMDIyMjk4LCJ1c2VySWQiOiJjMzlmOTRlZjFmMjQ0OTFiYTNiZjYyYjNmNGFkNTY4NCIsImlhdCI6MTYxMTkxNDI5OCwidXNlcm5hbWUiOiIxNTcxMTQ5NDU0NiJ9.r3WAopKA3MXPKBX4ocmajt49MQNqEtglxyXtHFIaDb0","version":null,"sessionKey":null}
     * timestamp : 2021-01-29 17:58:18
     */

    private boolean opFlag;
    private String error;
    private ServiceResultBean serviceResult;
    private String timestamp;

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ServiceResultBean getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(ServiceResultBean serviceResult) {
        this.serviceResult = serviceResult;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class ServiceResultBean {
        /**
         * caid : c39f94ef1f24491ba3bf62b3f4ad5684
         * code : 0000
         * msg : 登录成功
         * accountId : null
         * userId : null
         * dataOwnerOrgId : 129179145690288128
         * userName : null
         * roleName : 000050开发工程师
         * realName : 白二鹏
         * unionid : null
         * phone : null
         * password : null
         * deleted : false
         * smsCode : null
         * createTime : null
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhT3duZXJPcmdJZCI6IjEyOTE3OTE0NTY5MDI4ODEyOCIsImFjY291bnRJZCI6ImMzOWY5NGVmMWYyNDQ5MWJhM2JmNjJiM2Y0YWQ1Njg0IiwiZXhwIjoxNjEyMDIyMjk4LCJ1c2VySWQiOiJjMzlmOTRlZjFmMjQ0OTFiYTNiZjYyYjNmNGFkNTY4NCIsImlhdCI6MTYxMTkxNDI5OCwidXNlcm5hbWUiOiIxNTcxMTQ5NDU0NiJ9.r3WAopKA3MXPKBX4ocmajt49MQNqEtglxyXtHFIaDb0
         * version : null
         * sessionKey : null
         */

        private String caid;
        private String code;
        private String msg;
        private Object accountId;
        private String userId;
        private String dataOwnerOrgId;
        private String userName;
        private String realName;
        private Object unionid;
        private String  phone;
        private String password;
        private boolean deleted;
        private Object smsCode;
        private Object createTime;
        private String token;
        private Object version;
        private Object sessionKey;

        public String getCaid() {
            return caid;
        }

        public void setCaid(String caid) {
            this.caid = caid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getAccountId() {
            return accountId;
        }

        public void setAccountId(Object accountId) {
            this.accountId = accountId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDataOwnerOrgId() {
            return dataOwnerOrgId;
        }

        public void setDataOwnerOrgId(String dataOwnerOrgId) {
            this.dataOwnerOrgId = dataOwnerOrgId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }


        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public Object getUnionid() {
            return unionid;
        }

        public void setUnionid(Object unionid) {
            this.unionid = unionid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public Object getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(Object smsCode) {
            this.smsCode = smsCode;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public Object getSessionKey() {
            return sessionKey;
        }

        public void setSessionKey(Object sessionKey) {
            this.sessionKey = sessionKey;
        }
    }
}
