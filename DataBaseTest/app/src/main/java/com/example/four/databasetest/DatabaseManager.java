package com.example.four.databasetest;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create on 2019/03/12
 *
 * @author Four
 * @description 每次创建SQLiteOpenHelper对象时，都是在建立新的数据库连接，如果有两个不同的数据库连接同时写入数据库，
 * 其中一个就会写入失败。因此需要保证在多个线程中操作数据库时，使用相同的数据库连接，
 * 换句话说，就是要在程序中使用单例的SQLiteOpenHelper对象。
 */
public class DatabaseManager {

    // 使用一个计数器跟踪数据库使用者的数量，避免错误的关闭一个其他使用者正在使用的数据库
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static SQLiteOpenHelper mHelper;
    private static DatabaseManager instance;
    private static SQLiteDatabase mDatabase;

    private DatabaseManager() { }

    // 在 MyApplication 里初始化。
    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mHelper = helper;
        }
    }

    public static synchronized  DatabaseManager getInstance(){
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initialize(..) method first.");
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (mHelper == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initialize(..) method first.");
        }

        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = mHelper.getWritableDatabase();
        }

        return mDatabase;
    }

    public synchronized void closeDatabase(){
        if (mDatabase != null && mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }

}

