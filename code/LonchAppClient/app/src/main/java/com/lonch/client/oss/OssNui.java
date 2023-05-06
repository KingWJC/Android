package com.lonch.client.oss;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.http.Http;
import com.lonch.client.http.HttpService;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.SystemUtil;
import com.tencent.mmkv.MMKV;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 阿里一句话语音识别
 */
public class OssNui {
    private static final String TAG = "OSSNUi";
    private static OssNui ossNui;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static OssNui getInstance() {
        if (ossNui == null) {
            synchronized (OssNui.class) {
                if (ossNui == null) {
                    ossNui = new OssNui();
                }
            }
        }
        return ossNui;
    }

    public String genParams() {
        String params = "";
        try {
            JSONObject nls_config = new JSONObject();
            nls_config.put("enable_intermediate_result", false);

            JSONObject parameters = new JSONObject();

            parameters.put("nls_config", nls_config);
//            parameters.put("service_type", Constants.kServiceTypeASR);
            params = parameters.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    public String genInitParams(String workpath, String debugpath) {
        String str = "";
        try {
            //获取token方式一般有两种：

            //方法1：
            //参考Auth类的实现在端上访问阿里云Token服务获取SDK进行获取
            //JSONObject object = Auth.getAliYunTicket();

            //方法2：（推荐做法）
            //在您的服务端进行token管理，此处获取服务端的token进行语音服务访问


            //请输入您申请的id与token，否则无法使用语音服务，获取方式请参考阿里云官网文档：
            //https://help.aliyun.com/document_detail/72153.html?spm=a2c4g.11186623.6.555.59bd69bb6tkTSc
            JSONObject object = new JSONObject();

            //token 24小时过期，因此需要通过阿里云SDK来进行更新
            object.put("app_key", Config.OSS_NUI_APP_KEY);
            object.put("token", MMKV.defaultMMKV().decodeString("ossToken", ""));
            object.put("url", "wss://nls-gateway.cn-shanghai.aliyuncs.com:443/ws/v1");
            object.put("device_id", SystemUtil.getAndroidDeviceId(LonchCloudApplication.getApplicationsContext()));
            object.put("workspace", workpath);
            object.put("debug_path", debugpath);
            object.put("sample_rate", "16000");
            object.put("format", "opus");
//            object.put("save_wav", "true");
            str = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "InsideUserContext:" + str);
        return str;
    }

    public String genDialogParams() {
        String params = "";
        try {
            JSONObject dialog_param = new JSONObject();
            params = dialog_param.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "dialog params: " + params);
        return params;
    }

    public void getToken(HttpCallBack httpCallBack) {
        String token = (String) SpUtils.get("token","");
        if(TextUtils.isEmpty(token)){
            return;
        }
        HttpService httpService = Http.getInstance().getApiService(HttpService.class);
        RequestBody requestBody = RequestBody.create(JSON, "{}");
        httpService.getOssNuiToken(token,requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()){
                        if (null!=response.body()){
                            String data = response.body().string();
                            JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(data);
                            boolean opFlag = jsonObject.getBoolean("opFlag");
                            if (opFlag) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("serviceResult");
                                String ossToken;
                                if (jsonObject1.containsKey("data")){
                                    boolean success = jsonObject1.getBoolean("success");
                                    JSONObject dataJsonObject = jsonObject1.getJSONObject("data");
                                    if (success){
                                        ossToken = dataJsonObject.getString("token");
                                    }else{
                                        return;
                                    }
                                }else{
                                    ossToken = jsonObject1.getString("token");
                                }

                                if (!TextUtils.isEmpty(ossToken)) {
                                    MMKV mmkv = MMKV.defaultMMKV();
                                    mmkv.encode("ossToken", ossToken);
                                    mmkv.encode(ossToken, System.currentTimeMillis());
                                    httpCallBack.onSuccess(ossToken);
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public interface HttpCallBack {
        void onSuccess(String data);

        void onFailure();
    }


}
