package com.lonch.client.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_zip_entity")
public class LocalZipEntity {
    @Id(autoincrement = true)
    private Long id;
    private String software_id;//软件id
    private String path;//json
    private String version;//version
    private String json;
    @Generated(hash = 1075632766)
    public LocalZipEntity(Long id, String software_id, String path, String version,
                          String json) {
        this.id = id;
        this.software_id = software_id;
        this.path = path;
        this.version = version;
        this.json = json;
    }
    @Generated(hash = 1980247613)
    public LocalZipEntity() {
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
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getVersion() {
        return this.version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
}
