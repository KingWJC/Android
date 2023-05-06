package com.lonch.client.utils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.ZipUtils;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.common.CompressStatus;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class ZipTask extends AsyncTask<Void, Integer, Long> {
    private final PlistPackageBean plistPackageBean;
    private final WeakReference<Handler> handlerWeakReference;
    private final String input;
    private final String outPut;
    public ZipTask(String zipFile, String dest, PlistPackageBean plistPackageBean, Handler handler){
        this.plistPackageBean = plistPackageBean;
        this.handlerWeakReference = new WeakReference<>(handler);
        input = zipFile;
        outPut = dest;
    }
    @Override
    protected Long doInBackground(Void... params) {
        // TODO Auto-generated method stub
        long size = 0;
        try {
            size = unzip();
        } catch (Exception e) {
            e.printStackTrace();
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("packageBean", plistPackageBean);
            bundle2.putString("error",e.getMessage());
            Message msgEnd = new Message();
            msgEnd.setData(bundle2);
            msgEnd.what = CompressStatus.ERROR;
            if (null!=handlerWeakReference && handlerWeakReference.get() != null){
                handlerWeakReference.get().sendMessage(msgEnd);
            }
        }
        return size;
    }

    @Override
    protected void onPostExecute(Long result) {
        // TODO Auto-generated method stub
        //super.onPostExecute(result);
        if(result == 0){
            //解压失败
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("packageBean", plistPackageBean);
            Message msgEnd = new Message();
            msgEnd.setData(bundle2);
            msgEnd.what = CompressStatus.ERROR;
            if (null!=handlerWeakReference && handlerWeakReference.get() != null){
                handlerWeakReference.get().sendMessage(msgEnd);
            }
        }else {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("packageBean", plistPackageBean);
            Message msgEnd = new Message();
            msgEnd.setData(bundle2);
            msgEnd.what = CompressStatus.COMPLETED;
            if (null!=handlerWeakReference && handlerWeakReference.get() != null){
                handlerWeakReference.get().sendMessage(msgEnd);
            }
        }

    }
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        //super.onPreExecute();
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        //super.onProgressUpdate(values);
    }
    private long unzip() throws IOException {
        long extractedSize = 0L;

        List<File> unzipFile =  ZipUtils.unzipFile(input,outPut);
        if (unzipFile.size()>0){
            extractedSize = 100;
        }
        return extractedSize;
    }

}
