package com.lonch.client.base;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.OSS;
import com.billy.cc.core.component.CC;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ZipUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.activity.MyZxingActivity;
import com.lonch.client.bean.ApiResult;
import com.lonch.client.bean.NetLinkBean;
import com.lonch.client.bean.StsToken;
import com.lonch.client.common.AppConstants;
import com.lonch.client.common.CommParameter;
import com.lonch.client.database.bean.ApiResponseEntity;
import com.lonch.client.database.bean.LogEntity;
import com.lonch.client.database.bean.NetLinkEntity;
import com.lonch.client.database.bean.NetLinkFail;
import com.lonch.client.database.bean.SmallVideoEntity;
import com.lonch.client.database.tabutils.ApiResponseUtils;
import com.lonch.client.database.tabutils.SmallVideoUtils;
import com.lonch.client.manager.CheckNetManger;
import com.lonch.client.manager.WBH5FaceVerifySDK;
import com.lonch.client.oss.Config;
import com.lonch.client.oss.OssClient;
import com.lonch.client.oss.OssService;
import com.lonch.client.service.NetBroadcastReceiver;
import com.lonch.client.utils.FileUtils;
import com.lonch.client.utils.HeaderUtils;
import com.lonch.client.utils.KeyboardUtil;
import com.lonch.client.utils.LogUtils;
import com.lonch.client.utils.NetLinkUtils;
import com.lonch.client.utils.OkHttpUtil;
import com.lonch.client.utils.RSAUtil;
import com.lonch.client.utils.SDCardUtil;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.SystemUtil;
import com.lonch.client.utils.UrlUtil;
import com.lonch.client.utils.Utils;
import com.lonch.client.view.MyWebView;
import com.tencent.mmkv.MMKV;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class BaseWebActivity extends BaseActivity  {
    private static final String TAG = "BaseWebActivity";
    protected MyWebView myWebView;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private GeolocationPermissions.Callback geolocationCallback;
    private String origin;
    private final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 200;
    private final static int FILECHOOSER_CAMERA_RESULTCODE_FOR_ANDROID_5 = 300;
    private final static int REQUEST_CODE = 301;
    private NetBroadcastReceiver netBroadcastReceiver;
    private String codeSn;//扫描二维码sn
    private String netFileDate, filePath;
    private MyHandler handler = new MyHandler(this);

    final static class MyHandler extends Handler {
        WeakReference<BaseWebActivity> baseWebActivityWeakReference;

        public MyHandler(BaseWebActivity baseWebActivity) {
            this.baseWebActivityWeakReference = new WeakReference<>(baseWebActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseWebActivity activity = baseWebActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                if (msg.what == 100001) {
                    File file = new File(activity.filePath);
                    if (file.exists()) {
                        activity.initOSS(file, "log");
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseWebView();
        netBroadcastReceiver = new NetBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netBroadcastReceiver, filter);
        netBroadcastReceiver.setNetEvent(netMobile -> {
            int status = 0;
            if (netMobile>-1){
                status = 1;
            }
            ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
            Map<String, Object> map = new HashMap<>();
            map.put("status", status);
            apiResult.setServiceResult(map);
            String apiResultJson = toJson(apiResult);
            appCallWeb("netStatusChanged",apiResultJson);
        });

    }
    private void initBaseWebView(){
        myWebView = new MyWebView(this);
        myWebView.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (description.contains("ERR_CONNECTION")) {
                    LonchCloudApplication.startMyService();
                    handler.postDelayed(()->view.reload(),250);
                }
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
                }else if (url.startsWith("alipay") || url.startsWith("alipays:")){
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
               // printOffice();
            }
        });
        myWebView.setWebChromeClient(new WebChromeClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if(WBH5FaceVerifySDK.getInstance().recordVideoForApi21(webView, filePathCallback, BaseWebActivity.this, fileChooserParams)){
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
                BaseWebActivity.this.origin = origin;
                if (checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION)){
                    callback.invoke(origin,true,false);
                }else{
                    ActivityCompat.requestPermissions(BaseWebActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.ACTIVITY_LOCATION_REQ_PERMISSION_CODE);
                }
            }
        });

    }
    private void  printOffice(){
        PrintManager printManager = (PrintManager) BaseWebActivity.this.getSystemService(Context.PRINT_SERVICE);
        String jobName = "测试打印";
        PrintDocumentAdapter printDocumentAdapter = myWebView.createPrintDocumentAdapter();
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        printManager.print(jobName,printDocumentAdapter,builder.build());
    }

    private void openFileChooseImplyForAndroid() {
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("*/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }
    public  void openCamera() {
        if (!SDCardUtil.isSDCardEnableByEnvironment()){
            Toast.makeText(this, "请检查您的SD卡!", Toast.LENGTH_SHORT).show();
            if (null != mUploadMessageForAndroid5) {
                mUploadMessageForAndroid5.onReceiveValue(null);
                mUploadMessageForAndroid5 = null;
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                File file  = new File(BaseWebActivity.this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsoluteFile()+ File.separator +"camera_photos.jpg");
                if(file.exists()){
                    file.delete();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Uri imageUri = FileProvider
                            .getUriForFile(BaseWebActivity.this, LonchCloudApplication.getAppConfigDataBean().FILE_PROVIDER, file);//FileProvider.getUriForFile(mContext, getFileProviderName(mContext), file);//通过FileProvider创建一个content类型的Uri
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
                    startActivityForResult(intent, FILECHOOSER_CAMERA_RESULTCODE_FOR_ANDROID_5);
                }catch (Exception e){
                    Toast.makeText(BaseWebActivity.this,"您的设备暂不支持拍照",Toast.LENGTH_LONG).show();
                }
            } else {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 下面这句指定调用相机拍照后的照片存储的路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(BaseWebActivity.this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsoluteFile()+ File.separator +"camera_photos.jpg")));
                    startActivityForResult(intent, FILECHOOSER_CAMERA_RESULTCODE_FOR_ANDROID_5);
                }catch (Exception e){
                    Toast.makeText(BaseWebActivity.this,"您的设备暂不支持拍照",Toast.LENGTH_LONG).show();
                }

            }
        }
    }
    ///选择拍照还是相册
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void openImageChooserActivity() {
        new MaterialDialog.Builder(this)
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
                        if (Utils.isCameraCanUse(BaseWebActivity.this)){
                            if (checkSinglePermission(Manifest.permission.CAMERA)){
                                openCamera();
                            }else{
                                ActivityCompat.requestPermissions(BaseWebActivity.this, new String[]{Manifest.permission.CAMERA}, AppConstants.ACTIVITY_CAMERA_REQ_PERMISSION_CODE);
                            }
                        }else{
                            Toast.makeText(BaseWebActivity.this,"您的设备暂不支持拍照",Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 1) { //从相册选取
                        if (checkSinglePermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                            openFileChooseImplyForAndroid();
                        }else{
                            ActivityCompat.requestPermissions(BaseWebActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.ACTIVITY_STORE_REQ_PERMISSION_CODE);
                        }
                    }
                }).show();
    }

    /**
     * 扫描二维码
     * @param sn sn
     */
    public void scanZing(String sn) {
        codeSn = sn;
        if (checkSinglePermission(Manifest.permission.CAMERA)) {
            //扫码
            Intent intent = new Intent(this, MyZxingActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, AppConstants.ACTIVITY_WEB_CAMERA_REQ_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions,  @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstants.ACTIVITY_STORE_REQ_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFileChooseImplyForAndroid();
                } else {
                    if (null != mUploadMessageForAndroid5) {
                        mUploadMessageForAndroid5.onReceiveValue(null);
                        mUploadMessageForAndroid5 = null;
                    }
                    Toast.makeText(this, "你拒绝了权限申请，无法上传附件!", Toast.LENGTH_SHORT).show();
                }
                break;
            case AppConstants.ACTIVITY_LOCATION_REQ_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (geolocationCallback!=null){
                        geolocationCallback.invoke(origin,true,false);
                    }
                } else {
                    Toast.makeText(this, "您拒绝了权限申请，无法获取您的定位!", Toast.LENGTH_SHORT).show();
                }
                break;
            case AppConstants.ACTIVITY_CAMERA_REQ_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    if (null != mUploadMessageForAndroid5) {
                        mUploadMessageForAndroid5.onReceiveValue(null);
                        mUploadMessageForAndroid5 = null;
                    }
                    Toast.makeText(this, "您拒绝了权限申请，无法拍照!", Toast.LENGTH_SHORT).show();
                }
                break;
            case AppConstants.ACTIVITY_WEB_CAMERA_REQ_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //扫码
                    scanZing(codeSn);
                } else {
                    Toast.makeText(this, "您拒绝了权限申请，无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (WBH5FaceVerifySDK.getInstance().receiveH5FaceVerifyResult(requestCode,resultCode,data)){
            return;
        }
        if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5){
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (data == null || resultCode != RESULT_OK) ? null : data.getData();
            if (null != result) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(null);
            }
            mUploadMessageForAndroid5 = null;
        }else if (requestCode == FILECHOOSER_CAMERA_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            if (resultCode == Activity.RESULT_OK) {
                File mCameraFilePath = new File(
                        BaseWebActivity.this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator
                                + "camera_photos.jpg");
                if (Build.VERSION.SDK_INT >= 24) {
                    Uri result = FileProvider
                            .getUriForFile(BaseWebActivity.this, LonchCloudApplication.getAppConfigDataBean().FILE_PROVIDER, mCameraFilePath);
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
                } else {
                    Uri uri = Uri.fromFile(mCameraFilePath);
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{uri});
                }
            } else {
                mUploadMessageForAndroid5.onReceiveValue(null);
            }
            mUploadMessageForAndroid5 = null;
        }else if (requestCode == REQUEST_CODE){
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "");
                    map.put("stringValue", result);
                    apiResult.setServiceResult(map);
                    String apiResultJson = toJson(apiResult);
                    appCallWeb(codeSn, apiResultJson);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(BaseWebActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public void initKeyWordStatus(){
        KeyboardUtil keyboardUtil = new KeyboardUtil(this);
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
    }

    /**
     * appCallWeb 客户端回传数据给前端
     * @param sn 流水号
     * @param response 返回数据
     */
    public void appCallWeb(String sn,String response){
        if (null == myWebView || TextUtils.isEmpty(sn) || TextUtils.isEmpty(response)) {
            return;
        }
        String data = Utils.reportDataForProtocol(sn,response);
        String jsonR = UrlUtil.getURLEncoderString(data);
        final String json = jsonR.replace("+", "%20");
        String [] strings = sn.split(",");
        String jsScript;
        if (strings.length >1 || Utils.isAppCallWeb(sn)){
            jsScript = "javascript:" + "LonchJsApi.appCallWebV2" + "('" + json + "')";
        }else {
            jsScript = "javascript:" + "LonchJsApi.appCallWeb" + "('" + sn + "','" + json + "')";
        }
        String finalJsScript = jsScript;
        handler.post(() -> {
            if (myWebView != null) {
                myWebView.evaluateJavascript(finalJsScript, value -> {});
            }
        });
    }

    /**
     * appCallWeb 客户端回传数据给前端
     * @param sn 流水号
     * @param response 返回数据
     */
    public void appCallWebV2(String sn,String response){
        if (null == myWebView || TextUtils.isEmpty(sn)) {
            return;
        }
        String jsonR = UrlUtil.getURLEncoderString(response);
        final String json = jsonR.replace("+", "%20");
        handler.post(() -> {
            if (myWebView != null) {
                myWebView.evaluateJavascript("javascript:" + "LonchJsApi.appCallWebV2" + "('" + json + "')", value -> {});
            }
        });
    }

    /**
     * 将Java对象employee序列化成为JSON格式
     *
     * @return
     */
    public String toJson(Object obj) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        if (netBroadcastReceiver!=null){
            unregisterReceiver(netBroadcastReceiver);
        }
    }
    /**
     * 重新登录清除数据
     */
    public void reStartLogin() {
        String res = Utils.setReportDataApp("logout", (String) SpUtils.get("caId",""), new ArrayList<>());
        LogEntity logEntity = new LogEntity();
        logEntity.setTime(Long.parseLong(Utils.getDate(0)));
        logEntity.setArgs(res);
        logEntity.setOperation("logout");
        logEntity.setFromType("2");
        LogUtils.getInstance().insert(logEntity);
        SpUtils.put("token", "");
        SpUtils.put("dataOwnerOrgId", "");
        SpUtils.put("userInfo", "");
        SpUtils.put("caId", "");
        SpUtils.put("saveDevices","");
        SpUtils.setObject(CommParameter.SpImLoginInfo, null);
        Objects.requireNonNull(MMKV.defaultMMKV()).removeValueForKey("userToken");
        CC.obtainBuilder("AppComponent")
                .setActionName("showLoginActivity")
                .setContext(this)
                .build()
                .call();
        finish();
    }

    /**
     * 处理行为日志 链路加速 api日志
     */
    public void initAllLog(){
        new Thread(() -> {
            initLog();
            initNetLog();
            initApiLog();
        }).start();
    }

    private void initLog() {
        try {
            List<LogEntity> list = LogUtils.getInstance().queryAllDevices(Long.parseLong(Utils.getDate(0)));
            String caId = (String) SpUtils.get("caId", "");
            if (null != list && list.size() > 0 && !TextUtils.isEmpty(caId)) {
                filePath = getFilesDir().getAbsolutePath() + File.separator + list.get(list.size() - 1).getTime() + ".zip";
                if (new File(filePath).exists()) {
                    FileUtils.deleteFile(filePath);
                }
                List<SmallVideoEntity> smallVideoEntityList = SmallVideoUtils.getInstance().queryAllDevices();
                List<LogEntity> appBehaviors = LogUtils.getInstance().queryByType("2");
                List<HashMap<String,Object>> hashMapList = new ArrayList<>();
                if (appBehaviors!=null && appBehaviors.size()>0){
                    for (LogEntity logEntity: appBehaviors) {
                        String args = logEntity.getArgs();
                        HashMap map = JSON.parseObject(args,HashMap.class);
                        hashMapList.add(map);
                    }
                }
                List<LogEntity> h5Behaviors = LogUtils.getInstance().queryByType("1");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("version", "v5");
                hashMap.put("appBehaviors", hashMapList);
                hashMap.put("h5Behaviors",h5Behaviors);
                hashMap.put("shortVideos", smallVideoEntityList);
                hashMap.put("width", ScreenUtils.getAppScreenWidth());
                hashMap.put("height", ScreenUtils.getAppScreenHeight());
                hashMap.put("deviceId",HeaderUtils.md5(SystemUtil.getAndroidDeviceId(BaseWebActivity.this)));
                hashMap.put("appType",LonchCloudApplication.getAppConfigDataBean().APP_TYPE);
                hashMap.put("appVersion",LonchCloudApplication.getAppConfigDataBean().appVersionName);
                String path = getFilesDir().getAbsolutePath() + File.separator + "log.txt"; //临时txt 文件路径
                String zipFilePath = getFilesDir().getAbsolutePath() + File.separator + "log.zip"; // 临时zip文件路径
                if (new File(path).exists()) {
                    FileUtils.deleteFile(path);
                }
                if (new File(zipFilePath).exists()) {
                    FileUtils.deleteFile(zipFilePath);
                }
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.excludeFieldsWithoutExposeAnnotation();
                Gson gson = gsonBuilder.create();
                boolean write = FileIOUtils.writeFileFromString(new File(path),gson.toJson(hashMap), false);
                if (write) {
                    boolean zip = ZipUtils.zipFile(path, zipFilePath);
                    if (zip) {
                        RSAUtil.encryptUserData(zipFilePath, filePath);
                        handler.sendEmptyMessage(100001);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.getInstance().deleteAll();
            SmallVideoUtils.getInstance().deleteAll();
        }

    }

    private void initApiLog() {
        List<ApiResponseEntity> list = ApiResponseUtils.getInstance().queryAllDevices(Long.parseLong(Utils.getDate(0)));
        String caId = (String) SpUtils.get("caId", "");
        if (null != list && list.size() > 0 && !TextUtils.isEmpty(caId)) {
            String deviceId = HeaderUtils.md5(SystemUtil.getAndroidDeviceId(BaseWebActivity.this));
            String apiFileDate = list.get(list.size() - 1).getTime() + "";
            //保存文件地址
            String netFilePath = getFilesDir().getAbsolutePath() + File.separator + apiFileDate + "_" + caId + "_" + deviceId + "_api" + ".zip";
            if (new File(netFilePath).exists()) {
                FileUtils.deleteFile(netFilePath);
            }
            File netLogFile = new File(netFilePath);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            Gson gson = gsonBuilder.create();
            String path = getFilesDir().getAbsolutePath() + File.separator + "api.txt"; //临时txt 文件路径
            if (new File(path).exists()) {
                FileUtils.deleteFile(path);
            }
            File file = new File(path);
            boolean isWrite = FileUtils.writeFileFromString(file, gson.toJson(list), false);
            if (isWrite) {
                try {
                    boolean isZip = ZipUtils.zipFile(file, netLogFile);
                    if (isZip) {
                        FileUtils.deleteFile(path);
                        initOSS(netLogFile, "apiLog");
                    }
                } catch (IOException ignored) {
                }
            }
        }

    }

    private void initNetLog() {
        //检测链路全部的数据
        List<NetLinkEntity> list = NetLinkUtils.getInstance().queryAllDevices(Long.parseLong(Utils.getDate(0)));
        String caId = (String) SpUtils.get("caId", "");
        if (null != list && list.size() > 0 && !TextUtils.isEmpty(caId)) {
            String deviceId = HeaderUtils.md5(SystemUtil.getAndroidDeviceId(BaseWebActivity.this));
            netFileDate = list.get(list.size() - 1).getTime() + "";
            //封装数据map
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("version", "v1");
            hashMap.put("deviceId", deviceId);
            //保存文件地址
            String netFilePath = getFilesDir().getAbsolutePath() + File.separator + caId + "_" + deviceId + ".zip";
            if (new File(netFilePath).exists()) {
                FileUtils.deleteFile(netFilePath);
            }
            File netLogFile = new File(netFilePath);
            //链路检测成功数据
            List<NetLinkEntity> succcessList = NetLinkUtils.getInstance().queryByType(1);
            if (null != succcessList && succcessList.size() > 0) {
                hashMap.put("details", succcessList);
            } else {
                hashMap.put("details", new ArrayList<>());
            }
            //链路检测失败数据
            List<NetLinkFail> failList = NetLinkUtils.getInstance().queryByTypeFail();
            if (null != failList && failList.size() > 0) {
                hashMap.put("fails", failList);
            } else {
                hashMap.put("fails", new ArrayList<>());
            }
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            Gson gson = gsonBuilder.create();
            String path = getFilesDir().getAbsolutePath() + File.separator + "netLog.txt"; //临时txt 文件路径
            if (new File(path).exists()) {
                FileUtils.deleteFile(path);
            }
            File file = new File(path);
            boolean isWrite = FileUtils.writeFileFromString(file, gson.toJson(hashMap), false);
            if (isWrite) {
                try {
                    boolean isZip = ZipUtils.zipFile(file, netLogFile);
                    if (isZip) {
                        FileUtils.deleteFile(path);
                        initOSS(netLogFile, "netLog");
                    }
                } catch (IOException ignored) {
                }
            }
        } else {
            checkNet();
        }

    }

    /**
     * 日志上传OSS
     */
    public void initOSS(File file, String type) {
        OkHttpUtil.getInstance().getOSSToken(new OkHttpUtil.HttpCallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    boolean opFlag = jsonObject.getBoolean("opFlag");
                    if (opFlag) {
                        JSONObject serviceResult = jsonObject.getJSONObject("serviceResult");
                        JSONObject credentials;
                        if (serviceResult.has("data")) {
                            boolean success = serviceResult.getBoolean("success");
                            if (success) {
                                JSONObject dataJsonObject = serviceResult.getJSONObject("data");
                                credentials = dataJsonObject.getJSONObject("credentials");
                            } else {
                                return;
                            }
                        } else {
                            credentials = serviceResult.getJSONObject("credentials");
                        }
                        StsToken stsToken = new StsToken();
                        stsToken.setAccessKeyId(credentials.getString("accessKeyId"));
                        stsToken.setAccessKeySecret(credentials.getString("accessKeySecret"));
                        stsToken.setSecurityToken(credentials.getString("securityToken"));

                        OSS oss = OssClient.getInstance().getOSS(stsToken);
                        OssService ossService = new OssService(oss, Config.BUCKET_NAME, new OssService.UploadCallBack() {
                            @Override
                            public void onError(String msg) {
                                Log.d(TAG, "onError:" + msg);
                            }

                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "onSuccess:");
                                Log.d(TAG, Thread.currentThread().getName());
                                FileUtils.deleteFile(file.getAbsolutePath());
                                if (type.equals("log")) {
                                    List<LogEntity> logEntityList = LogUtils.getInstance().queryAllDevices(Long.parseLong(Utils.getDate(0)));
                                    if (null != logEntityList && logEntityList.size() > 0) {
                                        for (LogEntity entity : logEntityList) {
                                            LogUtils.getInstance().delete(entity);
                                        }
                                    }
                                    SmallVideoUtils.getInstance().deleteAll();
                                } else if (type.equals("apiLog")) {
                                    List<ApiResponseEntity> logEntityList = ApiResponseUtils.getInstance().queryAllDevices(Long.parseLong(Utils.getDate(0)));
                                    if (null != logEntityList && logEntityList.size() > 0) {
                                        for (ApiResponseEntity entity : logEntityList) {
                                            ApiResponseUtils.getInstance().delete(entity);
                                        }
                                    }
                                } else {
                                    boolean isDelete = NetLinkUtils.getInstance().deleteAll();
                                    if (isDelete) {
                                        checkNet();
                                    }
                                }
                            }
                        });
                        String name = "";
                        String normal = "";
                        if (type.equals("log")) {
                            normal = LonchCloudApplication.getAppConfigDataBean().OSS_APP_FILE_NAME + Utils.getDate(0) + File.separator + file.getName().replace(".zip", "") + "_" + SpUtils.get("caId", "") + "_" + HeaderUtils.md5(SystemUtil.getAndroidDeviceId(BaseWebActivity.this)) + ".zip";
                        } else if (type.equals("bridge")) {
                            normal = LonchCloudApplication.getAppConfigDataBean().OSS_APP_BRIDGE_NAME + Utils.getDate(0) + File.separator + file.getName().replace("bridge.zip", "") + "_" + SpUtils.get("caId", "") + "_" + HeaderUtils.md5(SystemUtil.getAndroidDeviceId(BaseWebActivity.this)) + ".zip";
                        } else if (type.equals("apiLog")) {
                            normal = LonchCloudApplication.getAppConfigDataBean().OSS_APP_API_RESPONSE_NAME + Utils.getDate(0) + File.separator + file.getName().replace("_api", "");
                        } else {
                            normal = LonchCloudApplication.getAppConfigDataBean().OSS_APP_ACCELERATION_NAME + netFileDate + File.separator + file.getName();
                        }
                        if (LonchCloudApplication.getAppConfigDataBean().SERVICE_URL.contains("test")) {
                            name = "test/" + normal;
                        } else {
                            name = "product/" + normal;
                        }
                        Log.i(TAG, name);
                        ossService.asyncPutFile(name, file.getAbsolutePath());
                    }
                } catch (Exception ignored) {
                }
            }

            @Override
            public void onFailure() {
            }
        });

    }

    private void checkNet() {
        NetLinkBean netLinkBean = CheckNetManger.getInstance().getNetLinkBean();
        if (null != netLinkBean && null!= netLinkBean.getServiceResult() && null!= netLinkBean.getServiceResult().getData()) {
            NetLinkBean.ConfigBean configBean = netLinkBean.getServiceResult().getData().getConfig();
            if (null != configBean && configBean.isEnableTest()) {
                List<NetLinkEntity> linkEntityList = NetLinkUtils.getInstance().queryAllDevices();
                int size = 0;
                if (null != linkEntityList) {
                    size = linkEntityList.size();
                }
                int loopTime = configBean.getLoopTime();
                int count = CheckNetManger.getInstance().countSize(netLinkBean);// 返回数据是否有数据能执行链路分析
                if (count == 0) {
                    return;
                }
                if (size < loopTime * count) {
                    int timerInterval = configBean.getTimerInterval();
                    int startSecond = configBean.getStartSecond();
                    handler.postDelayed(() -> CheckNetManger.getInstance().CountLink(netLinkBean, (String) SpUtils.get("token", "")), startSecond * 1000);
                    if (loopTime > 1) {
                        handler.postDelayed(runnable, (long) timerInterval * 1000 * 60);
                    }
                } else {
                    handler.removeCallbacks(runnable);
                }
            }
        }

    }
    //执行链路检测工作
    Runnable runnable = () -> checkNet();
}
