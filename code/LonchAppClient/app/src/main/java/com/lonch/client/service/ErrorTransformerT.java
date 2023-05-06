package com.lonch.client.service;

import com.lonch.client.exception.ApiException;
import com.lonch.client.exception.ErrorType;
import com.lonch.client.exception.ExceptionEngine;
import com.lonch.client.exception.ServerException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by gaosheng on 2016/11/6.
 * 23:00
 * com.example.gaosheng.myapplication.transformer
 */

public class ErrorTransformerT<T> implements Observable.Transformer<T, T> {

    private static ErrorTransformerT errorTransformer = null;
    private static final String TAG = "ErrorTransformer";

    @Override
    public Observable<T> call(Observable<T> responseObservable) {

        return responseObservable.map(new Func1<T, T>() {
            @Override
            public T call(T httpResult) {

                if (httpResult == null)
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");

//                /**
//                 * 判断是返回码是否为成功 1~
//                 */
//                if (httpResult.getStatus() != ErrorType.SUCCESS)
//                    throw new ServerException(httpResult.getStatus(), httpResult.getMessage());
                return httpResult;
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                try{
//                    Log.d(throwable);
                    return Observable.error(ExceptionEngine.handleException(throwable));
                }catch (Exception e){
//                    Log.d(e.toString());
                    return Observable.error(new ApiException(e, ErrorType.UNKONW));
                }
            }
        });

    }

    /**
     * @return 线程安全, 双层校验
     */
    public static <T> ErrorTransformerT<T> getInstance() {

        if (errorTransformer == null) {
            synchronized (ErrorTransformerT.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformerT<>();
                }
            }
        }
        return errorTransformer;

    }
}
