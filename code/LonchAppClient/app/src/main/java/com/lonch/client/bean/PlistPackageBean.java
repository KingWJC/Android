package com.lonch.client.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class PlistPackageBean implements Parcelable {

    /**
     * app_package_name : pivottable
     * file_hash_code : ca268fcd4b0179bca5143ac77bced54d
     * force_update : true
     * fromPackage : false
     * local_path :
     * software_id : 2261993dc1aa480886e2c1180c64137f
     * software_name : 透视图
     * using_online_url : false
     * version : v1.0.7
     * version_id : 9cc6d688a5824f5f841fd4fe2619a241
     * webapp_url : https://test-pivottable.lonch.com.cn
     * zip_path : https://resources.lonch.com.cn/bi-test/app/package/2021/02/01/test-pivottable_v1.0.720210201215814.zip
     */

    private String app_package_name;
    private String file_hash_code;
    private boolean force_update;
    private boolean fromPackage;
    private String local_path;
    private String software_id;
    private String software_name;
    private boolean using_online_url;
    private String version;
    private String version_id;
    private String webapp_url;
    private String zip_path;

    public PlistPackageBean() {

    }

    public String getSoftware_type() {
        return software_type;
    }

    public void setSoftware_type(String software_type) {
        this.software_type = software_type;
    }

    private String  software_type;

    public PlistPackageBean(Parcel in) {
        app_package_name = in.readString();
        file_hash_code = in.readString();
        force_update = in.readByte() != 0;
        fromPackage = in.readByte() != 0;
        local_path = in.readString();
        software_id = in.readString();
        software_name = in.readString();
        using_online_url = in.readByte() != 0;
        version = in.readString();
        version_id = in.readString();
        webapp_url = in.readString();
        zip_path = in.readString();
        software_type= in.readString();
    }

    public static final Creator<PlistPackageBean> CREATOR = new Creator<PlistPackageBean>() {
        @Override
        public PlistPackageBean createFromParcel(Parcel in) {
            return new PlistPackageBean(in);
        }

        @Override
        public PlistPackageBean[] newArray(int size) {
            return new PlistPackageBean[size];
        }
    };


    public String getApp_package_name() {
        return app_package_name;
    }

    public void setApp_package_name(String app_package_name) {
        this.app_package_name = app_package_name;
    }

    public String getFile_hash_code() {
        return file_hash_code;
    }

    public void setFile_hash_code(String file_hash_code) {
        this.file_hash_code = file_hash_code;
    }

    public boolean isForce_update() {
        return force_update;
    }

    public void setForce_update(boolean force_update) {
        this.force_update = force_update;
    }

    public boolean isFromPackage() {
        return fromPackage;
    }

    public void setFromPackage(boolean fromPackage) {
        this.fromPackage = fromPackage;
    }

    public String getLocal_path() {
        return local_path;
    }

    public void setLocal_path(String local_path) {
        this.local_path = local_path;
    }

    public String getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(String software_id) {
        this.software_id = software_id;
    }

    public String getSoftware_name() {
        return software_name;
    }

    public void setSoftware_name(String software_name) {
        this.software_name = software_name;
    }

    public boolean isUsing_online_url() {
        return using_online_url;
    }

    public void setUsing_online_url(boolean using_online_url) {
        this.using_online_url = using_online_url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion_id() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }

    public String getWebapp_url() {
        return webapp_url;
    }

    public void setWebapp_url(String webapp_url) {
        this.webapp_url = webapp_url;
    }

    public String getZip_path() {
        return zip_path;
    }

    public void setZip_path(String zip_path) {
        this.zip_path = zip_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(app_package_name);
        dest.writeString(file_hash_code);
        dest.writeByte((byte) (force_update ? 1 : 0));
        dest.writeByte((byte) (fromPackage ? 1 : 0));
        dest.writeString(local_path);
        dest.writeString(software_id);
        dest.writeString(software_name);
        dest.writeByte((byte) (using_online_url ? 1 : 0));
        dest.writeString(version);
        dest.writeString(version_id);
        dest.writeString(webapp_url);
        dest.writeString(zip_path);
        dest.writeString(software_type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlistPackageBean that = (PlistPackageBean) o;
        return force_update == that.force_update &&
                fromPackage == that.fromPackage &&
                using_online_url == that.using_online_url &&
                Objects.equals(software_type,that.software_type ) &&
                Objects.equals(app_package_name, that.app_package_name) &&
                Objects.equals(file_hash_code, that.file_hash_code) &&
                Objects.equals(local_path, that.local_path) &&
                Objects.equals(software_id, that.software_id) &&
                Objects.equals(software_name, that.software_name) &&
                Objects.equals(version, that.version) &&
                Objects.equals(version_id, that.version_id) &&
                Objects.equals(webapp_url, that.webapp_url) &&
                Objects.equals(zip_path, that.zip_path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app_package_name, file_hash_code, force_update, fromPackage, local_path, software_id, software_name, using_online_url, version, version_id, webapp_url, zip_path, software_type);
    }
}
