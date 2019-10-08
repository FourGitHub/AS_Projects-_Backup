package com.example.four.servicebestpractice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

// 为了保证下载一直在后台运行，我们创建一个下载服务
public class DownloadService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public DownloadService() {
    }

    private String downloadUrl;
    private DownloadTask downloadTask;// 下载任务，DownloadTask extends AsyncTask{} // 封装的异步消息处理机制
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManger().notify(1,getNotification("下载中···",progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManger().notify(1,getNotification("下载成功！",-1));
            Toast.makeText(DownloadService.this,"Download success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManger().notify(1,getNotification("下载失败！",-1));
            Toast.makeText(DownloadService.this,"Download failed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
        downloadTask = null;
            getNotificationManger().notify(1,getNotification("暂停下载！",-1));
            Toast.makeText(DownloadService.this,"Download paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"Download canceled！", Toast.LENGTH_SHORT).show();
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{
        public void pauseDownload() {
            if (downloadTask!= null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            }
            if (downloadUrl != null) {
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);
                if (file.exists()) {
                    file.delete();
                }
                getNotificationManger().cancel(1);// 取消以前显示的通知。 如果是暂时的，视图将被隐藏。 如果它是持久的，它将从状态栏中删除。
                stopForeground(true);
                Toast.makeText(DownloadService.this,"Canceled", Toast.LENGTH_SHORT).show();
            }
        }
        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                startForeground(1,getNotification("开始下载···",0));
                Toast.makeText(DownloadService.this,"Start downloading···", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private NotificationManager getNotificationManger(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if (progress >= 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100,progress,false);
        }
        return  builder.build();
    }
}
