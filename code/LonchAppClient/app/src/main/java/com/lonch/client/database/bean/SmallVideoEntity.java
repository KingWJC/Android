package com.lonch.client.database.bean;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_video_entity")
public class SmallVideoEntity {
    @Id
    @Expose(serialize = false,deserialize = false)
    private Long id;
    @Expose
    private String videoId;
    @Expose
    private Long startTime;
    @Expose
    private Long endTime;
    @Expose
    private Long leaveVideoTime;
    @Expose
    private int isLeaveActive; //是否主动离开 0 否 1是
    @Expose
    private int isForward;// 是否快进 0 否 1是
    @Generated(hash = 913849687)
    public SmallVideoEntity(Long id, String videoId, Long startTime, Long endTime,
            Long leaveVideoTime, int isLeaveActive, int isForward) {
        this.id = id;
        this.videoId = videoId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.leaveVideoTime = leaveVideoTime;
        this.isLeaveActive = isLeaveActive;
        this.isForward = isForward;
    }
    @Generated(hash = 98711102)
    public SmallVideoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVideoId() {
        return this.videoId;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public Long getStartTime() {
        return this.startTime;
    }
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    public Long getEndTime() {
        return this.endTime;
    }
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    public Long getLeaveVideoTime() {
        return this.leaveVideoTime;
    }
    public void setLeaveVideoTime(Long leaveVideoTime) {
        this.leaveVideoTime = leaveVideoTime;
    }
    public int getIsLeaveActive() {
        return this.isLeaveActive;
    }
    public void setIsLeaveActive(int isLeaveActive) {
        this.isLeaveActive = isLeaveActive;
    }
    public int getIsForward() {
        return this.isForward;
    }
    public void setIsForward(int isForward) {
        this.isForward = isForward;
    }

    
   

}
