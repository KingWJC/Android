package com.lonch.client.bean;

import java.util.List;

public class WebAppUpdateSetting {

    /**
     * clientId : string
     * userVersions : [{"software_id":"string","current_version_id":"string"}]
     */

    private String clientId;
    private List<UserVersionsBean> userVersions;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<UserVersionsBean> getUserVersions() {
        return userVersions;
    }

    public void setUserVersions(List<UserVersionsBean> userVersions) {
        this.userVersions = userVersions;
    }

    public static class UserVersionsBean {
        /**
         * software_id : string
         * current_version_id : string
         */

        private String software_id;
        private String current_version_id;

        public String getSoftware_id() {
            return software_id;
        }

        public void setSoftware_id(String software_id) {
            this.software_id = software_id;
        }

        public String getCurrent_version_id() {
            return current_version_id;
        }

        public void setCurrent_version_id(String current_version_id) {
            this.current_version_id = current_version_id;
        }
    }
}
