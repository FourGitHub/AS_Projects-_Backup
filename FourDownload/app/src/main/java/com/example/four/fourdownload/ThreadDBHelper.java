package com.example.four.fourdownload;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/6/6 0006.
 * 数据库存储记录线程信息(thread_id,url,起点，终点，已下载长度，和url对应的文件总长度)
 */

public class ThreadDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "download.db";
    public static final int VERSION = 1;

    public static final String CREATE_DB = "CREATE TABLE thread_info("
            + "id integer primary key autoincrement,"
            + "thread_id integer,"
            + "filelength text,"
            + "url text,"
            + "start text,"
            + "end text,"
            + "finished text)";

    public static final String DROP_DB = "DROP TABLE if exists thread_info";

    public ThreadDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_DB);
        db.execSQL(CREATE_DB);
    }
}
