package com.lonch.client.databases;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lonch.client.database.bean.ConversationListEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "lonch_conversation_entity".
*/
public class ConversationListEntityDao extends AbstractDao<ConversationListEntity, Long> {

    public static final String TABLENAME = "lonch_conversation_entity";

    /**
     * Properties of entity ConversationListEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Conversation = new Property(1, String.class, "conversation", false, "CONVERSATION");
        public final static Property Json = new Property(2, String.class, "json", false, "JSON");
        public final static Property Seq = new Property(3, long.class, "seq", false, "SEQ");
        public final static Property UserId = new Property(4, String.class, "userId", false, "USER_ID");
    }


    public ConversationListEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ConversationListEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"lonch_conversation_entity\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CONVERSATION\" TEXT," + // 1: conversation
                "\"JSON\" TEXT," + // 2: json
                "\"SEQ\" INTEGER NOT NULL ," + // 3: seq
                "\"USER_ID\" TEXT);"); // 4: userId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"lonch_conversation_entity\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ConversationListEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String conversation = entity.getConversation();
        if (conversation != null) {
            stmt.bindString(2, conversation);
        }
 
        String json = entity.getJson();
        if (json != null) {
            stmt.bindString(3, json);
        }
        stmt.bindLong(4, entity.getSeq());
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(5, userId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ConversationListEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String conversation = entity.getConversation();
        if (conversation != null) {
            stmt.bindString(2, conversation);
        }
 
        String json = entity.getJson();
        if (json != null) {
            stmt.bindString(3, json);
        }
        stmt.bindLong(4, entity.getSeq());
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(5, userId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ConversationListEntity readEntity(Cursor cursor, int offset) {
        ConversationListEntity entity = new ConversationListEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // conversation
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // json
            cursor.getLong(offset + 3), // seq
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // userId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ConversationListEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setConversation(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setJson(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSeq(cursor.getLong(offset + 3));
        entity.setUserId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ConversationListEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ConversationListEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ConversationListEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
