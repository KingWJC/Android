package com.lonch.client.bean;

import androidx.annotation.NonNull;

/**
 * APP的一些动态配置信息，通过主app动态传过来
 */
public class AppConfigDataBean {
    @NonNull
    public String SERVICE_URL; //online
    @NonNull
    public String SERVICE_LOG_URL;
    public int sdkImAppId;
    public final static int EXPIRATION_TIME = 24*15;
    @NonNull
    public String APP_TYPE;
    @NonNull
    public String LOCAL_HOST = "http://127.0.0.1";
    public int PORT = 7350;
    @NonNull
    public String LIVE_CHECK_URL;
    public int SAMPLE_RATE = 16000;
    public int WAVE_FRAM_SIZE = 20 * 2 * 1 * 16000 / 1000; //20ms audio for 16k/16bit/mono
    public String WECHAT_ID;//微信
    @NonNull
    public String APP_CLIENT_ID;
    @NonNull
    public String USER_AGREEMENT;
    @NonNull
    public String PRIVACY_POLICY;
    @NonNull
    public String LOCAL_APP_NAME;
    @NonNull
    public String appName;
    @NonNull
    public String appVersionName;
    @NonNull
    public String LOCAL_COMPANY;
    @NonNull
    public String QIYE_APPID;
    @NonNull
    public String QIYE_AGENTID;
    @NonNull
    public String QIYE_SCHEMA;
    @NonNull
    public String RSA_PUBLIC_KEY;
    @NonNull
    public String AES_KEY;
    @NonNull
    public String OSS_APP_FILE_NAME;
    @NonNull
    public String OSS_APP_BRIDGE_NAME;
    @NonNull
    public String OSS_APP_ACCELERATION_NAME;
    @NonNull
    public String OSS_APP_API_RESPONSE_NAME;
    @NonNull
    public int color_radiobutton;
    @NonNull
    public String OPPO_PUSH_APPKEY;
    @NonNull
    public String OPPO_PUSH_APPSECRET;
    @NonNull
    public long XM_PUSH_BUZID;
    @NonNull
    public long HW_PUSH_BUZID;
    @NonNull
    public long MZ_PUSH_BUZID;
    @NonNull
    public long OPPO_PUSH_BUZID;
    @NonNull
    public long VIVO_PUSH_BUZID;
    @NonNull
    public String FILE_PROVIDER;
    @NonNull
    public String APP_PROCESS_NAME;
    @NonNull
    public String PRODUCT_ID;
    @NonNull
    public String MZ_PUSH_APPKEY;
    @NonNull
    public String MZ_PUSH_APPID;
    public boolean isShowGuestLogin;
    //是否来自私域商城
    public boolean isFromMall;
    //是否需要支持定位
    public boolean isNeedSupportLocation;
}
