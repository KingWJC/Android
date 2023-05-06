package com.lonch.client.utils;

import static com.lonch.client.utils.ImageUtil.buildTransaction;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lonch.client.LonchCloudApplication;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.wework.api.IWWAPI;
import com.tencent.wework.api.WWAPIFactory;
import com.tencent.wework.api.model.WWMediaImage;
import com.tencent.wework.api.model.WWMediaLink;
import com.tencent.wework.api.model.WWMediaText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;


public class WxShareUtils {
    /**
     * 分享网页类型至微信
     * @param context 上下文
     * @param webUrl  网页的url
     * @param title   网页标题
     * @param content 网页描述
     * @param imgUrl  位图
     */
    public static void shareWeb(Context context, final String webUrl, final String title, final String content, final String imgUrl, final int shareType) {

        // 通过appId得到IWXAPI这个对象
        final IWXAPI wxapi = WXAPIFactory.createWXAPI(context, LonchCloudApplication.getAppConfigDataBean().WECHAT_ID, false);
        wxapi.registerApp(LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            ToastUtils.showText("您还没有安装微信");
            return;
        }

        new Thread(() -> {
            try{

                // 初始化一个WXWebpageObject对象
                WXWebpageObject webpageObject = new WXWebpageObject();
                // 填写网页的url
                webpageObject.webpageUrl = webUrl;
                // 用WXWebpageObject对象初始化一个WXMediaMessage对象
                WXMediaMessage msg = new WXMediaMessage(webpageObject);
                // 填写网页标题、描述、位图
                msg.title = title;
                msg.description = content;

                try{
                    Bitmap thumb=BitmapFactory.decodeStream(new URL(imgUrl).openStream());
                    //注意下面的这句压缩，120，150是长宽。
                    //一定要压缩，不然会分享失败
                    Bitmap thumbBmp=Bitmap.createScaledBitmap(thumb,120,150,true);
                    //Bitmap回收
                    thumb.recycle();
                    msg.thumbData=ImageUtil.bmpToByteArray(thumbBmp,true);
                }catch(IOException e){
                    e.printStackTrace();
                }

                // 构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                // transaction用于唯一标识一个请求（可自定义）
                req.transaction =  buildTransaction("lonch");
                // 上文的WXMediaMessage对象
                req.message = msg;
                // SendMessageToWX.Req.WXSceneSession是分享到好友会话
                // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
                if(shareType == 0){
                    req.scene = SendMessageToWX.Req.WXSceneSession;
                }else {
                    req.scene = SendMessageToWX.Req.WXSceneTimeline;
                }

                // 向微信发送请求
                wxapi.sendReq(req);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


    }

    /**
     * 分享网页类型至微信
     * @param context 上下文
     * @param title   网页标题
     * @param content 网页描述
     */
    public static void shareText(Context context, final String title, final String content, final int shareType) {

        // 通过appId得到IWXAPI这个对象
        final IWXAPI wxapi = WXAPIFactory.createWXAPI(context, LonchCloudApplication.getAppConfigDataBean().WECHAT_ID, false);
        wxapi.registerApp(LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            ToastUtils.showText("您还没有安装微信");
            return;
        }

        new Thread(() -> {
            try{

                //初始化一个 WXTextObject 对象，填写分享的文本内容
                WXTextObject textObj = new WXTextObject();
                textObj.text = title;

                //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = textObj;
                msg.description = content;

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("text");
                req.message = msg;
                if(shareType == 0){
                    req.scene = SendMessageToWX.Req.WXSceneSession;
                }else {
                    req.scene = SendMessageToWX.Req.WXSceneTimeline;
                }

                //调用api接口，发送数据到微信
                wxapi.sendReq(req);



            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


    }

    /**
     * 分享网页类型至微信
     * @param context 上下文
     * @param imgUrl  位图
     */
    public static void shareImage(Context context, final String imgUrl, final int shareType) {

        // 通过appId得到IWXAPI这个对象
        final IWXAPI wxapi = WXAPIFactory.createWXAPI(context, LonchCloudApplication.getAppConfigDataBean().WECHAT_ID, false);
        wxapi.registerApp(LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            ToastUtils.showText("您还没有安装微信");
            return;
        }

        new Thread(() -> {
            try{
                Bitmap bmp=BitmapFactory.decodeStream(new URL(imgUrl).openStream());
                //注意下面的这句压缩，120，150是长宽。
                //一定要压缩，不然会分享失败

                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXImageObject imgObj = new WXImageObject(bmp);
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;

                //设置缩略图
                Bitmap thumbBmp=Bitmap.createScaledBitmap(bmp,120,150,true);

                bmp.recycle();
                msg.thumbData = ImageUtil.bmpToByteArray(thumbBmp, true);

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                if(shareType == 0){
                    req.scene = SendMessageToWX.Req.WXSceneSession;
                }else {
                    req.scene = SendMessageToWX.Req.WXSceneTimeline;
                }
//                    req.userOpenId = getOpenId();
                //调用api接口，发送数据到微信
                wxapi.sendReq(req);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


    }


    /**
     * 分享网页类型至微信企业
     * @param context 上下文
     * @param imgUrl  位图
     */

    public static void QyWxshareWeb(Context context, final String webUrl, final String title, final String content, final String imgUrl) {
        //企业微信
        IWWAPI iwwapi = WWAPIFactory.createWWAPI(context);
        iwwapi.registerApp(LonchCloudApplication.getAppConfigDataBean().QIYE_SCHEMA);
        // 通过appId得到IWXAPI这个对象
        WWMediaLink link = new WWMediaLink();
        link.thumbUrl = imgUrl;
        link.webpageUrl = webUrl;
        link.title = title;
        link.description = content;
        link.appPkg = LonchCloudApplication.getAppConfigDataBean().APP_PROCESS_NAME;
        link.appName = LonchCloudApplication.getAppConfigDataBean().appName;
        link.appId = LonchCloudApplication.getAppConfigDataBean().QIYE_APPID;
        link.agentId = LonchCloudApplication.getAppConfigDataBean().QIYE_AGENTID;
        iwwapi.sendMessage(link);
        // 检查手机或者模拟器是否安装了微信
    }
    public static void QyWxShareText(Context context, final String title, final String content) {
        //企业微信
        IWWAPI iwwapi = WWAPIFactory.createWWAPI(context);
        iwwapi.registerApp(LonchCloudApplication.getAppConfigDataBean().QIYE_SCHEMA);
        WWMediaText txt;
        if(TextUtils.isEmpty(content)){
            txt = new WWMediaText(
                    title);
        }else {
            txt = new WWMediaText(
                    content);
        }

        txt.appPkg = LonchCloudApplication.getAppConfigDataBean().APP_PROCESS_NAME;
        txt.appName = LonchCloudApplication.getAppConfigDataBean().appName;
        txt.appId = LonchCloudApplication.getAppConfigDataBean().QIYE_APPID;
        txt.agentId = LonchCloudApplication.getAppConfigDataBean().QIYE_AGENTID;
        iwwapi.sendMessage(txt);
    }
    public static void QyWxShareImage(Context context, final String imgUrl) {
        FileDownloader.getImpl().create(imgUrl)
                .setPath(LonchCloudApplication.getApplicationsContext().getExternalCacheDir().getAbsolutePath()+"/"+System.currentTimeMillis()+".jpg")
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        try {
                           String string =  MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                    task.getTargetFilePath(), task.getFilename(), null);
                            Uri uri = Uri.parse(string);

                            String[] projection = { MediaStore.Images.Media.DATA };
                            Cursor cursor = null;

                            try {
                                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                                if (cursor.moveToFirst()) {
                                    //企业微信
                                    IWWAPI iwwapi = WWAPIFactory.createWWAPI(context);
                                    iwwapi.registerApp(LonchCloudApplication.getAppConfigDataBean().QIYE_SCHEMA);
                                    WWMediaImage img = new WWMediaImage();
                                    img.fileName = LonchCloudApplication.getAppConfigDataBean().APP_TYPE;
                                    img.filePath = cursor.getString(0);
                                    img.appPkg = LonchCloudApplication.getAppConfigDataBean().APP_PROCESS_NAME;
                                    img.appName = LonchCloudApplication.getAppConfigDataBean().appName;
                                    img.appId = LonchCloudApplication.getAppConfigDataBean().QIYE_APPID;
                                    img.agentId = LonchCloudApplication.getAppConfigDataBean().QIYE_AGENTID;
                                    iwwapi.sendMessage(img);
                                    return;
                                }
                            } catch (Exception e) {
                                ToastUtils.showText("图片处理失败,请重试！");
                            }
                            if (cursor != null) {
                                cursor.close();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            ToastUtils.showText("图片处理失败,请重试！");
                        }
                    }


                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        ToastUtils.showText("图片处理失败,请重试！");
                    }

                }).start();

    }
}
