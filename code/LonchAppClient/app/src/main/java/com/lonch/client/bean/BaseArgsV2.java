package com.lonch.client.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class BaseArgsV2 implements Serializable {
    private String sn;
    private String command;
    private long timestamp = System.currentTimeMillis();

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArgsBean getArgs() {
        return args;
    }

    public void setArgs(ArgsBean args) {
        this.args = args;
    }

    private String version = "2.0";
    private ArgsBean args;

    public static class ArgsBean {
        public JSONObject getConfig() {
            return config;
        }

        public void setConfig(JSONObject config) {
            this.config = config;
        }

        public JSONObject getData() {
            return data;
        }

        public void setData(JSONObject data) {
            this.data = data;
        }

        private JSONObject config;
        private JSONObject data;
    }

}
