package com.lonch.client.database.tabutils;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.WebSocketEntity;
import com.lonch.client.databases.DaoSession;
import com.lonch.client.databases.WebSocketEntityDao;

import java.util.List;

public class WebSocketUtils {

    private static final String TAG = WebSocketUtils.class.getSimpleName();

    private static WebSocketUtils instance;

    private WebSocketEntityDao umsEntityDao;
    private DaoSession daoSession;

    private WebSocketUtils() {
        umsEntityDao = LonchCloudApplication.getDaoSession().getWebSocketEntityDao();
        daoSession = LonchCloudApplication.getDaoSession();
    }

    //***初始化
    public static WebSocketUtils getInstance() {
        if (instance == null) {
            synchronized (WebSocketUtils.class) {
                if (instance == null) {
                    instance = new WebSocketUtils();
                }
            }
        }
        return instance;
    }


    //***添加用户信息名称头像等
    public synchronized boolean insert(WebSocketEntity entity) {
        return umsEntityDao.insert(entity) != -1;
    }


    //**查询搜索
    public List<WebSocketEntity> queryByTime(Long time) {
        return umsEntityDao.queryBuilder().where(WebSocketEntityDao.Properties.Time.eq(time)).list();
    }

    public List<WebSocketEntity> queryByReport(int report) {
        return umsEntityDao.queryBuilder().where(WebSocketEntityDao.Properties.IsReported.eq(report)).list();
    }
    public synchronized boolean updateDevice(WebSocketEntity device){
        boolean flag = false;
        try {
            umsEntityDao.update(device);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    //**查询搜索
    public List<WebSocketEntity> queryAllDevices() {
        return daoSession.loadAll(WebSocketEntity.class);
    }

    public synchronized void delete(WebSocketEntity entity) {
        umsEntityDao.delete(entity);
    }

    //***删除所有
    public synchronized boolean deleteAll() {
        boolean flag = false;
        try {
            daoSession.deleteAll(WebSocketEntity.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
