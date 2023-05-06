package com.lonch.client.http;

import android.os.Build;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.utils.HeaderUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpClientUtil {

    private volatile static OkHttpClient instance;
    private static final byte[] LOCKER = new byte[0];


    public static OkHttpClient getInstance() {
        if (instance == null) {
            synchronized (LOCKER) {
                if (instance == null) {
                    instance = new OkHttpClient
                            .Builder()
                            .addInterceptor(mHeaderInterceptor) //头部信息拦截器
                            .dispatcher(getDispatcher())
                            .connectionPool(new ConnectionPool(32, 20, TimeUnit.MILLISECONDS))
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return instance;
    }
    private OkHttpClientUtil(){}

    private static Dispatcher getDispatcher() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(10);
        return dispatcher;
    }

    private static final Interceptor mHeaderInterceptor = chain -> {
        Request newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "Mozilla/ 5.0  (Linux; Android  "+ Build.VERSION.RELEASE +")" + LonchCloudApplication.getAppConfigDataBean().APP_TYPE + "_" + HeaderUtils.getAppVersion())
                .build();
        return chain.proceed(newRequest);
    };
}
