package com.lonch.client.database.tabutils;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.WebVersionEntity;
import com.lonch.client.databases.DaoSession;
import com.lonch.client.databases.WebVersionEntityDao;

import java.util.List;

public class WebVersionUtils {

    private static final String TAG = WebVersionUtils.class.getSimpleName();

    private static WebVersionUtils instance;

    private WebVersionEntityDao umsEntityDao;
    private DaoSession daoSession;

    private WebVersionUtils() {
        umsEntityDao = LonchCloudApplication.getDaoSession().getWebVersionEntityDao();
        daoSession = LonchCloudApplication.getDaoSession();
    }

    //***初始化
    public static WebVersionUtils getInstance() {
        if (instance == null) {
            synchronized (WebVersionUtils.class) {
                if (instance == null) {
                    instance = new WebVersionUtils();
                }
            }
        }
        return instance;
    }


    //***添加用户信息名称头像等
    public synchronized boolean insert(WebVersionEntity entity){

        WebVersionEntity temp = umsEntityDao.queryBuilder()
                .where(WebVersionEntityDao.Properties.Software_id.eq(entity.getSoftware_id())).build().unique();
        if(temp != null){
            temp.setSoftware_id(entity.getSoftware_id());
            temp.setJson(entity.getJson());
            temp.setVersion(entity.getVersion());
            return updateDevice(temp);
        }else{
            return umsEntityDao.insert(entity) != -1;
        }

    }

    //二级
    public synchronized boolean insertByLable(WebVersionEntity entity){
        WebVersionEntity temp = umsEntityDao.queryBuilder()
                .where(WebVersionEntityDao.Properties.Software_id.eq(entity.getSoftware_id())).build().unique();
        if(temp != null){
            temp.setSoftware_id(entity.getSoftware_id());
            temp.setJson(entity.getJson());

            return updateDevice(temp);
        }else{
            return umsEntityDao.insert(entity) != -1;
        }
    }


    public boolean insertMultDevices(final List<WebVersionEntity> WebVersionEntityList){
        boolean flag = false;
        try{
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for(WebVersionEntity device : WebVersionEntityList){
                        updateDevice(device);
                    }
                }
            });
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    ///****更新
    public synchronized boolean updateDevice(WebVersionEntity device){
        boolean flag = false;
        try {
            umsEntityDao.update(device);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    ///**根据对象删除  单个字段对比
    public boolean deleteDeviceEvent(WebVersionEntity device){
        boolean flag = false;
        try {
            WebVersionEntity temp = umsEntityDao.queryBuilder()
                    .where(WebVersionEntityDao.Properties.Software_id.eq(device.getSoftware_id())).build().unique();
            umsEntityDao.delete(temp);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }


    ///**根据对象删除  多个字段对比
    public boolean deleteDeviceKey(WebVersionEntity device){
        boolean flag = false;
        try {
            WebVersionEntity temp = umsEntityDao.queryBuilder()
                    .where(WebVersionEntityDao.Properties.Software_id.eq(device.getSoftware_id())).build().unique();
            umsEntityDao.delete(temp);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    //***删除所有
    public synchronized boolean deleteAll(){
        boolean flag = false;
        try {
            daoSession.deleteAll(WebVersionEntity.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    //**查询搜索
    public List<WebVersionEntity> queryAllDevices(){
        return daoSession.loadAll(WebVersionEntity.class);
    }

    //根据字段查询
    public List<WebVersionEntity> queryDeviceByTypeId(String softWareId)
    {
        List<WebVersionEntity> list = umsEntityDao.queryBuilder()
                .where(WebVersionEntityDao.Properties.Software_id.eq(softWareId)).build().list();
        return list;
    }


}