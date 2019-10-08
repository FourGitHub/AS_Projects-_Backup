package com.example.four.appwidgetprovidertest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("remoteView_Noti", "其他", NotificationManager.IMPORTANCE_HIGH);
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }

    /*
     * 按钮点击事件
     */
    public void sendRemoteNotification(View view) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, RemoteNotificationActivity.class);
        intent.putExtra("extra", 1);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remote_notification);
        remoteViews.setOnClickPendingIntent(R.id.remote_img, pi);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainActivity.this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(),"remoteView_Noti")
                .setSmallIcon(R.mipmap.icon_notification)
                .setContentIntent(pi)
                .setCustomContentView(remoteViews)
                .setTicker("Ticker")
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);
    }

}
