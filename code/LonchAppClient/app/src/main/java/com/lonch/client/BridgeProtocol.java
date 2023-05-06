package com.lonch.client;

public class BridgeProtocol {
    public String command;

    public String sn;

    public String data;

    public BridgeProtocol(String cmd, String sn, String data) {
        this.command = cmd;
        this.sn = sn;
        this.data = data;
    }
}