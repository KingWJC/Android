package com.lonch.client.bean;

public class AppClientUpdateBean {


    /**
     * code : null
     * opFlag : true
     * error : null
     * serviceResult : {"app_file_url":"https://resources.lonch.com.cn/bi-test/app/package/2021/04/12/Test36.apk","current_version":"v1.4.0","current_version_id":"3d1becfd0005424b862da6202ee59f28","current_inner_version":36,"inner_version":36,"force_update":true,"file_hash_code":"ea82024db76280019feb99936108ab58","remark":"","file_size":54797315}
     * timestamp : 2021-04-18 00:37:27
     */

    private Object code;
    private boolean opFlag;
    private Object error;
    private ServiceResultBean serviceResult;
    private String timestamp;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
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
         * app_file_url : https://resources.lonch.com.cn/bi-test/app/package/2021/04/12/Test36.apk
         * current_version : v1.4.0
         * current_version_id : 3d1becfd0005424b862da6202ee59f28
         * current_inner_version : 36
         * inner_version : 36
         * force_update : true
         * file_hash_code : ea82024db76280019feb99936108ab58
         * remark :
         * file_size : 54797315
         */

        private String app_file_url;
        private String current_version;
        private String current_version_id;
        private int current_inner_version;
        private int inner_version;
        private boolean force_update;
        private String file_hash_code;
        private String remark;
        private int file_size;

        public String getApp_file_url() {
            return app_file_url;
        }

        public void setApp_file_url(String app_file_url) {
            this.app_file_url = app_file_url;
        }

        public String getCurrent_version() {
            return current_version;
        }

        public void setCurrent_version(String current_version) {
            this.current_version = current_version;
        }

        public String getCurrent_version_id() {
            return current_version_id;
        }

        public void setCurrent_version_id(String current_version_id) {
            this.current_version_id = current_version_id;
        }

        public int getCurrent_inner_version() {
            return current_inner_version;
        }

        public void setCurrent_inner_version(int current_inner_version) {
            this.current_inner_version = current_inner_version;
        }

        public int getInner_version() {
            return inner_version;
        }

        public void setInner_version(int inner_version) {
            this.inner_version = inner_version;
        }

        public boolean isForce_update() {
            return force_update;
        }

        public void setForce_update(boolean force_update) {
            this.force_update = force_update;
        }

        public String getFile_hash_code() {
            return file_hash_code;
        }

        public void setFile_hash_code(String file_hash_code) {
            this.file_hash_code = file_hash_code;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getFile_size() {
            return file_size;
        }

        public void setFile_size(int file_size) {
            this.file_size = file_size;
        }
    }
}
