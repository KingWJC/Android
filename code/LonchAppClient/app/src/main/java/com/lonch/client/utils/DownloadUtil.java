package com.lonch.client.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lonch.client.bean.PlistPackageBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DownloadUtil {

    private  volatile static DownloadUtil instance;
    private static final byte[] LOCKER = new byte[0];
    private DownloadCallBack downloadCallBack;
    private FileDownloadListener fileDownloadListener;

    public DownloadUtil() {
    }

    public static DownloadUtil getInstance() {
        if (instance == null) {
            synchronized (LOCKER) {
                if (instance == null) {
                    instance = new DownloadUtil();
                }
            }
        }
        return instance;
    }

    private FileDownloadListener createListener(){
        return new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                if (downloadCallBack!=null){
                    Log.i("task----","completed");
                    downloadCallBack.onSuccess((PlistPackageBean) task.getTag(),task.getPath());
                }

            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                if (downloadCallBack!=null){
                    downloadCallBack.onError(e.getMessage());
                }
            }

            @Override
            protected void warn(BaseDownloadTask task) {
            }
        };
    }

    /**
     * 多任务下载
     * @param list
     */
    public void downLoadMultiFiles(List<PlistPackageBean> list){
        if (null!=list && list.size()>0){
            List<BaseDownloadTask> tasks = new ArrayList<>();
            fileDownloadListener = createListener();
            final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(fileDownloadListener);
            for (int i = 0; i < list.size() ; i++) {
                BaseDownloadTask task = FileDownloader.getImpl().create(list.get(i).getZip_path())
                        .setPath(list.get(i).getLocal_path(),false)
                        .setTag(list.get(i));
                tasks.add(task);
            }

            queueSet.setAutoRetryTimes(1);

            //(4)串行下载
            queueSet.downloadSequentially(tasks);

            //(5)任务启动
            queueSet.start();
        }

    }

    public void  downloadSingleFile(PlistPackageBean bean, String path, DownloadCallBack downloadCallBack){
        this.downloadCallBack = downloadCallBack;
        FileDownloader.getImpl().create(bean.getZip_path())
                .setPath(path,true)
                .setAutoRetryTimes(2)
                .setTag(bean)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        if (downloadCallBack!=null){
                            downloadCallBack.onSuccess((PlistPackageBean) task.getTag(),task.getTargetFilePath());
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        if (downloadCallBack!=null){
                            downloadCallBack.onError(e.getMessage());
                        }
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) throws Throwable {
                        Log.i("sizePage----","blockComplete");
                        super.blockComplete(task);
                    }
                }).start();

    }

    public void addOnDownloadListener(DownloadCallBack downloadCallBack) {
        this.downloadCallBack = downloadCallBack;
    }

    public interface DownloadCallBack{
        void onError(String msg);
        void onSuccess(PlistPackageBean bean, String path);
    }

    public void downloadImage(Context mContext, String imageUrl){
        if (TextUtils.isEmpty(imageUrl) ||  !imageUrl.contains("http")){
            Toast.makeText(mContext,"图片地址无法下载", Toast.LENGTH_LONG).show();
        }else{
            if (SDCardUtil.isSDCardEnableByEnvironment()){
                File appDir = mContext.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsoluteFile();
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName= System.currentTimeMillis() + ".jpg";
                final File target = new File(appDir, fileName);
                FileDownloader.getImpl().
                        create(imageUrl)
                        .setPath(target.getAbsolutePath(),false)
                        .setListener(new FileDownloadSampleListener(){
                            @Override
                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                super.pending(task, soFarBytes, totalBytes);
                            }

                            @Override
                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                super.progress(task, soFarBytes, totalBytes);
                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {
                                super.completed(task);
                                Toast.makeText(mContext,"图片下载成功,请到相册中查看", Toast.LENGTH_LONG).show();
                                Utils.savePhotoAlbum(mContext,task.getTargetFilePath(),fileName);
                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {
                                super.error(task, e);
                                Toast.makeText(mContext,"图片下载失败,请重试", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                super.paused(task, soFarBytes, totalBytes);
                            }

                            @Override
                            protected void warn(BaseDownloadTask task) {
                                super.warn(task);
                            }

                            @Override
                            protected void blockComplete(BaseDownloadTask task) {
                                super.blockComplete(task);
                            }
                        }).start();
            }else {
                Toast.makeText(mContext,"手机暂不支持内存卡存储", Toast.LENGTH_LONG).show();
            }
        }
    }
}
