package com.lonch.client.bean.argsbean;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

public class AppZipBeanMyInfo {


    /**
     * opFlag : true
     * error : null
     * serviceResult : {"resources":{"software_id":"92f1c1b15b904a2191c15f9f6820e7c4","software_name":"H5资源目录","app_package_name":"common-library","webapp_url":"https://resources.lonch.com.cn","local_path":null,"zip_path":"http://yunwei-lonch.oss-cn-beijing.aliyuncs.com/package/portal/common-library.zip","version":"c1325633383511ebbf05b8599f52bf4c","version_id":null,"force_update":false,"file_hash_code":"5006a76b9ee0575ce07bbe7820ff3d14","using_online_url":false},"webApps":[{"software_id":"5ae5c90c09e64dbc85ac6b22b4876f14","software_name":"朗致云服","app_package_name":"manage","webapp_url":"http://devmanage.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/app/package/c3d75eb2-812b-465d-b515-9d07d1036ca0.zip","version":"1.3.1","version_id":null,"force_update":false,"file_hash_code":"0586a556185a584d75ebaa966e761868","using_online_url":false},{"software_id":"658abade45c011ebbf05b8599f52bf4c","software_name":"Portal","app_package_name":"portal","webapp_url":"https://dev-portal.lonch.com.cn","local_path":null,"zip_path":"https://yunwei-lonch.oss-cn-beijing.aliyuncs.com/package/portal/portal_v1.1.4.zip","version":"61f1678ba6f04fc39fbbed212f34205a","version_id":null,"force_update":false,"file_hash_code":"8e5c75cc06581f4fd5a14fb6266ad6d1","using_online_url":true}]}
     * timestamp : 2020-12-27 22:54:54
     */

    private boolean opFlag = true;
    private String  error ="";

    private ServiceResultBean serviceResult;
    private String timestamp;

    public AppZipBeanMyInfo() {
        long timeMillis = System.currentTimeMillis();
        String tMillis = String.valueOf(timeMillis);
        Log.d("test", "  当前时间戳1->:"+tMillis);
        setTimestamp(tMillis);
    }

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
         * resources : {"software_id":"92f1c1b15b904a2191c15f9f6820e7c4","software_name":"H5资源目录","app_package_name":"common-library","webapp_url":"https://resources.lonch.com.cn","local_path":null,"zip_path":"http://yunwei-lonch.oss-cn-beijing.aliyuncs.com/package/portal/common-library.zip","version":"c1325633383511ebbf05b8599f52bf4c","version_id":null,"force_update":false,"file_hash_code":"5006a76b9ee0575ce07bbe7820ff3d14","using_online_url":false}
         * webApps : [{"software_id":"5ae5c90c09e64dbc85ac6b22b4876f14","software_name":"朗致云服","app_package_name":"manage","webapp_url":"http://devmanage.lonch.com.cn","local_path":null,"zip_path":"https://resources.lonch.com.cn/app/package/c3d75eb2-812b-465d-b515-9d07d1036ca0.zip","version":"1.3.1","version_id":null,"force_update":false,"file_hash_code":"0586a556185a584d75ebaa966e761868","using_online_url":false},{"software_id":"658abade45c011ebbf05b8599f52bf4c","software_name":"Portal","app_package_name":"portal","webapp_url":"https://dev-portal.lonch.com.cn","local_path":null,"zip_path":"https://yunwei-lonch.oss-cn-beijing.aliyuncs.com/package/portal/portal_v1.1.4.zip","version":"61f1678ba6f04fc39fbbed212f34205a","version_id":null,"force_update":false,"file_hash_code":"8e5c75cc06581f4fd5a14fb6266ad6d1","using_online_url":true}]
         * clientInfo
         */

        private ResourcesBean resources;
        private List<WebAppsBean> webApps;
        private AppClientInfo clientInfo;
        public HashMap<String, Object> getCustomSettings() {
            return customSettings;
        }

        public void setCustomSettings(HashMap<String, Object> customSettings) {
            this.customSettings = customSettings;
        }

        private HashMap<String,Object> customSettings;

        public AppClientInfo getClientInfo() {
            return clientInfo;
        }

        public void setClientInfo(AppClientInfo clientInfo) {
            this.clientInfo = clientInfo;
        }

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

        public static class AppClientInfo {
            String companyName = "" ; //yaoFaCai 朗致江西的全名
            String appName = "";       //yaoFaCai
            String version = "";
            boolean isRelease = false;
            String deviceType  = "androidApp";
            String token  ;
            int isVisitor;//是否是游客：0非游客，1游客

            public int getBigVersion() {
                return bigVersion;
            }

            public void setBigVersion(int bigVersion) {
                this.bigVersion = bigVersion;
            }

            int bigVersion;

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            String deviceId;
            HashMap<String,Object> map;

            public String getProtocolVersion() {
                return protocolVersion;
            }

            public void setProtocolVersion(String protocolVersion) {
                this.protocolVersion = protocolVersion;
            }

            String protocolVersion = "";

            public boolean isRelease() {
                return isRelease;
            }

            public void setRelease(boolean release) {
                isRelease = release;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getAppName() {
                return appName;
            }

            public void setAppName(String appName) {
                this.appName = appName;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public int getIsVisitor() {
                return isVisitor;
            }

            public void setIsVisitor(int isVisitor) {
                this.isVisitor = isVisitor;
            }
        }
        public static class ResourcesBean {
            /**
             * software_id : 92f1c1b15b904a2191c15f9f6820e7c4
             * software_name : H5资源目录
             * app_package_name : common-library
             * webapp_url : https://resources.lonch.com.cn
             * local_path : null
             * zip_path : http://yunwei-lonch.oss-cn-beijing.aliyuncs.com/package/portal/common-library.zip
             * version : c1325633383511ebbf05b8599f52bf4c
             * version_id : null
             * force_update : false
             * file_hash_code : 5006a76b9ee0575ce07bbe7820ff3d14
             * using_online_url : false
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

            public String getSoftware_type() {
                return software_type;
            }

            public void setSoftware_type(String software_type) {
                this.software_type = software_type;
            }

            private String software_type;


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
        }

        public static class WebAppsBean {
            /**
             * software_id : 5ae5c90c09e64dbc85ac6b22b4876f14
             * software_name : 朗致云服
             * app_package_name : manage
             * webapp_url : http://devmanage.lonch.com.cn
             * local_path : null
             * zip_path : https://resources.lonch.com.cn/app/package/c3d75eb2-812b-465d-b515-9d07d1036ca0.zip
             * version : 1.3.1
             * version_id : null
             * force_update : false
             * file_hash_code : 0586a556185a584d75ebaa966e761868
             * using_online_url : false
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

            public String getSoftware_type() {
                return software_type;
            }

            public void setSoftware_type(String software_type) {
                this.software_type = software_type;
            }

            private String software_type;


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
        }
    }
}
