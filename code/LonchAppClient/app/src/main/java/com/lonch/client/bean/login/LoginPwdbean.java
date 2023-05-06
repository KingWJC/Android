package com.lonch.client.bean.login;

public class LoginPwdbean {


    /**
     * opFlag : true
     * error : 成功
     * serviceResult : {"caid":"f51d19c4a7e046ca8671b9431f24f440","code":"0000","msg":"登录成功","accountId":"564f28035ec64e59bf6900144505ef28","userId":"f51d19c4a7e046ca8671b9431f24f440","dataOwnerOrgId":"129179145690288128","userName":"15711494546","roleName":null,"realName":null,"unionid":null,"phone":null,"password":null,"deleted":false,"smsCode":null,"createTime":null,"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhT3duZXJPcmdJZCI6IjEyOTE3OTE0NTY5MDI4ODEyOCIsImFjY291bnRJZCI6IjU2NGYyODAzNWVjNjRlNTliZjY5MDAxNDQ1MDVlZjI4IiwiZXhwIjoxMjQxNzY0NjQ4MywidXNlcklkIjoiZjUxZDE5YzRhN2UwNDZjYTg2NzFiOTQzMWYyNGY0NDAiLCJpYXQiOjE2MTc2NDY0ODMsInVzZXJuYW1lIjoiMTU3MTE0OTQ1NDYifQ.JOZNtQCQ3QHOV9KBX_90tmqGIzJ_LstXiMdGELe6E3Y","version":null,"sessionKey":null}
     * timestamp : 2021-04-06 02:14:43
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
         * caid : f51d19c4a7e046ca8671b9431f24f440
         * code : 0000
         * msg : 登录成功
         * accountId : 564f28035ec64e59bf6900144505ef28
         * userId : f51d19c4a7e046ca8671b9431f24f440
         * dataOwnerOrgId : 129179145690288128
         * userName : 15711494546
         * roleName : null
         * realName : null
         * unionid : null
         * phone : null
         * password : null
         * deleted : false
         * smsCode : null
         * createTime : null
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhT3duZXJPcmdJZCI6IjEyOTE3OTE0NTY5MDI4ODEyOCIsImFjY291bnRJZCI6IjU2NGYyODAzNWVjNjRlNTliZjY5MDAxNDQ1MDVlZjI4IiwiZXhwIjoxMjQxNzY0NjQ4MywidXNlcklkIjoiZjUxZDE5YzRhN2UwNDZjYTg2NzFiOTQzMWYyNGY0NDAiLCJpYXQiOjE2MTc2NDY0ODMsInVzZXJuYW1lIjoiMTU3MTE0OTQ1NDYifQ.JOZNtQCQ3QHOV9KBX_90tmqGIzJ_LstXiMdGELe6E3Y
         * version : null
         * sessionKey : null
         */

        private String caid;
        private String code;
        private String msg;
        private String accountId;
        private String userId;
        private String dataOwnerOrgId;
        private String userName;
        private Object roleName;
        private Object realName;
        private Object unionid;
        private Object phone;
        private Object password;
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

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
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

        public Object getRoleName() {
            return roleName;
        }

        public void setRoleName(Object roleName) {
            this.roleName = roleName;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
            this.realName = realName;
        }

        public Object getUnionid() {
            return unionid;
        }

        public void setUnionid(Object unionid) {
            this.unionid = unionid;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
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
