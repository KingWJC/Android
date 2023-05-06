package com.lonch.client.bean.event;

public class ToothBus {

    public ToothBus(boolean isConnected) {
        this.isConnected = isConnected;
    }

    boolean isConnected;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
