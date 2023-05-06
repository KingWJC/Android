package com.lonch.client.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "lonch_order_entity")
public class AccelerationOrderEntity {
    @Id(autoincrement = true)
    private Long id;
    private String linkId;
    private int order;
    private Long time;
    private int sum;
    @Generated(hash = 661836323)
    public AccelerationOrderEntity(Long id, String linkId, int order, Long time,
                                   int sum) {
        this.id = id;
        this.linkId = linkId;
        this.order = order;
        this.time = time;
        this.sum = sum;
    }
    @Generated(hash = 1218087510)
    public AccelerationOrderEntity() {
    }
    public String getLinkId() {
        return this.linkId;
    }
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
   
    public int getSum() {
        return this.sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
}
