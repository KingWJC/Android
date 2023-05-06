package com.lonch.client.bean.event;

public class IpEvent {
    private String msg;
    private String mainPage;

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public IpEvent(String msg, String mainPage) {
        this.msg = msg;
        this.mainPage = mainPage;
    }

    public IpEvent(String msg) {

        this.msg = msg;
    }
}
