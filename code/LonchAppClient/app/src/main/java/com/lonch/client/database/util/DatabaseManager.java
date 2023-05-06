package com.lonch.client.database.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lonch.client.databases.DaoMaster;
import com.lonch.client.databases.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

public class DatabaseManager {
    private static final String DB_NAME = "lonch_databases";
    private Context mContext;

    private LonchOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    private static volatile DatabaseManager mInstance;
    private static final byte[] LOCKER = new byte[0];

    public synchronized static DatabaseManager getInstance() {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new DatabaseManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context){
        this.mContext = context;
    }

    private DaoMaster getDaoMaster(){
        if(mDaoMaster == null){
            mHelper = LonchOpenHelper.getInstance(mContext,DB_NAME,null);
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
            db =  mHelper.getWritableDatabase();
            db.disableWriteAheadLogging();
            mDaoMaster = new DaoMaster(db);
        }
        return mDaoMaster;
    }

    public DaoSession getDaoSession(){
        if(mDaoSession == null){
            if(mDaoMaster == null){
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
    public static String getTableName(){
        return "ums_manager_entity";
    }
    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    private void closeHelper(){
        if(mHelper != null){
            mHelper.close();
            mHelper = null;
        }
    }

    private void closeDaoSession(){
        if(mDaoSession != null){
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

}
