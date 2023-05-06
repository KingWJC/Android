package com.lonch.client.oss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;

public class OssService {

    public OSS mOss;
    private String mBucket;
    private UploadCallBack mUpload;
    public OssService(OSS oss, String bucket, UploadCallBack mUpload) {
        this.mOss = oss;
        this.mBucket = bucket;
        this.mUpload = mUpload;
    }

    public void setBucketName(String bucket) {
        this.mBucket = bucket;
    }

    public void initOss(OSS _oss) {
        this.mOss = _oss;
    }


    public void asyncPutFile(String object, String localFile) {
        if (object.equals("")) {
            return;
        }
        File file = new File(localFile);
        if (!file.exists()) {
            return;
        }
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(mBucket, object, localFile);

        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (mUpload!=null){
                    mUpload.onSuccess();
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    info = serviceException.toString();
                }
                if (mUpload!=null){
                    mUpload.onError(info);
                }
            }
        });
    }
    public interface UploadCallBack{
        void onError(String msg);
        void onSuccess();
    }


}
