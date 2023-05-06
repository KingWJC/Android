package com.lonch.client.utils;

import android.util.Log;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.AccelerationOrderEntity;
import com.lonch.client.databases.AccelerationOrderEntityDao;
import com.lonch.client.databases.DaoSession;

import java.util.List;

public class AccelerationOrderUtils {

    private static final String TAG = AccelerationOrderUtils.class.getSimpleName();

    private static AccelerationOrderUtils instance;

    private AccelerationOrderEntityDao umsEntityDao;
    private DaoSession daoSession;

    private AccelerationOrderUtils() {
        umsEntityDao = LonchCloudApplication.getDaoSession().getAccelerationOrderEntityDao();
        daoSession = LonchCloudApplication.getDaoSession();
    }

    //***初始化
    public static AccelerationOrderUtils getInstance() {
        if (instance == null) {
            synchronized (AccelerationOrderUtils.class) {
                if (instance == null) {
                    instance = new AccelerationOrderUtils();
                }
            }
        }
        return instance;
    }


    //***添加用户信息名称头像等
    public synchronized boolean insert(AccelerationOrderEntity entity){
            return umsEntityDao.insert(entity) != -1;
    }

    public AccelerationOrderEntity queryByLinkIdAndTime(String linkId, Long time){
        return umsEntityDao.queryBuilder().where(AccelerationOrderEntityDao.Properties.LinkId.eq(linkId),AccelerationOrderEntityDao.Properties.Time.eq(time)).unique();
    }

    //***添加用户信息名称头像等
    public synchronized boolean updateDevice(AccelerationOrderEntity device){
        boolean flag = false;
        try {
            umsEntityDao.update(device);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
            Log.i("CheckNetManger---",e.getMessage());
        }
        return flag;
    }


    //**查询搜索
    public List<AccelerationOrderEntity> queryAllDevices(){
        return daoSession.loadAll(AccelerationOrderEntity.class);
    }

    public synchronized void delete(AccelerationOrderEntity entity){
        umsEntityDao.delete(entity);
    }

    //***删除所有
    public synchronized boolean deleteAll(){
        boolean flag = false;
        try {
            daoSession.deleteAll(AccelerationOrderEntity.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

}
