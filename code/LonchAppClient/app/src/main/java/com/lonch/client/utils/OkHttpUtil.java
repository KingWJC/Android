package com.lonch.client.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.bean.AppLog;
import com.lonch.client.bean.NetLinkBean;
import com.lonch.client.http.Http;
import com.lonch.client.http.HttpService;
import com.tencent.mmkv.MMKV;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpUtil {

    private volatile static OkHttpUtil instance;
    private static final byte[] LOCKER = new byte[0];
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();
    private HttpCallBack callBack;


    public static OkHttpUtil getInstance() {
        if (instance == null) {
            synchronized (LOCKER) {
                if (instance == null) {
                    instance = new OkHttpUtil();
                }
            }
        }
        return instance;
    }


    public void sendPostRequest(String url, AppLog appLog) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sid", "android" + UUID.randomUUID().toString().replace("-", ""));
        map.put("timestamp", System.currentTimeMillis());
        map.put("ip", (String) SpUtils.get("serviceIp", ""));
        map.put("demand", appLog);
        RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(map));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("ACCESS-TOKEN", (String) SpUtils.get("token", ""))
                .addHeader("protocol-version", "2.0")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });

    }

    public AppLog getPhoneInfo(Context mContext) {
        AppLog appLog = new AppLog();
        appLog.setAppVer(HeaderUtils.getAppVersion());
        appLog.setMac(HeaderUtils.getMacAddress(mContext));
        appLog.setDeviceModel(HeaderUtils.getModel());
        appLog.setSysVer(HeaderUtils.getSysVersion());
        appLog.setDeviceId(HeaderUtils.md5(SystemUtil.getAndroidDeviceId(mContext)));
        appLog.setOperIp((String) SpUtils.get("serviceIp", ""));
        appLog.setUserAgent("Mozilla/ 5.0  (Linux; Android  "+ Build.VERSION.RELEASE +")" + LonchCloudApplication.getAppConfigDataBean().APP_TYPE + "_" + HeaderUtils.getAppVersion());
        appLog.setSourceType("Android");
        appLog.setSourceName(LonchCloudApplication.getAppConfigDataBean().APP_TYPE);
        appLog.setToken((String) SpUtils.get("token", ""));
        return appLog;
    }

    public void getOSSToken(HttpCallBack mCallBack) {
        String token = (String) SpUtils.get("token", "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        RequestBody requestBody = RequestBody.create(JSON, "{}");
        HttpService httpService = Http.getInstance().getApiService(HttpService.class);
        httpService.getOSSToken(token, requestBody).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull retrofit2.Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (mCallBack != null) {
                        String responseData = null;
                        try {
                            if (null != response.body()) {
                                responseData = response.body().string();
                                mCallBack.onSuccess(responseData);
                            } else {
                                mCallBack.onFailure();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            mCallBack.onFailure();
                        }
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                if (mCallBack != null) {
                    mCallBack.onFailure();
                }
            }
        });
    }

    public interface HttpCallBack {
        void onSuccess(String data);

        void onFailure();
    }

    public void getAcceleratedLink(String token) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
        MMKV mmkv = MMKV.defaultMMKV();
        assert mmkv != null;
        boolean isInit = mmkv.
                decodeBool(Utils.getDate(0));
        if (!isInit) {
            HashMap<String, String> map = new HashMap<>();
            map.put("appClientType", LonchCloudApplication.getAppConfigDataBean().APP_TYPE);
            RequestBody requestBody = RequestBody.create(JSON, GsonUtils.getInstance().toJson(map));
            HttpService httpService = Http.getInstance().getApiService(HttpService.class);
            httpService.getAcceleratedLink(token, requestBody).enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull retrofit2.Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {
                    try {
                        String data = response.body().string();
                        if (!TextUtils.isEmpty(data)){
                            NetLinkBean netLinkBean = com.alibaba.fastjson.JSON.parseObject(data,NetLinkBean.class);
                            if (netLinkBean.isOpFlag()){
                                if (netLinkBean.getServiceResult().isSuccess()){
                                    mmkv.encode(Utils.getDate(0), true);
                                    mmkv.encode("netLinkInfo", data);
                                }
                            }
                        }
                    } catch (Exception e) {}
                }

                @Override
                public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {}
            });
        }
    }

    public void reportSunMiSn(String sn) {
        String token = (String) SpUtils.get("token", "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sn", sn);
        RequestBody requestBody = RequestBody.create(JSON, GsonUtils.getInstance().toJson(hashMap));
        HttpService httpService = Http.getInstance().getApiService(HttpService.class);
        httpService.reportSunMiSn(token, requestBody).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull retrofit2.Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void getUserLinkSetting() {
        String token = (String) SpUtils.get("token", "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        boolean isLink = Objects.requireNonNull(MMKV.defaultMMKV()).decodeBool(Utils.getDate(0)+"link");
        if (isLink){
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("os", "Android");
        hashMap.put("appType",LonchCloudApplication.getAppConfigDataBean().APP_TYPE);
        hashMap.put("deviceId",HeaderUtils.md5(SystemUtil.getAndroidDeviceId(LonchCloudApplication.getApplicationsContext())));
        RequestBody requestBody = RequestBody.create(JSON, GsonUtils.getInstance().toJson(hashMap));
        HttpService httpService = Http.getInstance().getApiService(HttpService.class);
        httpService.getUserLinkSetting(token, requestBody).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull retrofit2.Call<ResponseBody> call, @NotNull retrofit2.Response<ResponseBody> response) {
                try {

                } catch (Exception ignored) { Objects.requireNonNull(MMKV.defaultMMKV()).encode("userLink","");}
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Objects.requireNonNull(MMKV.defaultMMKV()).encode("userLink","");
            }
        });
    }

    public void getNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String ipLine = "";
        HttpURLConnection httpConnection = null;
        try {
            infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            URLConnection connection = infoUrl.openConnection();
            httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null){
                    strber.append(line + "\n");
                }
                Pattern pattern = Pattern
                        .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
                Matcher matcher = pattern.matcher(strber.toString());
                if (matcher.find()) {
                    ipLine = matcher.group();
                    SpUtils.put("serviceIp", ipLine);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStream!=null){
                    inStream.close();
                    httpConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
