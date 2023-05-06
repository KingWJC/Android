package com.lonch.client.service;

import android.text.TextUtils;
import android.util.Log;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.bean.event.ScanCodeEvent;
import com.lonch.client.utils.SpUtils;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;

import org.greenrobot.eventbus.EventBus;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class WebAppServer {

    public enum ServerActionType {
        SERVER_START,
        SERVER_STOP,
    }

    private volatile static WebAppServer instance;
    private Server mServer;
    private InetAddress addr;
    private final String TAG = "WebAppServer";
    private OnWebServerCallBack onWebServerCallBack;

    public WebAppServer() {

    }

    public static WebAppServer getInstance(OnWebServerCallBack onWebServerCallBack) {
        if (null == instance) {
            synchronized (WebAppServer.class) {
                if (null == instance) {
                    instance = new WebAppServer();
                }
            }

        }
        if (instance != null)
            instance.onWebServerCallBack = onWebServerCallBack;
        return instance;
    }

    public void initServer() {
        try {
            addr = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        stopServer();
        mServer = AndServer.webServer(LonchCloudApplication.getApplicationsContext())
                .inetAddress(addr)
                .port(LonchCloudApplication.getAppConfigDataBean().PORT)
                .timeout(10, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        SpUtils.put("ip", "127.0.0.1");
                        Log.d(TAG, "onStarted");
                        EventBus.getDefault().post(new ScanCodeEvent("onStarted", ""));
                        if (onWebServerCallBack != null)
                            onWebServerCallBack.onSuccessed(ServerActionType.SERVER_START);
                    }

                    @Override
                    public void onStopped() {
                        if (onWebServerCallBack != null)
                            onWebServerCallBack.onSuccessed(ServerActionType.SERVER_STOP);
                    }

                    @Override
                    public void onException(Exception e) {
                        Log.i(TAG, e.getMessage());
                        stopServer();
                        if (onWebServerCallBack != null)
                            onWebServerCallBack.onFailed(e);
                    }
                })
                .build();
        startServer();
    }

    /**
     * Start server.
     */
    private void startServer() {
        if (mServer.isRunning()) {
            String hostAddress = mServer.getInetAddress().getHostAddress();
            if (!TextUtils.isEmpty(hostAddress)) {
                SpUtils.put("ip", hostAddress);
            }
            Log.d(TAG, "00000000");
        } else {
            Log.d(TAG, "bbbbbbrrrrrr");
            mServer.startup();
        }
    }

    /**
     * Stop server.
     */
    public void stopServer() {
        if (null != mServer) {
            mServer.shutdown();
        }
    }


    public interface OnWebServerCallBack {
        /**
         * 服务器启动或停止成功回调
         *
         * @param actionType
         */
        void onSuccessed(ServerActionType actionType);

        void onFailed(Exception e);
    }

}
