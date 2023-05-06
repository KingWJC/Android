package com.lonch.client.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioRecord;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.idst.nui.AsrResult;
import com.alibaba.idst.nui.CommonUtils;
import com.alibaba.idst.nui.INativeNuiCallback;
import com.alibaba.idst.nui.KwsResult;
import com.alibaba.idst.nui.NativeNui;
import com.chinaums.pppay.unify.UnifyPayListener;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.R;
import com.lonch.client.activity.MyZxingActivity;
import com.lonch.client.base.BaseWebActivity;
import com.lonch.client.bean.ApiResult;
import com.lonch.client.bean.AppLog;
import com.lonch.client.bean.BaseArgs;
import com.lonch.client.bean.BaseArgsV2;
import com.lonch.client.bean.BaseSixFourBean;
import com.lonch.client.bean.FromJsBean;
import com.lonch.client.bean.FromJsBeanRefreshToken;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.bean.RefreshTokenBean;
import com.lonch.client.bean.ToolBarBeanMy;
import com.lonch.client.bean.WebJsFunction;
import com.lonch.client.bean.argsbean.ArgsAppPay;
import com.lonch.client.bean.argsbean.ArgsAppPayWeixin;
import com.lonch.client.bean.argsbean.ArgsAppPayZhiAi;
import com.lonch.client.bean.argsbean.ArgsDisplay;
import com.lonch.client.bean.event.DisplayEvent;
import com.lonch.client.bean.event.IpEvent;
import com.lonch.client.bean.event.PayWxEvent;
import com.lonch.client.bean.event.ScanCodeEvent;
import com.lonch.client.common.AppConstants;
import com.lonch.client.common.CompressStatus;
import com.lonch.client.common.ConstantValue;
import com.lonch.client.interfaces.JsDataContract;
import com.lonch.client.manager.WBH5FaceVerifySDK;
import com.lonch.client.model.JsDataModel;
import com.lonch.client.oss.OssNui;
import com.lonch.client.pay.DeviceUtil;
import com.lonch.client.service.NetBroadcastReceiver;
import com.lonch.client.utils.DownloadUtil;
import com.lonch.client.utils.FileUtils;
import com.lonch.client.utils.KeyboardUtil;
import com.lonch.client.utils.LocalWebInfoUtil;
import com.lonch.client.utils.OkHttpUtil;
import com.lonch.client.utils.SDCardUtil;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.UrlUtil;
import com.lonch.client.utils.Utils;
import com.lonch.client.utils.ZYToastUtils;
import com.lonch.client.utils.ZipTask;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mmkv.MMKV;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * User:白二鹏
 * Created by Administrator-10-19 23 : 57
 */

public class WebFragment extends Fragment implements JsDataContract.JsGetDataView, WebJsFunction.CallbackJsFun, INativeNuiCallback {
    private static final String TAG = "FragmentTestChat";
    public MyWebView webView;
    private String mRootUrl = "";
    private String webPack;
    private JsDataModel jsDataModel;
    private ToolBarBeanMy toolBarBean;
    public String paySn;
    private LinearLayout main_layout;
    boolean isPay = false;
    boolean onWxFlg = false;
    private String imageUrl = "";
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private final static int FILECHOOSER_CAMERA_RESULTCODE_FOR_ANDROID_5 = 20;
    private final static int REQUEST_CODE = 302;
    private String recordSn = "";
    private String openSn = "";
    NativeNui nui_instance = new NativeNui();
    private AudioRecord mAudioRecorder;
    private boolean mInit = false;
    private GeolocationPermissions.Callback geolocationCallback;
    private String orgin;
    private NetBroadcastReceiver netBroadcastReceiver;
    private String codeSn;
    private MyHandler handler = new MyHandler(this);
    final static class MyHandler extends Handler {
        WeakReference<WebFragment> webFragmentWeakReference;

        public MyHandler(WebFragment webFragment) {
            this.webFragmentWeakReference = new WeakReference<>(webFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WebFragment webFragment = webFragmentWeakReference.get();
            if (webFragment != null && !webFragment.isVisible()) {
                if (msg.what == CompressStatus.COMPLETED) {
                    Bundle bundle = msg.getData();
                    if (null == bundle) {
                        return;
                    }
                    PlistPackageBean packageBean = bundle.getParcelable("packageBean");
                    Gson gson = new Gson();
                    String data = gson.toJson(packageBean);
                    FileUtils.saveDataToFile(packageBean, data);
                    webFragment.startWebView();
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScanFinished(IpEvent event) {
        if (!TextUtils.isEmpty(event.getMsg())) {
            appCallWeb(openSn, Utils.setAppWebData(new HashMap<>()));
        }
    }

    //EventBus 微信支付
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayWxFinished(PayWxEvent event) {
        if (!TextUtils.isEmpty(event.getMsg())) {
            String type = event.getType();
            if (!TextUtils.isEmpty(type)) {
                doAppCallWeb(event.getMsg());
            } else {
                onWxFlg = true;
                ApiResult<HashMap<String, String>> apiResult = new ApiResult<>();
                apiResult.setOpFlag(event.isOpFlag());
                apiResult.setError(event.getMsg());
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(paySn, toJson(apiResult));
            }
        }
    }

    /**
     * 本地服务器启动成功事件
     *
     * @param event 参数
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void scanCode(ScanCodeEvent event) {
        if (event.getSn().equals("onStarted")) {
            if (webView != null) {
                webView.reload();
                handler.postDelayed(() -> webView.setVisibility(View.VISIBLE), 250);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        EventBus.getDefault().register(this);
        assert arguments != null;
        toolBarBean = arguments.getParcelable("toolBarBean");
        return inflater.inflate(R.layout.fragment_main_webview, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        nui_instance.release();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        handler.removeCallbacksAndMessages(null);
        if (null != netBroadcastReceiver) {
            requireActivity().unregisterReceiver(netBroadcastReceiver);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView(view);
        String token = (String) SpUtils.get("token", "");
        jsDataModel = new JsDataModel(this, token);
        this.webPack = toolBarBean.getWeb_app_id();
        //主页面需要等服务启动
        startWebView();
        //语音识别参数
        HandlerThread mHandlerThread = new HandlerThread("process_thread");
        mHandlerThread.start();
        if (checkSinglePermission(Manifest.permission.RECORD_AUDIO) && checkSinglePermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            getOssToken(false);
        }
        KeyboardUtil keyboardUtil = new KeyboardUtil(requireActivity());
        keyboardUtil.setOnKeyboardChangeListener(new KeyboardUtil.KeyboardChangeListener() {
            @Override
            public void onKeyboardShow(int keyboardHight) {
                ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                Map<String, Object> map = new HashMap<>();
                map.put("status", "1");
                map.put("hight", keyboardHight);
                apiResult.setServiceResult(map);
                String apiResultJson = toJson(apiResult);
                appCallWeb("keyboardChange", apiResultJson);
            }

            @Override
            public void onKeyboardHide() {
                ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                Map<String, Object> map = new HashMap<>();
                map.put("status", "0");
                apiResult.setServiceResult(map);
                String apiResultJson = toJson(apiResult);
                appCallWeb("keyboardChange", apiResultJson);
            }
        });

        netBroadcastReceiver = new NetBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        requireActivity().registerReceiver(netBroadcastReceiver, filter);
        netBroadcastReceiver.setNetEvent(netMobile -> {
            int status = 0;
            if (netMobile > -1) {
                status = 1;
            }
            ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
            Map<String, Object> map = new HashMap<>();
            map.put("status", status);
            apiResult.setServiceResult(map);
            String apiResultJson = toJson(apiResult);
            appCallWeb("netStatusChanged", apiResultJson);
        });
    }

    private void getOssToken(boolean isCheck) {
        MMKV mmkv = MMKV.defaultMMKV();
        String ossToken = mmkv.decodeString("ossToken");
        long time = mmkv.decodeLong(ossToken);
        if (TextUtils.isEmpty(ossToken) || Utils.differHours(time, System.currentTimeMillis()) > 23) {
            OssNui.getInstance().getToken(new OssNui.HttpCallBack() {
                @Override
                public void onSuccess(String data) {
                    LonchCloudApplication.handler.post(() -> doInit(isCheck));
                }

                @Override
                public void onFailure() {
                }
            });
        } else {
            LonchCloudApplication.handler.post(() -> doInit(isCheck));
        }
    }

    private void doInit(boolean isCheck) {
        if (!isAdded()) {
            return;
        }
        //获取工作路径
        String asset_path = CommonUtils.getModelPath(requireActivity());
        String debug_path = requireActivity().getCacheDir().getAbsolutePath() + "/debug_" + System.currentTimeMillis();
        FileUtils.createOrExistsDir(debug_path);
        //录音初始化，录音参数中格式只支持16bit/单通道，采样率支持8K/16K
//        mAudioRecorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, LonchCloudApplication.getAppConfigDataBean().SAMPLE_RATE,
//                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, LonchCloudApplication.getAppConfigDataBean().WAVE_FRAM_SIZE * 4);
        //这里主动调用完成SDK配置文件的拷贝
        if (CommonUtils.copyAssetsData(requireActivity())) {
            Log.i(TAG, "copy assets data done");
        } else {
            Log.i(TAG, "copy assets failed");
            AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(requireActivity());
            appLog.setErrCode("ANDyfc0005200001");
            appLog.setErrLevel("warn");
            appLog.setErrMsg("语音SDK初始化基础数据失败");
            appLog.setEventName("语音SDK初始化基础数据失败");
            OkHttpUtil.getInstance().sendPostRequest(LonchCloudApplication.getAppConfigDataBean().SERVICE_LOG_URL, appLog);
            return;
        }
        //初始化SDK，注意用户需要在Auth.getAliYunTicket中填入相关ID信息才可以使用。
        int ret = nui_instance.initialize(this, OssNui.getInstance().genInitParams(asset_path, debug_path), com.alibaba.idst.nui.Constants.LogLevel.LOG_LEVEL_VERBOSE, false);
        if (ret == com.alibaba.idst.nui.Constants.NuiResultCode.SUCCESS) {
            mInit = true;
        } else {
            AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(requireActivity());
            appLog.setErrCode(ret + "");
            appLog.setErrLevel("warn");
            appLog.setErrMsg("语音SDK初始化失败");
            appLog.setEventName("语音SDK初始化失败");
            OkHttpUtil.getInstance().sendPostRequest(LonchCloudApplication.getAppConfigDataBean().SERVICE_LOG_URL, appLog);
        }
        //设置相关识别参数，具体参考API文档
        nui_instance.setParams(OssNui.getInstance().genParams());
        if (isCheck && mInit) {
            startDialog();
        }
    }

    private void startDialog() {
        LonchCloudApplication.handler.post(() -> {
            int ret = nui_instance.startDialog(com.alibaba.idst.nui.Constants.VadMode.TYPE_P2T,
                    OssNui.getInstance().genDialogParams());
        });
    }

    private void stopDialog() {
        LonchCloudApplication.handler.post(() -> {
            long ret = nui_instance.stopDialog();
            if (ret != com.alibaba.idst.nui.Constants.NuiResultCode.SUCCESS) {
                appCallWeb(recordSn, Utils.setAppErrorData("语音识别失败"));
                AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(requireActivity());
                appLog.setErrCode(ret + "");
                appLog.setErrLevel("warn");
                appLog.setErrMsg("语音识别失败");
                appLog.setEventName("语音识别失败");
                OkHttpUtil.getInstance().sendPostRequest(LonchCloudApplication.getAppConfigDataBean().SERVICE_LOG_URL, appLog);
            }
        });
    }

    public void startWebView() {
        String ip = (String) SpUtils.get("ip", "");
        if (TextUtils.isEmpty(webPack)) {
            return;
        }
        PlistPackageBean resources = Utils.getPackageBean(webPack);
        if (null == resources) {
            return;
        }
        String app_package_name = resources.getApp_package_name();
        boolean online = resources.isUsing_online_url();
        if (online) {
            String onlineUrl = resources.getWebapp_url();
            if (!TextUtils.isEmpty(onlineUrl) && !onlineUrl.endsWith("/")) {
                onlineUrl += "/";
            }
            mRootUrl = onlineUrl + toolBarBean.getUrl_path();
        } else {
            if (!TextUtils.isEmpty(ip)) {
                final String app_package_namea = requireActivity().getFilesDir().getAbsolutePath() + "/" + "App/";
                try {
                    if (!TextUtils.isEmpty(resources.getVersion())) {
                        String filePath = app_package_namea + app_package_name + "/" + resources.getVersion() + "/index.html";
                        File file = new File(filePath);
                        if (file.exists()) {
                            mRootUrl = "http://" + ip + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/" + app_package_name + "/" + resources.getVersion() + "/" + toolBarBean.getUrl_path();
                        } else {
                            DownloadUtil.getInstance().downloadSingleFile(resources, requireActivity().getFilesDir() + File.separator + "Zip", new DownloadUtil.DownloadCallBack() {
                                @Override
                                public void onError(String msg) {
                                }

                                @Override
                                public void onSuccess(PlistPackageBean bean, String path) {
                                    final String unZipPath = requireActivity().getFilesDir().getAbsolutePath() + "/App/" + resources.getApp_package_name() + "/" + resources.getVersion();
                                    ZipTask zipTask = new ZipTask(path, unZipPath, resources, handler);
                                    zipTask.execute();
                                }
                            });
                        }
                    }
                    String mBaseUrl = "http://" + ip + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/";
                    SpUtils.put("baseUrl", mBaseUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        webView.clearCache(true);
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(mRootUrl);
    }

    @Override
    public void webCallAppV2(String msg) {
        BaseArgsV2 baseArgsV2 = JSON.parseObject(msg, BaseArgsV2.class);
        String sn = baseArgsV2.getSn();
        String command = baseArgsV2.getCommand();
        baseArgsV2.setSn(sn + "," + command);
        if (command.equals("getAppProxyData")) {
            jsDataModel.getJsDataV2(baseArgsV2);
        }
    }

    //web调用android
    @Override
    public void webCallApp(String msg) {
        Gson gson = new Gson();
        BaseArgs baseArgs = gson.fromJson(msg, BaseArgs.class);
        String command = baseArgs.getCommand();
        String sn = baseArgs.getSn();
        switch (command) {
            case "getAppProxyData"://获取接口数据
                FromJsBean fromJsBean = new Gson().fromJson(msg, FromJsBean.class);
                jsDataModel.getJsData(fromJsBean);
                break;
            case "refreshToken"://切换组织更新token
                FromJsBeanRefreshToken fromJsBeanRefreshToken = new Gson().fromJson(msg, FromJsBeanRefreshToken.class);
                jsDataModel.refreshToken(fromJsBeanRefreshToken);
                break;
            case "openSideMenu"://打开侧栏
                // requireActivity().runOnUiThread(() -> ((MainActivity) requireActivity()).mDrawerlayout.openDrawer(GravityCompat.START));
                break;
            case "appPay"://支付
                BaseArgs<ArgsAppPay> argsAppPayBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsAppPay>>() {
                });
                appPay(sn, argsAppPayBaseArgs.getArgs().getType(), msg, argsAppPayBaseArgs.getArgs().getPayType());
                break;
            case "getAppInfo"://获取接口数据,本地版本信息
                appCallWeb(sn, LocalWebInfoUtil.getLocalWebInfoAll());
                break;
            case "scanQRCode"://关闭页面
                scanZing(sn);
                break;
            case "pay"://普药商城支付
                pypay(sn, msg);
                break;
            case "cmdAppToggleHeader"://二楼
                final BaseArgs<ArgsDisplay> displayBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsDisplay>>() {
                });
                EventBus.getDefault().post(new DisplayEvent(displayBaseArgs.getArgs().getDisplay()));
                break;
            case "cmdStartRecord": //开始录音
                requireActivity().runOnUiThread(() -> {
                    if (checkPermission()) {
                        if (!mInit) {
                            appCallWeb(sn, Utils.setAppErrorData("语音识别SDK初始化失败"));
                            return;
                        }
                        startDialog();
                    }
                });
                break;
            case "cmdEndRecord": //结束录音
                if (!mInit) {
                    return;
                }
                recordSn = sn;
                stopDialog();
                break;
            case "cmdOpenThirdPartyApp":
                JSONObject parseObject = JSON.parseObject(msg);
                JSONObject argsJSON = parseObject.getJSONObject("args");
                String appType = argsJSON.getString("type");
                if (!TextUtils.isEmpty(appType)) {
                    if (appType.equals("wechatMiniProgram")) {
                        ArgsAppPayWeixin argsAppPayWeixin = new ArgsAppPayWeixin();
                        argsAppPayWeixin.setPath(argsJSON.getString("path"));
                        argsAppPayWeixin.setUserName(argsJSON.getString("userName"));
                        goToOtherApp(argsAppPayWeixin);
                    }
                }
                break;
            default:
                appCallWeb(sn, Utils.setAppErrorData(getResources().getString(R.string.txt_h5_protocol_not_achieved)));
        }

    }

    @Override
    public void webCallSn(String sn) {
        if (!TextUtils.isEmpty(sn)) {
            openSn = sn;
        }
    }

    private org.json.JSONObject lastH5JsonObject;
    public static final int SHOP_WEIXIN_PAY = 1;
    public static final int SHOP_ALI_PAY = 2;

    private void pypay(String sn, final String jsonStr) {
        paySn = sn;
        isPay = true;
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonStr);
            lastH5JsonObject = jsonObject;
            org.json.JSONObject paramArgsJsonObject = jsonObject.optJSONObject("args");
            String paramJsonStr;
            org.json.JSONObject paramJsonObject = null;
            if (paramArgsJsonObject != null) {
                paramJsonStr = paramArgsJsonObject.optString("parameters");
                if (!TextUtils.isEmpty(paramJsonStr))
                    paramJsonObject = new org.json.JSONObject(paramJsonStr);
            }
            String command = jsonObject.optString("command");
            if (!TextUtils.isEmpty(command)) {
                switch (command) {
                    case AppConstants.WebViewCommandProtocalName.SHOP_PAY:
                        pay(paramArgsJsonObject);
                        break;
                    case AppConstants.WebViewCommandProtocalName.GET_DATA:
                        break;
                    case AppConstants.WebViewCommandProtocalName.OPEN_MINI_PROGRAM:
                        assert paramArgsJsonObject != null;
                        jumpToWXProgram((String) paramArgsJsonObject.opt("appId"), (String) paramArgsJsonObject.opt("path"));
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到微信小程序，是一个通用方法
     */
    private void jumpToWXProgram(String appId, String path) {
        if (TextUtils.isEmpty(appId)) {
            ZYToastUtils.showLongToast("sorry, appid is null~~");
            return;
        }
        if (TextUtils.isEmpty(path)) {
            ZYToastUtils.showLongToast("sorry, path is null~~");
            return;
        }
        IWXAPI api = WXAPIFactory.createWXAPI(requireActivity(), LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = appId; // 填小程序原始id
        req.path = path;
        if (LonchCloudApplication.getAppConfigDataBean().SERVICE_URL.contains("test")) {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
        } else {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        }
        api.sendReq(req);
    }

    /**
     * 跳转到商城微信支付小程序，为啥支付不走微信客户端呢，说是为了节省成本。。
     */
    private void jumpToShopWXPayProgram(String args) {
        IWXAPI api = WXAPIFactory.createWXAPI(requireActivity(), LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = ConstantValue.WX_SHOP_PAY_RELEASE_APPID; // 填小程序原始id
        req.path = "pages/index/index?args=" + args;
        if (LonchCloudApplication.getAppConfigDataBean().SERVICE_URL.contains("test")) {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
        } else {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        }
        api.sendReq(req);
    }

    public void pay(org.json.JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        int payChannel = jsonObject.optInt("payChannel");
        org.json.JSONObject paramArgsJsonObject = lastH5JsonObject.optJSONObject("args");
        switch (payChannel) {
            case SHOP_WEIXIN_PAY://跳小程序
                if (!DeviceUtil.checkWeiXinInstalled(requireActivity())) {
                    //没有安装微信
                    try {
                        assert paramArgsJsonObject != null;
                        paramArgsJsonObject.put("noPayWay", true);
                        doAppCallWeb(requireActivity().getResources().getString(R.string.error_activity_wx_no_install), null, lastH5JsonObject, true);
                    } catch (JSONException ignored) {
                    }
                    return;
                }
                org.json.JSONObject payDataJsonObject = jsonObject.optJSONObject("payData");
                if (payDataJsonObject == null) {
                    ZYToastUtils.showLongToast("error: payData is null~~");
                    return;
                }
                assert paramArgsJsonObject != null;
                jumpToShopWXPayProgram(paramArgsJsonObject.toString());

                break;
            case SHOP_ALI_PAY://跳全民支付到支付宝
                if (!DeviceUtil.checkAliPayInstalled(requireActivity())) {
                    //没有安装微信
                    paramArgsJsonObject.remove("payData");
                    try {
                        paramArgsJsonObject.put("noPayWay", true);
                        doAppCallWeb(requireActivity().getResources().getString(R.string.error_activity_alipay_no_install), null, lastH5JsonObject, true);
                    } catch (JSONException ignored) {
                    }
                    return;
                }
                String payData = jsonObject.optString("payData");
                if (TextUtils.isEmpty(payData)) {
                    return;
                }
                chinaumsAliPayPySc(requireActivity(), payData, (s, s1) -> {
                });
                // goToAliPay(payData);
                break;
        }
    }

    private void goToAliPay(String payUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(payUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(uri);
        startActivity(intent);
    }

    /**
     * 银联商务全民付(普药商城)
     */
    public synchronized void chinaumsAliPayPySc(Context context, String payParamsStr, UnifyPayListener unifyPayListener) {
        if (context == null) {
            return;
        }
        if (!(context instanceof Activity)) {
            return;
        }
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        msg.payData = payParamsStr;
        UnifyPayPlugin.getInstance(context).setListener(unifyPayListener);
        UnifyPayPlugin.getInstance(context).sendPayRequest(msg);
    }

    public void doAppCallWeb(String payStatus) {
        if (lastH5JsonObject == null) {
            ZYToastUtils.showLongToast("AppCallWeb异常：木有WebCallApp的数据");
            return;
        }
        try {
            org.json.JSONObject paramArgsJsonObject = lastH5JsonObject.optJSONObject("args");
            paramArgsJsonObject.remove("payData");//删除多余的字段，因为h5解析会有问题
            paramArgsJsonObject.put("payStatus", payStatus);
            doAppCallWeb(null, null, lastH5JsonObject, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doAppCallWeb(String errorMessage, String responseJson, org.json.JSONObject h5ProtocalJsonObject, boolean isProcessingJson/* 是否要加工JSON格式，不加工则直接传h5ProtocalJsonObject */) {
        try {
            final String sn = h5ProtocalJsonObject.optString("sn");
            org.json.JSONObject result;
            if (!TextUtils.isEmpty(responseJson)) {
                result = new org.json.JSONObject(responseJson);
            } else {
                result = new org.json.JSONObject();
            }
            if (isProcessingJson) {
                h5ProtocalJsonObject.put("opFlag", TextUtils.isEmpty(errorMessage));
                h5ProtocalJsonObject.put("error", errorMessage);
                h5ProtocalJsonObject.put("serviceResult", result);
                h5ProtocalJsonObject.put("timestamp", System.currentTimeMillis());
            }
            Toast.makeText(requireActivity(), String.valueOf((isProcessingJson ? h5ProtocalJsonObject : result)), Toast.LENGTH_SHORT).show();
            PYappCallWeb("javascript:LonchJsApi.appCallWeb('" + sn + "','" + (isProcessingJson ? h5ProtocalJsonObject : result) + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void PYappCallWeb(final String responseJson) {
        // Android版本变量
        final int version = Build.VERSION.SDK_INT; //获取版本号 的 sdk int类型值
        if (version < 18) { //18 是4.4？
            webView.loadUrl(responseJson, null);//这里面传参
        } else {
            handler.post(() -> {
                if (null == webView) {
                    return;
                }
                webView.evaluateJavascript(responseJson, value -> {
                });
            });

        }
    }

    private void appPay(String sn, String type, String msg, String payType) {
        paySn = sn;
        Map<String, Object> map = new HashMap<>();
        if (type.equals("getDeviceInfo")) {
            if (!DeviceUtil.checkWeiXinInstalled(requireActivity())) {
                //没有安装微信
                map.put("isWXAppInstalled", false);
            } else {
                map.put("isWXAppInstalled", true);
            }
            if (!DeviceUtil.checkAliPayInstalled(requireActivity())) {
                //没有安装支付宝
                map.put("isAlipayAppInstalled", false);
            } else {
                map.put("isAlipayAppInstalled", true);
            }
            final ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
            apiResult.setServiceResult(map);
            appCallWeb(sn, toJson(apiResult));
        } else {
            isPay = true;
            if (payType.equals("5") || payType.equals("9")) {
                //微信
                BaseArgs<ArgsAppPayWeixin> weixinPay = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsAppPayWeixin>>() {
                });
                if (weixinPay.getArgs().getPath() == null) {
                    return;
                }
                goToOtherApp(weixinPay.getArgs());
            } else {
                //支付宝
                BaseArgs<ArgsAppPayZhiAi> alibaba = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsAppPayZhiAi>>() {
                });
                // chinaumsAliPay(requireActivity(), alibaba.getArgs().getData().getQrCodeUrl());

                goToAliPay(alibaba.getArgs().getData().getQrCodeUrl());
            }
        }
    }

    /**
     * 扫描二维码
     *
     * @param sn sn
     */
    private void scanZing(String sn) {
        requireActivity().runOnUiThread(() -> Objects.requireNonNull(MMKV.defaultMMKV()).encode("scanQRCode", webView.getUrl()));
        codeSn = sn;
        if (checkSinglePermission(Manifest.permission.CAMERA)) {
            //扫码
            Intent intent = new Intent(requireActivity(), MyZxingActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, AppConstants.FRAGMENT_WEB_CAMERA_REQ_PERMISSION_CODE);
        }
    }

    private void goToOtherApp(ArgsAppPayWeixin weixinPay) {
        IWXAPI api = WXAPIFactory.createWXAPI(requireActivity(), LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = weixinPay.getUserName(); // 填小程序原始id
        String path = weixinPay.getPath();
        if (path.contains("token")) {
            req.path = path;
        } else {
            req.path = path + "&token=" + SpUtils.get("token", "");
        }
        if (LonchCloudApplication.getAppConfigDataBean().SERVICE_URL.contains("test")) {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
        } else {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        }
        api.sendReq(req);
    }

    /**
     * 银联商务全民付
     */
    public void chinaumsAliPay(Context context, String payParamsStr) {
        if (context == null) {
            return;
        }
        if (!(context instanceof Activity)) {
            return;
        }
        UnifyPayPlugin payPlugin = UnifyPayPlugin.getInstance(requireActivity());
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY_MINI_PROGRAM;
        msg.payData = payParamsStr;
        payPlugin.setListener((resultCode, resultInfo) -> {
            isPay = false;
            if ("0000".equals(resultCode)) {
                //支付成功
                ApiResult<HashMap<String, String>> apiResult = new ApiResult<>();
                apiResult.setOpFlag(true);
                apiResult.setError("支付成功");
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(paySn, toJson(apiResult));
            } else {
                ApiResult<HashMap<String, String>> apiResult = new ApiResult<>();
                apiResult.setOpFlag(true);
                apiResult.setError("支付失败");
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(paySn, toJson(apiResult));
                //其他
            }
        });
        UnifyPayPlugin.getInstance(context).sendPayRequest(msg);
    }

    /**
     * 将Java对象employee序列化成为JSON格式
     *
     * @return
     */
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

    public void onRefreshUpdate(RefreshTokenBean bean) {
        String serviceResult = bean.getServiceResult();
        String[] split = serviceResult.split("\\.");
        byte[] decode = Base64.decode(split[1], Base64.DEFAULT);
        String str = new String(decode, StandardCharsets.UTF_8);
        Gson gson = new Gson();
        BaseSixFourBean fromJsBeanRefreshToken = gson.fromJson(str, BaseSixFourBean.class);
        SpUtils.put("token", serviceResult);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponseSuccess(final String sn, String response, boolean isUpdateToken) {
        appCallWeb(sn, response);
        if (isUpdateToken) {
            Gson gson = new Gson();
            RefreshTokenBean fromJsBeanRefreshToken = gson.fromJson(response, RefreshTokenBean.class);
            onRefreshUpdate(fromJsBeanRefreshToken);
        }
    }

    @SuppressLint("NewApi")
    private void appCallWeb(final String sn, String responseJson) {
        if (null == webView || TextUtils.isEmpty(sn) || TextUtils.isEmpty(responseJson)) {
            return;
        }
        String data = Utils.reportDataForProtocol(sn, responseJson);
        String jsonR = UrlUtil.getURLEncoderString(data);
        final String json = jsonR.replace("+", "%20");
        String[] strings = sn.split(",");
        String jsScript;
        if (strings.length > 1 || Utils.isAppCallWeb(sn)) {
            jsScript = "javascript:" + "LonchJsApi.appCallWebV2" + "('" + json + "')";
        } else {
            jsScript = "javascript:" + "LonchJsApi.appCallWeb" + "('" + sn + "','" + json + "')";
        }
        String finalJsScript = jsScript;
        handler.post(() -> {
            if (webView != null) {
                webView.evaluateJavascript(finalJsScript, value -> {
                });
            }
        });
    }

    @Override
    public void onResponseFailed(String sn, String fileMessage) {
        appCallWeb(sn, fileMessage);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("JavascriptInterface")
    private void initWebView(@NonNull View view) {
        main_layout = view.findViewById(R.id.main_layout);
        webView = new MyWebView(requireActivity());
        main_layout.removeAllViews();
        main_layout.addView(webView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        webView.addJavascriptInterface(new WebJsFunction((BaseWebActivity) requireActivity(), webView, this), "LonchJsApi");
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView,true);
       // CookieManager.getInstance().acceptThirdPartyCookies(webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.setVisibility(View.GONE);
                if (description.contains("ERR_CONNECTION")) {
                    LonchCloudApplication.startMyService();
                }
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.endsWith(".apk") || url.endsWith(".zip")) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                } else if (url.contains(".apk")) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                } else if (url.startsWith("alipay") || url.startsWith("alipays:")) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (WBH5FaceVerifySDK.getInstance().recordVideoForApi21(webView, filePathCallback, requireActivity(), fileChooserParams)) {
                    return true;
                }
                mUploadMessageForAndroid5 = filePathCallback;
                openImageChooserActivity();
                return true;
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                geolocationCallback = callback;
                orgin = origin;
                if (checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    callback.invoke(origin, true, false);
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.FRAGMENT_LOCATION_REQ_PERMISSION_CODE);
                }
            }
        });
        registerForContextMenu(webView);
    }

    private void openFileChooseForAndroid() {
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("*/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    public void openCamera() {
        if (!SDCardUtil.isSDCardEnableByEnvironment()) {
            Toast.makeText(requireActivity(), "请检查您的SD卡!", Toast.LENGTH_SHORT).show();
            if (null != mUploadMessageForAndroid5) {
                mUploadMessageForAndroid5.onReceiveValue(null);
                mUploadMessageForAndroid5 = null;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                File file = new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + "camera_photos.jpg");
                if (file.exists()) {
                    file.delete();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri imageUri = FileProvider
                        .getUriForFile(requireActivity(), LonchCloudApplication.getAppConfigDataBean().FILE_PROVIDER, file);//FileProvider.getUriForFile(mContext, getFileProviderName(mContext), file);//通过FileProvider创建一个content类型的Uri
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
                startActivityForResult(intent, FILECHOOSER_CAMERA_RESULTCODE_FOR_ANDROID_5);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 下面这句指定调用相机拍照后的照片存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsoluteFile() + File.separator + "camera_photos.jpg")));
                startActivityForResult(intent, FILECHOOSER_CAMERA_RESULTCODE_FOR_ANDROID_5);
            }
        }

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (webView != null) {
            WebView.HitTestResult hitTestResult = webView.getHitTestResult();
            if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE || hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                menu.setHeaderTitle("图片选项");
                menu.add(0, 1, 0, "保存到手机");
                imageUrl = hitTestResult.getExtra();
            }
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5) {
                return;
            }
            Uri result = (data == null || resultCode != Activity.RESULT_OK) ? null : data.getData();
            if (null != result) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(null);
            }
            mUploadMessageForAndroid5 = null;
        } else if (requestCode == FILECHOOSER_CAMERA_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            if (resultCode == Activity.RESULT_OK) {
                File mCameraFilePath = new File(
                        requireActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator
                                + "camera_photos.jpg");
                if (Build.VERSION.SDK_INT >= 24) {
                    Uri result = FileProvider
                            .getUriForFile(requireActivity(), LonchCloudApplication.getAppConfigDataBean().FILE_PROVIDER, mCameraFilePath);
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
                } else {
                    Uri uri = Uri.fromFile(mCameraFilePath);
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{uri});
                }
            } else {
                mUploadMessageForAndroid5.onReceiveValue(null);
            }
            mUploadMessageForAndroid5 = null;
        } else if (requestCode == REQUEST_CODE) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String pageUrl = Objects.requireNonNull(MMKV.defaultMMKV()).getString("scanQRCode", "");
                    if (!TextUtils.isEmpty(pageUrl) && pageUrl.equals(webView.getUrl())) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                        Map<String, Object> map = new HashMap<>();
                        map.put("type", "");
                        map.put("stringValue", result);
                        apiResult.setServiceResult(map);
                        String apiResultJson = toJson(apiResult);
                        appCallWeb(codeSn, apiResultJson);
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(requireActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getGroupId() == 0) {
            if (checkSinglePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                DownloadUtil.getInstance().downloadImage(requireActivity(), imageUrl);
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.FRAGMENT_FILE_REQ_PERMISSION_CODE);
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstants.FRAGMENT_STORE_REQ_PERMISSION_CODE: //选择相册
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFileChooseForAndroid();
                } else {
                    Toast.makeText(requireActivity(), "您拒绝了权限申请,无法上传附件!", Toast.LENGTH_LONG).show();
                    if (null != mUploadMessageForAndroid5) {
                        mUploadMessageForAndroid5.onReceiveValue(null);
                        mUploadMessageForAndroid5 = null;
                    }
                }
                break;
            case AppConstants.FRAGMENT_FILE_REQ_PERMISSION_CODE: // 存储权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DownloadUtil.getInstance().downloadImage(requireActivity(), imageUrl);
                } else {
                    Toast.makeText(requireActivity(), "您拒绝了权限申请,无法下载图片!", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstants.FRAGMENT_LOCATION_REQ_PERMISSION_CODE: // 定位权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (geolocationCallback != null) {
                        geolocationCallback.invoke(orgin, true, false);
                    }
                } else {
                    Toast.makeText(requireActivity(), "您拒绝了权限申请,无法获取您的位置!", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstants.FRAGMENT_AUDIO_REQ_PERMISSION_CODE: // 录音权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getOssToken(true);
                } else {
                    Toast.makeText(requireActivity(), "您拒绝了权限申请,无法语音识别!", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstants.FRAGMENT_CAMERA_REQ_PERMISSION_CODE: // 相机权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(requireActivity(), "您拒绝了权限申请,无法拍照!", Toast.LENGTH_LONG).show();
                    if (null != mUploadMessageForAndroid5) {
                        mUploadMessageForAndroid5.onReceiveValue(null);
                        mUploadMessageForAndroid5 = null;
                    }
                }
                break;
            case AppConstants.FRAGMENT_WEB_CAMERA_REQ_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //扫码
                    scanZing(codeSn);
                } else {
                    Toast.makeText(requireActivity(), "你拒绝了权限申请，无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            main_layout.removeAllViews();
            webView.destroy();
            webView = null;
        }
        if (jsDataModel != null) {//针对WEBAPPD5BA8E255563FFD14B2D5EA79363EB78流水号的socket is closed的优化
            jsDataModel.release();
        }
        super.onDestroy();

    }

    public boolean checkSinglePermission(String strings) {
        boolean isPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            isPermission = ContextCompat.checkSelfPermission(requireActivity(), strings) == PackageManager.PERMISSION_GRANTED;
        } else {
            isPermission = true;
        }
        return isPermission;
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                requestPermissions(permissions.toArray(new String[0]), AppConstants.FRAGMENT_AUDIO_REQ_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    //支付宝支付和所有支付非正常返回的callweb
    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
            webView.resumeTimers();
        }
        if (isPay) {
            if (onWxFlg) {
                onWxFlg = false;
                return;
            }
            handler.postDelayed(() -> {
                ApiResult<HashMap<String, String>> apiResult = new ApiResult<>();
                apiResult.setOpFlag(true);
                apiResult.setError("暂未获取到支付结果,正为您刷新页面...");
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(paySn, toJson(apiResult));
            }, 1000);
            isPay = false;
        }
    }

    @Override
    public void onNuiEventCallback(com.alibaba.idst.nui.Constants.NuiEvent event, int i, int i1, KwsResult kwsResult, AsrResult asrResult) {
        if (event == com.alibaba.idst.nui.Constants.NuiEvent.EVENT_ASR_RESULT) {//语音识别最终结果
            if (!TextUtils.isEmpty(asrResult.asrResult)) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(asrResult.asrResult);
                    org.json.JSONObject payload = jsonObject.getJSONObject("payload");
                    String result = payload.getString("result");
                    requireActivity().runOnUiThread(() -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("text", result);
                        appCallWeb(recordSn, Utils.setAppWebData(map));
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    appCallWeb(recordSn, Utils.setAppErrorData("数据解析失败"));
                }
            }
        } else if (event == com.alibaba.idst.nui.Constants.NuiEvent.EVENT_ASR_PARTIAL_RESULT) {//语音识别中间结果

        } else if (event == com.alibaba.idst.nui.Constants.NuiEvent.EVENT_ASR_ERROR) {
            requireActivity().runOnUiThread(() -> Toast.makeText(requireActivity(), "语音识别失败,请重试",
                    Toast.LENGTH_SHORT).show());

        }

    }

    @Override
    public int onNuiNeedAudioData(byte[] bytes, int i) {
        int ret;
        if (mAudioRecorder.getState() != AudioRecord.STATE_INITIALIZED) {
            return -1;
        }
        ret = mAudioRecorder.read(bytes, 0, i);
        return ret;
    }

    @Override
    public void onNuiAudioStateChanged(com.alibaba.idst.nui.Constants.AudioState audioState) {
        if (audioState == com.alibaba.idst.nui.Constants.AudioState.STATE_OPEN) {
            mAudioRecorder.startRecording();
        } else if (audioState == com.alibaba.idst.nui.Constants.AudioState.STATE_CLOSE) {
            mAudioRecorder.release();
        } else if (audioState == com.alibaba.idst.nui.Constants.AudioState.STATE_PAUSE) {
            mAudioRecorder.stop();
        }
    }

    @Override
    public void onNuiAudioRMSChanged(float v) {
    }

    @Override
    public void onNuiVprEventCallback(com.alibaba.idst.nui.Constants.NuiVprEvent nuiVprEvent) {
    }

    ///选择拍照还是相册
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void openImageChooserActivity() {
        new MaterialDialog.Builder(requireActivity())
                .items(new String[]{"拍照", "从相册选取"})
                .positiveText("取消")
                .onPositive((dialog, which) -> {
                    if (null != mUploadMessageForAndroid5) {
                        mUploadMessageForAndroid5.onReceiveValue(null);
                        mUploadMessageForAndroid5 = null;
                    }
                    dialog.dismiss();
                })
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .itemsCallback((dialog, itemView, position, text) -> {
                    if (position == 0) { // 拍照
                        if (Utils.isCameraCanUse(requireActivity())) {
                            if (checkSinglePermission(Manifest.permission.CAMERA)) {
                                openCamera();
                            } else {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, AppConstants.FRAGMENT_CAMERA_REQ_PERMISSION_CODE);
                            }
                        } else {
                            Toast.makeText(requireActivity(), "您的设备暂不支持拍照", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 1) { //从相册选取
                        if (checkSinglePermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            openFileChooseForAndroid();
                        } else {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.FRAGMENT_STORE_REQ_PERMISSION_CODE);
                        }
                    }
                }).show();
    }


}
