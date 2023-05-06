package com.lonch.client.model;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.bean.ApiResult;
import com.lonch.client.bean.ApiResultV2;
import com.lonch.client.bean.AppLog;
import com.lonch.client.bean.BaseArgsV2;
import com.lonch.client.bean.FromJsBean;
import com.lonch.client.bean.FromJsBeanRefreshToken;
import com.lonch.client.http.HttpJsService;
import com.lonch.client.http.OkHttpClientUtil;
import com.lonch.client.interfaces.JsDataContract;
import com.lonch.client.utils.OkHttpUtil;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class JsDataModel {

    JsDataContract.JsGetDataView responseJSData;
    private StringBuffer stringBuffer;
    private String token;
    private final HttpJsService httpService;
    private final static String TAG = "JsDataModel";
    private Map hashMap;

    public JsDataModel(JsDataContract.JsGetDataView getDataView, String token) {
        this.responseJSData = getDataView;
        this.token = token;
        Retrofit retrofit = new Retrofit
                .Builder()
                .client(OkHttpClientUtil.getInstance())
                .baseUrl(LonchCloudApplication.getAppConfigDataBean().SERVICE_URL)  //自己配置
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpService = retrofit.create(HttpJsService.class);
    }


    public void getJsData(FromJsBean jsBean) {
        JsonObject jsonObjectParameters = jsBean.getArgs().getParameters();//get请求参数
        String url = jsBean.getArgs().getUrl();
//        String baseUrl = Objects.requireNonNull(MMKV.defaultMMKV()).decodeString("userLink");
//        if (!TextUtils.isEmpty(baseUrl) && baseUrl.startsWith("http") && !TextUtils.isEmpty(url) && url.contains(LonchCloudApplication.getAppConfigDataBean().SERVICE_URL)){
//            Uri uri = Uri.parse(url);
//            if (null!=uri){
//                String path = uri.getPath();
//                url = baseUrl + path;
//            }
//        }
        //json转map
        JSONObject jsonObject = JSON.parseObject(jsonObjectParameters.toString());//String转json
        Map<String, Object> jsonMapAll = JSONObject.toJavaObject(jsonObject, Map.class);
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        } else {
            stringBuffer.delete(0, stringBuffer.length());
        }
        for (Map.Entry<String, Object> entry : jsonMapAll.entrySet()) {
            if (!entry.getKey().contains("manageProductId")) {
                stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        final String sn = jsBean.getSn();
        String parameters = jsBean.getArgs().getParameters().toString();//body 参数
        RequestBody requestBody = RequestBody.create(MediaType.parse(""), parameters);
        String method = jsBean.getArgs().getMethod();
        String config = jsBean.getArgs().getConfig();
        if (!TextUtils.isEmpty(config)) {
            JSONObject jsonObject1 = JSONObject.parseObject(config);
            if (hashMap != null) {
                hashMap.clear();
            }
            hashMap = JSONObject.toJavaObject(jsonObject1.getJSONObject("headers"), Map.class);
        } else {
            hashMap = new HashMap<>();
        }
        token = (String) SpUtils.get("token", "");
        if (!TextUtils.isEmpty(token)) {
            Call<ResponseBody> requestBodyCall = null;
            if (method.equals("GET")) {
                if (!url.contains("?")) {
                    url = url + "?" + stringBuffer;
                } else {
                    url = url + "&" + stringBuffer;
                }

                requestBodyCall = httpService.jsGetGetData(url, token, hashMap);
            } else if (method.equals("POST")) {
                if (jsBean.getArgs().getDataType().equals("FormData")) {
                    Map<String, String> jsonMap = JSONObject.toJavaObject(jsonObject, Map.class);
                    Map<String, RequestBody> stringRequestBodyMap = generateRequestBody(jsonMap);
                    requestBodyCall = httpService.jsMultipart(url, token, hashMap, stringRequestBodyMap);
                } else if (jsBean.getArgs().getDataType().equals("JSON")) {
                    requestBodyCall = httpService.jsGetData(url, token, hashMap, requestBody);
                }
            }
            String finalUrl = url;
            long startTime = System.currentTimeMillis();
            requestBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            String responseString = responseBody.string();
                            if (responseString.contains("SERGTW1201100003") || responseString.contains("SERGTW1201100006") || responseString.contains("SERgtw1201100003") || responseString.contains("SERgtw1201100010") || responseString.contains("SERgtw1201100006")) {
                                Utils.reStartLogin(LonchCloudApplication.getApplicationsContext());
                            } else {
                                JSONObject jsonObject1 = JSONObject.parseObject(responseString);
                                boolean opFlag = jsonObject1.getBoolean("opFlag");
                                if (!opFlag) {
                                    sendLog(finalUrl, parameters, responseString);
                                }
                                responseJSData.onResponseSuccess(sn, responseString, false);
                            }
                            Utils.saveApiResponse(finalUrl, 1, System.currentTimeMillis() - startTime);
                        } else {
                            ResponseBody errorBody = response.errorBody();
                            if (null != errorBody) {
                                String errorString = errorBody.string();
                                errorString += "response code:" + response.code();
                                responseJSData.onResponseFailed(sn, setResponseErrorData(errorString, parameters));
                                if (null != hashMap) {
                                    sendLog(finalUrl, parameters, errorString);
                                }
                            }
                            Utils.saveApiResponse(finalUrl, 0, System.currentTimeMillis() - startTime);
                        }

                    } catch (Exception e) {
                        Utils.saveApiResponse(finalUrl, 0, System.currentTimeMillis() - startTime);
                        if (finalUrl.contains("json")){
                            return;
                        }
                        responseJSData.onResponseFailed(sn, setResponseErrorData(e.getMessage(), parameters));
                        if (null != hashMap) {
                            sendLog(finalUrl, parameters, e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                    try {
                        responseJSData.onResponseFailed(sn, setResponseErrorData(t.getMessage(), parameters));
                        if (null != hashMap) {
                            sendLog(finalUrl, parameters, t.getMessage());
                        }
                        Utils.saveApiResponse(finalUrl, 0, System.currentTimeMillis() - startTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Utils.reStartLogin(LonchCloudApplication.getApplicationsContext());
        }
    }

    public void release() {
//        if (okHttpClient!=null){
//            okHttpClient.dispatcher().cancelAll();
//            okHttpClient.connectionPool().evictAll();
//        }

    }

    /**
     * webCallAppV2 代理方请求法
     *
     * @param baseArgsV2 参数
     */
    public void getJsDataV2(BaseArgsV2 baseArgsV2) {
        JSONObject dataJsonObject = baseArgsV2.getArgs().getData();
        String url = dataJsonObject.getString("url");
        String method = dataJsonObject.getString("method");

        JSONObject jsonObject = dataJsonObject.getJSONObject("parameters");

        if (method.equals("GET")) {
            Map<String, Object> jsonMapAll = JSONObject.parseObject(jsonObject.toString(), new TypeReference<Map<String, Object>>() {
            });
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            } else {
                stringBuffer.delete(0, stringBuffer.length());
            }

            for (Map.Entry<String, Object> entry : jsonMapAll.entrySet()) {
                stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        final String sn = baseArgsV2.getSn();
        String parameters = JSON.toJSONString(jsonObject);//body 参数
        RequestBody requestBody = RequestBody.create(MediaType.parse(""), parameters);
        JSONObject config = baseArgsV2.getArgs().getConfig();
        if (null != config) {
            JSONObject headers = config.getJSONObject("headers");
            if (null != headers) {
                if (hashMap != null) {
                    hashMap.clear();
                }
                hashMap = JSONObject.parseObject(headers.toString(), new TypeReference<Map<String, Object>>() {
                });
            }
        } else {
            hashMap = new HashMap<>();
        }
        token = (String) SpUtils.get("token", "");
        if (!TextUtils.isEmpty(token)) {
            Call<ResponseBody> requestBodyCall = null;
            if (method.equals("GET")) {
                if (!url.contains("?")) {
                    url = url + "?" + stringBuffer;
                } else {
                    url = url + "&" + stringBuffer;
                }
                requestBodyCall = httpService.jsGetGetData(url, token, hashMap);
            } else if (method.equals("POST")) {
                if (dataJsonObject.getString("dataType").equals("FormData")) {
                    Map<String, String> jsonMap = JSONObject.parseObject(jsonObject.toString(), new TypeReference<Map<String, String>>() {
                    });
                    Map<String, RequestBody> stringRequestBodyMap = generateRequestBody(jsonMap);
                    requestBodyCall = httpService.jsMultipart(url, token, hashMap, stringRequestBodyMap);
                } else if (dataJsonObject.getString("dataType").equals("JSON")) {
                    requestBodyCall = httpService.jsGetData(url, token, hashMap, requestBody);
                }
            }
            String finalUrl = url;
            long startTime = System.currentTimeMillis();
            requestBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            String responseString = responseBody.string();
                            if (responseString.contains("SERGTW1201100003") || responseString.contains("SERGTW1201100006") || responseString.contains("SERgtw1201100003") || responseString.contains("SERgtw1201100010") || responseString.contains("SERgtw1201100006")) {
                                Utils.reStartLogin(LonchCloudApplication.getApplicationsContext());
                            } else {
                                JSONObject jsonObject1 = JSONObject.parseObject(responseString);
                                boolean opFlag = jsonObject1.getBoolean("opFlag");
                                if (!opFlag) {
                                    sendLog(finalUrl, parameters, responseString);
                                }
                                responseJSData.onResponseSuccess(sn, responseString, false);
                            }
                            Utils.saveApiResponse(finalUrl, 1, System.currentTimeMillis() - startTime);
                        } else {
                            ResponseBody errorBody = response.errorBody();
                            if (null != errorBody) {
                                String errorString = errorBody.string();
                                errorString += "response code:" + response.code();
                                responseJSData.onResponseFailed(sn, setResponseErrorData(errorString, parameters));
                                if (null != hashMap) {
                                    sendLog(finalUrl, parameters, errorString);
                                }
                            }
                            Utils.saveApiResponse(finalUrl, 0, System.currentTimeMillis() - startTime);
                        }

                    } catch (Exception e) {
                        Utils.saveApiResponse(finalUrl, 0, System.currentTimeMillis() - startTime);
                        if (finalUrl.contains("json")){
                            return;
                        }
                        responseJSData.onResponseFailed(sn, setResponseErrorData(e.getMessage(), parameters));
                        if (null != hashMap) {
                            sendLog(finalUrl, parameters, e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                    try {
                        responseJSData.onResponseFailed(sn, setResponseErrorData(t.getMessage(), parameters));
                        if (null != hashMap) {
                            sendLog(finalUrl, parameters, t.getMessage());
                        }
                        Utils.saveApiResponse(finalUrl, 0, System.currentTimeMillis() - startTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Utils.reStartLogin(LonchCloudApplication.getApplicationsContext());
        }
    }


    //比如可以这样生成Map<String, RequestBody> requestBodyMap
    //Map<String, String> requestDataMap这里面放置上传数据的键值对。
    private static Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : Objects.requireNonNull(requestDataMap.get(key)));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

    public void refreshToken(FromJsBeanRefreshToken refreshJson) {
        String dataOwnerOrgId1 = refreshJson.getArgs().getParameters().getDataOwnerOrgId();
        String manageProductId = refreshJson.getArgs().getParameters().getManageProductId();
        Map<String, Object> map = new HashMap<>();
        map.put("dataOwnerOrgId", dataOwnerOrgId1);
        map.put("manageProductId", manageProductId);
        String apiResultJson = toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse(""), apiResultJson);

        String url = refreshJson.getArgs().getUrl();
        String method = refreshJson.getArgs().getMethod();
        final String sn = refreshJson.getSn();
        HashMap<String, String> map1 = new HashMap<>();
        if (!TextUtils.isEmpty(token)) {

            final Call<ResponseBody> requestBodyCall;
            if (method.equals("GET")) {
                requestBodyCall = httpService.jsGetGetData(url, token, map1);
            } else {
                requestBodyCall = httpService.jsGetData(url, token, map1, requestBody);
            }

            requestBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    try {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            String string = responseBody.string();
                            responseJSData.onResponseSuccess(sn, string, true);
                        } else {
                            ResponseBody responseBody = response.errorBody();
                            responseJSData.onResponseFailed(sn, responseBody.string());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        responseJSData.onResponseFailed(sn, setResponseErrorData(e.getMessage(), ""));
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                    try {
                        Log.d("onFailure=", "" + call.toString());
                        responseJSData.onResponseFailed(sn, setResponseErrorData(t.getMessage(), ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

    private String toJson(Object obj) {
        // 序列化
        ObjectMapper mapper = new ObjectMapper();
        String object = null;
        try {
            object = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }

    private String setResponseErrorData(String error, String request) {
        String apiResultJson;
        if (hashMap != null && hashMap.containsKey("protocol-version")) {
            ApiResultV2<Map<String, Object>> apiResult = new ApiResultV2<>();
            apiResult.setErrorMsg(error);
            apiResult.setOpFlag(false);
            if (!TextUtils.isEmpty(request)) {
                if (request.trim().startsWith("{") && request.trim().endsWith("}")) {
                    JSONObject jsonObject = JSON.parseObject(request);
                    if (jsonObject.containsKey("sid")) {
                        apiResult.setSid(jsonObject.getString("sid"));
                    }
                }
            }
            Map<String, Object> map = new HashMap<>();
            apiResult.setServiceResult(map);
            apiResultJson = toJson(apiResult);
        } else {
            ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
            apiResult.setError(error);
            apiResult.setOpFlag(false);
            Map<String, Object> map = new HashMap<>();
            apiResult.setServiceResult(map);
            apiResultJson = toJson(apiResult);
        }
        return apiResultJson;
    }

    private void sendLog(String request, String paramsStr, String error) {
        AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(LonchCloudApplication.getApplicationsContext());
        appLog.setReqUrl(request);
        appLog.setReqParam(paramsStr);
        appLog.setErrMsg(error);
        appLog.setReqResult(error);
        appLog.setEventName("代理请求服务异常");
        if (!TextUtils.isEmpty(error)) {
            if (error.contains("Unable to resolve host") || error.contains("connect") || error.contains("reset") || error.contains("timeout")
                    || error.contains("socket") || error.contains("Socket") || error.contains("stream") || request.contains("config.json") || error.contains("SSL handshake")) {
                appLog.setErrLevel("warn");
            } else {
                appLog.setErrLevel("error");
            }
        }
        appLog.setErrCode("ANDyfc0002400001");
        appLog.setRemark("service");
        if (null != hashMap && hashMap.containsKey("sid")) {
            appLog.setSid(Objects.requireNonNull(hashMap.get("sid")).toString());
        } else {
            String paramSid = Utils.getParamSid(paramsStr);
            if (!TextUtils.isEmpty(paramSid)) {
                appLog.setSid(paramSid);
            }
        }
        OkHttpUtil.getInstance().sendPostRequest(LonchCloudApplication.getAppConfigDataBean().SERVICE_LOG_URL, appLog);
    }
}
