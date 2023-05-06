package com.lonch.client.databases;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lonch.client.database.bean.ApiResponseEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "lonch_api_entity".
*/
public class ApiResponseEntityDao extends AbstractDao<ApiResponseEntity, Long> {

    public static final String TABLENAME = "lonch_api_entity";

    /**
     * Properties of entity ApiResponseEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Url = new Property(1, String.class, "url", false, "URL");
        public final static Property Success = new Property(2, int.class, "success", false, "SUCCESS");
        public final static Property CreateTime = new Property(3, Long.class, "createTime", false, "CREATE_TIME");
        public final static Property ResponseTime = new Property(4, Long.class, "responseTime", false, "RESPONSE_TIME");
        public final static Property Time = new Property(5, Long.class, "time", false, "TIME");
    }


    public ApiResponseEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ApiResponseEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"lonch_api_entity\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"URL\" TEXT," + // 1: url
                "\"SUCCESS\" INTEGER NOT NULL ," + // 2: success
                "\"CREATE_TIME\" INTEGER," + // 3: createTime
                "\"RESPONSE_TIME\" INTEGER," + // 4: responseTime
                "\"TIME\" INTEGER);"); // 5: time
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"lonch_api_entity\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ApiResponseEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
        stmt.bindLong(3, entity.getSuccess());
 
        Long createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(4, createTime);
        }
 
        Long responseTime = entity.getResponseTime();
        if (responseTime != null) {
            stmt.bindLong(5, responseTime);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(6, time);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ApiResponseEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
        stmt.bindLong(3, entity.getSuccess());
 
        Long createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(4, createTime);
        }
 
        Long responseTime = entity.getResponseTime();
        if (responseTime != null) {
            stmt.bindLong(5, responseTime);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(6, time);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ApiResponseEntity readEntity(Cursor cursor, int offset) {
        ApiResponseEntity entity = new ApiResponseEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url
            cursor.getInt(offset + 2), // success
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // createTime
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // responseTime
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5) // time
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ApiResponseEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSuccess(cursor.getInt(offset + 2));
        entity.setCreateTime(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setResponseTime(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setTime(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ApiResponseEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ApiResponseEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ApiResponseEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
