package com.lonch.client.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_webversion_entity")
public class WebVersionEntity {

    @Id(autoincrement = true)
    private Long id;
    private String software_id;//软件id
    private String json;//json
    private String version;//version
    @Generated(hash = 709243667)
    public WebVersionEntity(Long id, String software_id, String json,
                            String version) {
        this.id = id;
        this.software_id = software_id;
        this.json = json;
        this.version = version;
    }
    @Generated(hash = 666852145)
    public WebVersionEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSoftware_id() {
        return this.software_id;
    }
    public void setSoftware_id(String software_id) {
        this.software_id = software_id;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    public String getVersion() {
        return this.version;
    }
    public void setVersion(String version) {
        this.version = version;
    }



}
