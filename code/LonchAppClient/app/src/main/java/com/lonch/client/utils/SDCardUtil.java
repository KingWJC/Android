package com.lonch.client.utils;

import android.os.Environment;

public class SDCardUtil {
    /**
     * Return whether sdcard is enabled by environment.
     *
     * @return {@code true}: enabled<br>{@code false}: disabled
     */
    public static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
