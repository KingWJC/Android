package com.lonch.client.utils;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.database.bean.NetLinkEntity;
import com.lonch.client.database.bean.NetLinkFail;
import com.lonch.client.databases.DaoSession;
import com.lonch.client.databases.NetLinkEntityDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NetLinkUtils {

    private static final String TAG = NetLinkUtils.class.getSimpleName();

    private static NetLinkUtils instance;

    private NetLinkEntityDao umsEntityDao;
    private DaoSession daoSession;

    private NetLinkUtils() {
        umsEntityDao = LonchCloudApplication.getDaoSession().getNetLinkEntityDao();
        daoSession = LonchCloudApplication.getDaoSession();
    }

    //***初始化
    public static NetLinkUtils getInstance() {
        if (instance == null) {
            synchronized (NetLinkUtils.class) {
                if (instance == null) {
                    instance = new NetLinkUtils();
                }
            }
        }
        return instance;
    }


    //***添加用户信息名称头像等
    public synchronized boolean insert(NetLinkEntity entity) {
        return umsEntityDao.insert(entity) != -1;
    }


    //**查询搜索
    public List<NetLinkEntity> queryAllDevices(Long time) {
        return umsEntityDao.queryBuilder().where(NetLinkEntityDao.Properties.Time.lt(time)).list();
    }

    //**查询搜索
    public List<NetLinkEntity> queryByType(int type) {
        return umsEntityDao.queryBuilder().where(NetLinkEntityDao.Properties.Type.eq(type)).list();
    }

    @SuppressLint("Range")
    public List<NetLinkFail> queryByTypeFail() {
        String sql = "select a.LINK_ID,a.API_ID,a.IP,a.allCount, IFNULL(B.failCount,0) failCount from " +
                "(select LINK_ID,API_ID,IP, count(1) as allCount from  lonch_net_entity    GROUP BY  LINK_ID,API_ID) A " +
                "left join (select  LINK_ID,API_ID,IP,  count(1) as failCount from  lonch_net_entity where TYPE= 0 GROUP BY  LINK_ID,API_ID ) B" +
                " on A.LINK_ID=B.LINK_ID and A.API_ID=B.API_ID and A.IP=B.IP";

        Cursor cursor = daoSession.getDatabase().rawQuery(sql, null);
        List<NetLinkFail> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            NetLinkFail lEmployeeTableBean = new NetLinkFail();
            lEmployeeTableBean.setId(UUID.randomUUID().toString().replace("-", ""));
            lEmployeeTableBean.setReportTime(Utils.getReportTime());
            lEmployeeTableBean.setApiId(cursor.getString(cursor.getColumnIndex("API_ID")));
            lEmployeeTableBean.setLinkId(cursor.getString(cursor.getColumnIndex("LINK_ID")));
            lEmployeeTableBean.setIp(cursor.getString(cursor.getColumnIndex("IP")));
            lEmployeeTableBean.setFailAccumulate(cursor.getInt(cursor.getColumnIndex("failCount")));
            lEmployeeTableBean.setAllCount(cursor.getInt(cursor.getColumnIndex("allCount")));
            lEmployeeTableBean.setFailAverage(BigDecimal.valueOf((float) cursor.getInt(cursor.getColumnIndex("failCount")) / (cursor.getInt(cursor.getColumnIndex("allCount")) == 0 ? 1 : cursor.getInt(cursor.getColumnIndex("allCount")))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            list.add(lEmployeeTableBean);
        }
        return list;

    }

    //**查询搜索
    public List<NetLinkEntity> queryAllDevices() {
        return daoSession.loadAll(NetLinkEntity.class);
    }

    public synchronized void delete(NetLinkEntity entity) {
        umsEntityDao.delete(entity);
    }

    //***删除所有
    public synchronized boolean deleteAll() {
        boolean flag = false;
        try {
            daoSession.deleteAll(NetLinkEntity.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
