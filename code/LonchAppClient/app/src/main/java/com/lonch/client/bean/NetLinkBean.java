package com.lonch.client.bean;

import java.util.List;

public class NetLinkBean {
    private boolean opFlag;
    private String timestamp;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private String errorMsg;
    private ServiceResultBean serviceResult;

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


    public ServiceResultBean getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(ServiceResultBean serviceResult) {
        this.serviceResult = serviceResult;
    }

    public static class ServiceResultBean {
        private boolean success;
        private String reason;
        private String errorCode;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        private DataBean data;


    }
    public static class DataBean{
        private ConfigBean config;
        private List<ServiceTestUrlsBean> serviceTestUrls;
        private List<AcceleratedLinkUrlsBean> acceleratedLinkUrls;

        public ConfigBean getConfig() {
            return config;
        }

        public void setConfig(ConfigBean config) {
            this.config = config;
        }

        public List<ServiceTestUrlsBean> getServiceTestUrls() {
            return serviceTestUrls;
        }

        public void setServiceTestUrls(List<ServiceTestUrlsBean> serviceTestUrls) {
            this.serviceTestUrls = serviceTestUrls;
        }

        public List<AcceleratedLinkUrlsBean> getAcceleratedLinkUrls() {
            return acceleratedLinkUrls;
        }

        public void setAcceleratedLinkUrls(List<AcceleratedLinkUrlsBean> acceleratedLinkUrls) {
            this.acceleratedLinkUrls = acceleratedLinkUrls;
        }
    }
    public static class ConfigBean{
        private boolean enableTest;
        private int loopTime;
        private int startSecond;
        private int timerInterval;

        public boolean isEnableTest() {
            return enableTest;
        }

        public void setEnableTest(boolean enableTest) {
            this.enableTest = enableTest;
        }

        public int getLoopTime() {
            return loopTime;
        }

        public void setLoopTime(int loopTime) {
            this.loopTime = loopTime;
        }

        public int getStartSecond() {
            return startSecond;
        }

        public void setStartSecond(int startSecond) {
            this.startSecond = startSecond;
        }

        public int getTimerInterval() {
            return timerInterval;
        }

        public void setTimerInterval(int timerInterval) {
            this.timerInterval = timerInterval;
        }
    }
    public static class  ServiceTestUrlsBean {
        private String id;
        private String path;
        private String serviceName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }
    }
    public static class  AcceleratedLinkUrlsBean {
        private String id;
        private String accelerationLinkUrl;
        private String remark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccelerationLinkUrl() {
            return accelerationLinkUrl;
        }

        public void setAccelerationLinkUrl(String accelerationLinkUrl) {
            this.accelerationLinkUrl = accelerationLinkUrl;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
