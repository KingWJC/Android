package com.lonch.client.utils;

import android.app.Application;

public class ContextHolder {
    static Application ApplicationContext;

    public ContextHolder() {
    }

    public static void init(Application context) {
        ApplicationContext = context;
    }

    public static Application getContext() {
        if(ApplicationContext == null) {
            Application application;
            try {
                application = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke((Object)null, (Object[])null);
                if(application != null) {
                    ApplicationContext = application;
                    return application;
                }
            } catch (Exception var2) {
                var2.printStackTrace();
            }

            try {
                application = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication", new Class[0]).invoke((Object)null, (Object[])null);
                if(application != null) {
                    ApplicationContext = application;
                    return application;
                }
            } catch (Exception var1) {
                var1.printStackTrace();
            }

            throw new IllegalStateException("ContextHolder is not initialed, it is recommend to init with application context.");
        } else {
            return ApplicationContext;
        }
    }
}
