package com.lonch.client.oss;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.bean.StsToken;

public class OssClient {
    private static OssClient ossClient;
    public static OssClient getInstance(){
        if (ossClient==null){
            synchronized (OssClient.class){
                if (ossClient==null){
                    ossClient=new OssClient();
                }
            }
        }
        return ossClient;
    }
    public  synchronized OSS getOSS(StsToken stsToken){
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(stsToken.getAccessKeyId(),stsToken.getAccessKeySecret(),stsToken.getSecurityToken());
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(LonchCloudApplication.getApplicationsContext(), Config.OSS_ENDPOINT, credentialProvider, conf);
        OSSLog.enableLog();
        return oss;
    }
}
