package com.example.four.intentservicetest;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class UploadImgService extends IntentService {
//    private static final String ACTION_UPLOAD_IMG = "com.four.example.intentservce.action.UPLOAD_IMAGE";
//    private static final String EXTRA_IMG_PATH
    private static final String ACTION_UPLOAD_IMG = "com.zhy.blogcodes.intentservice.action.UPLOAD_IMAGE";
    public static final String EXTRA_IMG_PATH = "com.zhy.blogcodes.intentservice.extra.IMG_PATH";
    private LocalBroadcastManager localBroadcastManager;

    // 静态方法
    public static void startUploadImg(Context context, String path) {
        Intent intent = new Intent(context, UploadImgService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH, path);
        context.startService(intent);
    }


    public UploadImgService() {
        super("UploadImgService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD_IMG.equals(action)) {
                final String path = intent.getStringExtra(EXTRA_IMG_PATH);
                handleUploadImg(path);
            }
        }
    }

    private void handleUploadImg(String path) {
        try {
            //模拟上传耗时
            Thread.sleep(3000);

            Intent intent = new Intent(MainActivity.UPLOAD_RESULT);
            intent.putExtra(EXTRA_IMG_PATH, path);
            localBroadcastManager.sendBroadcast(intent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private static final String TAG = "UploadImgService";
    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        Log.e("TAG", "onCreate");
        Log.d(TAG, "2哈哈ID IS " + localBroadcastManager.toString());

        Toast.makeText(getApplicationContext(), "UploadingService onCreate() executed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
        Toast.makeText(getApplicationContext(), "UploadingService onDestory() executed.", Toast.LENGTH_SHORT).show();

    }
}