package com.lonch.client.bean;

import java.io.Serializable;

public class ImLoginBean {

    /**
     * error :
     * opFlag : true
     * serviceResult : {"createTime":"","userId":"","userSig":"","userSigExpire":0}
     * timestamp :
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

    public static class ServiceResultBean implements Serializable {
        /**
         * createTime :
         * userId :
         * userSig :
         * userSigExpire : 0
         */

        private String createTime;
        private String userId;
        private String userSig;
        private int userSigExpire;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserSig() {
            return userSig;
        }

        public void setUserSig(String userSig) {
            this.userSig = userSig;
        }

        public int getUserSigExpire() {
            return userSigExpire;
        }

        public void setUserSigExpire(int userSigExpire) {
            this.userSigExpire = userSigExpire;
        }
    }
}
