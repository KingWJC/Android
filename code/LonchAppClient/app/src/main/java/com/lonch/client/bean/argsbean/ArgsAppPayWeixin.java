package com.lonch.client.bean.argsbean;

public class ArgsAppPayWeixin {


    /**
     * type : doAppPay
     * userName : gh_99a9dbf7543e
     * path : /pages/pay/wxPay?id=bbdd5daeccff4744a038fb68af3ef02d
     * payType : 5
     */

    private String type;
    private String userName;
    private String path;
    private String payType;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
