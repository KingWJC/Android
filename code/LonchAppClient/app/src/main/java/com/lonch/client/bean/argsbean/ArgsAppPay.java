package com.lonch.client.bean.argsbean;

public class ArgsAppPay {


    /**
     * type : getDeviceInfo
     */

    private String type;
    private String payType;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
