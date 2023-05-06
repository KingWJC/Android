package com.lonch.client.http;


import static com.alibaba.fastjson.util.IOUtils.UTF8;

import android.os.Build;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.bean.AppLog;
import com.lonch.client.utils.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * http 网络请求工具类
 */

public class Http {

    private static Retrofit retrofit;
    private static Http retrofitUtils;
    private static OkHttpClient okHttpClient;


    public <T> T getApiService(Class<T> service) {
        Retrofit retrofit = getRetrofit();
        return retrofit.create(service);
    }

    public static Http getInstance() {
        if (retrofitUtils == null) {
            synchronized (Http.class) {
                if (retrofitUtils == null) {
                    retrofitUtils = new Http();
                }
            }
        }
        return retrofitUtils;
    }

    private Http() {
    }


    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient
                    .Builder()
                    .addInterceptor(mHeaderInterceptor) //头部信息拦截器
                    .addInterceptor(mRequestInterceptor)
                    .addInterceptor(responseInterceptor)
                    .dispatcher(getDispatcher())
                    .connectionPool(new ConnectionPool(32, 20, TimeUnit.MILLISECONDS))
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }

    private static Dispatcher getDispatcher() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(10);
        return dispatcher;
    }

    public static synchronized Retrofit getRetrofit() {
        if (null == retrofit) {
//            String baseUrl = LonchCloudApplication.getAppConfigDataBean().SERVICE_URL;
//            String linkUrl = Objects.requireNonNull(MMKV.defaultMMKV()).decodeString("userLink");
//            if (!TextUtils.isEmpty(linkUrl) && linkUrl.startsWith("http")){
//                baseUrl = linkUrl;
//            }
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(LonchCloudApplication.getAppConfigDataBean().SERVICE_URL)  //自己配置
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 增加头部信息的拦截器
     */
    private static final Interceptor mHeaderInterceptor = chain -> {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type", "application/json")
//                .addHeader("Connection","close")
                .addHeader("User-Agent", "Mozilla/ 5.0  (Linux; Android  " + Build.VERSION.RELEASE + ")" + LonchCloudApplication.getAppConfigDataBean().APP_TYPE + "_" + HeaderUtils.getAppVersion());
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    };
    /**
     * 拦截请求添加公共参数
     */
    private static final Interceptor mRequestInterceptor = chain -> {
        Request request = chain.request();
        String method = request.method();
        Map<String, Object> commonMap = new HashMap<>();
        commonMap.put("sid", "android" + UUID.randomUUID().toString().replace("-", ""));
        commonMap.put("timestamp", System.currentTimeMillis());
        commonMap.put("ip", SpUtils.get("serviceIp", ""));
        if (method.equals("POST")) {
            try {
                RequestBody requestBody = request.body();
                Buffer buffer = new Buffer();
                assert requestBody != null;
                requestBody.writeTo(buffer);
                String oldJson = buffer.readUtf8();
                if (oldJson.startsWith("{")) {
                    HashMap oldMap = JSON.parseObject(oldJson, HashMap.class);
                    if (!oldMap.containsKey("demand")) {
                        commonMap.put("demand", oldMap);
                    } else {
                        commonMap.put("demand", oldMap.get("demand"));
                    }
                } else if (oldJson.startsWith("[")) {
                    JSONArray array = JSON.parseArray(oldJson);
                    commonMap.put("demand", array);
                }
                String newJson = JSON.toJSONString(commonMap);
                request = request.newBuilder().addHeader("protocol-version", "2.0")
                        .post(RequestBody.create(newJson, MediaType.parse(""))).build();

            } catch (Exception ignored) {

            }
        }

        return chain.proceed(request);
    };
    private static final Interceptor responseInterceptor = chain -> {

        Request request = chain.request();
        String sid = request.header("sid");
        RequestBody requestBody = request.body();
        Buffer buffer1 = new Buffer();
        assert requestBody != null;
        requestBody.writeTo(buffer1);

        MediaType contentType1 = requestBody.contentType();
        Charset charset1 = UTF8;

        if (contentType1 != null) {
            charset1 = contentType1.charset(UTF8);
        }

        assert charset1 != null;
        String paramsStr = buffer1.readString(charset1);
        long startTime = System.currentTimeMillis();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            sendLog(request.url().toString(), paramsStr, e.getMessage(), sid);
//            if (okHttpClient!=null){
//               okHttpClient.dispatcher().cancelAll();
//               okHttpClient.connectionPool().evictAll();
//            }
            Utils.saveApiResponse(request.url().toString(), 0, System.currentTimeMillis() - startTime);
            throw e;
        }
        ResponseBody responseBody = response.body();
        assert responseBody != null;
        long contentLength = responseBody.contentLength();

        if (!HttpHeaders.promisesBody(response)) { //HttpHeader -> 改成了 HttpHeaders，看版本进行选择
            //END HTTP
        } else if (bodyEncoded(response.headers())) {
            //HTTP (encoded body omitted)
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.getBuffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    //Couldn't decode the response body; charset is likely malformed.
                    return response;
                }
            }
            if (!isPlaintext(buffer)) {
                return response;
            }
            if (contentLength != 0) {
                assert charset != null;
                String result = buffer.clone().readString(charset);
                if (response.code() > 200) {
                    sendLog(request.url().toString(), paramsStr, result + "response code:" + response.code(), sid);
                    Utils.saveApiResponse(request.url().toString(), 0, System.currentTimeMillis() - startTime);
                } else {
                    try {
                        Utils.saveApiResponse(request.url().toString(), 1, System.currentTimeMillis() - startTime);
                        JSONObject jsonObject = new JSONObject(result);
                        boolean opFlag = false;
                        if (jsonObject.has("opFlag")) {
                            opFlag = jsonObject.getBoolean("opFlag");
                        }
                        if (!opFlag) {
                            //token已过期请重新登录
                            if (result.contains("SERGTW1201100003") || result.contains("SERGTW1201100006")
                                    || result.contains("SERgtw1201100003") || result.contains("SERgtw1201100010")
                                    || result.contains("SERgtw1201100006")) {
                                Utils.reStartLogin(LonchCloudApplication.getApplicationsContext());
                            } else {
                                sendLog(request.url().toString(), paramsStr, result, sid);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        sendLog(request.url().toString(), paramsStr, e.getMessage(), sid);
                    }
                }
            }
        }
        return response;
    };

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    static boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    public static void sendLog(String request, String paramsStr, String error, String sid) {
        AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(LonchCloudApplication.getApplicationsContext());
        appLog.setReqUrl(request);
        appLog.setReqParam(paramsStr);
        appLog.setErrMsg(error);
        appLog.setReqResult(error);
        appLog.setEventName("代理请求服务异常1");
        if (!TextUtils.isEmpty(error)) {
            if (error.contains("Unable to resolve host") || error.contains("connect") || error.contains("reset") || error.contains("timeout")
                    || error.contains("socket") || error.contains("Socket") || error.contains("SSL handshake")) {
                appLog.setErrLevel("warn");
            } else {
                appLog.setErrLevel("error");
            }
        }
        appLog.setErrCode("ANDyfc0002400001");
        appLog.setRemark("service");
        if (!TextUtils.isEmpty(sid)) {
            appLog.setSid(sid);
        } else {
            String paramSid = Utils.getParamSid(paramsStr);
            if (!TextUtils.isEmpty(paramSid)) {
                appLog.setSid(paramSid);
            }
        }
        OkHttpUtil.getInstance().sendPostRequest(LonchCloudApplication.getAppConfigDataBean().SERVICE_LOG_URL, appLog);
    }

}
