package com.lenovohit.administrator.tyut.greendao;

/**
 * Created by Administrator on 2017/3/22.
 */

import android.content.Context;

/**
 *  greenDao管理类
 */
public class DaoManager {
    private static DaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DaoManager(Context context) {
        DBHelper devOpenHelper =new DBHelper(context);
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public static DaoManager getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new DaoManager(context);
        }
        return mInstance;
    }
}