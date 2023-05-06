package com.lonch.client.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.LogEntity;
import com.lonch.client.utils.LogUtils;
import com.lonch.client.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyWebView extends WebView {
    private String TAG = "MyWebView";
    private int normalCount = 10;
    private List<String> points = new ArrayList<>();
    private Context mContext;

    private GestureDetector gd;

    public MyWebView(@NonNull Context context) {
        super(context);
        init();
        this.mContext = context;
        gd = new GestureDetector(context, simpleOnGestureListener);
    }

    public MyWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        this.mContext = context;
        gd = new GestureDetector(context, simpleOnGestureListener);
    }

    public MyWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        gd = new GestureDetector(context, simpleOnGestureListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        gd = new GestureDetector(context, simpleOnGestureListener);
    }

    private void init() {
        clearCache(true);
        clearHistory();
        getSettings().setDomStorageEnabled(true);
        getSettings().setSupportZoom(false);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setAppCacheEnabled(false);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        getSettings().setJavaScriptEnabled(true);//支持js
        getSettings().setAllowFileAccess(true);  //设置可以访问文件
        getSettings().setLoadsImagesAutomatically(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setBlockNetworkImage(false); // 解决图片不显示
        getSettings().setTextZoom(100);
        getSettings().setGeolocationEnabled(true);
       // syncCookie();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gd.onTouchEvent(event);
            }
        });

    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (!Utils.isFastDoubleClick()) {
                if (points == null) {
                    points = new ArrayList<>();
                }
                points.add(e.getRawX() + "," + e.getRawY());
                String res = Utils.setReportData("click", getUrl(), points);
                LogEntity logEntity = new LogEntity();
                logEntity.setTime(Long.parseLong(Utils.getDate(0)));
                logEntity.setArgs(res);
                logEntity.setFromType("2");
                logEntity.setOperation("click");
                LogUtils.getInstance().insert(logEntity);
                points.clear();
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (points == null) {
                points = new ArrayList<>();
            }
            points.add(e.getRawX() + "," + e.getRawY());
            String res = Utils.setReportData("longPress", getUrl(), points);
            LogEntity logEntity = new LogEntity();
            logEntity.setTime(Long.parseLong(Utils.getDate(0)));
            logEntity.setArgs(res);
            logEntity.setFromType("2");
            logEntity.setOperation("longPress");
            LogUtils.getInstance().insert(logEntity);
            points.clear();
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (points == null) {
                points = new ArrayList<>();
            }
            points.add(e2.getRawX() + "," + e2.getRawY());
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (points != null) {
                List<String> uploadPoints = new ArrayList<>();
                String resData = "";
                if (points.size() > 10) {
                    int count = (points.size() - 2) / (normalCount - 2);
                    for (int i = 0; i < normalCount; i++) {
                        if (i * count < points.size()) {
                            uploadPoints.add(points.get(i * count));
                        }
                    }
                    resData = Utils.setReportData("move", getUrl(), uploadPoints);
                } else {
                    resData = Utils.setReportData("move", getUrl(), points);
                }
                LogEntity logEntity = new LogEntity();
                logEntity.setTime(Long.parseLong(Utils.getDate(0)));
                logEntity.setArgs(resData);
                logEntity.setOperation("move");
                logEntity.setFromType("2");
                LogUtils.getInstance().insert(logEntity);
                points.clear();
            }
            return super.onFling(e1, e2, velocityX, velocityY);

        }
    };
    private void syncCookie(){
        CookieSyncManager.getInstance().createInstance(LonchCloudApplication.getApplicationsContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        CookieSyncManager.getInstance().sync();;
    }
}
