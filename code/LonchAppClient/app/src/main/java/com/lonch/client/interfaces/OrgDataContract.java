package com.lonch.client.interfaces;


import com.lonch.client.bean.CreateOrgBen;
import com.lonch.client.bean.RefreshOrgBen;

public class OrgDataContract {


    public interface OrgDataView{

        void onResponseSuccess(CreateOrgBen bean);
        void onResponseFailed(String fileMessage);
    }


    public interface RefreshDataView{

        void onRefreshSuccess(RefreshOrgBen bean);
        void onRefreshFailed(String fileMessage);
    }
}
