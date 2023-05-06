package com.lonch.client.base;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.utils.ScreenUtil;
import com.tencent.mmkv.MMKV;

public class BaseActivity extends AppCompatActivity {
    private BaseActivity oContext;
    static float fontScale = 1f;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
    }

    public void showSharePop(View.OnClickListener itemsOnClick) {

//        final View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_share, null);
//        final PopupWindow mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        TextView canaleTv = (TextView) contentView.findViewById(R.id.share_cancle);
//        LinearLayout weiXFriend = (LinearLayout) contentView.findViewById(R.id.weixinghaoyou);
//        LinearLayout friendster = (LinearLayout) contentView.findViewById(R.id.pengyouquan);
//        LinearLayout qywx_share = (LinearLayout) contentView.findViewById(R.id.qywx_share);
//        weiXFriend.setOnClickListener(itemsOnClick);
//        friendster.setOnClickListener(itemsOnClick);
//        qywx_share.setOnClickListener(itemsOnClick);
//        contentView.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = contentView.findViewById(R.id.pop_layout).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        //销毁弹出框
//                        mPopWindow.dismiss();
//                    }
//                }
//                return true;
//            }
//        });
//        canaleTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //销毁弹出框
//                mPopWindow.dismiss();
//            }
//        });
//        mPopWindow.setAnimationStyle(R.style.take_photo_anim);
//        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 1.0f;
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                getWindow().setAttributes(lp);
//            }
//        });
//        mPopWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.5f;
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        getWindow().setAttributes(lp);
    }


    // 添加Activity方法
    public void addActivity() {
        LonchCloudApplication.addActivity_(oContext);// 调用myApplication的添加Activity方法
    }

    //销毁当个Activity方法
    public void removeActivity() {
        LonchCloudApplication.removeActivity_(oContext);// 调用myApplication的销毁单个Activity方法
    }

    //销毁所有Activity方法
    public void removeALLActivity() {
        LonchCloudApplication.removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }

    public void fullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        MMKV mmkv = MMKV.defaultMMKV();
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) { //切换到竖屏
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            mmkv.encode("screenHeight", ScreenUtil.getScreenHeight(oContext));
            mmkv.encode("screenWidth", ScreenUtil.getScreenWidth(oContext));
        } else {
            fullScreen();
            mmkv.encode("screenHeight", ScreenUtil.getScreenWidth(oContext));
            mmkv.encode("screenWidth", ScreenUtil.getScreenHeight(oContext));
        }
    }

    public boolean checkSinglePermission(String strings) {
        boolean isPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, strings) == PackageManager.PERMISSION_GRANTED) {
                isPermission = true;
            } else {
                isPermission = false;
            }

        } else {
            isPermission = true;
        }
        return isPermission;
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (null != resources && resources.getConfiguration().fontScale != 1.0f) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(false)
                .setMessage("请检查您的网络是否正确")
                .setPositiveButton("确定", (dialog, which) -> finish());
        builder.show();
    }

    public void showFileErrorDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(false)
//                .setMessage("下载资源包失败,请退出重试")
//                .setPositiveButton("确定", (dialog, which) -> Utils.reStartWelcome(this));
//        builder.show();
    }

}
