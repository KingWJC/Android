package com.lonch.client.databases;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lonch.client.database.bean.InfoFriendsEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "lonch_friendinfo_entity".
*/
public class InfoFriendsEntityDao extends AbstractDao<InfoFriendsEntity, Long> {

    public static final String TABLENAME = "lonch_friendinfo_entity";

    /**
     * Properties of entity InfoFriendsEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property FaceUrl = new Property(2, String.class, "faceUrl", false, "FACE_URL");
        public final static Property NickName = new Property(3, String.class, "nickName", false, "NICK_NAME");
        public final static Property TimMessage = new Property(4, long.class, "timMessage", false, "TIM_MESSAGE");
    }


    public InfoFriendsEntityDao(DaoConfig config) {
        super(config);
    }
    
    public InfoFriendsEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"lonch_friendinfo_entity\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"FACE_URL\" TEXT," + // 2: faceUrl
                "\"NICK_NAME\" TEXT," + // 3: nickName
                "\"TIM_MESSAGE\" INTEGER NOT NULL );"); // 4: timMessage
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"lonch_friendinfo_entity\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, InfoFriendsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String faceUrl = entity.getFaceUrl();
        if (faceUrl != null) {
            stmt.bindString(3, faceUrl);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(4, nickName);
        }
        stmt.bindLong(5, entity.getTimMessage());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, InfoFriendsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String faceUrl = entity.getFaceUrl();
        if (faceUrl != null) {
            stmt.bindString(3, faceUrl);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(4, nickName);
        }
        stmt.bindLong(5, entity.getTimMessage());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public InfoFriendsEntity readEntity(Cursor cursor, int offset) {
        InfoFriendsEntity entity = new InfoFriendsEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // faceUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickName
            cursor.getLong(offset + 4) // timMessage
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, InfoFriendsEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFaceUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNickName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTimMessage(cursor.getLong(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(InfoFriendsEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(InfoFriendsEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(InfoFriendsEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
