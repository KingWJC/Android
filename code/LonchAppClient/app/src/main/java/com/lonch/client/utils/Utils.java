package com.lonch.client.utils;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.billy.cc.core.component.CC;
import com.google.gson.Gson;
import com.lonch.client.bean.ApiResult;
import com.lonch.client.bean.ApiResultV2;
import com.lonch.client.bean.BaseArgsV2;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.common.CommParameter;
import com.lonch.client.database.bean.ApiResponseEntity;
import com.lonch.client.database.bean.BridgeEntity;
import com.lonch.client.database.bean.LogEntity;
import com.lonch.client.database.tabutils.ApiResponseUtils;
import com.tencent.mmkv.MMKV;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Utils {

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {

        long time = System.currentTimeMillis();

        if (time - lastClickTime < 500) {

            return true;

        }

        lastClickTime = time;

        return false;

    }


    /**
     * 获取文件对应uri
     */
    public static Uri getPathUri(Context context, String filePath) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider",
                    new File(filePath));
        } else {
            uri = Uri.fromFile(new File(filePath));
        }
        return uri;
    }

    public static Intent getInstallApkIntent(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//增加读写权限
        }
        intent.setDataAndType(getPathUri(context, filePath), "application/vnd.android.package-archive");
        return intent;

    }

    //计算相差多少小时
    public static int differHours(long startStamp, long endStamp) {
        int hour;
        BigInteger startStampInt = BigInteger.valueOf(startStamp);
        BigInteger endStampInt = BigInteger.valueOf(endStamp);
        BigInteger apartStamp = endStampInt.subtract(startStampInt);
        BigInteger hourStamp = BigInteger.valueOf(1000 * 60 * 60);
        BigInteger hours = apartStamp.divide(hourStamp);//相差几小时
        hour = hours.intValue();
        return hour;
    }

    public static void reStartLogin(Context mContext) {
        String res = Utils.setReportDataApp("logout", (String) SpUtils.get("caId",""), new ArrayList<>());
        LogEntity logEntity = new LogEntity();
        logEntity.setTime(Long.parseLong(Utils.getDate(0)));
        logEntity.setArgs(res);
        logEntity.setFromType("2");
        logEntity.setOperation("logout");
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
                .addParam("flags", Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)//关掉所要到的界面中间的activity
                .build()
                .call();
    }

    public static void reStartWelcome(Context mContext) {
        CC.obtainBuilder("AppComponent")
                .setActionName("showWelcomeActivity")
                .addParam("flags", Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)//关掉所要到的界面中间的activity
                .build()
                .call();
    }

    public static void clickView(float x, float y) {
        List<String> list = new ArrayList<>();
        list.add("input");
        list.add("tap");
        list.add("" + x);
        list.add("" + y);
        ProcessBuilder processBuilder = new ProcessBuilder(list);
        try {
            processBuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片到相册
     *
     * @param context
     */
    public static void savePhotoAlbum(Context context, String path, String fileName) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    path, fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("scan--", e.getMessage());
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(path))));

    }

    /**
     * 获取当前日期之前或之后
     *
     * @param count
     * @return
     */
    public static String getDate(int count) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.DAY_OF_MONTH, count);
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
        String curDate = s.format(ca.getTime());
        return curDate;
    }

    public static String getReportTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String getNetLogFileTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String setReportData(String operation, String url, List<String> points) {
        Map<String, Object> map = new HashMap<>();
        map.put("operation", operation);
        map.put("startTime", System.currentTimeMillis());
        map.put("endTime", System.currentTimeMillis());
        map.put("url", url);
        map.put("points", points);
        map.put("sender", "browser");
        map.put("fromType", "2");
        return GsonUtils.getInstance().toJson(map);
    }

    public static String setReportDataApp(String operation, String name, List<String> points) {
        Map<String, Object> map = new HashMap<>();
        map.put("operation", operation);
        map.put("startTime", System.currentTimeMillis());
        map.put("endTime", System.currentTimeMillis());
        map.put("url", "");
        map.put("points", points);
        map.put("sender", name);
        map.put("fromType", "2");
        return GsonUtils.getInstance().toJson(map);
    }

    public static String setAppErrorData(String errorMsg) {
        ApiResult<Map<String, Object>> mapApiResult = new ApiResult<>();
        mapApiResult.setError(errorMsg);
        mapApiResult.setOpFlag(false);
        Map<String, Object> map2 = new HashMap<>();
        mapApiResult.setServiceResult(map2);
        return GsonUtils.getInstance().toJson(mapApiResult);
    }

    public static String setAppWebData(Map<String, Object> map) {
        ApiResult<Map<String, Object>> mapApiResult = new ApiResult<>();
        mapApiResult.setError("");
        mapApiResult.setOpFlag(true);
        mapApiResult.setServiceResult(map);
        return GsonUtils.getInstance().toJson(mapApiResult);
    }

    public static void callTelephone(Context mContext, String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            mContext.startActivity(intent);
        } catch (Exception e) {

        }
    }

    public static PlistPackageBean getPackageBean(String packId) {
        //如果有文件 找到对应配置文件，看是否网络url，否则接着判断包是否存在c:判断版本包/c配置文件更新b下载
        String datafromFile = FileUtils.getDatafromFile(packId);
        if (!TextUtils.isEmpty(datafromFile)) {
            Gson gson = new Gson();
            PlistPackageBean localappZipBean = gson.fromJson(datafromFile, PlistPackageBean.class);
            if (!TextUtils.isEmpty(localappZipBean.getSoftware_id())){
                if (packId.equals(localappZipBean.getSoftware_id())) {//找到json
                    return localappZipBean;
                }
            }
        }
        return null;
    }

    public static String getSN() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Build.getSerial();
        } else
            return Build.SERIAL;
    }

    /**
     * 根据通讯协议修改数据返回格式
     *
     * @param data
     * @return
     */

    public static String reportDataForProtocol(String sid, String data) {
        String response = data;
        try {
            String[] strings = sid.split(",");
            if (strings.length > 1) { //数组大于1 有sn 和 command
                sid = strings[0];
                String command = strings[1];
                if (command.equals("getAppProxyData")) {
                    BaseArgsV2 baseArgsV2 = new BaseArgsV2();
                    BaseArgsV2.ArgsBean argsBean = new BaseArgsV2.ArgsBean();
                    baseArgsV2.setSn(sid);
                    baseArgsV2.setCommand("getAppProxyDataResult");
                    argsBean.setData(JSON.parseObject(data));
                    baseArgsV2.setArgs(argsBean);
                    response = JSON.toJSONString(baseArgsV2, SerializerFeature.WriteMapNullValue);
                } else {
                    response = JSON.toJSONString(getApiResultV2(data, sid, command),SerializerFeature.WriteMapNullValue);
                }
            } else {
                if (sid.equals("keyboardChange") || sid.equals("recivedC2CTextMessage")
                        || sid.equals("recivedGroupTextMessage") || sid.equals("recivedNewConversation")
                        || sid.equals("recivedConversationChanged")|| sid.equals("netStatusChanged")
                        || sid.equals("noticeLiveLinkData") || sid.equals("cmdEnterLeaveRoom")) {
                    BaseArgsV2 baseArgsV2 = new BaseArgsV2();
                    BaseArgsV2.ArgsBean argsBean = new BaseArgsV2.ArgsBean();
                    baseArgsV2.setSn(UUID.randomUUID().toString().replace("-",""));
                    baseArgsV2.setCommand(sid);
                    baseArgsV2.setVersion("2.0");
                    JSONObject jsonObject = JSON.parseObject(data);
                    JSONObject args = jsonObject.getJSONObject("serviceResult");
                    argsBean.setData(args);
                    baseArgsV2.setArgs(argsBean);
                    response = JSON.toJSONString(baseArgsV2,SerializerFeature.WriteMapNullValue);
                }
            }
        }catch (JSONException ignored){}

        return response;
    }

    public static BaseArgsV2 getApiResultV2(String json, String sn, String command) {
        BaseArgsV2 baseArgsV2 = new BaseArgsV2();
        BaseArgsV2.ArgsBean argsBean = new BaseArgsV2.ArgsBean();
        ApiResultV2<HashMap<String, Object>> apiResultV2 = new ApiResultV2<>();
        apiResultV2.setSid(sn);
        baseArgsV2.setSn(sn);
        baseArgsV2.setCommand(command + "Result");
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject.getBoolean("opFlag")) {
                HashMap<String, Object> objectHashMap = new HashMap<>();
                objectHashMap.put("success", true);
                objectHashMap.put("reason", "");
                objectHashMap.put("errorCode", "");
                objectHashMap.put("data", jsonObject.getJSONObject("serviceResult"));
                apiResultV2.setOpFlag(true);
                apiResultV2.setServiceResult(objectHashMap);
            } else {
                apiResultV2.setOpFlag(false);
                apiResultV2.setErrorMsg(jsonObject.getString("error"));
                apiResultV2.setServiceResult(new HashMap<>());
            }
        } catch (Exception e) {
            apiResultV2.setOpFlag(false);
            apiResultV2.setErrorMsg("处理数据解析异常");
            apiResultV2.setServiceResult(new HashMap<>());
        }
        argsBean.setData(JSON.parseObject(JSON.toJSONString(apiResultV2,SerializerFeature.WriteMapNullValue)));
        baseArgsV2.setArgs(argsBean);
        return baseArgsV2;
    }

    /**
     * 保存桥接协议的数据到数据库
     * @param sn
     * @param command
     * @param msg
     */
    public static void saveBridgeData(String sn,String command,String msg){
        BridgeEntity bridgeEntity = new BridgeEntity();
        bridgeEntity.setSn(sn);
        bridgeEntity.setCommand(command);
        bridgeEntity.setBp(msg);
        bridgeEntity.setTime(Long.parseLong(Utils.getDate(0)));
        BridgeUtils.getInstance().insert(bridgeEntity);
    }

    public static  boolean isAppCallWeb(String sid){
        return  sid.equals("keyboardChange") || sid.equals("recivedC2CTextMessage")
                || sid.equals("recivedGroupTextMessage") || sid.equals("recivedNewConversation")
                || sid.equals("recivedConversationChanged") || sid.equals("netStatusChanged")
                || sid.equals("cmdEnterLeaveRoom") || sid.equals("noticeLiveLinkData");
    }

    /**
     * 获取请求参数中的sid
     * @param params  请求参数
     * @return sid
     */
    public static String getParamSid(String params){
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            if (jsonObject.containsKey("sid")){
                return  jsonObject.getString("sid");
            }
        }catch (JSONException ignored){}
        return null;
    }

    /**
     * 保存请求api数据
     * @param url 地址
     * @param success 成功状态 1 成功 0失败
     * @param responseTime 响应时间
     */
    public static void saveApiResponse(String url,int success,Long responseTime){
        ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
        apiResponseEntity.setCreateTime(System.currentTimeMillis());
        apiResponseEntity.setUrl(url);
        apiResponseEntity.setTime(Long.parseLong(Utils.getDate(0)));
        apiResponseEntity.setSuccess(success);
        apiResponseEntity.setResponseTime(responseTime);
        ApiResponseUtils.getInstance().insert(apiResponseEntity);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isCameraCanUse(Context context){
        CameraManager connManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] strings = connManager.getCameraIdList();
            return strings.length > 0;
        }catch (CameraAccessException e){
            return false;
        }
    }
}
