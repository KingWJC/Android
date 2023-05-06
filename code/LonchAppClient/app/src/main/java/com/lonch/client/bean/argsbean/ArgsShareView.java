package com.lonch.client.bean.argsbean;

public class ArgsShareView {

    /**
     * shareType : web
     * webpageUrl : http://127.0.0.1:7350/yfc/v1.0.220/index.html#/main/my
     * title : 分享网页
     * description : 测试分享网页
     * hdImage : https://resources.lonch.com.cn/ybd/image/home-slider-4.jpg
     * thumbImage : https://resources.lonch.com.cn/ybd/image/home-slider-4.jpg?x-oss-process=image/resize,w_360/quality,Q_80
     */

    private String shareType;
    private String webpageUrl;
    private String title;
    private String description;
    private String hdImage;
    private String thumbImage;


    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getWebpageUrl() {
        return webpageUrl;
    }

    public void setWebpageUrl(String webpageUrl) {
        this.webpageUrl = webpageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHdImage() {
        return hdImage;
    }

    public void setHdImage(String hdImage) {
        this.hdImage = hdImage;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }
}
