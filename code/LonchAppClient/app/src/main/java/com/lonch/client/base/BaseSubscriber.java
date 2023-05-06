package com.lonch.client.base;


import android.util.Log;

import com.lonch.client.exception.ApiException;

import rx.Subscriber;


public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        try{
            ApiException apiException = (ApiException) e;
            onError(apiException);
        }catch(Exception other_error){
            Log.d("PuppyAccount", other_error.getMessage());
        }
    }


    /**
     * @param e 错误的一个回调
     */
    protected abstract void onError(ApiException e);

}
