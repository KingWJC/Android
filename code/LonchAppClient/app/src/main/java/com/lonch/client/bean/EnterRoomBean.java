package com.lonch.client.bean;

public class EnterRoomBean {
    private String error;
    private boolean opFlag;
    private ServiceResultBean serviceResult;
    private String timestamp;

    public ServiceResultBean getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(ServiceResultBean serviceResult) {
        this.serviceResult = serviceResult;
    }

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


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class ServiceResultBean{
        public boolean isAllowEnter() {
            return allowEnter;
        }

        public void setAllowEnter(boolean allowEnter) {
            this.allowEnter = allowEnter;
        }

        public boolean isEncriptyRoom() {
            return isEncriptyRoom;
        }

        public void setEncriptyRoom(boolean encriptyRoom) {
            isEncriptyRoom = encriptyRoom;
        }

        private boolean allowEnter;
        private boolean isEncriptyRoom;
        private boolean inBlacklist;

        public boolean isInBlacklist() {
            return inBlacklist;
        }

        public void setInBlacklist(boolean inBlacklist) {
            this.inBlacklist = inBlacklist;
        }
    }
}
