package com.lonch.client.service;

import android.content.Context;

import com.lonch.client.base.BaseSubscriber;
import com.lonch.client.exception.ApiException;

/**
 * Created by bai on 2016/11/6.
 * com.puppy.account
 */

public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    private Context context;

    public CommonSubscriber(Context context) {
        this.context = context;
    }

    private static final String TAG = "CommonSubscriber";

    @Override
    public void onStart() {

    }

    @Override
    public void onCompleted() {
//        LogUtils.e(TAG, "成功了");
    }

    @Override
    protected void onError(ApiException e) {
//        LogUtils.e(TAG, "错误信息为 " + "code:" + e.code + "   message:" + e.message);
    }
}
