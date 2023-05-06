package com.lonch.client.utils;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.LogEntity;
import com.lonch.client.databases.DaoSession;
import com.lonch.client.databases.LogEntityDao;

import java.util.List;

public class LogUtils {

    private static final String TAG = LogUtils.class.getSimpleName();

    private static LogUtils instance;

    private LogEntityDao umsEntityDao;
    private DaoSession daoSession;

    private LogUtils() {
        umsEntityDao = LonchCloudApplication.getDaoSession().getLogEntityDao();
        daoSession = LonchCloudApplication.getDaoSession();
    }

    //***初始化
    public static LogUtils getInstance() {
        if (instance == null) {
            synchronized (LogUtils.class) {
                if (instance == null) {
                    instance = new LogUtils();
                }
            }
        }
        return instance;
    }


    //***添加用户信息名称头像等
    public void insert(LogEntity entity) {
        daoSession.startAsyncSession().insert(entity);
    }


    //**查询搜索
    public List<LogEntity> queryAllDevices(Long time) {
        return umsEntityDao.queryBuilder().where(LogEntityDao.Properties.Time.lt(time)).list();
    }
    public List<LogEntity> queryByType(String type){
        return umsEntityDao.queryBuilder().where(LogEntityDao.Properties.FromType.eq(type)).list();
    }

    //**查询搜索
    public List<LogEntity> queryAllDevices() {
        return daoSession.loadAll(LogEntity.class);
    }

    public synchronized void delete(LogEntity entity) {
        umsEntityDao.delete(entity);
    }

    //***删除所有
    public synchronized boolean deleteAll() {
        boolean flag = false;
        try {
            daoSession.deleteAll(LogEntity.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
