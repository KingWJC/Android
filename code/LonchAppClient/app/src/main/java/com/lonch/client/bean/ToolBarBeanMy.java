package com.lonch.client.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

public class ToolBarBeanMy implements Parcelable {


    /**
     * id : 0c9e023a2dc94536b1f952192367c2fa
     * form_id : 1bd5d49ab0d24c5f98f8e9a5a7e60ebc
     * name : 云服
     * type : 2
     * web_app_id : ed44b65e8e8a40768c9d07bc06a0307f
     * url_path : index.html
     * icon : https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128124819.png
     * icon_hover : https://resources.lonch.com.cn/bi-test/control-selling/2021/01/28/settings20210128124824.png
     * align : 2 居中
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
    private Fragment fragment;

    public ToolBarBeanMy() {
    }

    public ToolBarBeanMy(String id, String form_id, String name, String type, String web_app_id, String url_path, String icon, String icon_hover, String align, String toolbar_type) {
        this.id = id;
        this.form_id = form_id;
        this.name = name;
        this.type = type;
        this.web_app_id = web_app_id;
        this.url_path = url_path;
        this.icon = icon;
        this.icon_hover = icon_hover;
        this.align = align;
        this.toolbar_type = toolbar_type;
    }

    protected ToolBarBeanMy(Parcel in) {
        id = in.readString();
        form_id = in.readString();
        name = in.readString();
        type = in.readString();
        web_app_id = in.readString();
        url_path = in.readString();
        icon = in.readString();
        icon_hover = in.readString();
        align = in.readString();
        toolbar_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(form_id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(web_app_id);
        dest.writeString(url_path);
        dest.writeString(icon);
        dest.writeString(icon_hover);
        dest.writeString(align);
        dest.writeString(toolbar_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToolBarBeanMy> CREATOR = new Creator<ToolBarBeanMy>() {
        @Override
        public ToolBarBeanMy createFromParcel(Parcel in) {
            return new ToolBarBeanMy(in);
        }

        @Override
        public ToolBarBeanMy[] newArray(int size) {
            return new ToolBarBeanMy[size];
        }
    };

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

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
