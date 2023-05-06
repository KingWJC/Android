package com.lonch.client;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liulishuo.filedownloader.FileDownloader;
import com.lonch.client.bean.ApiResult;
import com.lonch.client.bean.AppConfigDataBean;
import com.lonch.client.bean.event.AppCallWebEvent;
import com.lonch.client.bean.event.TencentIMEvent;
import com.lonch.client.bean.event.UnReadCountEvent;
import com.lonch.client.bean.im.GroupUserInfo;
import com.lonch.client.bean.im.IMC2CMessage;
import com.lonch.client.bean.im.IMConversation;
import com.lonch.client.bean.im.IMCustomMessage;
import com.lonch.client.common.AppConfigInfo;
import com.lonch.client.database.util.DatabaseManager;
import com.lonch.client.databases.DaoSession;
import com.lonch.client.service.LocationService;
import com.lonch.client.service.WebAppServer;
import com.lonch.client.utils.CountTimer;
import com.lonch.client.utils.FileUtils;
import com.lonch.client.utils.SystemUtil;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationListener;
import com.tencent.imsdk.v2.V2TIMGroupChangeInfo;
import com.tencent.imsdk.v2.V2TIMGroupListener;
import com.tencent.imsdk.v2.V2TIMGroupMemberChangeInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMUserInfo;
import com.tencent.mmkv.MMKV;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yanzhenjie.andserver.util.IOUtils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局类，由主工程来调用
 */
public class LonchCloudApplication {
    private static Application application;
    public static boolean isAppDebugMode;
    public static File rootDir;
    private static DaoSession daoSession;
    public static Handler handler = new Handler(Looper.getMainLooper());
    private static List<Activity> oList;//用于存放所有启动的Activity的集合
    private static AppConfigDataBean appConfigDataBean;
    private static int activityCount = 0;// 判断app是否从前后台切换
    public static boolean blueToothStatus =false;
    public static LocationService locationService;

    public static Application getApplicationsContext() {
        return application;
    }

    public static CountTimer getCountTimer() {
        return CountTimer.getInstance();
    }

    public static AppConfigDataBean getAppConfigDataBean() {
        return appConfigDataBean;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void init(Application application, boolean isAppDebugMode, String mainProcessName, AppConfigDataBean appConfigDataBean) {
        LonchCloudApplication.isAppDebugMode = isAppDebugMode;
        LonchCloudApplication.appConfigDataBean = appConfigDataBean;
        locationService = new LocationService(application);
        AppConfigInfo.getInstance().setDebug(BuildConfig.DEBUG);
        FileDownloader.setup(application);
        MMKV.initialize(application);
        if (LonchCloudApplication.application == null) {
            LonchCloudApplication.application = application;
            initRootPath(application);
        }
        //注册加密算法
        Security.addProvider(new BouncyCastleProvider());
        startMyService();
        ZXingLibrary.initDisplayOpinion(application);
        oList = new ArrayList<>();
        String processName = SystemUtil.getProcessName(application, android.os.Process.myPid());
        if (!TextUtils.isEmpty(processName)) {
            if (processName.equals(mainProcessName)) {
                setDatabase();
                registerRunning();
            }
        }
        setMConverListener();//设置监听
        setMsgListener();//设置监听
    }

    /**
     * 监听前后台切换
     */
    private static void registerRunning() {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (activityCount == 0) { //切换到后台
                    EventBus.getDefault().post(new TencentIMEvent(200));
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
                if (activityCount == 1) {
                    EventBus.getDefault().post(new TencentIMEvent(true));
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startMyService() {
        WebAppServer.getInstance(null).initServer();
    }


    /**
     * 设置greenDao
     */
    private static void setDatabase() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.init(application);
        daoSession = databaseManager.getDaoSession();
    }

    @NonNull
    public static File getRootDir() {
        return rootDir;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    private static void initRootPath(Context context) {
        if (rootDir != null) return;

        if (FileUtils.storageAvailable()) {
            rootDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        } else {
            rootDir = context.getFilesDir();
        }
        rootDir = new File(rootDir, "AndServer");
        IOUtils.createFolder(rootDir);
    }

    /**
     * 添加Activity
     */
    public static void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public static void removeActivity_(Activity activity) {
        //判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public static void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
        oList.removeAll(oList);
    }

    private static void setMsgListener() {
        if (v2TIMSimpleMsgListener == null) {
            v2TIMSimpleMsgListener = new MyMsgListener();
            V2TIMManager.getInstance().addSimpleMsgListener(v2TIMSimpleMsgListener);
        }
        if (myGroupListener == null) {
            myGroupListener = new MyGroupListener();
            V2TIMManager.getInstance().setGroupListener(myGroupListener);
        }
    }

    private static void setMConverListener() {
        V2TIMManager.getConversationManager().setConversationListener(new V2TIMConversationListener() {
            @Override
            public void onSyncServerStart() {
                super.onSyncServerStart();

            }

            @Override
            public void onSyncServerFinish() {
                super.onSyncServerFinish();

            }

            @Override
            public void onSyncServerFailed() {
                super.onSyncServerFailed();

            }

            // 3.1 收到会话新增的回调
            @Override
            public void onNewConversation(List<V2TIMConversation> conversationList) {
                try {
                    List<IMConversation> infoList = new ArrayList<>();
                    for (int i = 0; i < conversationList.size(); i++) {
                        V2TIMConversation conversation = conversationList.get(i);
                        if (null != conversation.getGroupType() && conversation.getGroupType().equals("AVChatRoom")) {
                            continue;
                        }
                        String conversationData = JSON.toJSONString(conversation);
                        IMConversation imConversation = JSON.parseObject(conversationData,IMConversation.class);
                        infoList.add(imConversation);
                    }
                    ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                    Map<String, Object> map = new HashMap<>();
                    map.put("list", infoList);
                    apiResult.setServiceResult(map);
                    String apiResultJson = toJson(apiResult);
                    EventBus.getDefault().post(new AppCallWebEvent("recivedNewConversation", apiResultJson));
                }catch (Exception e){ }

            }

            // 3.2 收到会话更新的回调
            @Override
            public void onConversationChanged(List<V2TIMConversation> conversationList) {
                try {
                    List<IMConversation> infoList = new ArrayList<>();
                    for (int i = 0; i < conversationList.size(); i++) {
                        V2TIMConversation conversation = conversationList.get(i);
                        if (null != conversation.getGroupType() && conversation.getGroupType().equals("AVChatRoom")) {
                            continue;
                        }
                        String conversationData = JSON.toJSONString(conversation);
                        IMConversation imConversation = JSON.parseObject(conversationData,IMConversation.class);
                        infoList.add(imConversation);
                    }
                    ApiResult<Map<String, Object>> apiResult = new ApiResult<>();
                    Map<String, Object> map = new HashMap<>();
                    map.put("list", infoList);
                    apiResult.setServiceResult(map);
                    String apiResultJson = toJson(apiResult);
                    appCallWeb("recivedConversationChanged", apiResultJson);
                    EventBus.getDefault().post(new UnReadCountEvent(0, true));
                }catch (Exception e){

                }
            }
        });

    }

    private static MyMsgListener v2TIMSimpleMsgListener;
    private static MyGroupListener myGroupListener;

    private static class MyMsgListener extends V2TIMSimpleMsgListener {
        @Override
        public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
            super.onRecvC2CTextMessage(msgID, sender, text);
            ApiResult<IMC2CMessage> apiResult = new ApiResult<>();
            IMC2CMessage imc2CMessage = new IMC2CMessage();
            imc2CMessage.setMsgId(msgID);
            imc2CMessage.setSender(sender.getUserID());
            imc2CMessage.setUserId(sender.getUserID());
            imc2CMessage.setNickName(sender.getNickName());
            imc2CMessage.setTimestamp(System.currentTimeMillis());
            IMC2CMessage.TextElem textElem = new IMC2CMessage.TextElem();
            textElem.setText(text);
            imc2CMessage.setTextElem(textElem);
            apiResult.setServiceResult(imc2CMessage);
            String apiResultJson = toJson(apiResult);
            appCallWeb("recivedC2CTextMessage", apiResultJson);
        }

        @Override
        public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
            super.onRecvC2CCustomMessage(msgID, sender, customData);
            try {
                String customDataString = new String(customData, StandardCharsets.UTF_8);
                ApiResult<IMCustomMessage> apiResult = new ApiResult<>();
                IMCustomMessage imCustomMessage = new IMCustomMessage();
                imCustomMessage.setMsgId(msgID);
                imCustomMessage.setSender(sender.getUserID());
                imCustomMessage.setUserId(sender.getUserID());
                imCustomMessage.setTimestamp(System.currentTimeMillis());
                IMCustomMessage.CustomElem customElem = new IMCustomMessage.CustomElem();
                customElem.setData(customDataString);
                customElem.setDesc(customDataString);
                customElem.setExtension(customDataString);
                imCustomMessage.setCustomElem(customElem);
                apiResult.setServiceResult(imCustomMessage);
                String apiResultJson = toJson(apiResult);
                appCallWeb("recivedC2CTextMessage", apiResultJson);
            } catch (Exception e){}

        }

        @Override
        public void onRecvGroupTextMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, String text) {
            super.onRecvGroupTextMessage(msgID, groupID, sender, text);
            ApiResult<IMC2CMessage> apiResult = new ApiResult<>();
            IMC2CMessage imc2CMessage = new IMC2CMessage();
            imc2CMessage.setMsgId(msgID);
            imc2CMessage.setSender(sender.getUserID());
            imc2CMessage.setUserId(sender.getUserID());
            imc2CMessage.setGroupId(groupID);
            imc2CMessage.setNickName(sender.getNickName());
            imc2CMessage.setTimestamp(System.currentTimeMillis());
            IMC2CMessage.TextElem textElem = new IMC2CMessage.TextElem();
            textElem.setText(text);
            imc2CMessage.setTextElem(textElem);
            apiResult.setServiceResult(imc2CMessage);
            String apiResultJson = toJson(apiResult);
            appCallWeb("recivedGroupTextMessage", apiResultJson);
        }

        @Override
        public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
            super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);
        }
    }

    private static class MyGroupListener extends V2TIMGroupListener {
        @Override
        public void onMemberEnter(String groupID, List<V2TIMGroupMemberInfo> memberList) {
            super.onMemberEnter(groupID, memberList);
            if (memberList!=null && memberList.size() > 0){
                List<GroupUserInfo> list = new ArrayList<>();
                for (V2TIMGroupMemberInfo v: memberList) {
                    sendUserEnterRoom(v,1,groupID);
                }
            }
        }

        //谁走了
        @Override
        public void onMemberLeave(String groupID, V2TIMGroupMemberInfo member) {
            super.onMemberLeave(groupID, member);
            sendUserEnterRoom(member,2,groupID);
        }


        private static void sendUserEnterRoom(V2TIMGroupMemberInfo memberInfo,int type,String groupId){
            ApiResult<HashMap<String,Object>> apiResult = new ApiResult<>();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("type",type);
            hashMap.put("groupId",groupId);
            hashMap.put("userId",memberInfo.getUserID());
            hashMap.put("userName",memberInfo.getNickName());
            apiResult.setServiceResult(hashMap);
            String apiResultJson = toJson(apiResult);
            appCallWeb("cmdEnterLeaveRoom",apiResultJson);
        }

        //某些人被拉入某群（全员能够收到）
        @Override
        public void onMemberInvited(String groupID, V2TIMGroupMemberInfo opUser, List<V2TIMGroupMemberInfo> memberList) {
            super.onMemberInvited(groupID, opUser, memberList);
        }

        //被踢出了
        @Override
        public void onMemberKicked(String groupID, V2TIMGroupMemberInfo opUser, List<V2TIMGroupMemberInfo> memberList) {
            super.onMemberKicked(groupID, opUser, memberList);
        }

        @Override
        public void onMemberInfoChanged(String groupID, List<V2TIMGroupMemberChangeInfo> v2TIMGroupMemberChangeInfoList) {
            super.onMemberInfoChanged(groupID, v2TIMGroupMemberChangeInfoList);
        }

        @Override
        public void onGroupCreated(String groupID) {
            super.onGroupCreated(groupID);
        }

        @Override
        public void onGroupDismissed(String groupID, V2TIMGroupMemberInfo opUser) {
            super.onGroupDismissed(groupID, opUser);
        }

        @Override
        public void onGroupRecycled(String groupID, V2TIMGroupMemberInfo opUser) {
            super.onGroupRecycled(groupID, opUser);
        }

        @Override
        public void onGroupInfoChanged(String groupID, List<V2TIMGroupChangeInfo> changeInfos) {
            super.onGroupInfoChanged(groupID, changeInfos);
        }

        @Override
        public void onReceiveJoinApplication(String groupID, V2TIMGroupMemberInfo member, String opReason) {
            super.onReceiveJoinApplication(groupID, member, opReason);
        }

        @Override
        public void onApplicationProcessed(String groupID, V2TIMGroupMemberInfo opUser, boolean isAgreeJoin, String opReason) {
            super.onApplicationProcessed(groupID, opUser, isAgreeJoin, opReason);
        }

        @Override
        public void onGrantAdministrator(String groupID, V2TIMGroupMemberInfo opUser, List<V2TIMGroupMemberInfo> memberList) {
            super.onGrantAdministrator(groupID, opUser, memberList);
        }

        @Override
        public void onRevokeAdministrator(String groupID, V2TIMGroupMemberInfo opUser, List<V2TIMGroupMemberInfo> memberList) {
            super.onRevokeAdministrator(groupID, opUser, memberList);
        }

        //我退出了
        @Override
        public void onQuitFromGroup(String groupID) {
            super.onQuitFromGroup(groupID);

        }

        @Override
        public void onReceiveRESTCustomData(String groupID, byte[] customData) {
            super.onReceiveRESTCustomData(groupID, customData);
        }

        @Override
        public void onGroupAttributeChanged(String groupID, Map<String, String> groupAttributeMap) {
            super.onGroupAttributeChanged(groupID, groupAttributeMap);
        }
    }

    /**
     * 将Java对象employee序列化成为JSON格式
     *
     * @return
     */
    private static String toJson(Object obj) {
        // 序列化
        ObjectMapper mapper = new ObjectMapper();
        String object = null;
        try {
            object = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static void appCallWeb(String sn, String msg) {
        EventBus.getDefault().post(new AppCallWebEvent(sn, msg));
    }
}
