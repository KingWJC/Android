package com.lonch.client.http;

import com.lonch.client.bean.ApiResult;
import com.lonch.client.bean.AppClientUpdateBean;
import com.lonch.client.bean.AppZipBean;
import com.lonch.client.bean.EnterRoomBean;
import com.lonch.client.bean.HumanOrganizationBean;
import com.lonch.client.bean.ImLoginBean;
import com.lonch.client.bean.LivePlayBean;
import com.lonch.client.bean.SaveClientDevicesBean;
import com.lonch.client.bean.ToolBarBean;
import com.lonch.client.bean.UpdateInfoVBean;
import com.lonch.client.bean.VideoBean;
import com.lonch.client.bean.YfcUserBean;
import com.lonch.client.bean.login.LoginCodebean;
import com.lonch.client.bean.login.LoginPwdbean;
import com.lonch.client.bean.login.LoginSmsBean;
import com.lonch.client.bean.login.SendSmsBean;
import com.lonch.client.bean.login.WxLoginBean;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface HttpService {
    @POST("/uaapi/user/sendloginSms")
    Observable<SendSmsBean> sendloginSms(@Body RequestBody phone);

    //登录
    @POST("/uaapi/user/smsdologin")
    Observable<LoginSmsBean> smsdologin(@Body RequestBody loginRequest);

    //账号登录 独立密码
    @POST("/uaapi/user/dologin")
    Observable<LoginPwdbean> pwdDologin(@Body RequestBody loginRequest);

    //游客登录
    @POST("uaapi/user/doUuidlogin2")
    Observable<LoginPwdbean> guestLogin(@Body RequestBody loginRequest);

    //账号登录 独立验证码
    @POST("/uaapi/user/smsdologinUnPassword")
    Observable<LoginCodebean> codeDologin(@Body RequestBody loginRequest);

    //微信登录不区分
    @POST("/uaapi/user/doWXlogin")
    Observable<WxLoginBean> doQyWXlogin(@Body RequestBody loginRequest);

    //注册
    @POST("/uaapi/user/sendCreatUserSmslog")
    Observable<SendSmsBean> sendSmsRegist(@Body RequestBody phone);

    @POST("/uaapi/user/creatUserSmslog")
    Observable<LoginSmsBean> loginRegist(@Body RequestBody phone);

    //忘记密码
    @POST("/uaapi/user/sendSms4loginPwd")
    Observable<SendSmsBean> forgetpwdSend(@Body RequestBody phone);

    @POST("/uaapi/user/loginPwdforget")
    Observable<LoginSmsBean> forgetPwd(@Body RequestBody phone);

    //android 一体机上报商米SN
    @POST("/appClient/App/ReportSunmiDeviceSn")
    Call<ResponseBody> reportSunMiSn(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    //链路加速
    @POST("/appClient/App/QueryAcceleratedLink")
    Call<ResponseBody> getAcceleratedLink(@Header("ACCESS-TOKEN") String token, @Body RequestBody body);

    @POST("/appClient/App/CheckUseNewProtocol")
    Call<ResponseBody> checkUseNewProtocol(@Body RequestBody loginRequest);

    //获取链路优先级， 数字越小优先级越高
    @POST("/appClient/App/QueryUserAcceleratedLinkSetting")
    Call<ResponseBody> getUserLinkSetting(@Header("ACCESS-TOKEN") String token, @Body RequestBody body);
    //上报WebSocket 数据
    @POST("/appClient/App/ReportWebSocketTest")
    Observable<ApiResult> reportWebSocketTest(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    //获取语音识别token
    @POST("/osssign/sign/getNlsToken")
    Call<ResponseBody> getOssNuiToken(@Header("ACCESS-TOKEN") String token, @Body RequestBody body);

    //OSS上传token
    @POST("/osssign/sign/getStsToken")
    Call<ResponseBody> getOSSToken(@Header("ACCESS-TOKEN") String token, @Body RequestBody body);

    @POST("/appClient/App/SaveUserUpdateInfo")
    Observable<UpdateInfoVBean> updatePackageInfo(@Header("ACCESS-TOKEN") String token, @Body RequestBody saveUpdateInfo);

    @POST("/appClient/App/QueryUserWebAppUpdateSettingFromAppV2")
    Observable<AppZipBean> updateSetting(@Header("ACCESS-TOKEN") String token, @Body RequestBody loginRequest);

    @POST("/appClient/App/QueryToolbarMenus")
    Observable<ToolBarBean> queryToolBar(@Header("ACCESS-TOKEN") String token, @Body RequestBody loginRequest);

    @POST("/appClient/App/QueryAppClientUpdate")
    Observable<AppClientUpdateBean> updateApp(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    @POST("/mserver/human/selectHumanByToken")
    Observable<HumanOrganizationBean> selectHumanByToken(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    //获取药发采的个人信息 业务员
    @POST("/yfcapi/user/info")
    Observable<YfcUserBean> getYfcUserInfo(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);


    @POST("/appClient/Broadcast/QueryShortVideosPlayList")
    Observable<VideoBean> getVideoList(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    //腾讯IM 签名
    @POST("/im/userSig/getUserSigByToken")
    Observable<ImLoginBean> imLoginSig(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    //上传授手机信息
    @POST("/appClient/App/SaveClientInstallInfo")
    Observable<SaveClientDevicesBean> saveClientDevices(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    //获取直播sign
    @POST("/appClient/Broadcast/QueryBroadcastUserSig")
    Observable<LivePlayBean> getLiveSign(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    //上报用户进入离开直播间
    @POST("/appClient/Broadcast/ReportUserEnterLeaveRoom")
    Observable<LivePlayBean> reportUserEnterLeaveRoom(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    //上报用户从分享链接进入直播间
    @POST("/appClient/Broadcast/ReportBroadcastRoomShare")
    Observable<LivePlayBean> reportBroadcastRoomShare(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

    //用户进入直播间二次验证
    @POST("/appClient/Broadcast/AllowAudienceEnterRoomFromApp")
    Observable<EnterRoomBean> allowAudienceEnterRoomFromApp(@Header("ACCESS-TOKEN") String token, @Body RequestBody phone);

}
