package com.lonch.client.interfaces;


public class JsDataContract {


    public interface JsGetDataView{

        void onResponseSuccess(String sn, String bean, boolean isUpdateToken);
        void onResponseFailed(String sn, String fileMessage);
    }



}
