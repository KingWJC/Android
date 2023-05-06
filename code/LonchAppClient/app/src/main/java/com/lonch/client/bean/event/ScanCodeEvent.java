package com.lonch.client.bean.event;

public class ScanCodeEvent {

    private String sn;
    private String msg;


    public ScanCodeEvent(String sn, String msg) {
        this.sn = sn;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
