package com.lonch.client.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.base.BaseModel;
import com.lonch.client.bean.EnterRoomBean;
import com.lonch.client.bean.ImLoginBean;
import com.lonch.client.bean.LivePlayBean;
import com.lonch.client.bean.SaveClientDevicesBean;
import com.lonch.client.bean.SaveDevicesSendBean;
import com.lonch.client.bean.VideoBean;
import com.lonch.client.exception.ApiException;
import com.lonch.client.http.Http;
import com.lonch.client.http.HttpService;
import com.lonch.client.interfaces.ImLoginContract;
import com.lonch.client.service.CommonSubscriber;
import com.lonch.client.service.CommonTransformerT;
import com.lonch.client.utils.GsonUtils;
import com.lonch.client.utils.HeaderUtils;
import com.lonch.client.utils.SystemUtil;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class IMLoginModel extends BaseModel {

    ImLoginContract.ImLoginView responseModel;
    ImLoginContract.SaveClientDevicesView saveClientModel;
    ImLoginContract.LiveLoginView liveLoginView;
    ImLoginContract.ReportUserView reportUserView;
    ImLoginContract.EnterLiveRoomView enterLiveRoomView;
    ImLoginContract.ReportUserShareView reportUserShareView;
    ImLoginContract.VideoListView videoListView;

    public IMLoginModel(ImLoginContract.ImLoginView resp, ImLoginContract.SaveClientDevicesView saveClientDevicesView) {
        this.responseModel = resp;
        this.saveClientModel = saveClientDevicesView;
    }

    public IMLoginModel(ImLoginContract.LiveLoginView resp, ImLoginContract.ImLoginView res, ImLoginContract.ReportUserView reportUserView, ImLoginContract.EnterLiveRoomView enterLiveRoomView, ImLoginContract.ReportUserShareView reportUserShareView) {
        this.liveLoginView = resp;
        this.responseModel = res;
        this.reportUserView = reportUserView;
        this.enterLiveRoomView = enterLiveRoomView;
        this.reportUserShareView = reportUserShareView;
    }
    public IMLoginModel(ImLoginContract.VideoListView videoListView){
        this.videoListView = videoListView;
    }

    /**
     * IM登录
     */
    public void imLogin(String token) {
        RequestBody body = RequestBody.create(MediaType.parse(""), "{}");
        httpService.imLoginSig(token, body)
                .compose(new CommonTransformerT<ImLoginBean>())
                .subscribe(new CommonSubscriber<ImLoginBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(ImLoginBean audiobean) {
                        if (audiobean.isOpFlag()) {
                            responseModel.onImLoginSuccess(audiobean);
                        } else {
                            responseModel.onImLoginFatal(audiobean.getError().toString());
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        responseModel.onImLoginFatal(e.getMessage());
                    }
                });

    }

    /**
     * 登录成功上报手机信息
     */
    public void saveClientDevices(Context context, String token) {
        SaveDevicesSendBean sendBean = new SaveDevicesSendBean();
        sendBean.setOs("android");
        sendBean.setAppClientId(LonchCloudApplication.getAppConfigDataBean().APP_CLIENT_ID);
        sendBean.setAppVersion(LonchCloudApplication.getAppConfigDataBean().appVersionName);
        sendBean.setAppType(LonchCloudApplication.getAppConfigDataBean().APP_TYPE);
        sendBean.setDeviceBrand(SystemUtil.getDeviceBrand());
        sendBean.setDeviceId(HeaderUtils.md5(SystemUtil.getAndroidDeviceId(context)));
        sendBean.setDeviceVersion(SystemUtil.getSystemVersion());//android9.0
        String jsonRequest = new Gson().toJson(sendBean);
        RequestBody body = RequestBody.create(MediaType.parse(""), jsonRequest);
        httpService.saveClientDevices(token, body)
                .compose(new CommonTransformerT<SaveClientDevicesBean>())
                .subscribe(new CommonSubscriber<SaveClientDevicesBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(SaveClientDevicesBean audiobean) {
                        if (audiobean.isOpFlag()) {
                            saveClientModel.onSaveDevicesSuccess(audiobean);
                        } else {
                            saveClientModel.onSaveDevicesFaile("");
                        }
                    }
                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        saveClientModel.onSaveDevicesFaile(e.getMessage());
                    }
                });

    }

    public void getLiveSign(String token, String content) {
        RequestBody body = RequestBody.create(MediaType.parse(""), content);
        httpService.getLiveSign(token, body)
                .compose(new CommonTransformerT<>())
                .subscribe(new CommonSubscriber<LivePlayBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(LivePlayBean livePlayBean) {
                        if (livePlayBean.isOpFlag()) {
                            liveLoginView.onLiveLoginSuccess(livePlayBean);
                        } else {
                            liveLoginView.onLiveLoginFatal(livePlayBean.getError());
                        }
                    }
                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        liveLoginView.onLiveLoginFatal(e.getMessage());
                    }
                });
    }

    public void reportUserEnterLeaveRoom(String token, String content) {
        RequestBody body = RequestBody.create(MediaType.parse(""), content);
        httpService.reportUserEnterLeaveRoom(token, body)
                .compose(new CommonTransformerT<>())
                .subscribe(new CommonSubscriber<LivePlayBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(LivePlayBean livePlayBean) {
                        if (livePlayBean.isOpFlag()) {
                            reportUserView.onReportSuccess(livePlayBean);
                        } else {
                            reportUserView.onReportFail(livePlayBean.getError());
                        }
                    }
                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        reportUserView.onReportFail(e.getMessage());
                    }
                });
    }

    public void reportUserShare(String token, String content) {
        RequestBody body = RequestBody.create(MediaType.parse(""), content);
        httpService.reportBroadcastRoomShare(token, body)
                .compose(new CommonTransformerT<>())
                .subscribe(new CommonSubscriber<LivePlayBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(LivePlayBean livePlayBean) {
                        if (livePlayBean.isOpFlag()) {
                            reportUserShareView.onReportSuccess(livePlayBean);
                        }
                    }
                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                    }
                });
    }

    public void checkEnterRoom(String id,String token){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        HttpService httpService = Http.getInstance().getApiService(HttpService.class);
        String data = GsonUtils.getInstance().toJson(hashMap);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data);
        httpService.allowAudienceEnterRoomFromApp(token, body)
                .compose(new CommonTransformerT<>())
                .subscribe(new CommonSubscriber<EnterRoomBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(EnterRoomBean livePlayBean) {
                        if (livePlayBean.isOpFlag()) {
                            enterLiveRoomView.onEnterSuccess(livePlayBean);
                        }else{
                            enterLiveRoomView.onEnterFail();
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        enterLiveRoomView.onEnterFail();
                    }
                });
    }
    public void getVideList(String token,String content){
        RequestBody body = RequestBody.create(MediaType.parse(""), content);
        httpService.getVideoList(token, body)
                .compose(new CommonTransformerT<>())
                .subscribe(new CommonSubscriber<VideoBean>(LonchCloudApplication.getApplicationsContext()) {
                    @Override
                    public void onNext(VideoBean livePlayBean) {
                        if (livePlayBean.isOpFlag()) {
                            videoListView.onVideoListSuccess(livePlayBean);
                        }else{
                            videoListView.onVideoListError();
                        }
                    }
                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        Log.i("ss---",e.getMessage());
                        videoListView.onVideoListError();
                    }
                });
    }


}
