package com.lonch.client.model;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.base.BaseModel;
import com.lonch.client.bean.login.LoginPwdbean;
import com.lonch.client.exception.ApiException;
import com.lonch.client.interfaces.LoginContract;
import com.lonch.client.service.CommonSubscriber;
import com.lonch.client.service.CommonTransformerT;

import okhttp3.RequestBody;

public class LoginPhoneModel extends BaseModel {

    LoginContract.sendSmsCodeView responseSendModel;
    LoginContract.LoginResponseView responseLoginModel;
    LoginContract.QyWxLoginResponseView responseQyWxLoginModel;

    public LoginPhoneModel(LoginContract.sendSmsCodeView sendCode, LoginContract.LoginResponseView login, LoginContract.QyWxLoginResponseView qyWxlogin) {
        this.responseSendModel = sendCode;
        this.responseLoginModel = login;
        this.responseQyWxLoginModel = qyWxlogin;
    }
    public LoginPhoneModel(LoginContract.QyWxLoginResponseView qyWxlogin) {
        this.responseQyWxLoginModel = qyWxlogin;
    }

    public LoginPhoneModel(LoginContract.LoginResponseView login) {
        this.responseLoginModel = login;
    }

    /**
     * 登录
     * @param loginJson
     */
    public void login(String loginJson,boolean ispwd){
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(""),loginJson);
            httpService.pwdDologin(body)
                    .compose(new CommonTransformerT<LoginPwdbean>())
                    .subscribe(new CommonSubscriber<LoginPwdbean>(LonchCloudApplication.getApplicationsContext()) {
                        @Override
                        public void onNext(LoginPwdbean audiobean) {
                            if(audiobean.isOpFlag()){
                                responseLoginModel.onloginSuccess(audiobean);
                            }else {
                                responseLoginModel.onloginFaile(audiobean.getError()+""+"");
                            }

                        }

                        @Override
                        protected void onError(ApiException e) {
                            super.onError(e);
                            responseLoginModel.onloginFaile("登录失败");
                        }
                    });
    }
}
