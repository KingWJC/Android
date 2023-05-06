package com.lonch.client.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
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
import com.alibaba.idst.nui.Constants;
import com.alibaba.idst.nui.INativeNuiCallback;
import com.alibaba.idst.nui.KwsResult;
import com.alibaba.idst.nui.NativeNui;
import com.google.gson.Gson;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.R;
import com.lonch.client.base.BaseWebActivity;
import com.lonch.client.bean.BaseArgs;
import com.lonch.client.bean.BaseArgsV2;
import com.lonch.client.bean.FromJsBean;
import com.lonch.client.bean.FromJsBeanRefreshToken;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.bean.ToolBarBeanMy;
import com.lonch.client.bean.WebJsFunction;
import com.lonch.client.bean.argsbean.ArgsAppPay;
import com.lonch.client.bean.argsbean.ArgsAppPayWeixin;
import com.lonch.client.bean.argsbean.ArgsDisplay;
import com.lonch.client.bean.event.DisplayEvent;
import com.lonch.client.common.AppConstants;
import com.lonch.client.common.CompressStatus;
import com.lonch.client.interfaces.JsDataContract;
import com.lonch.client.manager.WBH5FaceVerifySDK;
import com.lonch.client.model.JsDataModel;
import com.lonch.client.oss.OssNui;
import com.lonch.client.utils.FileUtils;
import com.lonch.client.utils.LocalWebInfoUtil;
import com.lonch.client.utils.SDCardUtil;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.UrlUtil;
import com.lonch.client.utils.Utils;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class BridgeFragment extends Fragment implements JsDataContract.JsGetDataView, WebJsFunction.CallbackJsFun, INativeNuiCallback {

    private static final String TAG = "FragmentBridge";
    public MyWebView webView;
    private LinearLayout main_layout;
    private EditText result_text;
    NativeNui nui_instance = new NativeNui();

    private ToolBarBeanMy toolBarBean;
    private JsDataModel jsDataModel;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private final static int FILECHOOSER_CAMERA_RESULTCODE_FOR_ANDROID_5 = 20;
    private GeolocationPermissions.Callback geolocationCallback;
    private String orgin;
    private String openSn = "";
    private String mRootUrl = "";
    private String webPack;
    private boolean mInit = false;

    private MyHandler handler = new MyHandler(this);

    final static class MyHandler extends Handler {
        WeakReference<BridgeFragment> webFragmentWeakReference;

        public MyHandler(BridgeFragment webFragment) {
            this.webFragmentWeakReference = new WeakReference<>(webFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BridgeFragment webFragment = webFragmentWeakReference.get();
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

    public BridgeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
//        EventBus.getDefault().register(this);
        assert arguments != null;
        toolBarBean = arguments.getParcelable("toolBarBean");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bridge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String token = (String) SpUtils.get("token", "");
        jsDataModel = new JsDataModel(this, token);
        this.webPack = toolBarBean.getWeb_app_id();

        initWebView(view);

        startWebView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("JavascriptInterface")
    private void initWebView(@NonNull View view) {
        result_text = view.findViewById(R.id.result_name_text);
        main_layout = view.findViewById(R.id.main_layout);
        webView = new MyWebView(requireActivity());
//        main_layout.removeAllViews();
        main_layout.addView(webView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        webView.addJavascriptInterface(new WebJsFunction((BaseWebActivity) requireActivity(), webView, this), "LonchJsApi");
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
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

    public void startWebView() {
        String ip = (String) SpUtils.get("ip", "");
//        if (TextUtils.isEmpty(webPack)) {
//            return;
//        }
//        PlistPackageBean resources = Utils.getPackageBean(webPack);
//        if (null == resources) {
//            return;
//        }
//        String app_package_name = resources.getApp_package_name();
//        boolean online = resources.isUsing_online_url();
//        if (online) {
//            String onlineUrl = resources.getWebapp_url();
//            if (!TextUtils.isEmpty(onlineUrl) && !onlineUrl.endsWith("/")) {
//                onlineUrl += "/";
//            }
//            mRootUrl = onlineUrl + toolBarBean.getUrl_path();
//        } else {
//            if (!TextUtils.isEmpty(ip)) {
//                final String app_package_namea = requireActivity().getFilesDir().getAbsolutePath() + "/" + "App/";
//                try {
//                    if (!TextUtils.isEmpty(resources.getVersion())) {
//                        String filePath = app_package_namea + app_package_name + "/" + resources.getVersion() + "/index.html";
//                        File file = new File(filePath);
//                        if (file.exists()) {
//                            mRootUrl = "http://" + ip + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/" + app_package_name + "/" + resources.getVersion() + "/" + toolBarBean.getUrl_path();
//                        } else {
//                            DownloadUtil.getInstance().downloadSingleFile(resources, requireActivity().getFilesDir() + File.separator + "Zip", new DownloadUtil.DownloadCallBack() {
//                                @Override
//                                public void onError(String msg) {
//                                }
//
//                                @Override
//                                public void onSuccess(PlistPackageBean bean, String path) {
//                                    final String unZipPath = requireActivity().getFilesDir().getAbsolutePath() + "/App/" + resources.getApp_package_name() + "/" + resources.getVersion();
//                                    ZipTask zipTask = new ZipTask(path, unZipPath, resources, handler);
//                                    zipTask.execute();
//                                }
//                            });
//                        }
//                    }
//                    String mBaseUrl = "http://" + ip + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/";
//                    SpUtils.put("baseUrl", mBaseUrl);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        webView.clearCache(true);
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl("http://10.0.2.2:8080/#/bridge");
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

    public boolean checkSinglePermission(String strings) {
        boolean isPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            isPermission = ContextCompat.checkSelfPermission(requireActivity(), strings) == PackageManager.PERMISSION_GRANTED;
        } else {
            isPermission = true;
        }
        return isPermission;
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

    @Override
    public void onNuiEventCallback(Constants.NuiEvent nuiEvent, int i, int i1, KwsResult kwsResult, AsrResult asrResult) {

    }

    @Override
    public int onNuiNeedAudioData(byte[] bytes, int i) {
        return 0;
    }

    @Override
    public void onNuiAudioStateChanged(Constants.AudioState audioState) {

    }

    @Override
    public void onNuiAudioRMSChanged(float v) {

    }

    @Override
    public void onNuiVprEventCallback(Constants.NuiVprEvent nuiVprEvent) {

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
//                appPay(sn, argsAppPayBaseArgs.getArgs().getType(), msg, argsAppPayBaseArgs.getArgs().getPayType());
                break;
            case "getAppInfo"://获取接口数据,本地版本信息
                appCallWeb(sn, LocalWebInfoUtil.getLocalWebInfoAll());
                break;
            case "scanQRCode"://关闭页面
//                scanZing(sn);
                break;
            case "pay"://普药商城支付
//                pypay(sn, msg);
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
//                recordSn = sn;
//                stopDialog();
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

    private void startDialog() {
        LonchCloudApplication.handler.post(() -> {
            int ret = nui_instance.startDialog(com.alibaba.idst.nui.Constants.VadMode.TYPE_P2T,
                    OssNui.getInstance().genDialogParams());
        });
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

    @Override
    public void onResponseSuccess(String sn, String bean, boolean isUpdateToken) {
        result_text.setText(bean);
    }

    @Override
    public void onResponseFailed(String sn, String fileMessage) {
        result_text.setText(fileMessage);
    }
}