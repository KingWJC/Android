package com.lonch.client.database.tabutils;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.ApiResponseEntity;
import com.lonch.client.databases.ApiResponseEntityDao;
import com.lonch.client.databases.DaoSession;

import java.util.List;

public class ApiResponseUtils {
    private static final String TAG = ApiResponseUtils.class.getSimpleName();

    private static ApiResponseUtils instance;

    private ApiResponseEntityDao umsEntityDao;
    private DaoSession daoSession;

    private ApiResponseUtils() {
        umsEntityDao = LonchCloudApplication.getDaoSession().getApiResponseEntityDao();
        daoSession = LonchCloudApplication.getDaoSession();
    }

    //***初始化
    public static ApiResponseUtils getInstance() {
        if (instance == null) {
            synchronized (ApiResponseUtils.class) {
                if (instance == null) {
                    instance = new ApiResponseUtils();
                }
            }
        }
        return instance;
    }


    //***添加用户信息名称头像等
    public void insert(ApiResponseEntity entity){
        daoSession.startAsyncSession().insert(entity);
    }


    //**查询搜索
    public List<ApiResponseEntity> queryAllDevices(Long time){
        return umsEntityDao.queryBuilder().where(ApiResponseEntityDao.Properties.Time.lt(time)).list();
    }

    //**查询搜索
    public List<ApiResponseEntity> queryAllDevices(){
        return daoSession.loadAll(ApiResponseEntity.class);
    }

    public synchronized void delete(ApiResponseEntity entity){
        umsEntityDao.delete(entity);
    }

    //***删除所有
    public synchronized boolean deleteAll(){
        boolean flag = false;
        try {
            daoSession.deleteAll(ApiResponseEntity.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
