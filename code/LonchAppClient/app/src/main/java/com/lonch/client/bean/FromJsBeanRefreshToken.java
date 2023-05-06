package com.lonch.client.bean;


public class FromJsBeanRefreshToken {


    /**
     * command : refreshToken
     * args : {"url":"https://test-gateway.lonch.com.cn/mserver/user/refreshToken","method":"POST","dataType":"JSON","parameters":{"manageProductId":1,"dataOwnerOrgId":"b35ad3546c984591907fc1b3d3cb19d7"}}
     * sn : 20210220210723665640
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
         * url : https://test-gateway.lonch.com.cn/mserver/user/refreshToken
         * method : POST
         * dataType : JSON
         * parameters : {"manageProductId":1,"dataOwnerOrgId":"b35ad3546c984591907fc1b3d3cb19d7"}
         */

        private String url;
        private String method;
        private String dataType;
        private ParametersBean parameters;

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

        public ParametersBean getParameters() {
            return parameters;
        }

        public void setParameters(ParametersBean parameters) {
            this.parameters = parameters;
        }

        public static class ParametersBean {
            /**
             * manageProductId : 1
             * dataOwnerOrgId : b35ad3546c984591907fc1b3d3cb19d7
             */

            private String manageProductId;
            private String dataOwnerOrgId;

            public String getManageProductId() {
                return manageProductId;
            }

            public void setManageProductId(String manageProductId) {
                this.manageProductId = manageProductId;
            }

            public String getDataOwnerOrgId() {
                return dataOwnerOrgId;
            }

            public void setDataOwnerOrgId(String dataOwnerOrgId) {
                this.dataOwnerOrgId = dataOwnerOrgId;
            }
        }
    }
}
