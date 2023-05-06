package com.lonch.client.bean;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lonch.client.R;
import com.lonch.client.activity.MainActivity;
import com.lonch.client.activity.WebActivity;
import com.lonch.client.base.BaseWebActivity;
import com.lonch.client.bean.argsbean.ArgsFakeTouch;
import com.lonch.client.bean.argsbean.ArgsOpenUrl;
import com.lonch.client.bean.argsbean.ArgsShareView;
import com.lonch.client.bean.argsbean.ArgsStartNewActivity;
import com.lonch.client.bean.argsbean.ArgsTelPhone;
import com.lonch.client.database.bean.LogEntity;
import com.lonch.client.utils.DensityUtils;
import com.lonch.client.utils.DownloadUtil;
import com.lonch.client.utils.LogUtils;
import com.lonch.client.utils.NetWorkInfoUtils;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.UrlUtil;
import com.lonch.client.utils.Utils;
import com.lonch.client.utils.VibratorUtil;
import com.lonch.client.utils.WxShareUtils;
import com.lonch.client.view.MyWebView;
import com.tencent.mmkv.MMKV;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * User:白二鹏
 * 注意：这里的协议如果有appCallWeb，目前只适合Activity中的WebView，对于WebFragment有此需求的暂时需要在Fragment处理，这里并非都是通用的协议都往里放，避免踩坑
 * Created by Administrator-01-07 18 : 32
 */

public class WebJsFunction {

    private BaseWebActivity context;
    private MyWebView myWebView;

    public WebJsFunction(BaseWebActivity context, MyWebView myWebView, CallbackJsFun fun) {
        this.callbackJsFun = fun;
        this.context = context;
        this.myWebView = myWebView;
    }

    @JavascriptInterface
    public void webCallApp(String msg) {
        initCommand(msg);
    }

    @JavascriptInterface
    public void webCallAppV2(String msg) {
        try {
            BaseArgsV2 baseArgsV2 = JSON.parseObject(msg, BaseArgsV2.class);
            String command = baseArgsV2.getCommand();
            if (command.equals("getAppProxyData")) {
                callbackJsFun.webCallAppV2(msg);
            } else {
                BaseArgs baseArgs = new BaseArgs();
                baseArgs.setCommand(baseArgsV2.getCommand());
                baseArgs.setSn(baseArgsV2.getSn() + "," + baseArgsV2.getCommand());
                baseArgs.setArgs(baseArgsV2.getArgs().getData());
                String message = JSON.toJSONString(baseArgs);
                initCommand(message);
            }
        } catch (Exception e) {
            Log.i("ssss---", e.getMessage());
        }

    }

    @JavascriptInterface
    public void iAmAndroid() {
    }

    /**
     * 处理公共的command
     *
     * @param msg 数据
     */
    private void initCommand(String msg) {
        Gson gson = new Gson();
        BaseArgs baseArgs = gson.fromJson(msg, BaseArgs.class);
        String command = baseArgs.getCommand();
        String sn = baseArgs.getSn();
        switch (command) {
            case "pushNewView": //跳转单人聊天页面(二级页面)
                BaseArgs<ArgsStartNewActivity> startMsgUser = com.alibaba.fastjson.JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsStartNewActivity>>() {
                });
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("from", "pushNewView");
                intent.putExtra("appid", startMsgUser.getArgs().getWeb_app_id());
                intent.putExtra("url", startMsgUser.getArgs().getUrl_path());
                intent.putExtra("query", startMsgUser.getArgs().getQuery());
                intent.putExtra("type", startMsgUser.getArgs().getType());
                intent.putExtra("isBackTitleBar", startMsgUser.getArgs().isBackTitleBar());
                intent.putExtra("topTitle", startMsgUser.getArgs().getTopTitle());
                context.startActivity(intent);
                break;
            case "cmdAppOpenUrl": //挑转二级业务页面
                BaseArgs<ArgsOpenUrl> openUrlActivity = com.alibaba.fastjson.JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsOpenUrl>>() {
                });
                String type = openUrlActivity.getArgs().getConfig().getType();
                if (type.equals("live")) {
                    String ownerId = openUrlActivity.getArgs().getConfig().getRoomOwner();
                    String caId = (String) SpUtils.get("caId", "");
                    Intent intent1 = new Intent();
                    if (!TextUtils.isEmpty(caId) && !TextUtils.isEmpty(ownerId) && ownerId.contains(caId)) {
//                        intent1.setClass(context, LiveAnchorActivity.class);
                    } else {
//                        intent1.setClass(context, LiveAudienceActivity.class);
                    }
                    intent1.putExtra("roomId", openUrlActivity.getArgs().getConfig().getRoomId());
                    intent1.putExtra("roomName", openUrlActivity.getArgs().getConfig().getRoomName());
                    intent1.putExtra("avChatRoomId", openUrlActivity.getArgs().getConfig().getAvChatRoomId());
                    intent1.putExtra("url", openUrlActivity.getArgs().getUrl());
                    intent1.putExtra("owner", ownerId);
                    intent1.putExtra("liveId", openUrlActivity.getArgs().getConfig().getId());
                    context.startActivity(intent1);
                } else {
                    boolean isAnimated = openUrlActivity.getArgs().getConfig().isAnimated();
                    String animationType = openUrlActivity.getArgs().getConfig().getAnimationType();
                    context.runOnUiThread(() -> {
                        Intent intentUrl = new Intent(context, WebActivity.class);
                        intentUrl.putExtra("from", "cmdAppOpenUrl");
                        intentUrl.putExtra("url", openUrlActivity.getArgs().getUrl());
                        intentUrl.putExtra("title", openUrlActivity.getArgs().getTitle());
                        intentUrl.putExtra("type", type);
                        intentUrl.putExtra("animated", isAnimated);
                        intentUrl.putExtra("isBackTitleBar", openUrlActivity.getArgs().getConfig().isBackTitleBar());
                        context.startActivity(intentUrl);
                        if (isAnimated && !TextUtils.isEmpty(animationType)) {
                            context.overridePendingTransition(R.anim.pop_enter_anim, R.anim.anim_no);
                        }
                    });
                }
                callbackJsFun.webCallSn(sn);
                break;
            case "popView"://关闭页面
                if (context instanceof WebActivity) {
                    callbackJsFun.webCallApp(msg);
                } else {
                    context.finish();
                }
                break;
            case "reportBehavior"://用户行为上报
                String content = JSON.toJSONString(baseArgs.getArgs());
                LogEntity entity = new LogEntity();
                entity.setTime(Long.parseLong(Utils.getDate(0)));
                entity.setArgs(content);
                entity.setFromType("1");
                LogUtils.getInstance().insert(entity);
                appCallWeb(sn, Utils.setAppErrorData("用户行为保存成功"));
                break;
            case "cmdAppLogout":
                if (context instanceof MainActivity) {
                    context.reStartLogin();
                } else {
                    Utils.reStartLogin(context);
                }
                break;
            case "callTel":
                BaseArgs<ArgsTelPhone> phoneBaseArgs = com.alibaba.fastjson.JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsTelPhone>>() {
                });
                Utils.callTelephone(context, phoneBaseArgs.getArgs().getPhone());
                break;
            case "cmdSetCustomSettings": //设置字体
                try {
                    JSONObject jsonObject = new JSONObject(msg);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("args");
                    JSONObject setting = jsonObject1.getJSONObject("customSettings");
                    MMKV.defaultMMKV().encode("customSettings", setting.toString());
                    Map<String, Object> map = new HashMap<>();
                    map.put("msg", "保存成功");
                    appCallWeb(sn, Utils.setAppWebData(map));
                } catch (Exception e) {
                    appCallWeb(sn, Utils.setAppErrorData("保存失败"));
                }
                break;
            case "cmdReOpenApp": //重新打开app
                Utils.reStartWelcome(context);
                break;
            case "shareToOtherApp"://分享
                final BaseArgs<ArgsShareView> argsShareBean = com.alibaba.fastjson.JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsShareView>>() {
                });
                final ArgsShareView argsShareView = argsShareBean.getArgs();
                context.runOnUiThread(() -> context.showSharePop(v -> {
                    int id = v.getId();
                    if (id == R.id.weixinghaoyou) {
                        switch (argsShareView.getShareType()) {
                            case "web":
                                WxShareUtils.shareWeb(context, argsShareView.getWebpageUrl(), argsShareView.getTitle(), argsShareView.getDescription(), argsShareView.getThumbImage(), 0);
                                break;
                            case "text":
                                WxShareUtils.shareText(context, argsShareView.getTitle(), argsShareView.getDescription(), 0);
                                break;
                            case "image":
                                WxShareUtils.shareImage(context, argsShareView.getHdImage(), 0);
                                break;
                        }
                    } else if (id == R.id.pengyouquan) {
                        switch (argsShareView.getShareType()) {
                            case "web":
                                WxShareUtils.shareWeb(context, argsShareView.getWebpageUrl(), argsShareView.getTitle(), argsShareView.getDescription(), argsShareView.getThumbImage(), 1);
                                break;
                            case "text":
                                WxShareUtils.shareText(context, argsShareView.getTitle(), argsShareView.getDescription(), 1);
                                break;
                            case "image":
                                WxShareUtils.shareImage(context, argsShareView.getHdImage(), 1);
                                break;
                        }
                    } else if (id == R.id.qywx_share) {
                        switch (argsShareView.getShareType()) {
                            case "web":
                                WxShareUtils.QyWxshareWeb(context, argsShareView.getWebpageUrl(), argsShareView.getTitle(), argsShareView.getDescription(), argsShareView.getThumbImage());
                                break;
                            case "text":
                                WxShareUtils.QyWxShareText(context, argsShareView.getTitle(), argsShareView.getDescription());
                                break;
                            case "image":
                                WxShareUtils.QyWxShareImage(context, argsShareView.getHdImage());
                                break;
                        }
                    }
                }));
                break;
            case "cmdSetMobileVibrate"://震动
                context.runOnUiThread(() -> VibratorUtil.vibrate(context, 200));
                break;
            case "openChatFile"://打开音视频或office文件
                com.alibaba.fastjson.JSONObject parseObject = JSON.parseObject(msg);
                com.alibaba.fastjson.JSONObject argsJSON = parseObject.getJSONObject("args");
                int fileType = argsJSON.getIntValue("type");
                String fileUrl = argsJSON.getString("fileUrl");
                Intent fileIntent = new Intent();
                fileIntent.putExtra("fileUrl", fileUrl);
//                if (fileType == 1 || fileType == 2) {
//                    fileIntent.setClass(context, PlayerActivity.class);
//                } else {
//                    fileIntent.setClass(context, ReaderActivity.class);
//                }
                context.startActivity(fileIntent);
                break;

            case "cmdAppFakeTouch":
                BaseArgs<ArgsFakeTouch> appFake = com.alibaba.fastjson.JSONObject.parseObject(msg, new TypeReference<BaseArgs<ArgsFakeTouch>>() {
                });
                String point = appFake.getArgs().getFakeTouchPoint();
                if (!TextUtils.isEmpty(point)) {
                    String[] touchPoint = point.split(",");
                    if (touchPoint.length > 1) {
                        int height = DensityUtils.getStatusBarHeight(context) + 30;
                        Utils.clickView(Float.parseFloat(touchPoint[0]), Float.parseFloat(touchPoint[1]) + (float) height);
                    }
                }
                break;
            case "cmdShareImage":
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(msg);
                com.alibaba.fastjson.JSONObject parseObjectJSONObject = jsonObject.getJSONObject("args");
                String shareType = parseObjectJSONObject.getString("type");
                String imageUrl = parseObjectJSONObject.getString("url");
                switch (shareType) {
                    case "weixin":
                        WxShareUtils.shareImage(context, imageUrl, 0);
                        break;
                    case "qyweixin":
                        WxShareUtils.QyWxShareImage(context, imageUrl);
                        break;
                    case "phone":
                        DownloadUtil.getInstance().downloadImage(context, imageUrl);
                        break;
                }
                break;
            case "queryNetStatus":
                int status = 0;
                int netStatus = NetWorkInfoUtils.verify(context);
                if (netStatus >-1){
                    status = 1;
                }
                ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                Map<String, Object> map = new HashMap<>();
                map.put("status", status);
                apiResult.setServiceResult(map);
                String apiResultJson = toJson(apiResult);
                appCallWeb(sn,apiResultJson);
                break;
            case "cmdGetGeolocation"://获取经纬度
                ApiResult<Map<String, Object>> apiResult1 = new ApiResult<>();
                Map<String, Object> map1 = new HashMap<>();
                map1.put("longitude", SpUtils.get("longitude", "-1"));
                map1.put("latitude", SpUtils.get("latitude", "-1"));
                apiResult1.setServiceResult(map1);
                appCallWeb(sn, toJson(apiResult1));
                break;
            default:
                callbackJsFun.webCallApp(msg);
        }
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

    /**
     * appCallWeb 客户端回传数据给前端
     *
     * @param sn       流水号
     * @param response 返回数据
     */
    public void appCallWeb(String sn, String response) {
        if (null == myWebView || TextUtils.isEmpty(sn) || TextUtils.isEmpty(response)) {
            return;
        }
        String data = Utils.reportDataForProtocol(sn, response);
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
        context.runOnUiThread(() -> {
            if (myWebView != null) {
                myWebView.evaluateJavascript(finalJsScript, value -> {
                });
            }
        });
    }


    private CallbackJsFun callbackJsFun;

    public interface CallbackJsFun {
        void webCallAppV2(String msg);

        void webCallApp(String msg);

        void webCallSn(String sn);
    }

}
