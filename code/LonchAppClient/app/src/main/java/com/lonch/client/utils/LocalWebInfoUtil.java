package com.lonch.client.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.bean.argsbean.AppZipBeanMyInfo;
import com.lonch.client.common.AppConstants;
import com.lonch.client.database.bean.WebVersionEntity;
import com.lonch.client.database.tabutils.WebVersionUtils;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LocalWebInfoUtil {

    private static final String TAG = "LocalWebInfo";

    public static String getLocalWebInfoAll() {
        String mBaseUrl = (String) SpUtils.get("baseUrl", "");
        String token = (String) SpUtils.get("token", "");

        AppZipBeanMyInfo appZipBeanMyInfo = new AppZipBeanMyInfo();
        AppZipBeanMyInfo.ServiceResultBean serviceResultBean = new AppZipBeanMyInfo.ServiceResultBean();

        List<AppZipBeanMyInfo.ServiceResultBean.WebAppsBean> listweb = new ArrayList<>();
        List<WebVersionEntity> webVersionEntities = WebVersionUtils.getInstance().queryAllDevices();

        for (int i = 0; i < webVersionEntities.size(); i++) {
            Gson gson = new Gson();
            PlistPackageBean plistPackageBean = gson.fromJson(webVersionEntities.get(i).getJson(), PlistPackageBean.class);
            if (null != plistPackageBean && null != plistPackageBean.getSoftware_id()) {
                AppZipBeanMyInfo.ServiceResultBean.WebAppsBean webAppsBean = new
                        AppZipBeanMyInfo.ServiceResultBean.WebAppsBean();
                webAppsBean.setSoftware_id(TextUtils.isEmpty(plistPackageBean.getSoftware_id()) ? "" : plistPackageBean.getSoftware_id());
                webAppsBean.setSoftware_name(TextUtils.isEmpty(plistPackageBean.getSoftware_name()) ? "" : plistPackageBean.getSoftware_name());
                webAppsBean.setApp_package_name(TextUtils.isEmpty(plistPackageBean.getApp_package_name()) ? "" : plistPackageBean.getApp_package_name());
                webAppsBean.setWebapp_url(TextUtils.isEmpty(plistPackageBean.getWebapp_url()) ? "" : plistPackageBean.getWebapp_url());
                String packageName = TextUtils.isEmpty(plistPackageBean.getApp_package_name()) ? "" : plistPackageBean.getApp_package_name();
                if (plistPackageBean.isUsing_online_url()) {//使用线上 不传local_path
                    webAppsBean.setLocal_path("");
                } else {
                    webAppsBean.setLocal_path(mBaseUrl + packageName);
                }
                webAppsBean.setZip_path(TextUtils.isEmpty(plistPackageBean.getZip_path()) ? "" : plistPackageBean.getZip_path());
                webAppsBean.setVersion(TextUtils.isEmpty(plistPackageBean.getVersion()) ? "" : plistPackageBean.getVersion());
                webAppsBean.setVersion_id(null == plistPackageBean.getVersion_id() ? "" : plistPackageBean.getVersion_id());
                webAppsBean.setForce_update(plistPackageBean.isForce_update());
                webAppsBean.setFile_hash_code(TextUtils.isEmpty(plistPackageBean.getFile_hash_code()) ? "" : plistPackageBean.getFile_hash_code());
                webAppsBean.setUsing_online_url(plistPackageBean.isUsing_online_url());
                webAppsBean.setSoftware_type(null == plistPackageBean.getSoftware_type() ? "" : plistPackageBean.getSoftware_type());
                listweb.add(webAppsBean);
            }
        }
        AppZipBeanMyInfo.ServiceResultBean.AppClientInfo clientInfo = new
                AppZipBeanMyInfo.ServiceResultBean.AppClientInfo();
        String data = Objects.requireNonNull(MMKV.defaultMMKV()).decodeString("customSettings");
        if (!TextUtils.isEmpty(data)) {
            HashMap map = JSON.parseObject(data, HashMap.class);
            serviceResultBean.setCustomSettings(map);
        }
        clientInfo.setAppName(LonchCloudApplication.getAppConfigDataBean().LOCAL_APP_NAME);
        clientInfo.setCompanyName(LonchCloudApplication.getAppConfigDataBean().LOCAL_COMPANY);
        clientInfo.setDeviceType("androidApp");
        clientInfo.setVersion("" + LonchCloudApplication.getAppConfigDataBean().appVersionName);
        clientInfo.setRelease(true);
        clientInfo.setToken(token);
        clientInfo.setProtocolVersion("2.0");
        clientInfo.setDeviceId(HeaderUtils.md5(SystemUtil.getAndroidDeviceId(LonchCloudApplication.getApplicationsContext())));
        clientInfo.setBigVersion(AppConstants.APP_BIG_VERSION);
        clientInfo.setIsVisitor(((boolean) SpUtils.get("isVisitor", false)) ? 1 : 0);
        serviceResultBean.setClientInfo(clientInfo);
        if (webVersionEntities.size() > 0) {
            serviceResultBean.setWebApps(listweb);
            appZipBeanMyInfo.setServiceResult(serviceResultBean);
        }
        return toJson(appZipBeanMyInfo);
    }

    /**
     * 将Java对象employee序列化成为JSON格式
     *
     * @return
     */
    private static String toJson(Object obj) {
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

    public static PlistPackageBean getSoftBean(String webUrl) {
        List<WebVersionEntity> webVersionEntities = WebVersionUtils.getInstance().queryAllDevices();
        PlistPackageBean packageBean = null;
        Gson gson = new Gson();
        for (int i = 0; i < webVersionEntities.size(); i++) {
            PlistPackageBean plistPackageBean = gson.fromJson(webVersionEntities.get(i).getJson(), PlistPackageBean.class);
            if (null != plistPackageBean && null != plistPackageBean.getSoftware_id()) {
                if (plistPackageBean.getWebapp_url().contains(webUrl)) {
                    packageBean = plistPackageBean;
                    break;
                }
            }
        }
        return packageBean;
    }


}
