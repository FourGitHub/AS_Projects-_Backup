package com.example.four.servicetest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    private DownloadBinder mBinder= new DownloadBinder();
    private static final String CHANNEL_ID = "foregroundService";

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("MyService", "哈哈startDownload() executed.");
            Toast.makeText(getApplicationContext(),"startDownload() executed.", Toast.LENGTH_SHORT).show();
        }

        public int getProgress(){
            Log.d("MyService", "哈哈getProgress() executed.");
            Toast.makeText(getApplicationContext(),"getProgress() executed.", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    // 一个空的构造方法
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("MyService", "哈哈onBind() executed.");
        Toast.makeText(getApplicationContext(),"onBind() executed.", Toast.LENGTH_SHORT).show();
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 进行版本判断，是否有必要创建通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        // 后台服务的系统优先级较低，当系统内存不足的时候很可能会被回收，这时候可以考虑使用前台服务。
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);

        // 如果用户的系统版本高于O, 且不指定有效通知渠道，运行时会crash
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setContentIntent(pi)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        startForeground(1,notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(){
        NotificationChannel channel =
                new NotificationChannel(MyService.CHANNEL_ID, "前台服务",NotificationManager.IMPORTANCE_LOW);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    // 会在每次启动服务时调用，是运行在主线程中的，所以当如果需要进行一些耗时操作，会造成主线程阻塞，UI无反应，造成ANR。
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "哈哈哈onStartCommand() executed.");
        Toast.makeText(getApplicationContext(),"onStartCommand() executed.", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    // 会在服务销毁时调用
    public void onDestroy() {
        Log.d("MyService", "哈哈哈onDestroy() executed.");
        Toast.makeText(getApplicationContext(),"onDestroy() executed.", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
