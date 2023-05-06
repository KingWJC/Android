package com.lonch.client.bean.argsbean;

public class ArgsStartNewActivity {


    /**
     * type : other current
     * name : 聊天
     * web_app_id : 46a9e8f330634c86b343f7af0da54a18
     * url_path : /#/main/chat
     * query : userId=f959a8957efa454f9ff2085816ccc93d&name=%E5%AE%8B%E4%BF%8A%E9%BE%99
     * isBackTitleBar
     * topTitle
     */

    private String type;
    private String name;
    private String web_app_id;
    private String url_path;
    private String query;
    private boolean isBackTitleBar;
    private String topTitle;

    public boolean isBackTitleBar() {
        return isBackTitleBar;
    }

    public void setBackTitleBar(boolean backTitleBar) {
        isBackTitleBar = backTitleBar;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
