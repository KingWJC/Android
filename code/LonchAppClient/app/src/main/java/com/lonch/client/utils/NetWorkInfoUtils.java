package com.lonch.client.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * User:白二鹏
 * Created by Administrator-11-13 21 : 08
 */

public class NetWorkInfoUtils {


    /**
     * 没有连接网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;


    /**
     * 网络判断方法
     */

    public static int verify(Context context){

        //网络连接管理器
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //网络可用对象
        NetworkInfo info = manager.getActiveNetworkInfo();

        //如果对象不为空  说明有活动的网络对象
        if(info !=null){

            //判读是否属于手机信号
            if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                //不让加载图片
                return NETWORK_MOBILE;
            }else if(info.getType() == ConnectivityManager.TYPE_WIFI){
                //加载大图
                return NETWORK_WIFI;
            }else {
                //没有 有效网络 wifi 可能没有网
                return NETWORK_NONE;
            }

        }else {
            //没有 有效网络
            return NETWORK_NONE;
        }

    }

}
