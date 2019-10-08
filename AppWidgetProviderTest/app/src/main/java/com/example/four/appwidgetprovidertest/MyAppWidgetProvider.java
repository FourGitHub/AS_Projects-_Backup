package com.example.four.appwidgetprovidertest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.widget.RemoteViews;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {

    // 点击小部件中发送的广播的Action
    public static String CLICK_ACTION = "com.example.four.appwidgetprovidertest.action.CLICK";

    public MyAppWidgetProvider() {
        super();
    }

    /**
     * AppWidgetProvider本质是个广播，onReceive是广播内置方法，根据不同的Action，分发具体事件给其他方法
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null && intent.getAction().equals(CLICK_ACTION)) {

            new Thread(() -> {
                Bitmap srcBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_app_widget_logo);
                AppWidgetManager manager = AppWidgetManager.getInstance(context);

                for (int i = 0; i <= 36; i++) {
                    float degree = (i * 10) % 360;
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                    remoteViews.setImageViewBitmap(R.id.widget_img, rotateBitmap(srcBmp, degree));
                    Intent clickIntent = new Intent(CLICK_ACTION);
                    PendingIntent pi = PendingIntent.getBroadcast(context, 0, clickIntent, 0);

                    // 给小部件的控件添加点击事件 --- 发送一条action为CLICK_ACTION的广播，从而onReceive()方法会被调用
                    remoteViews.setOnClickPendingIntent(R.id.widget_img, pi);

                    // manager.updateAppWidget(), 相当于就是用一个新的remoteViews替换之前的remoteViews
                    manager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteViews);

                    SystemClock.sleep(50);
                }
            }).start();
        }
    }

    /**
     * 桌面小部件被添加时(初始化)或者每次更新时（reset）都会调用一次此方法,
     * 小部件的更新时机由android:updatePeriodMillis来指定，每个周期小部件都会自动更新一次。
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

            // 桌面小部件 单击事件发送广播
            Intent clickIntent = new Intent(CLICK_ACTION);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, clickIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_img, pi);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    /*
    自定义方法， 利用Matrix将目标图片旋转指定角度
    */
    private Bitmap rotateBitmap(Bitmap srcBmp, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        return Bitmap.createBitmap(srcBmp, 0, 0, srcBmp.getWidth(), srcBmp.getHeight(), matrix, true);
    }

}
