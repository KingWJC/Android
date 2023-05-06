package com.lonch.client.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lonch.client.R;
import com.lonch.client.utils.QRCodeDecoder;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.lang.ref.WeakReference;

public class MyZxingActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN_GALLERY = 4005;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zxing);

        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();


        ImageView cancel = findViewById(R.id.id_zxing_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView photo = findViewById(R.id.id_zxing_photo);
        photo.setOnClickListener(photoOnClick);

    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            MyZxingActivity.this.setResult(RESULT_OK, resultIntent);
            MyZxingActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            MyZxingActivity.this.setResult(RESULT_OK, resultIntent);
            MyZxingActivity.this.finish();
        }
    };

    private View.OnClickListener photoOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //动态权限申请
            if (ContextCompat.checkSelfPermission(MyZxingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MyZxingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
            } else {
                openPhoto();
            }


        }
    };

    private void openPhoto() {
        Intent wrapperIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            //打开手机中的相册
            Intent innerIntent = new Intent(Intent.ACTION_PICK); // "android.intent.action.GET_CONTENT"
//            innerIntent.addCategory(Intent.CATEGORY_OPENABLE);
            innerIntent.setType("image/*");
            wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            wrapperIntent = Intent.createChooser(intent, "选择二维码图片");
        }
        startActivityForResult(wrapperIntent, REQUEST_CODE_SCAN_GALLERY);

    }

    /**
     * 扫码
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 11:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    //打开手机中的相册
                    openPhoto();
                } else {
                    Toast.makeText(this, "你拒绝了权限申请，无法识别图片！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_GALLERY:
                    handleAlbumPic(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取选取的图片的绝对地址
     *
     * @param c
     * @param uri
     * @return
     */
    private String getRealFilePath(Context c, Uri uri) {
        String photo_path = "";
        if (!TextUtils.isEmpty(uri.getAuthority())) {
            Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (null == cursor) {
                Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                return "";
            }

            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            photo_path = cursor.getString(column_index);
            cursor.close();
        } else {
            photo_path = uri.getPath();
        }
        return photo_path;
    }


    /**
     * 处理选择的图片
     *
     * @param data
     */
    private void handleAlbumPic(Intent data) {

        //获取选中图片的路径
        final String photoPath = getRealFilePath(this, data.getData());
        if (TextUtils.isEmpty(photoPath)) {
            Toast.makeText(getApplicationContext(), "路径获取失败", Toast.LENGTH_SHORT).show();
            return;
        } else {
            parsePhoto(photoPath);
        }


    }

    /**
     * 启动线程解析二维码图片
     *
     * @param path
     */
    private void parsePhoto(String path) {
        //启动线程完成图片扫码
        new QrCodeAsyncTask(this, path).execute(path);
    }

    /**
     * AsyncTask 静态内部类，防止内存泄漏
     */
    static class QrCodeAsyncTask extends AsyncTask<String, Integer, String> {
        private WeakReference<Activity> mWeakReference;
        private String path;
        private final ProgressDialog mProgress;

        public QrCodeAsyncTask(Activity activity, String path) {
            mWeakReference = new WeakReference<>(activity);
            mProgress = new ProgressDialog(activity);
            mProgress.setMessage("正在扫描...");
            mProgress.setCancelable(false);
            mProgress.show();
            this.path = path;
        }

        @Override
        protected String doInBackground(String... strings) {
            // 解析二维码/条码
            return QRCodeDecoder.syncDecodeQRCode(path);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //识别出图片二维码/条码，内容为s
            MyZxingActivity activity = (MyZxingActivity) mWeakReference.get();
            if (activity != null) {
                activity.handleQrCode(s);
            }
            if (activity!=null && !activity.isFinishing()){
                if (mProgress!=null){
                    mProgress.dismiss();
                }
            }

        }
    }

    /**
     * 处理图片二维码解析的数据
     *
     * @param result
     */
    public void handleQrCode(String result) {
        if (result != null) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(MyZxingActivity.this, "识别失败", Toast.LENGTH_SHORT).show();
        }
    }


}