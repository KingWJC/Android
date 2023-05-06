package com.lonch.client.bean;

import com.google.gson.JsonObject;

/**
 * {
 *  //流水号
 *     sn:"",
 *   //协议名称
 *     command: ""
 *     args:{
 *       //app内部协议时，args为参数列表，
 *       //代理协议时参数列表为，args.parameters
 *       url:"" // 接口地址
 *       method ""//请求方法，默认：post
 *       parameters "" //接口请求参数列表对象字符串
 *       config "" //请求头设置null||对象字符串，默认：不设置 **--新增配置
 *     }
 * }
 */

public class FromJsBean {


    /**
     * command : getAppProxyData
     * args : {"url":"https://test-gateway.lonch.com.cn/customerservice/customer_back_service/api/checkConsumner","method":"POST","dataType":"JSON","parameters":{"manageProductId":1}}
     * sn : 20210129162656103202
     */

    private String command;
    private ArgsBean args;
    private String sn;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ArgsBean getArgs() {
        return args;
    }

    public void setArgs(ArgsBean args) {
        this.args = args;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public static class ArgsBean {
        /**
         * url : https://test-gateway.lonch.com.cn/customerservice/customer_back_service/api/checkConsumner
         * method : POST
         * dataType : JSON
         * parameters : {"manageProductId":1}
         */

        private String url;
        private String method;
        private String dataType;
        private JsonObject parameters;
        private String config;

        public String getConfig() {
            return config;
        }

        public void setConfig(String config) {
            this.config = config;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public JsonObject getParameters() {
            return parameters;
        }

        public void setParameters(JsonObject parameters) {
            this.parameters = parameters;
        }


    }
}
