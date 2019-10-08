package com.example.four.fourdownload;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class DownloadService extends Service {
    private static final String TAG = "DownloadService";

    public static final int DOWNLOAD_START = 0;
    public static final int DOWNLOAD_PAUSE = 1;
    public static final int DOWNLOAD_CANCLE = 2;
    public static final int DOWNLOAD_FINISHED = 3;
    public static final int DOWNLOAD_FAILED = 4;
    public static final int DOWNLOADING = 5;
    public static final int DOWNLOAD_UPDATE_PROGRESS = 6;

    private DownloadTask downloadTask;

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {

        void startDownload(String url, Handler clientHandler) {
            downloadTask = new DownloadTask(getApplicationContext(), clientHandler);
            downloadTask.startDownload(url);
        }

        void pauseDownload() {
            downloadTask.pauseDownload();
        }

        // 在Activity调用之前，应该传入需要删除的文件对应的正确的url
        void cancleDownload(String url) {
            downloadTask.cancleDownload(url);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}