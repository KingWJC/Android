package com.lonch.client.common;

public class AppConstants {



    //权限申请requestCode
    public static final int ACTIVITY_CAMERA_REQ_PERMISSION_CODE = 800;
    public static final int ACTIVITY_LOCATION_REQ_PERMISSION_CODE = 801;
    public static final int ACTIVITY_STORE_REQ_PERMISSION_CODE = 802;
    public static final int ACTIVITY_FILE_REQ_PERMISSION_CODE = 803;
    public static final int ACTIVITY_AUDIO_REQ_PERMISSION_CODE = 804;
    public static final int ACTIVITY_WEB_CAMERA_REQ_PERMISSION_CODE = 805;
    public static final int ACTIVITY_WRITE_STORE_REQ_PERMISSION_CODE = 806;

    public static final int FRAGMENT_CAMERA_REQ_PERMISSION_CODE = 900;
    public static final int FRAGMENT_LOCATION_REQ_PERMISSION_CODE = 901;
    public static final int FRAGMENT_STORE_REQ_PERMISSION_CODE = 902;
    public static final int FRAGMENT_AUDIO_REQ_PERMISSION_CODE = 903;
    public static final int FRAGMENT_FILE_REQ_PERMISSION_CODE = 904;
    public static final int FRAGMENT_WEB_CAMERA_REQ_PERMISSION_CODE = 805;

    //appClientInfo 中协议变更数据版本号
    public static final int APP_BIG_VERSION = 20220401;
    /**
     * 协议Command名称
     */
    public static class WebViewCommandProtocalName {
        /* 获取app的一些信息，如版本号、终端类型等公共信息 */
        public static final String GET_APP_INFO = "GetAppInfo";
        /* H5获取app的数据 */
        public static final String GET_DATA = "GetData";
        /* 商城支付 */
        public static final String SHOP_PAY = "pay";
        /* 打开小程序 */
        public static final String OPEN_MINI_PROGRAM = "openMiniProgram";
    }

}
