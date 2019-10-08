package com.example.four.fourdownload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class ThreadDAOImple implements ThreadDAO {
    private static final String TAG = "ThreadDAOImple";

    private ThreadDBHelper dbHelper = null;

    public ThreadDAOImple(Context context) {
        this.dbHelper = new ThreadDBHelper(context);
    }

    //
    @Override
    public void insertThread(ThreadInfo info) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("thread_id", info.getId());
        values.put("url", info.getUrl());
        values.put("start", info.getStart() + "");
        values.put("end", info.getEnd() + "");
        values.put("finished", info.getFinished() + "");
        values.put("filelength", info.getFileLength() + "");
        db.insert("thread_info", null, values);

        db.close();
    }

    // 刪除该url的指定id线程
    @Override
    public void deleteThread(String url) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int nums = db.delete("thread_info", "url = ?", new String[]{url});
        Log.i(TAG, "-->> deleteThreadInfoNums = " + nums);
        db.close();
    }

    // 更新该url的指定id线程进度
    @Override
    public void updateThread(String url, int thread_id, long finished) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?", new Object[]{finished, url,
                thread_id});

        db.close();
    }

    // 查询该url的所有线程
    @Override
    public List<ThreadInfo> queryThreads(String url) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ThreadInfo threadInfo = new ThreadInfo();
        List<ThreadInfo> threadInfoList = new ArrayList<>();
        Cursor cursor = db.query("thread_info", null, "url = ?", new String[]{url}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
                threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                threadInfo.setStart(Long.valueOf(cursor.getString(cursor.getColumnIndex("start")).trim()));
                threadInfo.setEnd(Long.valueOf(cursor.getString(cursor.getColumnIndex("end")).trim()));
                threadInfo.setFinished(Long.valueOf(cursor.getString(cursor.getColumnIndex("finished")).trim()));
                threadInfo.setFileLength(Long.valueOf(cursor.getString(cursor.getColumnIndex("filelength")).trim()));

                threadInfoList.add(threadInfo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return threadInfoList;
    }

    // 根据 url 和 thread_id 可以唯一确定一个thread
    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("thread_info", null, "url = ? and thread_id = ?", new String[]{url, thread_id + ""},
                null, null, null);
        boolean exists = cursor.moveToFirst();
        db.close();
        cursor.close();
        return exists;
    }

    @Override
    public long queryProgress(String url) {
        List<ThreadInfo> threadInfos = queryThreads(url);
        long totleLength = -1;
        long finishedLength = 0;
        int size = threadInfos.size();
        if (size != 0) {
            totleLength = Long.valueOf(threadInfos.get(size - 1).getEnd()) + 1;
            for (ThreadInfo threadInfo : threadInfos) {
                finishedLength += threadInfo.getFinished();
            }
            return finishedLength / totleLength;
        }
        return 0;
    }

}
