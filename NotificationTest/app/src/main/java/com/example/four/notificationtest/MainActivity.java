package com.example.four.notificationtest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_CHAT_ID = "chat";
    private static final String CHANNEL_SUBSCRIBE_ID = "subscribe";
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 创建通知渠道（channel_id），并初始化通知渠道的重要性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotifucationChannel(CHANNEL_CHAT_ID, "聊天消息",
                    NotificationManager.IMPORTANCE_HIGH);
            createNotifucationChannel(CHANNEL_SUBSCRIBE_ID, "订阅消息",
                    NotificationManager.IMPORTANCE_DEFAULT);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotifucationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }

    public void onSendChat(View view) {
        // 防止用户误关重要的通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            guideUserOpenNotificationChannel(CHANNEL_CHAT_ID);
        }

        // 创建 PendingIntent，指定通知单击操作
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // 构建包含返回栈的 PendingIntent，效果：若程序此时在后台运行，PendingIntent之后，返回到主Activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        // 构建通知并指定通知渠道
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_CHAT_ID)
                .setContentTitle("收到一条聊天信息")
                .setContentText("今天中午吃什么？")
                .setSubText("subText")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.small)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.big))
                .setTicker("Ticker")
                .setColor(Color.parseColor("#CF2177")) // 紫红
                .setColorized(true)
                .setNumber(2)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setContentIntent(pi)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();
        notification.flags |= NotificationCompat.FLAG_NO_CLEAR;

        notificationManager.notify(1, notification);
    }

    public void onSendSubscribe(View view) {
        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        intent.putExtra("strInfo","newInfo");
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // 构建包含返回栈的 PendingIntent，效果：如果程序在后台运行，PendingIntent执行后，back键返回到MainActivity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_SUBSCRIBE_ID)
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setContentIntent(pi)
                .setSmallIcon(R.mipmap.icon_heart)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_heart))
                .setWhen(System.currentTimeMillis())

                .setAutoCancel(true)

                .setTicker("Ticker")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();
        notificationManager.notify(2, notification);
    }

    public void onRemoteView(View view) {
        int remoteViewNotificationId = 3;
        // RemoteView加载布局文件
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remote_views);

        // 设置布局中控件资源
        remoteViews.setImageViewResource(R.id.remote_image, R.mipmap.icon_heart);
        remoteViews.setTextViewText(R.id.remote_tv, "呐，小心心给你");

        // 创建PendingIntent
        Intent intent = new Intent(this, SecActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("notificationId", remoteViewNotificationId);
        intent.putExtra("notificationId", bundle);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // 为id为 remote_layout 的布局设置单击事件
        remoteViews.setOnClickPendingIntent(R.id.remote_layout, pi);

        // 创建通知
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_SUBSCRIBE_ID)
                .setSmallIcon(R.mipmap.icon_heart)
                .setTicker("RemoteView")
                .setAutoCancel(true)
                .setCustomContentView(remoteViews)
                .setContentIntent(pi)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();

        notificationManager.notify(remoteViewNotificationId, notification);
    }


    public void onUpdateRemoteView(View view) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remote_views);
        remoteViews.setImageViewResource(R.id.remote_image,R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.remote_tv,"锦鲤");

        Intent intent = new Intent(this, SecActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("notificationId", 3);
        intent.putExtra("notificationId", bundle);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainActivity.this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_SUBSCRIBE_ID)
                .setSmallIcon(R.mipmap.icon_heart)
                .setWhen(System.currentTimeMillis())
                .setCustomContentView(remoteViews)
                .setContentIntent(pi)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();

        notificationManager.notify(3,notification);
    }

    @RequiresApi(26)
    private void guideUserOpenNotificationChannel(String channelId) {
        NotificationChannel channel = notificationManager.getNotificationChannel(channelId);
        if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "请手动将通知功能打开", Toast.LENGTH_SHORT).show();
        }
    }

}