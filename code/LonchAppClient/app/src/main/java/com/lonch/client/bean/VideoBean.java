package com.lonch.client.bean;

import android.text.TextUtils;

import java.util.List;

public class VideoBean {
    private boolean opFlag;

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


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String error;

    public List<SmallVideoBean> getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(List<SmallVideoBean> serviceResult) {
        this.serviceResult = serviceResult;
    }

    private List<SmallVideoBean> serviceResult;
    private String timestamp;


    public static class SmallVideoBean {

        private String videoId;
        private String url;

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public String getVideoDescription() {
            return videoDescription;
        }

        public void setVideoDescription(String videoDescription) {
            this.videoDescription = videoDescription;
        }

        public int getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(int showOrder) {
            this.showOrder = showOrder;
        }

        private String videoName;
        private String videoDescription;
        private int showOrder;
        /* 小程序id */
        private String appletsId;
        /* 小程序url路径 */
        private String urlPath;

        public String getAppletsId() {
            return appletsId;
        }

        public void setAppletsId(String appletsId) {
            this.appletsId = appletsId;
        }

        public String getUrlPath() {
            return urlPath;
        }

        public void setUrlPath(String urlPath) {
            this.urlPath = urlPath;
        }

        //是否要在视频中显示跳转程序的入口
        public boolean isNeedGoMiniProgram() {
            return !TextUtils.isEmpty(appletsId) && !TextUtils.isEmpty(urlPath);
        }
    }


}
