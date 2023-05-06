package com.lonch.client.utils;

import java.lang.reflect.Method;

public class SystemProperties {

    public static void set(String key, String value) {
        Method getNameMethod = null;
        try {
            Class mClass = Class.forName("android.os.SystemProperties");
            getNameMethod = mClass.getDeclaredMethod("set", String.class, String.class);
            getNameMethod.invoke(mClass, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        Method getNameMethod = null;
        String value = null;
        try {
            Class mClass = Class.forName("android.os.SystemProperties");
            getNameMethod = mClass.getDeclaredMethod("get", String.class);
            value = (String) getNameMethod.invoke(mClass, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        Method getNameMethod = null;
        boolean value = defaultValue;
        try {
            Class mClass = Class.forName("android.os.SystemProperties");
            getNameMethod = mClass.getDeclaredMethod("getBoolean", String.class, boolean.class);
            value = (boolean) getNameMethod.invoke(mClass, key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
        return value;
    }
}
