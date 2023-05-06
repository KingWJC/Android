package com.lonch.client;


import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.lonch.client.bean.AppConfigDataBean;
import com.lonch.client.bean.event.TencentIMEvent;
import com.lonch.client.common.Constants;
import com.lonch.client.common.PrivateConstants;
import com.lonch.client.exception.CrashHandler;
import com.lonch.client.service.UrlHelper;
import com.lonch.client.utils.SpUtils;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMUserStatus;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static MyApplication application;
    private Handler handler = new Handler(Looper.getMainLooper());

    public Handler getHandler() {
        return handler;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);
        MultiDex.install(application);
        initImSdk();
        overallException();//全局异常捕获
        LonchCloudApplication.init(this, !BuildConfig.ENVIRONMENT, BuildConfig.APPLICATION_ID, generateConfigData());
    }

    /**
     * 全局异常捕获
     */
    private void overallException() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(application);
    }

    private AppConfigDataBean generateConfigData() {
        AppConfigDataBean appConfigDataBean = new AppConfigDataBean();
        appConfigDataBean.sdkImAppId = UrlHelper.sdkImAppId;
        appConfigDataBean.SERVICE_LOG_URL = UrlHelper.SERVICE_LOG_URL;
        appConfigDataBean.SERVICE_URL = UrlHelper.SERVICE_URL;
        appConfigDataBean.APP_TYPE = Constants.APP_TYPE;
        appConfigDataBean.appVersionName = BuildConfig.VERSION_NAME;
        appConfigDataBean.LOCAL_HOST = Constants.LOCAL_HOST;
        appConfigDataBean.PORT = Constants.PORT;
        appConfigDataBean.LIVE_CHECK_URL = Constants.LIVE_CHECK_URL;
        appConfigDataBean.SAMPLE_RATE = Constants.SAMPLE_RATE;
        appConfigDataBean.WAVE_FRAM_SIZE = Constants.WAVE_FRAM_SIZE;
        appConfigDataBean.WECHAT_ID = Constants.WECHAT_ID;
        appConfigDataBean.APP_CLIENT_ID = Constants.APP_CLIENT_ID;
        appConfigDataBean.USER_AGREEMENT = BuildConfig.USER_AGREEMENT;
        appConfigDataBean.LOCAL_APP_NAME = Constants.LOCAL_APP_NAME;
        appConfigDataBean.LOCAL_COMPANY = Constants.LOCAL_COMPANY;
        appConfigDataBean.QIYE_SCHEMA = Constants.QIYE_SCHEMA;
        appConfigDataBean.QIYE_APPID = Constants.QIYE_APPID;
        appConfigDataBean.QIYE_AGENTID = Constants.QIYE_AGENTID;
        appConfigDataBean.RSA_PUBLIC_KEY = Constants.RSA_PUBLIC_KEY;
        appConfigDataBean.AES_KEY = Constants.AES_KEY;
        appConfigDataBean.OSS_APP_FILE_NAME = Constants.OSS_APP_FILE_NAME;
        appConfigDataBean.OSS_APP_BRIDGE_NAME = Constants.OSS_APP_BRIDGE_NAME;
        appConfigDataBean.OSS_APP_ACCELERATION_NAME = Constants.OSS_APP_ACCELERATION_NAME;
        appConfigDataBean.OSS_APP_API_RESPONSE_NAME = Constants.OSS_APP_API_RESPONSE_NAME;
        appConfigDataBean.OPPO_PUSH_APPKEY = PrivateConstants.OPPO_PUSH_APPKEY;
        appConfigDataBean.OPPO_PUSH_APPSECRET = PrivateConstants.OPPO_PUSH_APPSECRET;
        appConfigDataBean.XM_PUSH_BUZID = PrivateConstants.XM_PUSH_BUZID;
        appConfigDataBean.HW_PUSH_BUZID = PrivateConstants.HW_PUSH_BUZID;
        appConfigDataBean.MZ_PUSH_BUZID = PrivateConstants.MZ_PUSH_BUZID;
        appConfigDataBean.OPPO_PUSH_BUZID = PrivateConstants.OPPO_PUSH_BUZID;
        appConfigDataBean.VIVO_PUSH_BUZID = PrivateConstants.VIVO_PUSH_BUZID;
        appConfigDataBean.color_radiobutton = R.drawable.color_radiobutton;
        appConfigDataBean.FILE_PROVIDER = BuildConfig.APPLICATION_ID+".fileprovider";
        appConfigDataBean.APP_PROCESS_NAME = BuildConfig.APPLICATION_ID;
        appConfigDataBean.PRODUCT_ID = Constants.PRODUCT_ID;
        appConfigDataBean.MZ_PUSH_APPKEY = PrivateConstants.MZ_PUSH_APPKEY;
        appConfigDataBean.MZ_PUSH_APPID = PrivateConstants.MZ_PUSH_APPID;
        return appConfigDataBean;
    }

    private void initImSdk() {
        //SDKAPPID 就是文章开头提到过的，在控制台上可以看
        // 1. 从 IM 控制台获取应用 SDKAppID，详情请参考 SDKAppID。
        // 2. 初始化 config 对象
        V2TIMSDKConfig config = new V2TIMSDKConfig();
        // 3. 指定 log 输出级别，详情请参考 SDKConfig。
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_DEBUG);
        // 4. 初始化 SDK 并设置 V2TIMSDKListener 的监听对象。
        // initSDK 后 SDK 会自动连接网络，网络连接状态可以在 V2TIMSDKListener 回调里面监听。
//        V2TIMManager.getInstance().unInitSDK();
        V2TIMManager.getInstance().addIMSDKListener(new V2TIMSDKListener() {
            @Override
            public void onConnecting() {
                super.onConnecting();
            }

            @Override
            public void onConnectSuccess() {
                super.onConnectSuccess();
                Log.i(TAG,"onConnectSuccess---");
//                initImPush();
            }

            @Override
            public void onConnectFailed(int code, String error) {
                super.onConnectFailed(code, error);
                Log.i(TAG,"onConnectFailed---"+error);
            }

            @Override
            public void onKickedOffline() {
                super.onKickedOffline();
                SpUtils.put("ImLogin", false);
            }

            @Override
            public void onUserSigExpired() {
                super.onUserSigExpired();
                EventBus.getDefault().post(new TencentIMEvent(false));
            }

            @Override
            public void onSelfInfoUpdated(V2TIMUserFullInfo info) {
                super.onSelfInfoUpdated(info);
            }

            @Override
            public void onUserStatusChanged(List<V2TIMUserStatus> userStatusList) {
                super.onUserStatusChanged(userStatusList);
            }
        });
        V2TIMManager.getInstance().initSDK(this,UrlHelper.sdkImAppId,config);
    }

//    private void initImPush() {
//        HeytapPushManager.init(this, true);
//        if (BrandUtil.isBrandXiaoMi()) {
//            // 小米离线推送
//            MiPushClient.registerPush(this, PrivateConstants.XM_PUSH_APPID, PrivateConstants.XM_PUSH_APPKEY);
//        } else if (BrandUtil.isBrandHuawei()) {
//            // 华为离线推送
//            // 华为离线推送，设置是否接收Push通知栏消息调用示例
//            HmsMessaging.getInstance(this).turnOnPush().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    DemoLog.i(TAG, "huawei turnOnPush Complete");
//                } else {
//                    DemoLog.e(TAG, "huawei turnOnPush failed: ret=" + task.getException().getMessage());
//                }
//            });
//
//        } else if (BrandUtil.isBrandVivo()) {
//            // vivo离线推送
//            PushClient.getInstance(getApplicationContext()).initialize();
//        }
//    }
}
