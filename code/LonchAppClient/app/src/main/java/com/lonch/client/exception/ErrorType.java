package com.lonch.client.exception;

/**
 * Created by gaosheng on 2016/11/6.
 * com.example.gaosheng.myapplication.exception
 */

public interface ErrorType {

    /**
     * 请求成功
     */
    int SUCCESS = 10000;
    /**
     * 未知错误
     */
    int UNKONW = 1000;

    /**
     * 解析错误
     */
    int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    int NETWORK_ERROR = 1002;

    /**
     * 解析对象为空
     */
    int EMPTY_BEAN = 1004;

    /**
     *SSL 证书有误(暂时)
     */
    int SSL_ERROR = 10010;

    /**
    * 参数错误
    * */
    int ERROR_PARAM = 1005;
}
