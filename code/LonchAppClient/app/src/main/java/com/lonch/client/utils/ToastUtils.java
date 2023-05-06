package com.lonch.client.utils;

import android.content.Context;
import android.widget.Toast;

import com.lonch.client.LonchCloudApplication;

/**
 * Created by yinazh on 18-4-17.
 */

public class ToastUtils {

    private static Toast toast;

    public static void showText(String str){
        if(str != null && !str.equals("")){
            showText(LonchCloudApplication.getApplicationsContext(), str);
        }
    }

    public static void showLongText(String str){
        if(str != null && !str.equals("")){
            showText(LonchCloudApplication.getApplicationsContext(), str);
        }
    }

    public static void dismissToast(){
        if(toast != null){
            toast.cancel();
        }
    }

    /**
     * @param context
     * @param content
     */
    public static void showText(Context context,
                                String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
