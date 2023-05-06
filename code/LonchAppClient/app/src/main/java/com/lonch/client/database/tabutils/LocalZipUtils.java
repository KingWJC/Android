package com.lonch.client.database.tabutils;


import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.LocalZipEntity;
import com.lonch.client.databases.DaoSession;
import com.lonch.client.databases.LocalZipEntityDao;

import java.util.List;

public class LocalZipUtils {

    private static final String TAG = LocalZipUtils.class.getSimpleName();

    private static LocalZipUtils instance;

    private LocalZipEntityDao umsEntityDao;
    private DaoSession daoSession;

    private LocalZipUtils() {
        umsEntityDao = LonchCloudApplication.getDaoSession().getLocalZipEntityDao();
        daoSession = LonchCloudApplication.getDaoSession();
    }

    //***初始化
    public static LocalZipUtils getInstance() {
        if (instance == null) {
            synchronized (LocalZipUtils.class) {
                if (instance == null) {
                    instance = new LocalZipUtils();
                }
            }
        }
        return instance;
    }


    //***添加用户信息名称头像等
    public synchronized boolean insert(LocalZipEntity entity) {

        LocalZipEntity temp = umsEntityDao.queryBuilder()
                .where(LocalZipEntityDao.Properties.Software_id.eq(entity.getSoftware_id())).build().unique();
        if (temp != null) {
            temp.setSoftware_id(entity.getSoftware_id());
            temp.setPath(entity.getPath());
            temp.setVersion(entity.getVersion());
            return updateDevice(temp);
        } else {
            return umsEntityDao.insert(entity) != -1;
        }

    }

    ///****更新
    public synchronized boolean updateDevice(LocalZipEntity device) {
        boolean flag = false;
        try {
            umsEntityDao.update(device);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //**查询搜索
    public List<LocalZipEntity> queryAllDevices() {
        return daoSession.loadAll(LocalZipEntity.class);
    }

}
