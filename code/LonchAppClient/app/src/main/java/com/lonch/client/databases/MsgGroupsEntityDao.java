package com.lonch.client.databases;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lonch.client.database.bean.MsgGroupsEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "lonch_msggroup_entity".
*/
public class MsgGroupsEntityDao extends AbstractDao<MsgGroupsEntity, Long> {

    public static final String TABLENAME = "lonch_msggroup_entity";

    /**
     * Properties of entity MsgGroupsEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserID = new Property(1, String.class, "userID", false, "USER_ID");
        public final static Property GroupID = new Property(2, String.class, "groupID", false, "GROUP_ID");
        public final static Property MsgID = new Property(3, String.class, "msgID", false, "MSG_ID");
        public final static Property MsgType = new Property(4, String.class, "msgType", false, "MSG_TYPE");
        public final static Property Text = new Property(5, String.class, "text", false, "TEXT");
        public final static Property IsSelf = new Property(6, boolean.class, "isSelf", false, "IS_SELF");
        public final static Property NickName = new Property(7, String.class, "nickName", false, "NICK_NAME");
        public final static Property FaceUrl = new Property(8, String.class, "faceUrl", false, "FACE_URL");
        public final static Property TimMessage = new Property(9, long.class, "timMessage", false, "TIM_MESSAGE");
        public final static Property OwnerId = new Property(10, String.class, "ownerId", false, "OWNER_ID");
        public final static Property Json = new Property(11, String.class, "json", false, "JSON");
    }


    public MsgGroupsEntityDao(DaoConfig config) {
        super(config);
    }
    
    public MsgGroupsEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"lonch_msggroup_entity\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userID
                "\"GROUP_ID\" TEXT," + // 2: groupID
                "\"MSG_ID\" TEXT," + // 3: msgID
                "\"MSG_TYPE\" TEXT," + // 4: msgType
                "\"TEXT\" TEXT," + // 5: text
                "\"IS_SELF\" INTEGER NOT NULL ," + // 6: isSelf
                "\"NICK_NAME\" TEXT," + // 7: nickName
                "\"FACE_URL\" TEXT," + // 8: faceUrl
                "\"TIM_MESSAGE\" INTEGER NOT NULL ," + // 9: timMessage
                "\"OWNER_ID\" TEXT," + // 10: ownerId
                "\"JSON\" TEXT);"); // 11: json
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"lonch_msggroup_entity\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MsgGroupsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userID = entity.getUserID();
        if (userID != null) {
            stmt.bindString(2, userID);
        }
 
        String groupID = entity.getGroupID();
        if (groupID != null) {
            stmt.bindString(3, groupID);
        }
 
        String msgID = entity.getMsgID();
        if (msgID != null) {
            stmt.bindString(4, msgID);
        }
 
        String msgType = entity.getMsgType();
        if (msgType != null) {
            stmt.bindString(5, msgType);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(6, text);
        }
        stmt.bindLong(7, entity.getIsSelf() ? 1L: 0L);
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(8, nickName);
        }
 
        String faceUrl = entity.getFaceUrl();
        if (faceUrl != null) {
            stmt.bindString(9, faceUrl);
        }
        stmt.bindLong(10, entity.getTimMessage());
 
        String ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindString(11, ownerId);
        }
 
        String json = entity.getJson();
        if (json != null) {
            stmt.bindString(12, json);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MsgGroupsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userID = entity.getUserID();
        if (userID != null) {
            stmt.bindString(2, userID);
        }
 
        String groupID = entity.getGroupID();
        if (groupID != null) {
            stmt.bindString(3, groupID);
        }
 
        String msgID = entity.getMsgID();
        if (msgID != null) {
            stmt.bindString(4, msgID);
        }
 
        String msgType = entity.getMsgType();
        if (msgType != null) {
            stmt.bindString(5, msgType);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(6, text);
        }
        stmt.bindLong(7, entity.getIsSelf() ? 1L: 0L);
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(8, nickName);
        }
 
        String faceUrl = entity.getFaceUrl();
        if (faceUrl != null) {
            stmt.bindString(9, faceUrl);
        }
        stmt.bindLong(10, entity.getTimMessage());
 
        String ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindString(11, ownerId);
        }
 
        String json = entity.getJson();
        if (json != null) {
            stmt.bindString(12, json);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MsgGroupsEntity readEntity(Cursor cursor, int offset) {
        MsgGroupsEntity entity = new MsgGroupsEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // groupID
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // msgID
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // msgType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // text
            cursor.getShort(offset + 6) != 0, // isSelf
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // nickName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // faceUrl
            cursor.getLong(offset + 9), // timMessage
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // ownerId
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // json
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MsgGroupsEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGroupID(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMsgID(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMsgType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setText(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsSelf(cursor.getShort(offset + 6) != 0);
        entity.setNickName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setFaceUrl(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTimMessage(cursor.getLong(offset + 9));
        entity.setOwnerId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setJson(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MsgGroupsEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MsgGroupsEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MsgGroupsEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}