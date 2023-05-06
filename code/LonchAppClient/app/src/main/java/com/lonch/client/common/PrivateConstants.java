package com.lonch.client.common;

public class PrivateConstants {


    /**
     *正式环境im后台推送证书id
     */
    public static final long HW_PUSH_BUZID = 16503;
    public static final long XM_PUSH_BUZID = 16502;
    public static final long MZ_PUSH_BUZID = 16505;
    public static final long VIVO_PUSH_BUZID = 16506;
    public static final long OPPO_PUSH_BUZID = 16507;

    /**
     * 测试环境im后台推送正式id
     */
//    public static final long HW_PUSH_BUZID = 16568;
//    public static final long XM_PUSH_BUZID = 16571;
//    public static final long MZ_PUSH_BUZID = 16698;
//    public static final long VIVO_PUSH_BUZID = 16699;
//    public static final long OPPO_PUSH_BUZID = 16703;



    /****** 华为离线推送参数start ******/

    // 华为开发者联盟给应用分配的应用APPID
    public static final String HW_PUSH_APPID = "104125047"; // 见清单文件
    /****** 华为离线推送参数end ******/

    /****** 小米离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    // 小米开放平台分配的应用APPID及APPKEY
    public static final String XM_PUSH_APPID = "2882303761519842538";
    public static final String XM_PUSH_APPKEY = "5181984227538";
    /****** 小米离线推送参数end ******/

    /****** 魅族离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID

    // 魅族开放平台分配的应用APPID及APPKEY
    public static final String MZ_PUSH_APPID = "140327";
    public static final String MZ_PUSH_APPKEY = "e66e328cc5754a00897a2bbb234f754e";
    /****** 魅族离线推送参数end ******/

    /****** vivo离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID

    // vivo开放平台分配的应用APPID及APPKEY
    public static final String VIVO_PUSH_APPID = "105474091"; // 见清单文件
    public static final String VIVO_PUSH_APPKEY = "6ef5882af28107654df9eccfec033a4b"; // 见清单文件
    /****** vivo离线推送参数end ******/

    /****** google离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long GOOGLE_FCM_PUSH_BUZID = 16504;
    /****** google离线推送参数end ******/

    /****** oppo离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID

    // oppo开放平台分配的应用APPID及APPKEY
    public static final String OPPO_PUSH_APPID = "30504585";
    public static final String OPPO_PUSH_APPKEY = "5f9126fad89741378ecf79136a370307";
    public static final String OPPO_PUSH_APPSECRET = "cf555d5c6edb475a9d33e324d07fb9c3";
    /****** oppo离线推送参数end ******/

}
