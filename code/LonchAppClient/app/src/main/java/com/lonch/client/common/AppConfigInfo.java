package com.lonch.client.common;

/**
 * Created by zouyifeng on 05/04/2018.
 * 16:28
 * <p>
 * App 配置信息，用于保存全局使用的对象
 */

public class AppConfigInfo {

    private boolean isDebug;
    /* 是否是线上版本，true为线上，false为线下 */
    private boolean isPublished;

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    private AppConfigInfo() {
    }

    private static AppConfigInfo instance = new AppConfigInfo();

    public static AppConfigInfo getInstance() {
        return instance;
    }

 
}
