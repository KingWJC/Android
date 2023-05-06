package com.lonch.client.interfaces;

import com.lonch.client.bean.login.*;

public class LoginContract {

    //验证码发送
    public interface sendSmsCodeView{
        void onSendSuccess(SendSmsBean bean);
        void onSendFaile(String fileMessage);
    }
    //登录
    public interface LoginResponseView {
        void onloginSuccess(LoginPwdbean bean);
        void onloginCodeSuccess(LoginCodebean bean);
        void onloginFaile(String message);
    }

    //企业微信登录
    public interface QyWxLoginResponseView {
        void onQyWxloginSuccess(WxLoginBean bean);
        void onQyWxloginFaile(String message);
        void onWXBand(WxLoginBean bean);

    }
    //个人微信登录
    public interface UserWxLoginResponseView {
        void onUserloginSuccess(LoginSmsBean bean);
        void onUserloginFaile(String message);
    }

    //验证码发送注册
    public interface RegistSendSmsCodeView{
        void onRegistSendSuccess(SendSmsBean bean);
        void onRegistSendFaile(String fileMessage);
    }
    //注册
    public interface RegistResponseView {
        void onRegistloginSuccess(LoginSmsBean bean);
        void onRegistloginFaile(String message);
    }

    /**
     * 找回密码验证码
     */
    public interface ResetPwdSendSmsCodeView{
        void onRegistSendSuccess(SendSmsBean bean);
        void onRegistSendFaile(String fileMessage);
    }
    //找回密码
    public interface ResetPwdPwdView{
        void onRegistSendSuccess(SendSmsBean bean);
        void onRegistSendFaile(String fileMessage);
    }
    //找回
    public interface ResetPwdResponseView {
        void onRegistloginSuccess(LoginSmsBean bean);
        void onRegistloginFaile(String message);
    }

}
