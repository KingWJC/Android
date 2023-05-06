package com.lonch.client.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_bridge_entity")
public class BridgeEntity {
    @Id(autoincrement = true)
    private Long id;
    private String sn;
    private String command;
    private String bp;
    private Long time;
    @Generated(hash = 15908370)
    public BridgeEntity(Long id, String sn, String command, String bp, Long time) {
        this.id = id;
        this.sn = sn;
        this.command = command;
        this.bp = bp;
        this.time = time;
    }
    @Generated(hash = 427629130)
    public BridgeEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSn() {
        return this.sn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }
    public String getCommand() {
        return this.command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public String getBp() {
        return this.bp;
    }
    public void setBp(String bp) {
        this.bp = bp;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
}
