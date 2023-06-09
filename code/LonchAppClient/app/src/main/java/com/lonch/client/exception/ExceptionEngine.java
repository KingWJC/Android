package com.lonch.client.exception;

import android.net.ParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by gaosheng on 2016/11/6.
 * 22:15
 * com.example.gaosheng.myapplication.exception
 */

public class ExceptionEngine {
    //对应HTTP的状态码
    private static final int FAIL = 0;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex;

        if (e instanceof ServerException) {             //HTTP错误
            ServerException httpException = (ServerException) e;
            ex = new ApiException(e, httpException.getCode());
            ex.message = e.getMessage();
            return ex;
        } else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ErrorType.PARSE_ERROR);
            ex.message = "解析错误";            //均视为解析错误
            return ex;
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e
                instanceof ConnectTimeoutException) {
            ex = new ApiException(e, ErrorType.NETWORK_ERROR);
            ex.message = "连接失败";  //均视为网络错误
            return ex;
        } else if (e instanceof HttpException) {
            if ("HTTP 404 Not Found".equals(e.getMessage())) {
                ex = new ApiException(e, ((HttpException) e).code());
                ex.message = "没有连接服务器";
            } else {
                ex = new ApiException(e, ((HttpException) e).code());
                ex.message = "其他连接服务器错误";
            }
            return ex;
        } else  if(e instanceof SSLHandshakeException){
            ex = new ApiException(e, ErrorType.SSL_ERROR);
            ex.message = "SSL证书校验错误";            //均视为解析错误
            return ex;
        }
        else {
            ex = new ApiException(e, ErrorType.UNKONW);
            ex.message = "未知错误";          //未知错误
            return ex;
        }
    }

}

