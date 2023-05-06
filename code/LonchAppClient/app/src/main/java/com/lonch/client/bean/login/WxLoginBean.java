package com.lonch.client.bean.login;

public class WxLoginBean {


    /**
     * error : 成功
     * opFlag : true
     * serviceResult : {"code":"0000","dataOwnerOrgId":"129179145690288128","deleted":false,"msg":"登录成功","realName":"白二鹏","roleName":"000070员工","token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhT3duZXJPcmdJZCI6IjEyOTE3OTE0NTY5MDI4ODEyOCIsImFjY291bnRJZCI6IjU2NGYyODAzNWVjNjRlNTliZjY5MDAxNDQ1MDVlZjI4IiwiZXhwIjoxNjEwNjM3MzYzLCJ1c2VySWQiOiJmNTFkMTljNGE3ZTA0NmNhODY3MWI5NDMxZjI0ZjQ0MCIsImlhdCI6MTYxMDUyOTM2MywidXNlcm5hbWUiOiIxNTcxMTQ5NDU0NiJ9.umIFRNG_5LWOmHBG8go6vHyIorAQgpUpV-1iBjKn43U","userId":"f51d19c4a7e046ca8671b9431f24f440"}
     * timestamp : 2021-01-13 17:16:03
     */

    private String error;
    private boolean opFlag;
    private ServiceResultBean serviceResult;
    private String timestamp;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class ServiceResultBean {
        /**
         * code : 0000
         * dataOwnerOrgId : 129179145690288128
         * deleted : false
         * msg : 登录成功
         * realName : 白二鹏
         * roleName : 000070员工
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhT3duZXJPcmdJZCI6IjEyOTE3OTE0NTY5MDI4ODEyOCIsImFjY291bnRJZCI6IjU2NGYyODAzNWVjNjRlNTliZjY5MDAxNDQ1MDVlZjI4IiwiZXhwIjoxNjEwNjM3MzYzLCJ1c2VySWQiOiJmNTFkMTljNGE3ZTA0NmNhODY3MWI5NDMxZjI0ZjQ0MCIsImlhdCI6MTYxMDUyOTM2MywidXNlcm5hbWUiOiIxNTcxMTQ5NDU0NiJ9.umIFRNG_5LWOmHBG8go6vHyIorAQgpUpV-1iBjKn43U
         * userId : f51d19c4a7e046ca8671b9431f24f440
         */

        private String code;
        private String dataOwnerOrgId;
        private boolean deleted;
        private String msg;
        private String realName;
        private String token;
        private String userId;

        private String unionid;

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDataOwnerOrgId() {
            return dataOwnerOrgId;
        }

        public void setDataOwnerOrgId(String dataOwnerOrgId) {
            this.dataOwnerOrgId = dataOwnerOrgId;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }


}
