package com.lonch.client.bean;

public class UpdateCurPackBean {


    /**
     * software_id : string
     * current_version_id : string
     */

    private String software_id;
    private String current_version_id;

    public UpdateCurPackBean(String software_id, String current_version_id) {
        this.software_id = software_id;
        this.current_version_id = current_version_id;
    }

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
