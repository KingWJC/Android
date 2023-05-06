package com.lonch.client.utils;

import android.os.CountDownTimer;
import android.util.Log;

public class CountTimer extends CountDownTimer{

    private static final String TAG = "baierepng";

    private static CountTimer INSTANCE;
    private static final int COUNT_VERIFY_CODE = 100;
    private static final int DELAY_MILLIS = 1000;
    private long temp_time = -1;

    private CountTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public static CountTimer getInstance(){
        if(INSTANCE == null){
            INSTANCE = new CountTimer(COUNT_VERIFY_CODE * DELAY_MILLIS, DELAY_MILLIS);
        }
        return INSTANCE;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        temp_time = (millisUntilFinished / 1000);
        Log.d(TAG, "倒计时:" + temp_time);
    }

    public boolean isCountEnd(String keyphone){
        long time = System.currentTimeMillis();
        if(keyphone == null || "".equals(keyphone)){
            Log.d(TAG, "get time key is null");
        }
        long start_time = (long) SpUtils.get(keyphone, (long)-1);
        if(start_time == -1){
            return true;
        }
        if((time-start_time)/1000 > 60){
            return true;
        }else{
            return false;
        }
    }

    public void storeStartTime(String keyphone, long time){
        if(keyphone != null && !"".equals(keyphone)){
            SpUtils.put(keyphone, time);
        }
    }

    @Override
    public void onFinish() {
        Log.d(TAG, "倒计时结束");
    }
}
