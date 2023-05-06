package com.lonch.client.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.lonch.client.BuildConfig;
import com.lonch.client.LonchCloudApplication;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HeaderUtils {


    public static boolean ifNA() {
        boolean isOversea = false;
        try {
            Method get = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class);
            String region = get.invoke(null, "ro.puppy.region").toString();
            if ("NA".equals(region)) {
                isOversea = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOversea;
    }

    public static String getAppVersionCode_Name() {
        StringBuffer appversion = new StringBuffer();
        appversion.append(BuildConfig.VERSION_CODE);
        return appversion.toString();
    }

    public static String getAppVersion() {
        String versionName = "";
        try { //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = LonchCloudApplication.getApplicationsContext().getPackageManager().getPackageInfo(LonchCloudApplication.getApplicationsContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getAppVersionCode(Context mContext) {
        int versionCode = 0;
        try { //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getMacAddress(Context context) {

        // 如果是6.0以下，直接通过wifimanager获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String macAddress0 = getMacAddress0(context);
            if (!TextUtils.isEmpty(macAddress0)) {
                return macAddress0;
            }
        }

        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            Log.e("----->" + "NetInfoManager", "getMacAddress:" + ex.toString());
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress:" + e.toString());
            }

        }
        return macSerial.replace(":", "").toUpperCase();
    }

    private static String getMacAddress0(Context context) {
        if (isAccessWifiStateAuthorized(context)) {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = null;
            try {
                wifiInfo = wifiMgr.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress0:" + e.toString());
            }

        }
        return "";

    }

    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context
                .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
            Log.e("----->" + "NetInfoManager", "isAccessWifiStateAuthorized:"
                    + "access wifi state is enabled");
            return true;
        } else
            return false;
    }

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    public static String getVersion() {
        return SystemProperties.get("ro.alias.version");
    }

    public static String getModel() {
        if (SystemProperties.get("ro.product.model") == null) {
            return "ABCDEFG";
        } else {
            return SystemProperties.get("ro.product.model");
        }
    }

    public static String getModelCode() {
        if (SystemProperties.get("ro.alias.model") == null) {
            return "ABCDEFG";
        } else {
            return SystemProperties.get("ro.alias.model");
        }
    }

    public static String getSN() {
        if (SystemProperties.get("ro.serialno") == null) {
            return "123456789";
        } else {
            return SystemProperties.get("ro.serialno");
        }
    }

    public static String getAliseVersion() {
        if (SystemProperties.get("ro.alias.version") == null) {
            return "123456789";
        } else {
            return SystemProperties.get("ro.alias.version");
        }

    }

    /**
     * new My
     *
     * @return
     */
    public static String getSysVersion() {
        if (SystemProperties.get("ro.build.display.id") == null) {
            return "123456789";
        } else {
            return SystemProperties.get("ro.build.display.id");
        }

    }

    public static String md5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            int tem;
            StringBuffer buffer = new StringBuffer();
            for (byte it : md.digest()) {
                tem = it;
                if (tem < 0) {
                    tem += 256;
                }
                if (tem < 16) {
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(tem));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
