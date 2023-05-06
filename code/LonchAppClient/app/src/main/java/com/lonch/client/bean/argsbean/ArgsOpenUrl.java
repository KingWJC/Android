package com.lonch.client.bean.argsbean;

import android.text.TextUtils;

public class ArgsOpenUrl {

    /**
     * manageProductId : 1
     * url : http://127.0.0.1:7350/yfc/v1.0.175/index.html#/main/productdetails
     * addChat : false
     * title :
     * query : {"salesmanId":"f51d19c4a7e046ca8671b9431f24f440","id":"b34cea9dee274cf6a4bf2642808b5694","businessType":"012","commodityType":"1"}
     * animated : false
     * isBackTitleBar : false //是否显示标题
     * *新增
     * type
     */

    private String url;
    private String title;


    public ConfigBean getConfig() {
        return config;
    }

    public void setConfig(ConfigBean config) {
        this.config = config;
    }

    private ConfigBean config;




    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class ConfigBean {
        private boolean animated;
        private boolean isBackTitleBar;
        private String type ;
        private String roomId;
        private String roomName;
        private String roomOwner;
        private String avChatRoomId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;

        public String getAnimationType() {
            return animationType;
        }

        public void setAnimationType(String animationType) {
            this.animationType = animationType;
        }

        private String animationType;

        public boolean isAnimated() {
            return animated;
        }

        public void setAnimated(boolean animated) {
            this.animated = animated;
        }

        public boolean isBackTitleBar() {
            return isBackTitleBar;
        }

        public void setBackTitleBar(boolean backTitleBar) {
            isBackTitleBar = backTitleBar;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getRoomOwner() {
            return roomOwner;
        }

        public void setRoomOwner(String roomOwner) {
            this.roomOwner = roomOwner;
        }

        public String getAvChatRoomId() {
            return avChatRoomId;
        }

        public void setAvChatRoomId(String avChatRoomId) {
            this.avChatRoomId = avChatRoomId;
        }

        public String getType() {
            if(TextUtils.isEmpty(type)){
                return "normal";
            }
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
