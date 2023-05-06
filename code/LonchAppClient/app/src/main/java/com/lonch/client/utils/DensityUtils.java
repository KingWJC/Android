package com.lonch.client.utils;

import android.content.Context;
import android.util.TypedValue;

import com.lonch.client.LonchCloudApplication;

public class DensityUtils {

    private DensityUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     14      * dp 转 px
     15      *
     16      * @param context
     17      * @param val
     18      * @return
     19      */
    public static int dp2px(Context context, float dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
    public static int dp2px( float dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, LonchCloudApplication.getApplicationsContext().getResources().getDisplayMetrics());
    }
    /**
     27      * sp 转 px
     28      *
     29      * @param context
     30      * @param val
     31      * @return
     32      */
    public static int sp2px(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
    /**
     40      * px 转 dp
     41      *
     42      * @param context
     43      * @param pxVal
     44      * @return
     45      */
    public static float px2dp(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     53      * px 转 sp
     54      *
     55      * @param fontScale
     56      * @param pxVal
     57      * @return
     58      */
    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
    public static int getStatusBarHeight(Context context){
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;

    }

}
