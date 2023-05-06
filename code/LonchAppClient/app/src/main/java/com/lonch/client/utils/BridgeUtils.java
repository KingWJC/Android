package com.lonch.client.utils;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.BridgeEntity;
import com.lonch.client.databases.BridgeEntityDao;
import com.lonch.client.databases.DaoSession;

import java.util.List;

public class BridgeUtils {

    private static final String TAG = BridgeUtils.class.getSimpleName();

    private static BridgeUtils instance;

    private BridgeEntityDao umsEntityDao;
    private DaoSession daoSession;

    private BridgeUtils() {
        umsEntityDao = LonchCloudApplication.getDaoSession().getBridgeEntityDao();
        daoSession = LonchCloudApplication.getDaoSession();
    }

    //***初始化
    public static BridgeUtils getInstance() {
        if (instance == null) {
            synchronized (BridgeUtils.class) {
                if (instance == null) {
                    instance = new BridgeUtils();
                }
            }
        }
        return instance;
    }


    //***添加用户信息名称头像等
    public void insert(BridgeEntity entity){
        daoSession.startAsyncSession().insert(entity);
    }


    //**查询搜索
    public List<BridgeEntity> queryAllDevices(Long time){
        return umsEntityDao.queryBuilder().where(BridgeEntityDao.Properties.Time.lt(time)).list();
    }

    //**查询搜索
    public List<BridgeEntity> queryAllDevices(){
        return daoSession.loadAll(BridgeEntity.class);
    }

    public synchronized void delete(BridgeEntity entity){
        umsEntityDao.delete(entity);
    }

    //***删除所有
    public synchronized boolean deleteAll(){
        boolean flag = false;
        try {
            daoSession.deleteAll(BridgeEntity.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

}
