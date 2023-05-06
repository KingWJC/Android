package com.lonch.client.interfaces;


import com.lonch.client.bean.*;
public class HtmlContract {

    public interface HtmlCodeView{
        void onHtmlSuccess(AppZipBean bean);
        void onHtmlFaile(String fileMessage);
    }

    public interface UpdateJsonInfoCodeView{
        void onSaveSuccess(UpdateInfoVBean bean);
        void onSaveFaile(String fileMessage);
    }
    public interface QueryToolBarMenusInfoCodeView{
        void onQuerySuccess(ToolBarBean bean);
        void onQueryFaile(String fileMessage);
    }
    public interface OrganizationInfoCodeView{
        void onOrganizationSuccss(HumanOrganizationBean bean);
        void onOrganizationFaile(String fileMessage);
    }

    public interface YaoFaCaiInfoView{
        void onYaoFaCaiInfoSuccess(YfcUserBean bean);
        void onYaoFaCaiInfoFaile(String fileMessage);
    }
    public interface AppClientUpdate{
        void onUpdateSuccess(AppClientUpdateBean bean);
        void onUpdateFaile(String fileMessage);
    }
}
