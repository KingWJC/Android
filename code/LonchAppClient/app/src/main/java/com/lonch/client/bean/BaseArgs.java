package com.lonch.client.bean;

import java.io.Serializable;

public class BaseArgs<T> implements Serializable {

    private String command;
    private String sn;
    public T args;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public T getArgs() {
        return args;
    }

    public void setArgs(T args) {
        this.args = args;
    }
}
