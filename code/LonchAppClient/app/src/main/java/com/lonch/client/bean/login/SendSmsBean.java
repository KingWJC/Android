package com.lonch.client.bean.login;

public class SendSmsBean {


    /**
     * opFlag : true
     * error : 成功
     * serviceResult : {"caid":null,"code":"0000","msg":"成功","phone":"15711494546","smsCode":null}
     * timestamp :
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

    public static class ServiceResultBean  {
        /**
         * caid : null
         * code : 0000
         * msg : 成功
         * phone : 15711494546
         * smsCode : null
         */

        private Object caid;
        private String code;
        private String msg;
        private String phone;
        private Object smsCode;

        public Object getCaid() {
            return caid;
        }

        public void setCaid(Object caid) {
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(Object smsCode) {
            this.smsCode = smsCode;
        }
    }
}
