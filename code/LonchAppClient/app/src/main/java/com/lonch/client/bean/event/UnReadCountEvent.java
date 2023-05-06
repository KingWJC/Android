package com.lonch.client.bean.event;

public class UnReadCountEvent {
    private int unReadCount;
    private boolean isUpdateAll;

    public UnReadCountEvent(int unReadCount, boolean isUpdateAll) {
        this.unReadCount = unReadCount;
        this.isUpdateAll = isUpdateAll;
    }

    public boolean isUpdateAll() {
        return isUpdateAll;
    }

    public void setUpdateAll(boolean updateAll) {
        isUpdateAll = updateAll;
    }

    public UnReadCountEvent(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }
}
