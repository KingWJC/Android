package com.lonch.client.databases;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lonch.client.database.bean.AccelerationOrderEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "lonch_order_entity".
*/
public class AccelerationOrderEntityDao extends AbstractDao<AccelerationOrderEntity, Long> {

    public static final String TABLENAME = "lonch_order_entity";

    /**
     * Properties of entity AccelerationOrderEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property LinkId = new Property(1, String.class, "linkId", false, "LINK_ID");
        public final static Property Order = new Property(2, int.class, "order", false, "ORDER");
        public final static Property Time = new Property(3, Long.class, "time", false, "TIME");
        public final static Property Sum = new Property(4, int.class, "sum", false, "SUM");
    }


    public AccelerationOrderEntityDao(DaoConfig config) {
        super(config);
    }
    
    public AccelerationOrderEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"lonch_order_entity\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"LINK_ID\" TEXT," + // 1: linkId
                "\"ORDER\" INTEGER NOT NULL ," + // 2: order
                "\"TIME\" INTEGER," + // 3: time
                "\"SUM\" INTEGER NOT NULL );"); // 4: sum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"lonch_order_entity\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AccelerationOrderEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String linkId = entity.getLinkId();
        if (linkId != null) {
            stmt.bindString(2, linkId);
        }
        stmt.bindLong(3, entity.getOrder());
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(4, time);
        }
        stmt.bindLong(5, entity.getSum());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AccelerationOrderEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String linkId = entity.getLinkId();
        if (linkId != null) {
            stmt.bindString(2, linkId);
        }
        stmt.bindLong(3, entity.getOrder());
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(4, time);
        }
        stmt.bindLong(5, entity.getSum());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AccelerationOrderEntity readEntity(Cursor cursor, int offset) {
        AccelerationOrderEntity entity = new AccelerationOrderEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // linkId
            cursor.getInt(offset + 2), // order
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // time
            cursor.getInt(offset + 4) // sum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AccelerationOrderEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLinkId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOrder(cursor.getInt(offset + 2));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setSum(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AccelerationOrderEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AccelerationOrderEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AccelerationOrderEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
