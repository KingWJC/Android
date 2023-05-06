package com.lonch.client.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.idst.nui.AsrResult;
import com.alibaba.idst.nui.CommonUtils;
import com.alibaba.idst.nui.Constants;
import com.alibaba.idst.nui.INativeNuiCallback;
import com.alibaba.idst.nui.KwsResult;
import com.alibaba.idst.nui.NativeNui;
import com.chinaums.pppay.unify.UnifyPayListener;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.google.gson.Gson;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.R;
import com.lonch.client.base.BaseWebActivity;
import com.lonch.client.bean.ApiResult;
import com.lonch.client.bean.AppLog;
import com.lonch.client.bean.ArgsConversationList;
import com.lonch.client.bean.ArgsSendCtcMsg;
import com.lonch.client.bean.BaseArgs;
import com.lonch.client.bean.BaseArgsV2;
import com.lonch.client.bean.FromJsBean;
import com.lonch.client.bean.ImLoginBean;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.bean.WebJsFunction;
import com.lonch.client.bean.argsbean.ArgsAppPay;
import com.lonch.client.bean.argsbean.ArgsAppPayWeixin;
import com.lonch.client.bean.argsbean.ArgsAppPayZhiAi;
import com.lonch.client.bean.argsbean.ArgsCreateGroupWithMemberList;
import com.lonch.client.bean.argsbean.ArgsGetCtcMsg;
import com.lonch.client.bean.argsbean.ArgsGroupHistoryMsg;
import com.lonch.client.bean.argsbean.ArgsGroupQuit;
import com.lonch.client.bean.argsbean.ArgsGroupsInfo;
import com.lonch.client.bean.argsbean.ArgsGroupsUserKick;
import com.lonch.client.bean.argsbean.ArgsGroupsUserTo;
import com.lonch.client.bean.argsbean.ArgsMsgRead;
import com.lonch.client.bean.argsbean.ArgsSendMsgGroup;
import com.lonch.client.bean.argsbean.ArgsUserInfoList;
import com.lonch.client.bean.event.AppCallWebEvent;
import com.lonch.client.bean.event.IpEvent;
import com.lonch.client.bean.event.PayWxEvent;
import com.lonch.client.bean.im.IMConversation;
import com.lonch.client.bean.im.IMGroupInfo;
import com.lonch.client.bean.im.IMGroupMemberInfo;
import com.lonch.client.bean.im.IMGroupMemberOperation;
import com.lonch.client.bean.im.IMMessage;
import com.lonch.client.bean.im.IMUserInfo;
import com.lonch.client.common.AppConfigInfo;
import com.lonch.client.common.AppConstants;
import com.lonch.client.common.CommParameter;
import com.lonch.client.common.ConstantValue;
import com.lonch.client.interfaces.JsDataContract;
import com.lonch.client.model.JsDataModel;
import com.lonch.client.oss.OssNui;
import com.lonch.client.pay.DeviceUtil;
import com.lonch.client.utils.DownloadUtil;
import com.lonch.client.utils.FileUtils;
import com.lonch.client.utils.LocalWebInfoUtil;
import com.lonch.client.utils.Logger;
import com.lonch.client.utils.OkHttpUtil;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.ToastUtils;
import com.lonch.client.utils.Utils;
import com.lonch.client.utils.ZYToastUtils;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationResult;
import com.tencent.imsdk.v2.V2TIMCreateGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMGroupInfo;
import com.tencent.imsdk.v2.V2TIMGroupInfoResult;
import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfoResult;
import com.tencent.imsdk.v2.V2TIMGroupMemberOperationResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageListGetOption;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通讯录和其他二级web页面
 */
@SuppressLint("HandlerLeak")
public class WebActivity extends BaseWebActivity implements JsDataContract.JsGetDataView, WebJsFunction.CallbackJsFun, INativeNuiCallback {

    private static final String TAG = "WebActivity";
    private String app_package_name;
    private String appid;
    private String url;
    private String type;
    private String query;
    private JsDataModel jsDataModel;
    private String topTitle;
    private boolean isTopBack;

    private String codeSn;

    public String paySn;
    private String fromMode;
    private LinearLayout main_layout;
    boolean isPay = false;
    boolean onWxFlg = false;
    private String imageUrl;
    private boolean isAnimated = false;
    private String recordSn = "";
    NativeNui nui_instance = new NativeNui();
    private AudioRecord mAudioRecorder;
    private boolean mInit = false;
    private V2TIMMessage lastMsg;
    private int mGrantedCount = 0;
    private String openSn = "";


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (null != myWebView) {
                if (myWebView.canGoBack()) {
                    myWebView.goBack(); //goBack()表示返回WebView的上一页面
                    return true;
                } else {
                    finish();
                }
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        RelativeLayout rlTop = findViewById(R.id.id_web_title_rl);
        ImageView ivBack = findViewById(R.id.id_web_title_back_iv);
        ivBack.setOnClickListener(v -> finish());
        TextView viewById = findViewById(R.id.id_web_title_tv);
        getIntentData();
        if (isTopBack) {
            rlTop.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(topTitle)) {
                viewById.setText(topTitle);
            }
        }
        main_layout = findViewById(R.id.main_layout);
        String token = (String) SpUtils.get("token", "");
        if (TextUtils.isEmpty(token)) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage("您的登录信息已失效,请退出重新登录")
                    .setPositiveButton("确定", (dialog, which) -> {
                        Utils.reStartLogin(WebActivity.this);
                        finish();
                    });
            builder1.show();
            return;
        }
        jsDataModel = new JsDataModel(this, token);
        initWebView();
        loadWebView();
        EventBus.getDefault().register(this);
        initKeyWordStatus();

        //语音识别参数
        HandlerThread mHandlerThread = new HandlerThread("process_thread");
        mHandlerThread.start();
        if (checkSinglePermission(Manifest.permission.RECORD_AUDIO) && checkSinglePermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            getOssToken(false);
        }
        ImLoginBean.ServiceResultBean serviceResultBean = SpUtils.getObject(CommParameter.SpImLoginInfo, ImLoginBean.ServiceResultBean.class);
        if (null != serviceResultBean) {
            if (V2TIMManager.getInstance().getLoginStatus() != V2TIMManager.V2TIM_STATUS_LOGINED) {
                initLoginChatSDK(serviceResultBean.getUserId(), serviceResultBean.getUserSig());
            }
        }
    }
    private void getIntentData() {
        if (null != getIntent()) {
            fromMode = getIntent().getStringExtra("from");
            if (TextUtils.isEmpty(fromMode)){
                finish();
                return;
            }
            if (fromMode.equals("cmdAppOpenUrl")) {
                url = getIntent().getStringExtra("url");
                topTitle = getIntent().getStringExtra("title");
                isTopBack = getIntent().getBooleanExtra("isBackTitleBar", false);
                isAnimated = getIntent().getBooleanExtra("animated", false);
                type = getIntent().getStringExtra("type");
            } else {
                appid = getIntent().getStringExtra("appid");
                url = getIntent().getStringExtra("url");
                query = getIntent().getStringExtra("query");
                type = getIntent().getStringExtra("type");
                isTopBack = getIntent().getBooleanExtra("isBackTitleBar", false);
                topTitle = getIntent().getStringExtra("topTitle");
            }
        }
    }

    private void loadWebView() {
        if (!TextUtils.isEmpty(fromMode)) {
            if (fromMode.equals("cmdAppOpenUrl")) {
                if (!TextUtils.isEmpty(url)) {
                    if (url.contains("www.lonch.com")) {
                        myWebView.loadUrl(url);
                        return;
                    }
                    myWebView.loadUrl(url);
                }
            } else {
                loadUrl();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!TextUtils.isEmpty(type) && type.equals("externalSystem")){
            EventBus.getDefault().post(new IpEvent("system"));
        }
        EventBus.getDefault().unregister(this);
        if (myWebView != null) {
            main_layout.removeAllViews();
            myWebView.destroy();
            myWebView = null;
        }
        if(jsDataModel != null) {//针对WEBAPPD5BA8E255563FFD14B2D5EA79363EB78流水号的socket is closed的优化
            jsDataModel.release();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        nui_instance.release();
    }

    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScanFinished(IpEvent event) {
//        if (!TextUtils.isEmpty(event.getMsg())) {
//            appCallWeb(openSn, Utils.setAppWebData(new HashMap<>()));
//        }
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
        //获取工作路径
        String asset_path = CommonUtils.getModelPath(this);
        Log.i(TAG, "use workspace " + asset_path);
        String debug_path = getCacheDir().getAbsolutePath() + "/debug_" + System.currentTimeMillis();
        FileUtils.createOrExistsDir(debug_path);
        //录音初始化，录音参数中格式只支持16bit/单通道，采样率支持8K/16K
        mAudioRecorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, LonchCloudApplication.getAppConfigDataBean().SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, LonchCloudApplication.getAppConfigDataBean().WAVE_FRAM_SIZE * 4);
        //这里主动调用完成SDK配置文件的拷贝
        if (CommonUtils.copyAssetsData(this)) {
            Log.i(TAG, "copy assets data done");
        } else {
            Log.i(TAG, "copy assets failed");
            AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(WebActivity.this);
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
            AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(WebActivity.this);
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
                AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(WebActivity.this);
                appLog.setErrCode(ret + "");
                appLog.setErrLevel("warn");
                appLog.setErrMsg("语音识别失败");
                appLog.setEventName("语音识别失败");
                OkHttpUtil.getInstance().sendPostRequest(LonchCloudApplication.getAppConfigDataBean().SERVICE_LOG_URL, appLog);
            }
        });
    }
    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAppCallWeb(AppCallWebEvent event) {
        appCallWeb(event.getSn(), event.getMsg());
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        main_layout.removeAllViews();
        main_layout.addView(myWebView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        myWebView.addJavascriptInterface(new WebJsFunction(this, myWebView, this), "LonchJsApi");
        registerForContextMenu(myWebView);
    }

    private void loadUrl() {
        //拿到当前页面要展示的软件id
        if (TextUtils.isEmpty(appid)) {
            return;
        }
        String ip = (String) SpUtils.get("ip", "");
        if (!TextUtils.isEmpty(ip)) {
            PlistPackageBean plistPackageBean = getName(appid);
            //本地bao存在
            if (null != plistPackageBean) {
                String PackageVerson = plistPackageBean.getVersion();
                String mRootUrl;
                if (plistPackageBean.isUsing_online_url()) {
                    String online = plistPackageBean.getWebapp_url();
                    if (!online.endsWith("/")) {
                        online += "/";
                    }
                    mRootUrl = online + url;
                    myWebView.loadUrl(mRootUrl);
                } else {
                    String filePath = getFilesDir().getAbsolutePath() + "/App/" + app_package_name + "/" + PackageVerson + "/index.html";
                    if (new File(filePath).exists()) {
                        String substring = url.substring(0, 1);
                        if (substring.equals("/")) {
                            if (TextUtils.isEmpty(query)) {
                                mRootUrl = "http://" + ip + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/" + app_package_name + "/" + PackageVerson + url;
                            } else {
                                mRootUrl = "http://" + ip + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/" + app_package_name + "/" + PackageVerson + url + "?" + query;
                            }
                        } else {
                            if (TextUtils.isEmpty(query)) {
                                mRootUrl = "http://" + ip + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/" + app_package_name + "/" + PackageVerson + "/" + url;
                            } else {
                                mRootUrl = "http://" + ip + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/" + app_package_name + "/" + PackageVerson + "/" + url + "?" + query;
                            }
                        }
                        myWebView.loadUrl(mRootUrl);
                    }
                }
            }
        }
    }


    public PlistPackageBean getName(String packId) {
        //如果有文件 找到对应配置文件，看是否网络url，否则接着判断包是否存在c:判断版本包/c配置文件更新b下载
        String datafromFile = FileUtils.getDatafromFile(packId);
        if (!TextUtils.isEmpty(datafromFile)) {
            Gson gson = new Gson();
            PlistPackageBean localappZipBean = gson.fromJson(datafromFile, PlistPackageBean.class);
            if (packId.equals(localappZipBean.getSoftware_id())) {//找到json
                app_package_name = localappZipBean.getApp_package_name();
                return localappZipBean;
            }
        }

        return null;
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
                ApiResult<HashMap<String,String>> apiResult = new ApiResult<>();
                apiResult.setOpFlag(event.isOpFlag());
                apiResult.setError(event.getMsg());
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(paySn, toJson(apiResult));
            }
        }
    }

    //支付宝支付和所有支付非正常返回的callweb
    @Override
    public void onResume() {
        super.onResume();
        if (myWebView != null) {
            myWebView.onResume();
            myWebView.resumeTimers();
        }
        if (isPay) {
            if (onWxFlg) {
                onWxFlg = false;
                return;
            }
            LonchCloudApplication.handler.postDelayed(() -> {
                ApiResult<HashMap<String,String>> apiResult = new ApiResult<>();
                apiResult.setOpFlag(true);
                apiResult.setError("暂未获取到支付结果,正为您刷新页面...");
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(paySn, toJson(apiResult));
            }, 1000);
            isPay = false;

        }
    }

    /**跳转支付宝支付
     * @param payUrl 支付url
     */
    private void goToAliPay(String payUrl){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(payUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(uri);
        startActivity(intent);
    }

    /**
     * 银联商务全民付
     */
    public void chinaumsAliPay(Context context, String payParamsStr) {
        if (context == null) {
            Log.e(TAG, "PayHelper.payAliPay() error: context is null.");
            return;
        }
        if (!(context instanceof Activity)) {
            Log.e(TAG, "PayHelper.payAliPay() error: context is not a Activity type.");
            return;
        }
        UnifyPayPlugin payPlugin = UnifyPayPlugin.getInstance(this);
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY_MINI_PROGRAM;
        msg.payData = payParamsStr;
        /*
         * 设置支付结果监听
         */
        payPlugin.setListener((resultCode, resultInfo) -> {
            isPay = false;
            /*
             * 根据返回的支付结果进行处理
             */
            if ("0000".equals(resultCode)) {
                //支付成功
                ApiResult<HashMap<String,String>> apiResult = new ApiResult<>();
                apiResult.setOpFlag(true);
                apiResult.setError("支付成功");
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(paySn, toJson(apiResult));
            } else {
                //其他
                ApiResult<HashMap<String,String>> apiResult = new ApiResult<>();
                apiResult.setOpFlag(true);
                apiResult.setError("支付失败");
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(paySn, toJson(apiResult));
            }
        });
        UnifyPayPlugin.getInstance(context).sendPayRequest(msg);

    }

    private void appPay(String sn, String type, String msg, String payType) {
        paySn = sn;
        Map<String, Object> map = new HashMap<>();
        if (type.equals("getDeviceInfo")) {
            if (!DeviceUtil.checkWeiXinInstalled(this)) {
                //没有安装微信
                map.put("isWXAppInstalled", false);
            } else {
                map.put("isWXAppInstalled", true);

            }
            if (!DeviceUtil.checkAliPayInstalled(this)) {
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
                    isPay = false;
                    return;
                }
                goToOtherApp(weixinPay.getArgs());
            } else {
                //支付宝
                BaseArgs<ArgsAppPayZhiAi> alibaba = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsAppPayZhiAi>>() {
                });
             //   chinaumsAliPay(this, alibaba.getArgs().getData().getQrCodeUrl());
               goToAliPay(alibaba.getArgs().getData().getQrCodeUrl());
            }
        }
    }

    private void goToOtherApp(ArgsAppPayWeixin weixinPay){
        IWXAPI api = WXAPIFactory.createWXAPI(WebActivity.this, LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = weixinPay.getUserName(); // 填小程序原始id
        String path = weixinPay.getPath();
        if (path.contains("token")){
            req.path = path;
        }else{
            req.path = path+"&token="+SpUtils.get("token","");
        }
        if (LonchCloudApplication.getAppConfigDataBean().SERVICE_URL.contains("test")){
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
        }else{
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE ;// 可选打开 开发版，体验版和正式版
        }
        api.sendReq(req);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (myWebView != null) {
            WebView.HitTestResult hitTestResult = myWebView.getHitTestResult();
            if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE || hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                menu.setHeaderTitle("图片选项");
                menu.add(0, 1, 0, "保存到手机");
                imageUrl = hitTestResult.getExtra();
            }
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getGroupId() == 0) {
            if (checkSinglePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                DownloadUtil.getInstance().downloadImage(this, imageUrl);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.ACTIVITY_FILE_REQ_PERMISSION_CODE);
            }
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void webCallAppV2(String msg) {
        BaseArgsV2 baseArgsV2 = JSON.parseObject(msg, BaseArgsV2.class);
        String sn = baseArgsV2.getSn();
        String command = baseArgsV2.getCommand();
        baseArgsV2.setSn(sn+","+command);
        if (command.equals("getAppProxyData")){
            jsDataModel.getJsDataV2(baseArgsV2);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void webCallApp(String msg) {
        Gson gson = new Gson();
        BaseArgs baseArgs = gson.fromJson(msg, BaseArgs.class);
        String command = baseArgs.getCommand();
        String sn = baseArgs.getSn();
        Log.d(TAG, "msg=" + msg);
        switch (command) {
            case "scanQRCode"://关闭页面
                scanZing(sn);
                break;
            case "appPay"://支付
                BaseArgs<ArgsAppPay> argsAppPayBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsAppPay>>() {
                });
                appPay(sn, argsAppPayBaseArgs.getArgs().getType(), msg, argsAppPayBaseArgs.getArgs().getPayType());
                break;
            case "getAppProxyData"://获取接口数据
                FromJsBean fromJsBeanAppProxyData = new Gson().fromJson(msg, FromJsBean.class);
                jsDataModel.getJsData(fromJsBeanAppProxyData);
                break;
            case "getConversationList"://获取会话列表
                BaseArgs<ArgsConversationList> fromJsBeanConverList = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsConversationList>>() {
                });
                ArgsConversationList args = fromJsBeanConverList.getArgs();
                getConversationListSdk(args.getNextSeq(), args.getCount(), sn);
                break;
            case "getUsersInfo"://获取网络用户数据数组（解决头像）
                BaseArgs<ArgsUserInfoList> listBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsUserInfoList>>() {
                });
                getUserInfoList(sn, listBaseArgs.getArgs().getUserIdList());
                break;
            case "getAppInfo"://获取接口数据,本地版本信息
                appCallWeb(sn, LocalWebInfoUtil.getLocalWebInfoAll());
                break;
            case "getC2CHistoryMessageList"://获取聊天记录、
                final BaseArgs<ArgsGetCtcMsg> fromJsBean = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsGetCtcMsg>>() {
                });
                String msgId1 = fromJsBean.getArgs().getLastMsg().getMsgId();
                if (TextUtils.isEmpty(msgId1)){
                    lastMsg = null;
                }
                getHistoryMessage(fromJsBean.getArgs().getUserId(),fromJsBean.getArgs().getCount(),"chat",sn);
                break;
            case "sendC2CTextMessage"://发送消息
                final BaseArgs<ArgsSendCtcMsg> sendCtcMsgBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsSendCtcMsg>>() {
                });
                senTextMsg(sendCtcMsgBaseArgs.getArgs().getText(), sendCtcMsgBaseArgs.getArgs().getUserId(), sn);
                break;
            case "getLocalUserInfo"://获取本地用户数据
                ImLoginBean.ServiceResultBean object = SpUtils.getObject(CommParameter.SpImLoginInfo, ImLoginBean.ServiceResultBean.class);
                //手机号
                ApiResult<Map<String, Object>> apiResultCurUser = new ApiResult<>();
                Map<String, Object> map = new HashMap<>();
                assert object != null;
                map.put("userId", object.getUserId());
                apiResultCurUser.setServiceResult(map);
                appCallWeb(sn, toJson(apiResultCurUser));
                break;
            case "createGroup"://建群
            case "createGroupWithMemberList"://建群指定成员
                BaseArgs<ArgsCreateGroupWithMemberList> groupWithMemberListBaseArgs1 = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsCreateGroupWithMemberList>>() {
                });
                createGroupList(groupWithMemberListBaseArgs1.getArgs(), sn);
                break;
            case "getGroupHistoryMessageList"://获取群聊历史
                BaseArgs<ArgsGroupHistoryMsg> groupHistoryMsgBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsGroupHistoryMsg>>() {
                });
                String msgId = groupHistoryMsgBaseArgs.getArgs().getLastMsg().getMsgId();
                if (TextUtils.isEmpty(msgId)){
                    lastMsg = null;
                }
                getHistoryMessage(groupHistoryMsgBaseArgs.getArgs().getGroupId(),groupHistoryMsgBaseArgs.getArgs().getCount(),"groupChat",sn);
                break;
            case "sendGroupTextMessage"://发送群聊
                BaseArgs<ArgsSendMsgGroup> sendMsgGroupBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsSendMsgGroup>>() {
                });
                senTextMsgGroup(sendMsgGroupBaseArgs.getArgs(), sn);
                break;
            case "getGroupsInfo"://获取群信息
                BaseArgs<ArgsGroupsInfo> groupsInfoBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsGroupsInfo>>() {
                });
                getGroupsInfo(groupsInfoBaseArgs.getArgs(), sn);
                break;
            case "getGroupMemberList"://获取群成员列表
                JSONObject jsonObject2 = JSON.parseObject(msg);
                getGroupMember(jsonObject2.getJSONObject("args"), sn);
                break;
            case "inviteUserToGroup"://邀请他人入群
                BaseArgs<ArgsGroupsUserTo> userToBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsGroupsUserTo>>() {
                });
                inviteUserToGroup(userToBaseArgs.getArgs(), sn);
                break;
            case "kickGroupMember"://踢人
                BaseArgs<ArgsGroupsUserKick> kickBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsGroupsUserKick>>() {
                });
                kickGroupMember(kickBaseArgs.getArgs(), sn);
                break;
            case "quitGroup"://退出群
                BaseArgs<ArgsGroupQuit> quitBaseArgs = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsGroupQuit>>() {
                });
                quitGroup(quitBaseArgs.getArgs(), sn);
                break;
            case "dismissGroup"://解散
                BaseArgs<ArgsGroupQuit> dismiss = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsGroupQuit>>() {
                });
                dismissQuitGroup(dismiss.getArgs(), sn);
                break;
            case "markC2CMessageAsRead"://消息已读
                BaseArgs<ArgsMsgRead> c2cMsgRead = JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsMsgRead>>() {
                });
                lastMsg = null;
                markC2CMessageAsRead(c2cMsgRead.getArgs().getUserId());//已读
                break;
            case "markGroupMessageAsRead":
                lastMsg = null;
                JSONObject jsonObject = JSON.parseObject(msg);
                markGroupMessageAsRead(jsonObject.getJSONObject("args").getString("groupId"));//已读
                break;
            case "pay"://普药商城支付
                pypay(sn,msg);
                break;
            case "cmdStartRecord": //开始录音
                runOnUiThread(() -> {
                    if (checkPermission()){
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
                JSONObject parseObject1 = JSON.parseObject(msg);
                JSONObject argsJSON1 = parseObject1.getJSONObject("args");
                String appType = argsJSON1.getString("type");
                if (!TextUtils.isEmpty(appType)){
                    if (appType.equals("wechatMiniProgram")){
                        ArgsAppPayWeixin argsAppPayWeixin = new ArgsAppPayWeixin();
                        argsAppPayWeixin.setPath(argsJSON1.getString("path"));
                        argsAppPayWeixin.setUserName(argsJSON1.getString("userName"));
                        goToOtherApp(argsAppPayWeixin);
                    }
                }
                break;
            case "deleteConversation":
                JSONObject parseObject2= JSON.parseObject(msg);
                JSONObject args1 = parseObject2.getJSONObject("args");
                String conversationId = args1.getString("conversationId");
                deleteConversation(sn,conversationId);
                break;
            case "setGroupInfo"://修改群资料
                JSONObject groupJson = JSON.parseObject(msg);
                JSONObject groupArgs = groupJson.getJSONObject("args");
                setGroupInfo(sn,groupArgs);
                break;
            case "deleteMessages":
                JSONObject deleteJson = JSON.parseObject(msg);
                JSONObject deleteJsonJSONObject = deleteJson.getJSONObject("args");
                JSONArray jsonArray = deleteJsonJSONObject.getJSONArray("messages");
                deleteMessage(sn,jsonArray);
                break;
            case "popView"://关闭页面
                runOnUiThread(() ->{
                    finish();
                    if (isAnimated){
                        overridePendingTransition(R.anim.anim_no, R.anim.pop_exit_anim);
                    }
                });
                break;
            default:
                appCallWeb(sn, Utils.setAppErrorData(getResources().getString(R.string.txt_h5_protocol_not_achieved)));
        }
    }

    @Override
    public void webCallSn(String sn) {
        if (!TextUtils.isEmpty(sn)){
            openSn = sn;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstants.ACTIVITY_FILE_REQ_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                DownloadUtil.getInstance().downloadImage(this, imageUrl);
            } else {
                Toast.makeText(this, "你拒绝了权限申请，无法下载图片!", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == AppConstants.ACTIVITY_AUDIO_REQ_PERMISSION_CODE) {
            for (int ret : grantResults) {
                if (PackageManager.PERMISSION_GRANTED == ret) {
                    mGrantedCount ++;
                }
            }
            if (mGrantedCount == permissions.length) {
                getOssToken(true);
            } else {
                Toast.makeText(this, "你拒绝了权限申请，无法语音识别!", Toast.LENGTH_SHORT).show();
            }
            mGrantedCount = 0;
        }
    }

    protected boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(WebActivity.this,
                        permissions.toArray(new String[0]),
                        AppConstants.ACTIVITY_AUDIO_REQ_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    /**
     * 获取user信息
     *
     * @param sn
     * @param list
     */
    public void getUserInfoList(final String sn, List<String> list) {
        V2TIMManager.getInstance().getUsersInfo(list, new V2TIMValueCallback<List<V2TIMUserFullInfo>>() {
            @Override
            public void onError(int code, String desc) {
                callWebError(code, desc, sn);
            }

            @Override
            public void onSuccess(List<V2TIMUserFullInfo> v2TIMUserFullInfos) {
                try {
                    List<IMUserInfo> imUserInfoList = new ArrayList<>();
                    for (V2TIMUserFullInfo v2TIMUserFullInfo: v2TIMUserFullInfos) {
                        String userData = JSON.toJSONString(v2TIMUserFullInfo);
                        IMUserInfo imUserInfo = JSON.parseObject(userData,IMUserInfo.class);
                        imUserInfoList.add(imUserInfo);
                    }
                    ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                    Map<String, Object> map = new HashMap<>();
                    map.put("list", imUserInfoList);
                    apiResult.setServiceResult(map);
                    appCallWeb(sn, toJson(apiResult));
                }catch (Exception e){
                    callWebError(400,"处理数据异常,请退出重试",sn);
                }

            }
        });
    }

    /**
     * 删除会话列表
     * @param sn
     * @param conversationId
     */
    private void deleteConversation(String sn,String conversationId){
        V2TIMManager.getConversationManager().deleteConversation(conversationId, new V2TIMCallback() {
            @Override
            public void onError(int i, String s) {
                callWebError(i, s, sn);
            }

            @Override
            public void onSuccess() {
                ApiResult<HashMap<String,String>> apiResult = new ApiResult<>();
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(sn, toJson(apiResult));
            }
        });
    }

    /***
     * 删除消息记录
     * @param sn
     * @param jsonArray
     */
    private void deleteMessage(String sn, JSONArray jsonArray){
        if (jsonArray == null || jsonArray.size() == 0){
            callWebError(500, "请选择要删除的消息", sn);
            return;
        }
        List<String> msgIdList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size() ; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String msgId = jsonObject.getString("msgId");
            msgIdList.add(msgId);
        }

        V2TIMManager.getMessageManager().findMessages(msgIdList, new V2TIMValueCallback<List<V2TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                callWebError(i, s, sn);
            }

            @Override
            public void onSuccess(List<V2TIMMessage> list) {
                V2TIMManager.getMessageManager().deleteMessages(list, new V2TIMCallback() {
                    @Override
                    public void onError(int i, String s) {
                        callWebError(i, s, sn);
                    }

                    @Override
                    public void onSuccess() {
                        ApiResult<HashMap<String,String>> apiResult = new ApiResult<>();
                        apiResult.setServiceResult(new HashMap<>());
                        appCallWeb(sn, toJson(apiResult));
                    }
                });
            }
        });

    }
    private void setGroupInfo(String sn,JSONObject groupInfo){
        if (null!=groupInfo){
            V2TIMGroupInfo v2TIMGroupInfo = new V2TIMGroupInfo();
            if (groupInfo.containsKey("groupId")){
                v2TIMGroupInfo.setGroupID(groupInfo.getString("groupId"));
            }
            if (groupInfo.containsKey("groupID")){
                v2TIMGroupInfo.setGroupID(groupInfo.getString("groupID"));
            }

            if (groupInfo.containsKey("groupName")){
                v2TIMGroupInfo.setGroupName(groupInfo.getString("groupName"));
            }
            if (groupInfo.containsKey("notification")){
                v2TIMGroupInfo.setNotification(groupInfo.getString("notification"));
            }
            if (groupInfo.containsKey("introduction")){
                v2TIMGroupInfo.setIntroduction(groupInfo.getString("introduction"));
            }
            if (groupInfo.containsKey("faceUrl")){
                v2TIMGroupInfo.setFaceUrl(groupInfo.getString("faceUrl"));
            }
            if (groupInfo.containsKey("allMuted")){
                v2TIMGroupInfo.setAllMuted(groupInfo.getBoolean("allMuted"));
            }
            V2TIMManager.getGroupManager().setGroupInfo(v2TIMGroupInfo, new V2TIMCallback() {
                @Override
                public void onError(int i, String s) {
                    callWebError(i, s, sn);
                }

                @Override
                public void onSuccess() {
                    ApiResult<HashMap<String,String>> apiResult = new ApiResult<>();
                    apiResult.setServiceResult(new HashMap<>());
                    appCallWeb(sn, toJson(apiResult));
                }
            });
        }

    }


    /**
     * 获取会话列表
     */
    private void getConversationListSdk(long nextSeq, int count, final String sn) {
        V2TIMManager.getConversationManager().getConversationList(nextSeq, count, new V2TIMValueCallback<V2TIMConversationResult>() {
            @Override
            public void onError(int code, String desc) {
                callWebError(code, desc, sn);
            }
            @Override
            public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                try {
                    List<IMConversation> imConversationList = new ArrayList<>();
                    for (int i = 0; i < v2TIMConversationResult.getConversationList().size(); i++) {
                        V2TIMConversation v2TIMConversation = v2TIMConversationResult.getConversationList().get(i);
                        if (null != v2TIMConversation.getGroupType() && v2TIMConversation.getGroupType().equals("AVChatRoom")){
                            continue;
                        }
                        String conversationData = JSON.toJSONString(v2TIMConversation);
                        IMConversation imConversation = JSON.parseObject(conversationData,IMConversation.class);
                        imConversationList.add(imConversation);
                    }
                    // 拉取成功，更新 UI 会话列表
                    ApiResult<HashMap<String,Object>> apiResult = new ApiResult<>();
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("list",imConversationList);
                    map.put("nextSeq",v2TIMConversationResult.getNextSeq());
                    map.put("isFinished",v2TIMConversationResult.isFinished());
                    apiResult.setServiceResult(map);
                    appCallWeb(sn, toJson(apiResult));
                }catch (Exception ignored){
                    Log.i(TAG,ignored.getMessage());
                    callWebError(400,"处理数据异常,请退出重试",sn);
                }

            }
        });
    }

    @Override
    public void onResponseSuccess(final String sn, String response, boolean isUpdateToken) {
        appCallWeb(sn,response);
    }

    @Override
    public void onResponseFailed(String sn, String fileMessage) {
        appCallWeb(sn, fileMessage);
    }

    /**
     * 所有失败信息
     *
     * @param code
     * @param desc
     * @param sn
     */
    private void callWebError(int code, String desc, String sn) {
        final ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
        Map<String, Object> map = new HashMap<>();
        map.put("desc", desc);
        map.put("code", code);
        apiResult.setOpFlag(false);
        apiResult.setError(desc);
        apiResult.setServiceResult(map);
        appCallWeb(sn, toJson(apiResult));
    }
    /**
     * 当前用户发起建群()
     */
    private void createGroupList(ArgsCreateGroupWithMemberList groupWithMemberList, final String sn) {
        // 示例代码：使用高级版 createGroup 创建一个工作群
        V2TIMGroupInfo v2TIMGroupInfo = new V2TIMGroupInfo();
        v2TIMGroupInfo.setGroupName(groupWithMemberList.getInfo().getGroupName());
        v2TIMGroupInfo.setGroupType(groupWithMemberList.getInfo().getGroupType());
        v2TIMGroupInfo.setFaceUrl(groupWithMemberList.getInfo().getFaceUrl());
        v2TIMGroupInfo.setIntroduction(groupWithMemberList.getInfo().getIntroduction());
        v2TIMGroupInfo.setGroupID(groupWithMemberList.getInfo().getGroupId());

        List<V2TIMCreateGroupMemberInfo> memberInfoList = new ArrayList<>();
        for (int i = 0; i < groupWithMemberList.getMemberList().size(); i++) {
            ArgsCreateGroupWithMemberList.MemberListBean memberListBean = groupWithMemberList.getMemberList().get(i);
            V2TIMCreateGroupMemberInfo member = new V2TIMCreateGroupMemberInfo();
            member.setUserID(memberListBean.getUserId());
            if (memberListBean.getRole() == 400) {
                member.setRole(200);
            } else {
                member.setRole(memberListBean.getRole());
            }
            memberInfoList.add(member);
        }

        V2TIMManager.getGroupManager().createGroup(
                v2TIMGroupInfo, memberInfoList, new V2TIMValueCallback<String>() {
                    @Override
                    public void onError(int code, String desc) {
                        // 创建失败
                        callWebError(code, desc, sn);
                    }

                    @Override
                    public void onSuccess(String groupID) {
                        // 创建成功
                        final ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                        Map<String, Object> map = new HashMap<>();
                        map.put("groupId", groupID);
                        apiResult.setServiceResult(map);
                        appCallWeb(sn, toJson(apiResult));
                    }
                });
    }

    private void getHistoryMessage(String id,int count,String type,String sn){
        V2TIMMessageListGetOption v2TIMMessageListGetOption = new V2TIMMessageListGetOption();
        v2TIMMessageListGetOption.setCount(count);
        v2TIMMessageListGetOption.setGetType(V2TIMMessageListGetOption.V2TIM_GET_CLOUD_OLDER_MSG);
        v2TIMMessageListGetOption.setLastMsg(lastMsg);
        if (type.equals("chat")){
            v2TIMMessageListGetOption.setUserID(id);
        }else{
            v2TIMMessageListGetOption.setGroupID(id);
        }
        V2TIMManager.getMessageManager().getHistoryMessageList(v2TIMMessageListGetOption, new V2TIMValueCallback<List<V2TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                callWebError(i, s, sn);
            }

            @Override
            public void onSuccess(List<V2TIMMessage> v2TIMMessages) {
                try {
                    List<IMMessage> imMessageList = new ArrayList<>();
                    for (V2TIMMessage v2TIMMessage:v2TIMMessages) {
                        String data = JSON.toJSONString(v2TIMMessage);
                        IMMessage imMessage = JSON.parseObject(data,IMMessage.class);
                        imMessageList.add(imMessage);
                    }
                    ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                    Map<String, Object> map = new HashMap<>();
                    map.put("list", imMessageList);
                    apiResult.setServiceResult(map);
                    appCallWeb(sn, toJson(apiResult));
                    if (v2TIMMessages.size()>0){
                        lastMsg = v2TIMMessages.get(v2TIMMessages.size()-1);
                    }
                }catch (Exception e){
                    callWebError(400,"处理数据异常,请退出重试",sn);
                }

            }
        });
    }
    /**
     * 发送群消息
     */
    private void senTextMsgGroup(ArgsSendMsgGroup argsSendMsgGroup, final String sn) {
        V2TIMManager.getInstance().sendGroupTextMessage(argsSendMsgGroup.getText(), argsSendMsgGroup.getGroupId(), V2TIMMessage.V2TIM_PRIORITY_NORMAL, new V2TIMValueCallback<V2TIMMessage>() {
            @Override
            public void onError(int code, String desc) {
                callWebError(code, desc, sn);
            }

            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                // 发送文字消息成功
                ApiResult<HashMap<String,Object>> apiResult = new ApiResult<>();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("msgId",v2TIMMessage.getMsgID());
                HashMap<String,Object> info = new HashMap<>();
                info.put("nickName",v2TIMMessage.getNickName());
                info.put("userId",v2TIMMessage.getUserID());
                info.put("faceUrl",v2TIMMessage.getFaceUrl());
                hashMap.put("info",info);
                apiResult.setServiceResult(hashMap);
                appCallWeb(sn, toJson(apiResult));
            }
        });
    }

    private void getGroupsInfo(ArgsGroupsInfo args, final String sn) {
        V2TIMManager.getGroupManager().getGroupsInfo(args.getGroupIdList(), new V2TIMValueCallback<List<V2TIMGroupInfoResult>>() {
            @Override
            public void onError(int code, String desc) {
                callWebError(code, desc, sn);
            }

            @Override
            public void onSuccess(List<V2TIMGroupInfoResult> v2TIMGroupInfoResults) {
                try {
                    List<IMGroupInfo> imGroupInfoList = new ArrayList<>();
                    for (V2TIMGroupInfoResult v2TIMGroupInfoResult:v2TIMGroupInfoResults) {
                        String groupInfoData = JSON.toJSONString(v2TIMGroupInfoResult);
                        IMGroupInfo imGroupInfo = JSON.parseObject(groupInfoData,IMGroupInfo.class);
                        imGroupInfoList.add(imGroupInfo);
                    }
                    ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                    Map<String, Object> map = new HashMap<>();
                    map.put("list", imGroupInfoList);
                    apiResult.setServiceResult(map);
                    appCallWeb(sn, toJson(apiResult));
                }catch (Exception e){callWebError(400,"处理数据异常,请退出重试",sn);}

            }
        });

    }

    private void getGroupMember(JSONObject  args, final String sn) {
        if (null!=args){
            String groupId= "";
            if (args.containsKey("groupId")){
                groupId = args.getString("groupId");
            }
            if (args.containsKey("groupID")){
                groupId = args.getString("groupID");
            }
            int type = V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_ALL;
            int seq = args.getIntValue("nextSeq");
            if (args.containsKey("filter")){
                switch (args.getIntValue("filter")){
                    case 0:
                        type = V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_ALL;
                        break;
                    case 1:
                        type = V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_OWNER;
                        break;
                    case 2:
                        type = V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_ADMIN;
                        break;
                    case 4:
                        type = V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_COMMON;
                        break;
                }
            }

            V2TIMManager.getGroupManager().getGroupMemberList(groupId, type, seq, new V2TIMValueCallback<V2TIMGroupMemberInfoResult>() {
                @Override
                public void onError(int code, String desc) {
                    callWebError(code, desc, sn);
                }

                @Override
                public void onSuccess(V2TIMGroupMemberInfoResult v2TIMGroupMemberInfoResult) {
                    try {
                        List<IMGroupMemberInfo> imGroupMemberInfoList = new ArrayList<>();
                        List<V2TIMGroupMemberFullInfo> v2TIMGroupMemberFullInfoList = v2TIMGroupMemberInfoResult.getMemberInfoList();
                        for (V2TIMGroupMemberFullInfo v2TIMGroupMemberFullInfo: v2TIMGroupMemberFullInfoList) {
                            String groupMemberData = JSON.toJSONString(v2TIMGroupMemberFullInfo);
                            IMGroupMemberInfo imGroupMemberInfo = JSON.parseObject(groupMemberData,IMGroupMemberInfo.class);
                            imGroupMemberInfoList.add(imGroupMemberInfo);
                        }

                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("list",imGroupMemberInfoList);
                        hashMap.put("nextSeq",v2TIMGroupMemberInfoResult.getNextSeq());
                        ApiResult<HashMap<String,Object>> apiResult = new ApiResult<>();
                        apiResult.setServiceResult(hashMap);
                        appCallWeb(sn, toJson(apiResult));
                    }catch (Exception e){
                        callWebError(400,"处理数据异常,请退出重试",sn);
                    }

                }
            });
        }
    }

    private void inviteUserToGroup(ArgsGroupsUserTo args, final String sn) {
        V2TIMManager.getGroupManager().inviteUserToGroup(args.getGroupId(), args.getUserList(), new V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>>() {
            @Override
            public void onError(int code, String desc) {
                callWebError(code, desc, sn);
            }

            @Override
            public void onSuccess(List<V2TIMGroupMemberOperationResult> v2TIMGroupMemberOperationResults) {
                try {
                    List<IMGroupMemberOperation> imGroupMemberOperationList = new ArrayList<>();
                    for (V2TIMGroupMemberOperationResult v2TIMGroupMemberOperationResult:v2TIMGroupMemberOperationResults) {
                        String data = JSON.toJSONString(v2TIMGroupMemberOperationResult);
                        IMGroupMemberOperation imGroupMemberOperation = JSON.parseObject(data,IMGroupMemberOperation.class);
                        imGroupMemberOperationList.add(imGroupMemberOperation);
                    }
                    ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                    Map<String, Object> map = new HashMap<>();
                    map.put("list", v2TIMGroupMemberOperationResults);
                    apiResult.setServiceResult(map);
                    appCallWeb(sn, toJson(apiResult));
                }catch (Exception e){callWebError(400,"处理数据异常,请退出重试",sn);}

            }
        });
    }

    private void kickGroupMember(ArgsGroupsUserKick args, final String sn) {
        V2TIMManager.getGroupManager().kickGroupMember(args.getGroupId(), args.getMemberList(), args.getReason(), new V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>>() {
            @Override
            public void onError(int code, String desc) {
                callWebError(code, desc, sn);
            }

            @Override
            public void onSuccess(List<V2TIMGroupMemberOperationResult> v2TIMGroupMemberOperationResults) {
                try {
                    List<IMGroupMemberOperation> imGroupMemberOperationList = new ArrayList<>();
                    for (V2TIMGroupMemberOperationResult v2TIMGroupMemberOperationResult:v2TIMGroupMemberOperationResults) {
                        String data = JSON.toJSONString(v2TIMGroupMemberOperationResult);
                        IMGroupMemberOperation imGroupMemberOperation = JSON.parseObject(data,IMGroupMemberOperation.class);
                        imGroupMemberOperationList.add(imGroupMemberOperation);
                    }
                    ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                    Map<String, Object> map = new HashMap<>();
                    map.put("list", v2TIMGroupMemberOperationResults);
                    apiResult.setServiceResult(map);
                    appCallWeb(sn, toJson(apiResult));
                }catch (Exception e){callWebError(400,"处理数据异常,请退出重试",sn);}
            }
        });
    }

    private void quitGroup(ArgsGroupQuit args, final String sn) {
        V2TIMManager.getInstance().quitGroup(args.getGroupId(), new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                callWebError(code, desc, sn);
            }

            @Override
            public void onSuccess() {
                String groupId = args.getGroupId();
                if (!groupId.contains("group")){
                    groupId = String.format("group_%s", groupId);
                }

                V2TIMManager.getConversationManager().deleteConversation(groupId, new V2TIMCallback() {
                    @Override
                    public void onError(int code, String desc) {
                        callWebError(code, desc, sn);
                    }

                    @Override
                    public void onSuccess() {
                        ApiResult<HashMap<String,String>> apiResult = new ApiResult<>();
                        apiResult.setServiceResult(new HashMap<>());
                        appCallWeb(sn, toJson(apiResult));
                    }
                });
            }
        });
    }

    private void dismissQuitGroup(ArgsGroupQuit args, final String sn) {
        V2TIMManager.getInstance().dismissGroup(args.getGroupId(), new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                callWebError(code, desc, sn);
            }

            @Override
            public void onSuccess() {
                ApiResult<HashMap<String,String>> apiResult = new ApiResult<>();
                apiResult.setServiceResult(new HashMap<>());
                appCallWeb(sn, toJson(apiResult));
            }
        });
    }


    /**
     * 发送文本消息
     */
    private void senTextMsg(final String content, String userId, final String sn) {
        V2TIMManager.getInstance().sendC2CTextMessage(content, userId, new V2TIMValueCallback<V2TIMMessage>() {
            @Override
            public void onError(int code, String desc) {
                callWebError(code, desc, sn);
            }

            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                // 发送文字消息成功
                ApiResult<HashMap<String,Object>> apiResult = new ApiResult<>();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("msgId",v2TIMMessage.getMsgID());
                apiResult.setServiceResult(hashMap);
                appCallWeb(sn, toJson(apiResult));
            }
        });
    }
    private void markC2CMessageAsRead(String userId) {
        if (TextUtils.isEmpty(userId)){
            return;
        }
        //将来自 haven 的消息均标记为已读
        V2TIMManager.getMessageManager().markC2CMessageAsRead(userId, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                // 设置消息已读失败
            }

            @Override
            public void onSuccess() {
                // 设置消息已读成功
            }
        });
    }
    private void markGroupMessageAsRead(String groupId) {
        //将来自 haven 的消息均标记为已读
        if (TextUtils.isEmpty(groupId)){
            return;
        }
        V2TIMManager.getMessageManager().markGroupMessageAsRead(groupId, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                // 设置消息已读失败
            }

            @Override
            public void onSuccess() {
                // 设置消息已读成功
            }
        });
    }
    /////////////////////普药商城

    private org.json.JSONObject lastH5JsonObject;
    public static final int SHOP_WEIXIN_PAY = 1;
    public static final int SHOP_ALI_PAY = 2;

    private void pypay(String sn,final String jsonStr) {
        paySn = sn;
        isPay = true;
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonStr);
            lastH5JsonObject = jsonObject;
            org.json.JSONObject paramArgsJsonObject = jsonObject.optJSONObject("args");
            String paramJsonStr = null;
            org.json.JSONObject paramJsonObject = null;
            if (paramArgsJsonObject != null) {
                paramJsonStr = paramArgsJsonObject.optString("parameters");
                if (!TextUtils.isEmpty(paramJsonStr))
                    paramJsonObject = new org.json.JSONObject(paramJsonStr);
            }
            String command = jsonObject.optString("command");
            if (!TextUtils.isEmpty(command)) {
                if (AppConstants.WebViewCommandProtocalName.SHOP_PAY.equals(command)) {
                    pay(paramArgsJsonObject);
                } else if (AppConstants.WebViewCommandProtocalName.GET_DATA.equals(command)) {
                } else if (AppConstants.WebViewCommandProtocalName.OPEN_MINI_PROGRAM.equals(command)) {
                    jumpToWXProgram((String) paramArgsJsonObject.opt("appId"), (String) paramArgsJsonObject.opt("path"));
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
        IWXAPI api = WXAPIFactory.createWXAPI(this, LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
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
        IWXAPI api = WXAPIFactory.createWXAPI(this, LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = AppConfigInfo.getInstance().isDebug() ? ConstantValue.WX_SHOP_PAY_DEBUG_APPID : ConstantValue.WX_SHOP_PAY_RELEASE_APPID; // 填小程序原始id
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
                if (!DeviceUtil.checkWeiXinInstalled(this)) {
                    //没有安装微信
                    try {
                        paramArgsJsonObject.put("noPayWay", true);
                        doAppCallWeb(this.getResources().getString(R.string.error_activity_wx_no_install), null, lastH5JsonObject, true);
                    } catch (JSONException e) {
                    }
                    return;
                }
                org.json.JSONObject payDataJsonObject = jsonObject.optJSONObject("payData");
                if (payDataJsonObject == null) {
                    ZYToastUtils.showLongToast("error: payData is null~~");
                    return;
                }
                jumpToShopWXPayProgram(paramArgsJsonObject.toString());

                break;
            case SHOP_ALI_PAY://跳全民支付到支付宝
                if (!DeviceUtil.checkAliPayInstalled(this)) {
                    //没有安装微信
                    paramArgsJsonObject.remove("payData");
                    try {
                        paramArgsJsonObject.put("noPayWay", true);
                        doAppCallWeb(this.getResources().getString(R.string.error_activity_alipay_no_install), null, lastH5JsonObject, true);
                    } catch (JSONException e) {}
                    return;
                }
                String payData = jsonObject.optString("payData");
                if (TextUtils.isEmpty(payData)) {
                    return;
                }
                chinaumsAliPayPySc(this, payData, (s, s1) -> Logger.i("payAliPay callback(): s:" + s + "----->s1:" + s1));
               // goToAliPay(payData);
                break;
        }
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
            Toast.makeText(this, String.valueOf((isProcessingJson ? h5ProtocalJsonObject : result)), Toast.LENGTH_SHORT).show();
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
            myWebView.loadUrl(responseJson, null);//这里面传参
        } else {
            LonchCloudApplication.handler.post(() -> {
                if (null == myWebView) {
                    return;
                }
                myWebView.evaluateJavascript(responseJson, value -> {});
            });

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
                    runOnUiThread(() -> {
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
            runOnUiThread(() -> Toast.makeText(WebActivity.this, "语音识别失败,请重试",
                    Toast.LENGTH_SHORT).show());

        }

    }

    @Override
    public int onNuiNeedAudioData(byte[] bytes, int i) {
        int ret = 0;
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

    private void initLoginChatSDK(String userId, String userSig) {
        V2TIMManager.getInstance().login(userId, userSig, new V2TIMCallback() {
            @Override
            public void onError(final int code, final String desc) {
                runOnUiThread(() -> ToastUtils.showLongText("聊天登录失败"));
            }

            @Override
            public void onSuccess() {}
        });

    }


}