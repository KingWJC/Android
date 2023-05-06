package com.lonch.client.bean.argsbean;

public class ArgsAppPayZhiAi {


    /**
     * type : doAppPay
     * data : {"returnCode":"0000","returnMessage":"交易成功","lineUserId":null,"lineOrderNo":null,"mchId":null,"wxpayOrderNo":null,"amount":null,"zyhpayOrderNo":"7872378c969f4a6f8030835075781fe1","aliWechat":null,"qrCodeUrl":"{\"msgType\":\"trade.precreate\",\"qrCode\":\"https://qr.alipay.com/bax00093aonf1rd3oh1q80fc\"}","version":null,"tranno":null,"jsapiDTO":null}
     * payType : 6
     */

    private String type;
    private DataBean data;
    private String payType;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public static class DataBean {
        /**
         * returnCode : 0000
         * returnMessage : 交易成功
         * lineUserId : null
         * lineOrderNo : null
         * mchId : null
         * wxpayOrderNo : null
         * amount : null
         * zyhpayOrderNo : 7872378c969f4a6f8030835075781fe1
         * aliWechat : null
         * qrCodeUrl : {"msgType":"trade.precreate","qrCode":"https://qr.alipay.com/bax00093aonf1rd3oh1q80fc"}
         * version : null
         * tranno : null
         * jsapiDTO : null
         */

        private String returnCode;
        private String returnMessage;
        private Object lineUserId;
        private Object lineOrderNo;
        private Object mchId;
        private Object wxpayOrderNo;
        private Object amount;
        private String zyhpayOrderNo;
        private Object aliWechat;
        private String qrCodeUrl;
        private Object version;
        private Object tranno;
        private Object jsapiDTO;

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public Object getLineUserId() {
            return lineUserId;
        }

        public void setLineUserId(Object lineUserId) {
            this.lineUserId = lineUserId;
        }

        public Object getLineOrderNo() {
            return lineOrderNo;
        }

        public void setLineOrderNo(Object lineOrderNo) {
            this.lineOrderNo = lineOrderNo;
        }

        public Object getMchId() {
            return mchId;
        }

        public void setMchId(Object mchId) {
            this.mchId = mchId;
        }

        public Object getWxpayOrderNo() {
            return wxpayOrderNo;
        }

        public void setWxpayOrderNo(Object wxpayOrderNo) {
            this.wxpayOrderNo = wxpayOrderNo;
        }

        public Object getAmount() {
            return amount;
        }

        public void setAmount(Object amount) {
            this.amount = amount;
        }

        public String getZyhpayOrderNo() {
            return zyhpayOrderNo;
        }

        public void setZyhpayOrderNo(String zyhpayOrderNo) {
            this.zyhpayOrderNo = zyhpayOrderNo;
        }

        public Object getAliWechat() {
            return aliWechat;
        }

        public void setAliWechat(Object aliWechat) {
            this.aliWechat = aliWechat;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public Object getTranno() {
            return tranno;
        }

        public void setTranno(Object tranno) {
            this.tranno = tranno;
        }

        public Object getJsapiDTO() {
            return jsapiDTO;
        }

        public void setJsapiDTO(Object jsapiDTO) {
            this.jsapiDTO = jsapiDTO;
        }
    }
}
