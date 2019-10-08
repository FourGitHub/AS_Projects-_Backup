package com.example.four.fourdownload;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.four.fourdownload.HttpUtil.HttpUtil;
import com.example.four.fourdownload.ToastUtil.ToastUtil;

// 应该写一个服务监听网络状况，一旦网络异常，立即暂停下载，更新数据库中ThreadInfo.

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageButton keepOrPauseBtn;

    private ProgressBar progressBar;

    private EditText urlEdit;
    private boolean isDownloading = false;

    private String url;

    // 下载前检查数据库中是否有threadInfo
    private ThreadDAOImple threadDAOImple;
    private DownloadService.DownloadBinder downloadBinder;

    public static final int WES_PERMISSION_REQUEST_CODE = 1;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlEdit = (EditText) findViewById(R.id.url_edit);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        keepOrPauseBtn = (ImageButton) findViewById(R.id.img_keep_pause);
        threadDAOImple = new ThreadDAOImple(this);
        Intent bindService = new Intent(this, DownloadService.class);
        bindService(bindService, conn, BIND_AUTO_CREATE);
    }

    public void onStartBtnClicked(View view) {
        String taskUrl = urlEdit.getText().toString().trim();

        // 检测网络
        if (!HttpUtil.networkStatus(getApplicationContext())) {
            ToastUtil.showToast(getApplicationContext(), "网络连接异常...", Toast.LENGTH_SHORT);
            return;
        }

        // 保证当前任务的唯一性
        if (url == null) {
            if (!TextUtils.isEmpty(taskUrl)) {
                url = taskUrl;
//                if (threadDAOImple.queryThreads(url).size() != 0) {
//                    progressBar.setProgress(threadDAOImple.queryProgress(url));
                    checkWESPermission();
//                }
            } else {
                ToastUtil.showToast(getApplicationContext(), "输入下载链接为空...", Toast.LENGTH_SHORT);
                urlEdit.setText("");
                return;
            }
        }else{
            ToastUtil.showToast(getApplicationContext(), "当前有下载任务正在进行...", Toast.LENGTH_SHORT);
        }

    }

    public void onKeepOrPauseBtnClicked(View view) {
        if (isDownloading) {
            // 如果当时正在下载，说明点击按钮需要执行暂停逻辑，
            ((ImageButton) view).setImageResource(R.mipmap.icon_pause);
            downloadBinder.pauseDownload();
            isDownloading = false;
        } else {
            // 继续下载, 若是关闭应用后恢复继续下载，url哪里来？？
            if (!TextUtils.isEmpty(url)) {
                downloadBinder.startDownload(url, clientHandler);
                ((ImageButton) view).setImageResource(R.mipmap.icon_keep);
                isDownloading = true;
            }

        }
    }

    public void onCancleBtnClicked(View view) {
        if (!TextUtils.isEmpty(url)) {
            downloadBinder.cancleDownload(url);
            url = null;
            isDownloading = false;
            progressBar.setProgress(0);
        } else {
            ToastUtil.showToast(getApplicationContext(), "当前没有任务正在下载", Toast.LENGTH_SHORT);
        }
    }


    public void checkWESPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WES_PERMISSION_REQUEST_CODE);
        } else {
            startDownload(url);
        }
    }

    public void startDownload(String url) {
        isDownloading = true;
        urlEdit.setText("");
        keepOrPauseBtn.setImageResource(R.mipmap.icon_keep);
        downloadBinder.startDownload(url, clientHandler);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        switch (requestCode) {
            case WES_PERMISSION_REQUEST_CODE:
                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload(url);
                } else {
                    ToastUtil.showToast(getApplicationContext(), "由于您拒绝授权，无法完成下载操作...", Toast.LENGTH_SHORT);
                }
                break;
        }
    }

    private Handler clientHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DownloadService.DOWNLOAD_START:
                    break;
                case DownloadService.DOWNLOAD_PAUSE:
                    ToastUtil.showToast(getApplicationContext(), "下载暂停...", Toast.LENGTH_SHORT);
                    keepOrPauseBtn.setImageResource(R.mipmap.icon_pause);
                    isDownloading = false;
                    break;
                case DownloadService.DOWNLOAD_FINISHED:
                    ToastUtil.showToast(getApplicationContext(), "下载完成...", Toast.LENGTH_SHORT);
                    url = null;
                    isDownloading = false;
                    break;
                case DownloadService.DOWNLOAD_CANCLE:
                    ToastUtil.showToast(getApplicationContext(), "下载取消...", Toast.LENGTH_SHORT);
                    url = null;
                    isDownloading = false;
                    break;
                case DownloadService.DOWNLOAD_FAILED:
                    ToastUtil.showToast(getApplicationContext(), "下载错误...", Toast.LENGTH_SHORT);
                    url = null;
                    isDownloading = false;
                    urlEdit.setText("");
                    break;
                case DownloadService.DOWNLOADING:
                    ToastUtil.showToast(getApplicationContext(), "该任务正在下载中...", Toast.LENGTH_SHORT);
                    break;
                case DownloadService.DOWNLOAD_UPDATE_PROGRESS:
                    progressBar.setProgress(msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadBinder.pauseDownload();
    }
}
