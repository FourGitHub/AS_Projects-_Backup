package com.example.four.databasetest;

import android.app.Application;
import android.content.Context;
import android.content.SyncAdapterType;

/**
 * Create on 2019/03/12
 *
 * @author Four
 * @description
 */
public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initDatabaseManager();
    }

    private void initDatabaseManager() {
        MyDatabaseHelper helper = new MyDatabaseHelper(mContext, "BookStore.db", null, 2);
        DatabaseManager.initializeInstance(helper);
    }

    public static Context getContext(){
        return mContext;
    }
}
