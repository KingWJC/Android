package com.lonch.client.interfaces;


import com.lonch.client.bean.EnterRoomBean;
import com.lonch.client.bean.ImLoginBean;
import com.lonch.client.bean.LivePlayBean;
import com.lonch.client.bean.SaveClientDevicesBean;
import com.lonch.client.bean.VideoBean;

public class ImLoginContract {

    public interface ImLoginView{
        void onImLoginSuccess(ImLoginBean bean);
        void onImLoginFatal(String fileMessage);
    }

    public interface LiveLoginView{
        void onLiveLoginSuccess(LivePlayBean bean);
        void onLiveLoginFatal(String fileMessage);
    }

    public interface SaveClientDevicesView{
        void onSaveDevicesSuccess(SaveClientDevicesBean bean);
        void onSaveDevicesFaile(String fileMessage);
    }

    public interface ReportUserView{
        void onReportSuccess(LivePlayBean bean);
        void onReportFail(String fileMessage);
    }
    public interface EnterLiveRoomView{
        void onEnterSuccess(EnterRoomBean enterRoomBean);
        void onEnterFail();
    }
    public interface ReportUserShareView{
        void onReportSuccess(LivePlayBean bean);
    }
    public interface VideoListView{
        void onVideoListSuccess(VideoBean videoBean);
        void onVideoListError();
    }
}
