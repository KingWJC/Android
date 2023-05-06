package com.lonch.client.database.util;

import static com.lonch.client.database.util.MigrationHelper.migrate;
import static com.lonch.client.databases.DaoMaster.createAllTables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lonch.client.databases.*;
import com.lonch.client.databases.MsgGroupsEntityDao;

import org.greenrobot.greendao.database.Database;


public class LonchOpenHelper extends DaoMaster.OpenHelper {
    public LonchOpenHelper(Context context, String name) {
        super(context, name);
    }

    public LonchOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    private static volatile LonchOpenHelper mInstance;
    private static final byte[] LOCKER = new byte[0];

    public synchronized static LonchOpenHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new LonchOpenHelper(context, name, factory);
                }
            }
        }
        return mInstance;
    }


    @Override
    public void onCreate(Database db) {
        createAllTables(db, true);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

        //把需要管理的数据库表DAO作为最后一个参数传入到方法中
        migrate(db, new MigrationHelper.ReCreateAllTableListener() {

                    @Override
                    public void onCreateAllTables(Database db, boolean ifNotExists) {
                        createAllTables(db, ifNotExists);
                    }

                    @Override
                    public void onDropAllTables(Database db, boolean ifExists) {
                        DaoMaster.dropAllTables(db, ifExists);
                    }
                }, InfoFriendsEntityDao.class, InfoUserEntityDao.class,
                MsgGroupsEntityDao.class, MsgPersonalEntityDao.class,
                ConversationListEntityDao.class, WebVersionEntityDao.class,
                LocalZipEntityDao.class, LogEntityDao.class,
                NetLinkEntityDao.class, AccelerationOrderEntityDao.class,
                WebVersionEntityDao.class, SmallVideoEntityDao.class); //更新的内容

    }

}
