package com.lonch.client.database.tabutils;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.SmallVideoEntity;
import com.lonch.client.databases.DaoSession;
import com.lonch.client.databases.SmallVideoEntityDao;

import java.util.List;

public class SmallVideoUtils {

    private static final String TAG = SmallVideoUtils.class.getSimpleName();

    private static SmallVideoUtils instance;

    private SmallVideoEntityDao umsEntityDao;
    private DaoSession daoSession;

    private SmallVideoUtils() {
        umsEntityDao = LonchCloudApplication.getDaoSession().getSmallVideoEntityDao();
        daoSession = LonchCloudApplication.getDaoSession();
    }

    //***初始化
    public static SmallVideoUtils getInstance() {
        if (instance == null) {
            synchronized (SmallVideoUtils.class) {
                if (instance == null) {
                    instance = new SmallVideoUtils();
                }
            }
        }
        return instance;
    }


    //***添加用户信息名称头像等
    public void insert(SmallVideoEntity entity) {
        daoSession.startAsyncSession().insert(entity);
    }


    //**查询搜索
//    public List<LogEntity> queryAllDevices(Long time) {
//        return umsEntityDao.queryBuilder().where(LogEntityDao.Properties.Time.lt(time)).list();
//    }
//    public List<LogEntity> queryByType(String type){
//        return umsEntityDao.queryBuilder().where(LogEntityDao.Properties.FromType.lt(type)).list();
//    }

    //**查询搜索
    public List<SmallVideoEntity> queryAllDevices() {
        return daoSession.loadAll(SmallVideoEntity.class);
    }

    public synchronized void delete(SmallVideoEntity entity) {
        umsEntityDao.delete(entity);
    }

    //***删除所有
    public synchronized boolean deleteAll() {
        boolean flag = false;
        try {
            daoSession.deleteAll(SmallVideoEntity.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
