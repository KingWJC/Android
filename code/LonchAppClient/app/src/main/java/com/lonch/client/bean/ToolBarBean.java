package com.lonch.client.bean;

import java.util.List;

public class ToolBarBean {


    /**
     * code : null
     * opFlag : true
     * error : null
     * serviceResult : {"siderbars":[{"id":"d9dd1f3dccb84d9c96c805ca79f6e1d7","name":"新增功能","type":"2","web_app_id":"ad9acc66a69146de9a2b26ae6878a41a","url_path":"index.html?#/main/sidebar/addFunction","icon":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128121611.png","remark":"新增功能","show_order":1},{"id":"dc3f71f5fe3d488dbdccd304c72d004e","name":"设置","type":"2","web_app_id":"ad9acc66a69146de9a2b26ae6878a41a","url_path":"index.html?#/main/sidebar/setting","icon":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128121649.png","remark":"设置","show_order":2}],"forms":[{"show_order":1,"bottombar":{"id":"348d411b1ee24edeb24536df767467c8","form_id":"5d7590022f204a56b12eaebad73a9511","name":"聊天","type":"2","web_app_id":"ad9acc66a69146de9a2b26ae6878a41a","url_path":"index.html","icon":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128152408.png","icon_hover":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128152412.png","align":"2","toolbar_type":"1"},"topbars":[],"id":"5d7590022f204a56b12eaebad73a9511","title":"聊天","type":"2"},{"show_order":2,"bottombar":{"id":"0c9e023a2dc94536b1f952192367c2fa","form_id":"1bd5d49ab0d24c5f98f8e9a5a7e60ebc","name":"云服","type":"2","web_app_id":"ed44b65e8e8a40768c9d07bc06a0307f","url_path":"index.html","icon":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128124819.png","icon_hover":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128124824.png","align":"2","toolbar_type":"1"},"topbars":[],"id":"1bd5d49ab0d24c5f98f8e9a5a7e60ebc","title":"朗致云服","type":"2"},{"show_order":3,"bottombar":{"id":"3821144eb689460f874c531aad9f2a3b","form_id":"209f938fca8940e3989eeff2511bed92","name":"组织","type":"2","web_app_id":"ad9acc66a69146de9a2b26ae6878a41a","url_path":"index.html?#/main/organize","icon":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128125053.png","icon_hover":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128125056.png","align":"2","toolbar_type":"1"},"topbars":[],"id":"209f938fca8940e3989eeff2511bed92","title":"组织管理","type":"2"}]}
     * timestamp : 2021-03-04 17:04:21
     */

    private Object code;
    private boolean opFlag;
    private Object error;
    private ServiceResultBean serviceResult;
    private String timestamp;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public ServiceResultBean getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(ServiceResultBean serviceResult) {
        this.serviceResult = serviceResult;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class ServiceResultBean {
        private boolean isShowHeaderPortrait;
        private String version;

        public boolean isShowHeaderPortrait() {
            return isShowHeaderPortrait;
        }

        public void setShowHeaderPortrait(boolean showHeaderPortrait) {
            isShowHeaderPortrait = showHeaderPortrait;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        private List<SiderbarsBean> siderbars;
        private List<FormsBean> forms;

        public List<SiderbarsBean> getSiderbars() {
            return siderbars;
        }

        public void setSiderbars(List<SiderbarsBean> siderbars) {
            this.siderbars = siderbars;
        }

        public List<FormsBean> getForms() {
            return forms;
        }

        public void setForms(List<FormsBean> forms) {
            this.forms = forms;
        }

        public static class SiderbarsBean {
            /**
             * id : d9dd1f3dccb84d9c96c805ca79f6e1d7
             * name : 新增功能
             * type : 2
             * web_app_id : ad9acc66a69146de9a2b26ae6878a41a
             * url_path : index.html?#/main/sidebar/addFunction
             * icon : https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128121611.png
             * remark : 新增功能
             * show_order : 1
             */

            private String id;
            private String name;
            private String type;
            private String web_app_id;
            private String url_path;
            private String icon;
            private String remark;
            private int show_order;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getWeb_app_id() {
                return web_app_id;
            }

            public void setWeb_app_id(String web_app_id) {
                this.web_app_id = web_app_id;
            }

            public String getUrl_path() {
                return url_path;
            }

            public void setUrl_path(String url_path) {
                this.url_path = url_path;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getShow_order() {
                return show_order;
            }

            public void setShow_order(int show_order) {
                this.show_order = show_order;
            }
        }

        public static class FormsBean {
            /**
             * show_order : 1
             * bottombar : {"id":"348d411b1ee24edeb24536df767467c8","form_id":"5d7590022f204a56b12eaebad73a9511","name":"聊天","type":"2","web_app_id":"ad9acc66a69146de9a2b26ae6878a41a","url_path":"index.html","icon":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128152408.png","icon_hover":"https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128152412.png","align":"2","toolbar_type":"1"}
             * topbars : []
             * id : 5d7590022f204a56b12eaebad73a9511
             * title : 聊天
             * type : 2
             */

            private int show_order;
            private BottombarBean bottombar;
            private String id;
            private String title;
            private String type;
            private List<?> topbars;

            public int getShow_order() {
                return show_order;
            }

            public void setShow_order(int show_order) {
                this.show_order = show_order;
            }

            public BottombarBean getBottombar() {
                return bottombar;
            }

            public void setBottombar(BottombarBean bottombar) {
                this.bottombar = bottombar;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<?> getTopbars() {
                return topbars;
            }

            public void setTopbars(List<?> topbars) {
                this.topbars = topbars;
            }

            public static class BottombarBean {
                /**
                 * id : 348d411b1ee24edeb24536df767467c8
                 * form_id : 5d7590022f204a56b12eaebad73a9511
                 * name : 聊天
                 * type : 2
                 * web_app_id : ad9acc66a69146de9a2b26ae6878a41a
                 * url_path : index.html
                 * icon : https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128152408.png
                 * icon_hover : https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128152412.png
                 * align : 2
                 * toolbar_type : 1
                 */

                private String id;
                private String form_id;
                private String name;
                private String type;
                private String web_app_id;
                private String url_path;
                private String icon;
                private String icon_hover;
                private String align;
                private String toolbar_type;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getForm_id() {
                    return form_id;
                }

                public void setForm_id(String form_id) {
                    this.form_id = form_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getWeb_app_id() {
                    return web_app_id;
                }

                public void setWeb_app_id(String web_app_id) {
                    this.web_app_id = web_app_id;
                }

                public String getUrl_path() {
                    return url_path;
                }

                public void setUrl_path(String url_path) {
                    this.url_path = url_path;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getIcon_hover() {
                    return icon_hover;
                }

                public void setIcon_hover(String icon_hover) {
                    this.icon_hover = icon_hover;
                }

                public String getAlign() {
                    return align;
                }

                public void setAlign(String align) {
                    this.align = align;
                }

                public String getToolbar_type() {
                    return toolbar_type;
                }

                public void setToolbar_type(String toolbar_type) {
                    this.toolbar_type = toolbar_type;
                }
            }
        }
    }
}
