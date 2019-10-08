package com.example.four.servicebestpractice;

/**
 * Created by Administrator on 2017/12/30 0030.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
