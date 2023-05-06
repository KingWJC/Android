package com.lonch.client.bean.event;

public class ScanZingEvent {
    private String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public ScanZingEvent(String sn) {
        this.sn = sn;
    }
}
