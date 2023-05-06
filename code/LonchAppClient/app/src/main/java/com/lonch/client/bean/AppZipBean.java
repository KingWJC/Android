package com.lonch.client.bean;

import java.util.List;

public class AppZipBean {


    /**
     * opFlag : true
     * error : null
     * serviceResult : {"resources":{"software_id":"fe6a0c2551194904bab20c823b177cec","software_name":"H5资源目录","app_package_name":"common-library","webapp_url":"https://resources.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/bi/app/package/2020/12/30/common-library20201230191759.zip","version":"v1.0.2","version_id":"38510c507b014bd38d63737d25ee25ee","force_update":true,"file_hash_code":"8ad1b786cb2a2230b0ec23a5cf1e5dd3","using_online_url":false,"user_current_version":"38510c507b014bd38d63737d25ee25ee"},"webApps":[{"software_id":"ad9acc66a69146de9a2b26ae6878a41a","software_name":"聊天","app_package_name":"chat","webapp_url":"https://chat.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/bi/app/package/2021/02/07/app20210207231815.zip","version":"v0.0.26","version_id":"56d91c8d68f24dc4b0017c182eb9b05a","force_update":true,"file_hash_code":"7c67409620e371108391e634dca2fcfe","using_online_url":false,"user_current_version":"56d91c8d68f24dc4b0017c182eb9b05a"},{"software_id":"ed44b65e8e8a40768c9d07bc06a0307f","software_name":"朗致云服","app_package_name":"manage","webapp_url":"https://manage.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/bi/app/package/2021/02/08/manage_v1.0.13520210208002459.zip","version":"v1.0.135","version_id":"d99137284f7d450eb30eebda6e777679","force_update":false,"file_hash_code":"7f3ef483418e5f0de4d9da6bbea4f909","using_online_url":false,"user_current_version":"d99137284f7d450eb30eebda6e777679"},{"software_id":"f1c401c477614d59a3b399834d18369b","software_name":"透视图","app_package_name":"pivottable","webapp_url":"https://pivottable.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/bi/app/package/2021/02/07/pivottable_v1.0.320210207231236.zip","version":"v1.0.3","version_id":"01137957c6824e759425009763c326e7","force_update":false,"file_hash_code":"52d448d0fbe779a808fc6ce299bcc97f","using_online_url":false,"user_current_version":"01137957c6824e759425009763c326e7"}]}
     * timestamp : 2021-02-08 00:27:25
     */

    private boolean opFlag;
    private Object error;
    private ServiceResultBean serviceResult;
    private String timestamp;

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
         * resources : {"software_id":"fe6a0c2551194904bab20c823b177cec","software_name":"H5资源目录","app_package_name":"common-library","webapp_url":"https://resources.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/bi/app/package/2020/12/30/common-library20201230191759.zip","version":"v1.0.2","version_id":"38510c507b014bd38d63737d25ee25ee","force_update":true,"file_hash_code":"8ad1b786cb2a2230b0ec23a5cf1e5dd3","using_online_url":false,"user_current_version":"38510c507b014bd38d63737d25ee25ee"}
         * webApps : [{"software_id":"ad9acc66a69146de9a2b26ae6878a41a","software_name":"聊天","app_package_name":"chat","webapp_url":"https://chat.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/bi/app/package/2021/02/07/app20210207231815.zip","version":"v0.0.26","version_id":"56d91c8d68f24dc4b0017c182eb9b05a","force_update":true,"file_hash_code":"7c67409620e371108391e634dca2fcfe","using_online_url":false,"user_current_version":"56d91c8d68f24dc4b0017c182eb9b05a"},{"software_id":"ed44b65e8e8a40768c9d07bc06a0307f","software_name":"朗致云服","app_package_name":"manage","webapp_url":"https://manage.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/bi/app/package/2021/02/08/manage_v1.0.13520210208002459.zip","version":"v1.0.135","version_id":"d99137284f7d450eb30eebda6e777679","force_update":false,"file_hash_code":"7f3ef483418e5f0de4d9da6bbea4f909","using_online_url":false,"user_current_version":"d99137284f7d450eb30eebda6e777679"},{"software_id":"f1c401c477614d59a3b399834d18369b","software_name":"透视图","app_package_name":"pivottable","webapp_url":"https://pivottable.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/bi/app/package/2021/02/07/pivottable_v1.0.320210207231236.zip","version":"v1.0.3","version_id":"01137957c6824e759425009763c326e7","force_update":false,"file_hash_code":"52d448d0fbe779a808fc6ce299bcc97f","using_online_url":false,"user_current_version":"01137957c6824e759425009763c326e7"}]
         */

        private ResourcesBean resources;
        private List<WebAppsBean> webApps;

        public ClientInfo getClientInfo() {
            return clientInfo;
        }

        public void setClientInfo(ClientInfo clientInfo) {
            this.clientInfo = clientInfo;
        }

        private ClientInfo clientInfo;

        public ResourcesBean getResources() {
            return resources;
        }

        public void setResources(ResourcesBean resources) {
            this.resources = resources;
        }

        public List<WebAppsBean> getWebApps() {
            return webApps;
        }

        public void setWebApps(List<WebAppsBean> webApps) {
            this.webApps = webApps;
        }

        public static class ResourcesBean {
            /**
             * software_id : fe6a0c2551194904bab20c823b177cec
             * software_name : H5资源目录
             * app_package_name : common-library
             * webapp_url : https://resources.lonch.com.cn
             * local_path : null
             * zip_path : https://resources.lonch.com.cn/bi/app/package/2020/12/30/common-library20201230191759.zip
             * version : v1.0.2
             * version_id : 38510c507b014bd38d63737d25ee25ee
             * force_update : true
             * file_hash_code : 8ad1b786cb2a2230b0ec23a5cf1e5dd3
             * using_online_url : false
             * user_current_version : 38510c507b014bd38d63737d25ee25ee
             */

            private String software_id;
            private String software_name;
            private String app_package_name;
            private String webapp_url;
            private String local_path;
            private String zip_path;
            private String version;
            private String version_id;
            private boolean force_update;
            private String file_hash_code;
            private boolean using_online_url;
            private String user_current_version;

            public String getSoftware_id() {
                return software_id;
            }

            public void setSoftware_id(String software_id) {
                this.software_id = software_id;
            }

            public String getSoftware_name() {
                return software_name;
            }

            public void setSoftware_name(String software_name) {
                this.software_name = software_name;
            }

            public String getApp_package_name() {
                return app_package_name;
            }

            public void setApp_package_name(String app_package_name) {
                this.app_package_name = app_package_name;
            }

            public String getWebapp_url() {
                return webapp_url;
            }

            public void setWebapp_url(String webapp_url) {
                this.webapp_url = webapp_url;
            }

            public String getLocal_path() {
                return local_path;
            }

            public void setLocal_path(String local_path) {
                this.local_path = local_path;
            }

            public String getZip_path() {
                return zip_path;
            }

            public void setZip_path(String zip_path) {
                this.zip_path = zip_path;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getVersion_id() {
                return version_id;
            }

            public void setVersion_id(String version_id) {
                this.version_id = version_id;
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

            public boolean isUsing_online_url() {
                return using_online_url;
            }

            public void setUsing_online_url(boolean using_online_url) {
                this.using_online_url = using_online_url;
            }

            public String getUser_current_version() {
                return user_current_version;
            }

            public void setUser_current_version(String user_current_version) {
                this.user_current_version = user_current_version;
            }
        }

        public static class WebAppsBean {
            /**
             * software_id : ad9acc66a69146de9a2b26ae6878a41a
             * software_name : 聊天
             * app_package_name : chat
             * webapp_url : https://chat.lonch.com.cn
             * local_path : null
             * zip_path : https://resources.lonch.com.cn/bi/app/package/2021/02/07/app20210207231815.zip
             * version : v0.0.26
             * version_id : 56d91c8d68f24dc4b0017c182eb9b05a
             * force_update : true
             * file_hash_code : 7c67409620e371108391e634dca2fcfe
             * using_online_url : false
             * user_current_version : 56d91c8d68f24dc4b0017c182eb9b05a
             */

            private String software_id;
            private String software_name;
            private String app_package_name;
            private String webapp_url;
            private Object local_path;
            private String zip_path;
            private String version;
            private String version_id;
            private boolean force_update;
            private String file_hash_code;
            private boolean using_online_url;
            private String user_current_version;

            public String getSoftware_type() {
                return software_type;
            }

            public void setSoftware_type(String software_type) {
                this.software_type = software_type;
            }

            private String  software_type;

            public String getSoftware_id() {
                return software_id;
            }

            public void setSoftware_id(String software_id) {
                this.software_id = software_id;
            }

            public String getSoftware_name() {
                return software_name;
            }

            public void setSoftware_name(String software_name) {
                this.software_name = software_name;
            }

            public String getApp_package_name() {
                return app_package_name;
            }

            public void setApp_package_name(String app_package_name) {
                this.app_package_name = app_package_name;
            }

            public String getWebapp_url() {
                return webapp_url;
            }

            public void setWebapp_url(String webapp_url) {
                this.webapp_url = webapp_url;
            }

            public Object getLocal_path() {
                return local_path;
            }

            public void setLocal_path(Object local_path) {
                this.local_path = local_path;
            }

            public String getZip_path() {
                return zip_path;
            }

            public void setZip_path(String zip_path) {
                this.zip_path = zip_path;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getVersion_id() {
                return version_id;
            }

            public void setVersion_id(String version_id) {
                this.version_id = version_id;
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

            public boolean isUsing_online_url() {
                return using_online_url;
            }

            public void setUsing_online_url(boolean using_online_url) {
                this.using_online_url = using_online_url;
            }

            public String getUser_current_version() {
                return user_current_version;
            }

            public void setUser_current_version(String user_current_version) {
                this.user_current_version = user_current_version;
            }
        }
        public static class ClientInfo{
            private String ip;
            private String appChannel;

            public String getAppChannel() {
                return appChannel;
            }

            public void setAppChannel(String appChannel) {
                this.appChannel = appChannel;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public boolean isEnableVisitorMode() {
                return enableVisitorMode;
            }

            public void setEnableVisitorMode(boolean enableVisitorMode) {
                this.enableVisitorMode = enableVisitorMode;
            }

            private String companyName;
            private String deviceType;
            private String version;
            private boolean enableVisitorMode;

            public boolean isUploadToolbarResult() {
                return uploadToolbarResult;
            }

            public void setUploadToolbarResult(boolean uploadToolbarResult) {
                this.uploadToolbarResult = uploadToolbarResult;
            }

            private boolean uploadToolbarResult;

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }
        }
    }
}
