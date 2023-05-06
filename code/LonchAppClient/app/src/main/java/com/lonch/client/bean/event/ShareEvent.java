package com.lonch.client.bean.event;


import com.lonch.client.bean.argsbean.ArgsShareView;

public class ShareEvent {
    private ArgsShareView msg;

    public ShareEvent(ArgsShareView msg) {
        this.msg = msg;
    }

    public ArgsShareView getMsg() {
        return msg;
    }

    public void setMsg(ArgsShareView msg) {
        this.msg = msg;
    }
}
