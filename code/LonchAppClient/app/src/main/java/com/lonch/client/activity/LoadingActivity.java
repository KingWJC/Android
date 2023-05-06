package com.lonch.client.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.R;
import com.lonch.client.base.BaseWebActivity;
import com.lonch.client.bean.AppLog;
import com.lonch.client.bean.AppZipBean;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.bean.UpdateCurPackBean;
import com.lonch.client.bean.UpdateInfoVBean;
import com.lonch.client.common.CompressStatus;
import com.lonch.client.common.Constants;
import com.lonch.client.database.bean.LocalZipEntity;
import com.lonch.client.database.bean.WebVersionEntity;
import com.lonch.client.database.tabutils.LocalZipUtils;
import com.lonch.client.database.tabutils.WebVersionUtils;
import com.lonch.client.interfaces.HtmlContract;
import com.lonch.client.model.HtmlZipModel;
import com.lonch.client.service.UrlHelper;
import com.lonch.client.utils.DownloadUtil;
import com.lonch.client.utils.FileUtils;
import com.lonch.client.utils.OkHttpUtil;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.Utils;
import com.lonch.client.utils.ZipTask;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoadingActivity extends BaseWebActivity implements HtmlContract.HtmlCodeView, HtmlContract.UpdateJsonInfoCodeView {
    private RotateAnimation rotate;
    private ImageView mHeaderChrysanthemumIv;
    private TextView textView;
    private LinearLayout downloading_ll;
    private HtmlZipModel htmlZipModel;
    private String token;
    private ArrayList<PlistPackageBean> packList;//所有包集合
    private ArrayList<PlistPackageBean> forcePackage;//服务器返回强制更新
    private int forceSize = 0;//数据库存在不强制更新个数
    private boolean showDialog = false; // 是否显示资源包下载错误dialog
    private boolean updateStatue;//当前更新状态 true强制更新
    private List<UpdateCurPackBean> updateCurPackBeanList = new ArrayList<>();
    private int packageForceSize = 0;//计算强制更新的UI
    boolean login = false;
    private final LoadingActivity.MyHandler handler = new LoadingActivity.MyHandler(this);

    final static class MyHandler extends Handler {
        WeakReference<LoadingActivity> loadingActivityWeakReference;

        public MyHandler(LoadingActivity loadingActivity) {
            this.loadingActivityWeakReference = new WeakReference<>(loadingActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingActivity activity = loadingActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                switch (msg.what) {
                    case CompressStatus.ERROR: {
                        Bundle bundle = msg.getData();
                        if (null == bundle) {
                            if (!activity.isFinishing()) {
                                if (!activity.showDialog) {
                                    activity.showFileErrorDialog();
                                    activity.showDialog = true;
                                }
                            }
                            return;
                        }
                        PlistPackageBean packageBean = bundle.getParcelable("packageBean");
                        if (null != packageBean) {
                            String[] split = packageBean.getZip_path().split("\\/");
                            int lastResource = split.length - 1;
                            //Zip包路径
                            final String package_zip_resource = activity.getFilesDir().getAbsolutePath() + "/" + "Zip/" + split[lastResource];
                            File file = new File(package_zip_resource);
                            if (file.exists()) {
                                file.delete();
                            }
                            AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(LonchCloudApplication.getApplicationsContext());
                            appLog.setReqUrl(packageBean.getZip_path());
                            appLog.setReqParam(packageBean.getZip_path());
                            appLog.setErrLevel("warn");
                            appLog.setErrMsg(bundle.getString("error"));
                            appLog.setEventName("H5资源包解压失败");
                            appLog.setErrCode("ANDyfc0002400001");
                            appLog.setRemark("service");
                            OkHttpUtil.getInstance().sendPostRequest(UrlHelper.SERVICE_LOG_URL, appLog);
                        }
                        if (!activity.isFinishing()) {
                            if (!activity.showDialog) {
                                activity.showFileErrorDialog();
                                activity.showDialog = true;
                            }
                        }
                        break;
                    }
                    case CompressStatus.COMPLETED: {
                        Bundle bundle = msg.getData();
                        if (null == bundle) {
                            if (!activity.isFinishing()) {
                                if (!activity.showDialog) {
                                    activity.showFileErrorDialog();
                                    activity.showDialog = true;
                                }
                            }
                            return;
                        }
                        PlistPackageBean packageBean = bundle.getParcelable("packageBean");
                        //资源包数据保存到集合中统一上传
                        activity.updateCurPackBeanList.add(new UpdateCurPackBean(packageBean.getSoftware_id(), packageBean.getVersion_id()));
                        Gson gson = new Gson();
                        String data = gson.toJson(packageBean);
                        FileUtils.saveDataToFile(packageBean, data);
                        //删除所有没用的包
                        FileUtils.deleteOldFile(activity, packageBean);
                        activity.sizePageUI();
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mHeaderChrysanthemumIv = findViewById(R.id.id_loading_iv);
        textView = findViewById(R.id.loading_text);
        downloading_ll = findViewById(R.id.downloading_ll);
        token = (String) SpUtils.get("token", "");
        String startTime = (String) SpUtils.get("curTimeMillis", "10000");
        long endTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(token) || Utils.differHours(Long.parseLong(Objects.requireNonNull(startTime)), endTime) >= Constants.EXPIRATION_TIME) {
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(false)
                    .setMessage("您的登录信息已失效,请退出重新登录")
                    .setPositiveButton("确定", (dialog, which) -> reStartLogin());
            builder.show();
            return;
        }
        if (getIntent() != null) {
            login = getIntent().getBooleanExtra("login", false);
        }
        initAnimation();
        htmlZipModel = new HtmlZipModel(this, this);
        htmlZipModel.updateZipSetting(token);
    }

    private void initAnimation() {
        rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(2000);//设置动画持续周期
        rotate.setRepeatCount(-1);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(10);//执行前的等待时间
        mHeaderChrysanthemumIv.setAnimation(rotate);
    }

    @Override
    public void onHtmlSuccess(AppZipBean bean) {
        if (null == bean) {
            showDialog();
            return;
        }
        AppZipBean.ServiceResultBean serviceResult = bean.getServiceResult();//service对象
        if (null == serviceResult) {
            showDialog();
            return;
        }
        List<AppZipBean.ServiceResultBean.WebAppsBean> webAppsNetWork = serviceResult.getWebApps();//web包
        if (packList == null) {
            packList = new ArrayList<>();
        } else {
            packList.clear();
        }
        if (forcePackage == null) {
            forcePackage = new ArrayList<>();
        } else {
            forcePackage.clear();
        }
        forceSize = 0;
        for (int i = 0; i < webAppsNetWork.size(); i++) {
            PlistPackageBean beanWeb = new PlistPackageBean();
            beanWeb.setSoftware_id(TextUtils.isEmpty(webAppsNetWork.get(i).getSoftware_id()) ? "" : webAppsNetWork.get(i).getSoftware_id());
            beanWeb.setSoftware_name(TextUtils.isEmpty(webAppsNetWork.get(i).getSoftware_name()) ? "" : webAppsNetWork.get(i).getSoftware_name());
            beanWeb.setApp_package_name(TextUtils.isEmpty(webAppsNetWork.get(i).getApp_package_name()) ? "" : webAppsNetWork.get(i).getApp_package_name());
            beanWeb.setWebapp_url(TextUtils.isEmpty(webAppsNetWork.get(i).getWebapp_url()) ? "" : webAppsNetWork.get(i).getWebapp_url());
            beanWeb.setLocal_path(null == webAppsNetWork.get(i).getLocal_path() ? "" : webAppsNetWork.get(i).getLocal_path().toString());
            beanWeb.setZip_path(TextUtils.isEmpty(webAppsNetWork.get(i).getZip_path()) ? "" : webAppsNetWork.get(i).getZip_path());
            beanWeb.setVersion(TextUtils.isEmpty(webAppsNetWork.get(i).getVersion()) ? "" : webAppsNetWork.get(i).getVersion());
            beanWeb.setVersion_id(null == webAppsNetWork.get(i).getVersion_id() ? "" : webAppsNetWork.get(i).getVersion_id());
            beanWeb.setForce_update(webAppsNetWork.get(i).isForce_update());
            beanWeb.setSoftware_type(TextUtils.isEmpty(webAppsNetWork.get(i).getSoftware_type()) ? "" : webAppsNetWork.get(i).getSoftware_type());
            beanWeb.setFile_hash_code(TextUtils.isEmpty(webAppsNetWork.get(i).getFile_hash_code()) ? "" : webAppsNetWork.get(i).getFile_hash_code());
            beanWeb.setUsing_online_url(webAppsNetWork.get(i).isUsing_online_url());
            if (!beanWeb.isUsing_online_url()) {//线上的不下载
                if (!packList.contains(beanWeb)) {
                    packList.add(beanWeb);
                }
                if (beanWeb.isForce_update()) {//是否有强制更新
                    forcePackage.add(beanWeb);
                }
            } else {
                Gson gson = new Gson();
                String data = gson.toJson(beanWeb);
                FileUtils.saveDataToFile(beanWeb, data);
            }
        }

        new Thread(() -> {
            List<WebVersionEntity> webVersionEntities = WebVersionUtils.getInstance().queryAllDevices();
            if (webVersionEntities != null && webVersionEntities.size() > 0) {
                for (int i = 0; i < webVersionEntities.size(); i++) {
                    String software_id = webVersionEntities.get(i).getSoftware_id();
                    String version = webVersionEntities.get(i).getVersion();
                    for (int j = 0; j < forcePackage.size(); j++) {
                        if (software_id.equals(forcePackage.get(j).getSoftware_id())) {
                            if (version.equals(forcePackage.get(j).getVersion())) {
                                //数据库存在,不提示升级
                                forceSize += 1;
                            }
                        }
                    }
                }
            }
            runOnUiThread(() -> {
                if (forceSize == forcePackage.size()) {
                    updateStatue = false;
                    setIntent();
                } else {
                    updateStatue = true;
                    //有的数据库没有，本地就没有 所以强制更新
                    calculationData();
                }
            });
        }).start();
    }

    private void setIntent() {
        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
        intent.putExtra("login", login);
        startActivity(intent);
        finish();
    }


    private void calculationData() {
        for (int i = 0; i < packList.size(); i++) {
            ifPackage(packList.get(i));
        }
    }

    /**
     * 判断本地是否有包
     * 判断包当前版本是否一致
     */
    private void ifPackage(PlistPackageBean packBean) {
        String dataframesFile = FileUtils.getDatafromFile(packBean.getSoftware_id());//本地存储的版本记录
        //配置文件不存在说明没有包 直接下载并return
        if (TextUtils.isEmpty(dataframesFile)) {
            //下载更新配置
            downLoadPackage(packBean);
            return;
        }
        Gson gson = new Gson();
        PlistPackageBean localZipBean = gson.fromJson(dataframesFile, PlistPackageBean.class);

        String packAgeName = getFilesDir().getAbsolutePath() + "/App/" + packBean.getApp_package_name() + "/" + packBean.getVersion();

        if (!TextUtils.isEmpty(packBean.getSoftware_type()) && packBean.getSoftware_type().equals("3")) {
            packAgeName = getFilesDir().getAbsolutePath() + "/App/" + packBean.getApp_package_name();
        }
        final File filesDirZip = new File(packAgeName);
        //包存在
        if (filesDirZip.exists()) {
            String version = localZipBean.getVersion();
            if (version.equals(packBean.getVersion())) {
                sizePageUI();
            } else {
                downLoadPackage(packBean);
            }
        } else {
            //下载更新配置
            downLoadPackage(packBean);
        }
    }

    /**
     * 下载所有类型包
     *
     * @param packBean
     */
    private void downLoadPackage(PlistPackageBean packBean) {
        if (null != packBean) {
            String unZipPath = getFilesDir().getAbsolutePath() + "/App/" + packBean.getApp_package_name() + "/" + packBean.getVersion();
            if (!TextUtils.isEmpty(packBean.getSoftware_type()) && packBean.getSoftware_type().equals("3")) {
                unZipPath = getFilesDir().getAbsolutePath() + "/App/" + packBean.getApp_package_name() + "/";
            }
            downLoadWebAppPackage(packBean, unZipPath);
        }
    }

    /**
     * 这里计算是所有包计算
     * 1:已存在并且版本一致
     * 2:不存在下载完毕
     * 以上都统计
     */
    private void sizePageUI() {
        if (updateStatue) {
            //计算强制更新UI
            packageForceSize++;
            if (null != packList && packList.size() > 0) {
                double progress = (double) packageForceSize / packList.size() * 100;
                progress = Math.floor(progress);
                textView.setText(getString(R.string.loading_resources) + "(" + progress + "%" + ")");
            }
            assert packList != null;
            if (packageForceSize == packList.size()) {
                setIntent();
                //上传服务器
                if (updateCurPackBeanList.size() > 0) {
                    String string = new Gson().toJson(updateCurPackBeanList);
                    htmlZipModel.updatePackageInfo(token, string);
                }
            }
        }

    }

    private void downLoadWebAppPackage(final PlistPackageBean packageBean, String unZipPath) {
        String localZipPath = getCacheDir().getAbsolutePath() + "/" + packageBean.getApp_package_name() + "/" + packageBean.getVersion() + ".zip";
        String webAppMd5 = FileUtils.getFileMD5(new File(localZipPath));
        if (!TextUtils.isEmpty(packageBean.getFile_hash_code()) && webAppMd5.equals(packageBean.getFile_hash_code())) {
            ZipTask zipTask = new ZipTask(localZipPath, unZipPath, packageBean, handler);
            zipTask.execute();
        } else {
            DownloadUtil.getInstance().downloadSingleFile(packageBean, getFilesDir() + File.separator + "Zip", new DownloadUtil.DownloadCallBack() {
                @Override
                public void onError(String msg) {
                    AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(LonchCloudApplication.getApplicationsContext());
                    appLog.setReqUrl(packageBean.getZip_path());
                    appLog.setReqParam(packageBean.getZip_path());
                    appLog.setErrLevel("warn");
                    appLog.setErrMsg(msg);
                    appLog.setEventName("H5资源包下载失败");
                    appLog.setErrCode("ANDyfc0002400001");
                    appLog.setRemark("service");
                    OkHttpUtil.getInstance().sendPostRequest(UrlHelper.SERVICE_LOG_URL, appLog);
                    if (packageBean.isForce_update()) {
                        if (!LoadingActivity.this.isFinishing()) {
                            if (!showDialog) {
                                showFileErrorDialog();
                                showDialog = true;
                            }
                        }
                    }
                }

                @Override
                public void onSuccess(PlistPackageBean bean, String path) {
                    if (updateStatue) {
                        ZipTask zipTask = new ZipTask(path, unZipPath, packageBean, handler);
                        zipTask.execute();
                    } else {
                        LocalZipEntity localZipEntity = new LocalZipEntity();
                        localZipEntity.setPath(path);
                        localZipEntity.setSoftware_id(bean.getSoftware_id());
                        localZipEntity.setVersion(bean.getVersion());
                        localZipEntity.setJson(new Gson().toJson(packageBean));
                        LocalZipUtils.getInstance().insert(localZipEntity);
                    }

                }
            });
        }
    }

    @Override
    public void onHtmlFaile(String fileMessage) {
        downloading_ll.setVisibility(View.GONE);
        showDialog();
    }

    @Override
    public void onSaveSuccess(UpdateInfoVBean bean) {

    }

    @Override
    public void onSaveFaile(String fileMessage) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                return false;//禁止用户取消
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        forceSize = 0;
        packageForceSize = 0;
        mHeaderChrysanthemumIv.clearAnimation();
        rotate = null;
    }
}