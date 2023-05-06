package com.lonch.client.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.base.BaseModel;
import com.lonch.client.bean.AppClientUpdateBean;
import com.lonch.client.bean.AppZipBean;
import com.lonch.client.bean.HumanOrganizationBean;
import com.lonch.client.bean.InterfaceToolBarBean;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.bean.ToolBarBean;
import com.lonch.client.bean.UpdateInfoVBean;
import com.lonch.client.bean.WebAppUpdateSetting;
import com.lonch.client.bean.YfcUserBean;
import com.lonch.client.common.CommParameter;
import com.lonch.client.database.bean.WebVersionEntity;
import com.lonch.client.database.tabutils.WebVersionUtils;
import com.lonch.client.exception.ApiException;
import com.lonch.client.interfaces.HtmlContract;
import com.lonch.client.service.CommonSubscriber;
import com.lonch.client.service.CommonTransformerT;
import com.lonch.client.utils.GsonUtils;
import com.lonch.client.utils.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;


public class HtmlZipModel extends BaseModel {

    HtmlContract.HtmlCodeView responseModel;
    HtmlContract.UpdateJsonInfoCodeView updateJsonInfoCodeView;
    HtmlContract.QueryToolBarMenusInfoCodeView queryToolBarMenusInfoCodeView;
    HtmlContract.OrganizationInfoCodeView organizationInfoCodeView;
    HtmlContract.YaoFaCaiInfoView yaoFaCaiInfoView;
    HtmlContract.AppClientUpdate appClientUpdate;

    private String __sign;

    public HtmlZipModel(HtmlContract.HtmlCodeView resp, HtmlContract.UpdateJsonInfoCodeView upinfo, HtmlContract.QueryToolBarMenusInfoCodeView bar, HtmlContract.OrganizationInfoCodeView organizationInfoCodeView, HtmlContract.YaoFaCaiInfoView yaoFaCaiInfoView, HtmlContract.AppClientUpdate appClientUpdate) {
        this.responseModel = resp;
        this.updateJsonInfoCodeView = upinfo;
        this.queryToolBarMenusInfoCodeView = bar;
        this.organizationInfoCodeView = organizationInfoCodeView;
        this.yaoFaCaiInfoView = yaoFaCaiInfoView;
        this.appClientUpdate = appClientUpdate;
    }

    public HtmlZipModel(HtmlContract.UpdateJsonInfoCodeView updateJsonInfoCodeView, HtmlContract.AppClientUpdate appClientUpdate1) {
        this.updateJsonInfoCodeView = updateJsonInfoCodeView;
        this.appClientUpdate = appClientUpdate1;
    }

    public HtmlZipModel(HtmlContract.HtmlCodeView resp, HtmlContract.UpdateJsonInfoCodeView updateJsonInfoCodeView) {
        this.responseModel = resp;
        this.updateJsonInfoCodeView = updateJsonInfoCodeView;
        
    }

    /**
     * 请求H5版本更新
     */
    public void updateZipSetting(String token) {
        WebAppUpdateSetting setting = new WebAppUpdateSetting();
        Gson gson = new Gson();
        List<WebAppUpdateSetting.UserVersionsBean> curVersionList = new ArrayList<>();
        List<WebVersionEntity> webVersionEntities = WebVersionUtils.getInstance().queryAllDevices();
        for (int i = 0; i < webVersionEntities.size(); i++) {
            PlistPackageBean bean = gson.fromJson(webVersionEntities.get(i).getJson(), PlistPackageBean.class);
            WebAppUpdateSetting.UserVersionsBean webAppUpdateSetting = new WebAppUpdateSetting.UserVersionsBean();
            webAppUpdateSetting.setCurrent_version_id(bean.getVersion_id());
            webAppUpdateSetting.setSoftware_id(webVersionEntities.get(i).getSoftware_id());
            curVersionList.add(webAppUpdateSetting);
        }
        setting.setClientId(LonchCloudApplication.getAppConfigDataBean().APP_TYPE);
        setting.setUserVersions(curVersionList);
        String jsonRequest = gson.toJson(setting);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(""), jsonRequest);
        httpService.updateSetting(token, body)
                .compose(new CommonTransformerT<AppZipBean>())
                .subscribe(new CommonSubscriber<AppZipBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(AppZipBean audiobean) {
                        if (audiobean != null) {
                            if (audiobean.isOpFlag()) {
                                responseModel.onHtmlSuccess(audiobean);
                            } else {
                                responseModel.onHtmlFaile(audiobean.getError().toString());
                            }
                        }

                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        responseModel.onHtmlFaile(e.getMessage());
                    }
                });
    }

    /**
     * 更新用户信息
     */
    public void updatePackageInfo(String token, String arrJson) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(""), arrJson);

        httpService.updatePackageInfo(token, body)
                .compose(new CommonTransformerT<UpdateInfoVBean>())
                .subscribe(new CommonSubscriber<UpdateInfoVBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(UpdateInfoVBean audiobean) {
                        if (audiobean != null) {
                            if (audiobean.isOpFlag()) {
                                updateJsonInfoCodeView.onSaveSuccess(audiobean);
                            } else {
                                updateJsonInfoCodeView.onSaveFaile(audiobean.getError().toString());
                            }
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        updateJsonInfoCodeView.onSaveFaile(e.getMessage());
                    }
                });


    }

    /**
     * 底部栏(和侧栏)
     */
    public void queryToolBar(String token) {
        InterfaceToolBarBean barBean = new InterfaceToolBarBean();
        barBean.setClientId(LonchCloudApplication.getAppConfigDataBean().APP_TYPE);
        String jsonRequest = new Gson().toJson(barBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(""), jsonRequest);

        httpService.queryToolBar(token, body)
                .compose(new CommonTransformerT<ToolBarBean>())
                .subscribe(new CommonSubscriber<ToolBarBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(ToolBarBean audiobean) {
                        if (audiobean != null) {
                            if (audiobean.isOpFlag()) {
                                queryToolBarMenusInfoCodeView.onQuerySuccess(audiobean);
                            } else {
                                queryToolBarMenusInfoCodeView.onQueryFaile(audiobean.getError().toString());
                            }
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        queryToolBarMenusInfoCodeView.onQueryFaile(e.getMessage());
                    }
                });


    }

    /**
     * 侧栏组织
     */
    public void getOrganizationList(String token) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("manageProductId", LonchCloudApplication.getAppConfigDataBean().PRODUCT_ID);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(""), GsonUtils.getInstance().toJson(map));

        httpService.selectHumanByToken(token, body)
                .compose(new CommonTransformerT<HumanOrganizationBean>())
                .subscribe(new CommonSubscriber<HumanOrganizationBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(HumanOrganizationBean audiobean) {
                        if (audiobean != null) {
                            if (audiobean.isOpFlag()) {
                                organizationInfoCodeView.onOrganizationSuccss(audiobean);
                            } else {
                                organizationInfoCodeView.onOrganizationFaile(audiobean.getError());
                            }
                        }

                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        organizationInfoCodeView.onOrganizationFaile(e.getMessage());
                    }
                });


    }

    /**
     * 普药商城接口
     */
    public void getYFCUserInfoData(String token) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("manageProductId", "1");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(""), GsonUtils.getInstance().toJson(map));

        httpService.getYfcUserInfo(token, body)
                .compose(new CommonTransformerT<YfcUserBean>())
                .subscribe(new CommonSubscriber<YfcUserBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(YfcUserBean audiobean) {
                        if (audiobean != null) {
                            if (audiobean.isOpFlag()) {
                                if (null != audiobean.getServiceResult()) {
                                    SpUtils.setObject(CommParameter.USERINFOLOGIN, GsonUtils.getInstance().toJson(audiobean.getServiceResult()));
                                }
                                yaoFaCaiInfoView.onYaoFaCaiInfoSuccess(audiobean);
                            }
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        yaoFaCaiInfoView.onYaoFaCaiInfoFaile(e.getMessage());
                    }
                });


    }

    /**
     * app升级
     */
    public void updateApp(String token) {
        InterfaceToolBarBean barBean = new InterfaceToolBarBean();
        barBean.setClientId(LonchCloudApplication.getAppConfigDataBean().APP_TYPE);
        barBean.setAppClientId(LonchCloudApplication.getAppConfigDataBean().APP_CLIENT_ID);
        String jsonRequest = new Gson().toJson(barBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(""), jsonRequest);

        httpService.updateApp(token, body)
                .compose(new CommonTransformerT<AppClientUpdateBean>())
                .subscribe(new CommonSubscriber<AppClientUpdateBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(AppClientUpdateBean audiobean) {
                        if (audiobean != null) {
                            if (audiobean.isOpFlag()) {
                                appClientUpdate.onUpdateSuccess(audiobean);
                            } else {
                                appClientUpdate.onUpdateFaile(audiobean.getError() + "");
                            }
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        appClientUpdate.onUpdateFaile(e.getMessage());
                    }
                });
    }


}
