package com.lonch.client.pay;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.chinaums.pppay.unify.UnifyPayListener;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;

/**
 * 支付工具类
 */
public class PayHelper {
    public static final String TAG = PayHelper.class.getSimpleName();
    private static PayHelper instance;

    public static PayHelper getInstance() {
        if (instance == null) {
            synchronized (PayHelper.class) {
                if (instance == null)
                    instance = new PayHelper();
            }
        }
        return instance;
    }

    /**
     * 银联商务全民付
     */
    public synchronized void chinaumsAliPay(Context context, String payParamsStr, UnifyPayListener unifyPayListener) {
        if (context == null) {
            Log.e(TAG, "PayHelper.payAliPay() error: context is null.");
            return;
        }
        if (!(context instanceof Activity)) {
            Log.e(TAG, "PayHelper.payAliPay() error: context is not a Activity type.");
            return;
        }
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY_MINI_PROGRAM;
        msg.payData = payParamsStr;

        UnifyPayPlugin.getInstance(context).setListener(unifyPayListener);
        UnifyPayPlugin.getInstance(context).sendPayRequest(msg);
    }
}
