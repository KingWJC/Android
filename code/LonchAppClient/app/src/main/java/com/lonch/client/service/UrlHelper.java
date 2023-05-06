package com.lonch.client.service;

/**
 * Created by bai on 2016/9/14.
 */

public class UrlHelper {
    /**
     * 正式环境
     */
//    public static String  SERVICE_URL = "https://gateway.lonch.com.cn"; //online
//    public static String  SERVICE_LOG_URL = "https://operlog.lonch.com.cn/log/sendAppErrorLog";
//    public static int  sdkImAppId = 1400462901;

    /**
     * 测试环境
     */
    public static String SERVICE_URL = "https://test-gateway.lonch.com.cn"; //test
    public static String SERVICE_LOG_URL = "https://test-operlog.lonch.com.cn/log/sendAppErrorLog";
    public static int sdkImAppId = 1400470547;

}
