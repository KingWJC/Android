package com.lonch.client.bean.event;

public class TencentIMEvent {
    private int status;
    private boolean isOnLine;

    public TencentIMEvent(boolean isOnLine) {
        this.isOnLine = isOnLine;
    }

    public TencentIMEvent(int status) {
        this.status = status;
        this.isOnLine = true;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isOnLine() {
        return isOnLine;
    }

    public void setOnLine(boolean onLine) {
        isOnLine = onLine;
    }
}
