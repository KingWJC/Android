package com.lonch.client.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.activity.WebActivity;
import com.lonch.client.bean.PlistPackageBean;

import java.io.UnsupportedEncodingException;

public class UrlUtil {

    private final static String ENCODE = "UTF-8";
    private final static String TAG = "UrlUtil";

    /**
     * URL 解码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:09:51
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * URL 转码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:10:28
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getLiveRoomId(@NonNull Intent intent) {
        try {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return null;
            }
            if (action.equals(Intent.ACTION_VIEW)) {
                Log.i(TAG, "getDataString--" + intent.getDataString());
                Uri data = intent.getData();
                if (null == data) {
                    return null;
                }
                String type = data.getQueryParameter("type");
                if (TextUtils.isEmpty(type)) {
                    return null;
                }
                if (type.equals("live")) {
                    return data.getQueryParameter("liveID");
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void openFromView(@NonNull Intent intent, @NonNull Context context) {
        try {
            String action = intent.getAction();
            String path = intent.getStringExtra("path");
            if (TextUtils.isEmpty(action)) {
                return;
            }
            if (action.equals(Intent.ACTION_VIEW)) {
                Uri data = intent.getData();
                if (null == data) {
                    return;
                }
                String type = data.getQueryParameter("type");
                if (TextUtils.isEmpty(type)) {
                    return;
                }
                Log.i(TAG, "getDataString--" + intent.getDataString());
                String url = data.getQueryParameter("urlStr");

                Log.i(TAG, "url---" + url);
                if (!TextUtils.isEmpty(url)) {
                    String realUrl = getRealUrl(url);
                    if (TextUtils.isEmpty(realUrl)) {
                        return;
                    }
                    if (type.equals("normal")) {
                        Intent intentUrl = new Intent(context, WebActivity.class);
                        intentUrl.putExtra("from", "cmdAppOpenUrl");
                        intentUrl.putExtra("url", realUrl);
                        context.startActivity(intentUrl);
                    }
                }

            }
        } catch (Exception e) {

        }

    }

    public static String getRealUrl(String url) {
        String realUrl = "";
        Uri str = Uri.parse(url);
        if (null != str) {
//            List<String> list = str.getPathSegments();
            String authority = str.getAuthority();
            Log.i(TAG, "Authority---" + authority);
            String fragment = str.getFragment();
            Log.i(TAG, "Fragment---" + fragment);
//            for (int i = 0; i < list.size(); i++) {
//                Log.i(TAG, "pathSegments---"+list.get(i));
//            }
            if (TextUtils.isEmpty(authority)) {
                return realUrl;
            }
            PlistPackageBean packageBean = LocalWebInfoUtil.getSoftBean(authority);
            if (null != packageBean && !TextUtils.isEmpty(fragment)) {
                realUrl = "http://127.0.0.1" + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/" + packageBean.getApp_package_name() + "/" + packageBean.getVersion() + "/index.html#" + fragment;
            } else {
                realUrl = url;
            }
        }
        return realUrl;
    }
}
