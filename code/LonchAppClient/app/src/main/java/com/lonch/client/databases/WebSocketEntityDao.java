package com.lonch.client.databases;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lonch.client.database.bean.WebSocketEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "lonch_websocket_entity".
*/
public class WebSocketEntityDao extends AbstractDao<WebSocketEntity, String> {

    public static final String TABLENAME = "lonch_websocket_entity";

    /**
     * Properties of entity WebSocketEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property AppType = new Property(1, String.class, "appType", false, "APP_TYPE");
        public final static Property DeviceId = new Property(2, String.class, "deviceId", false, "DEVICE_ID");
        public final static Property Os = new Property(3, String.class, "os", false, "OS");
        public final static Property DeviceType = new Property(4, String.class, "deviceType", false, "DEVICE_TYPE");
        public final static Property DeviceVersion = new Property(5, String.class, "deviceVersion", false, "DEVICE_VERSION");
        public final static Property DeviceBrand = new Property(6, String.class, "deviceBrand", false, "DEVICE_BRAND");
        public final static Property IsSupport = new Property(7, int.class, "isSupport", false, "IS_SUPPORT");
        public final static Property PingTimes = new Property(8, int.class, "pingTimes", false, "PING_TIMES");
        public final static Property PongTimes = new Property(9, int.class, "pongTimes", false, "PONG_TIMES");
        public final static Property TestDate = new Property(10, String.class, "testDate", false, "TEST_DATE");
        public final static Property Ip = new Property(11, String.class, "ip", false, "IP");
        public final static Property Time = new Property(12, Long.class, "time", false, "TIME");
        public final static Property IsReported = new Property(13, int.class, "isReported", false, "IS_REPORTED");
    }


    public WebSocketEntityDao(DaoConfig config) {
        super(config);
    }
    
    public WebSocketEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"lonch_websocket_entity\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"APP_TYPE\" TEXT," + // 1: appType
                "\"DEVICE_ID\" TEXT," + // 2: deviceId
                "\"OS\" TEXT," + // 3: os
                "\"DEVICE_TYPE\" TEXT," + // 4: deviceType
                "\"DEVICE_VERSION\" TEXT," + // 5: deviceVersion
                "\"DEVICE_BRAND\" TEXT," + // 6: deviceBrand
                "\"IS_SUPPORT\" INTEGER NOT NULL ," + // 7: isSupport
                "\"PING_TIMES\" INTEGER NOT NULL ," + // 8: pingTimes
                "\"PONG_TIMES\" INTEGER NOT NULL ," + // 9: pongTimes
                "\"TEST_DATE\" TEXT," + // 10: testDate
                "\"IP\" TEXT," + // 11: ip
                "\"TIME\" INTEGER," + // 12: time
                "\"IS_REPORTED\" INTEGER NOT NULL );"); // 13: isReported
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"lonch_websocket_entity\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WebSocketEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String appType = entity.getAppType();
        if (appType != null) {
            stmt.bindString(2, appType);
        }
 
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(3, deviceId);
        }
 
        String os = entity.getOs();
        if (os != null) {
            stmt.bindString(4, os);
        }
 
        String deviceType = entity.getDeviceType();
        if (deviceType != null) {
            stmt.bindString(5, deviceType);
        }
 
        String deviceVersion = entity.getDeviceVersion();
        if (deviceVersion != null) {
            stmt.bindString(6, deviceVersion);
        }
 
        String deviceBrand = entity.getDeviceBrand();
        if (deviceBrand != null) {
            stmt.bindString(7, deviceBrand);
        }
        stmt.bindLong(8, entity.getIsSupport());
        stmt.bindLong(9, entity.getPingTimes());
        stmt.bindLong(10, entity.getPongTimes());
 
        String testDate = entity.getTestDate();
        if (testDate != null) {
            stmt.bindString(11, testDate);
        }
 
        String ip = entity.getIp();
        if (ip != null) {
            stmt.bindString(12, ip);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(13, time);
        }
        stmt.bindLong(14, entity.getIsReported());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WebSocketEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String appType = entity.getAppType();
        if (appType != null) {
            stmt.bindString(2, appType);
        }
 
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(3, deviceId);
        }
 
        String os = entity.getOs();
        if (os != null) {
            stmt.bindString(4, os);
        }
 
        String deviceType = entity.getDeviceType();
        if (deviceType != null) {
            stmt.bindString(5, deviceType);
        }
 
        String deviceVersion = entity.getDeviceVersion();
        if (deviceVersion != null) {
            stmt.bindString(6, deviceVersion);
        }
 
        String deviceBrand = entity.getDeviceBrand();
        if (deviceBrand != null) {
            stmt.bindString(7, deviceBrand);
        }
        stmt.bindLong(8, entity.getIsSupport());
        stmt.bindLong(9, entity.getPingTimes());
        stmt.bindLong(10, entity.getPongTimes());
 
        String testDate = entity.getTestDate();
        if (testDate != null) {
            stmt.bindString(11, testDate);
        }
 
        String ip = entity.getIp();
        if (ip != null) {
            stmt.bindString(12, ip);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(13, time);
        }
        stmt.bindLong(14, entity.getIsReported());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public WebSocketEntity readEntity(Cursor cursor, int offset) {
        WebSocketEntity entity = new WebSocketEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // appType
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // deviceId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // os
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // deviceType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // deviceVersion
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // deviceBrand
            cursor.getInt(offset + 7), // isSupport
            cursor.getInt(offset + 8), // pingTimes
            cursor.getInt(offset + 9), // pongTimes
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // testDate
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // ip
            cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12), // time
            cursor.getInt(offset + 13) // isReported
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WebSocketEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAppType(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDeviceId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setOs(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDeviceType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDeviceVersion(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDeviceBrand(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIsSupport(cursor.getInt(offset + 7));
        entity.setPingTimes(cursor.getInt(offset + 8));
        entity.setPongTimes(cursor.getInt(offset + 9));
        entity.setTestDate(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setIp(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setTime(cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12));
        entity.setIsReported(cursor.getInt(offset + 13));
     }
    
    @Override
    protected final String updateKeyAfterInsert(WebSocketEntity entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(WebSocketEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(WebSocketEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
