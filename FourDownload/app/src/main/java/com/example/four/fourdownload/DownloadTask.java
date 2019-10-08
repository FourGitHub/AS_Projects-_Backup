package com.example.four.fourdownload;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class DownloadTask {
    private static final String TAG = "DownloadTask";

    public static final String DownloadDir = Environment.getExternalStoragePublicDirectory(Environment
            .DIRECTORY_DOWNLOADS).getAbsolutePath();
    private static final int THREAD_NUMS = 10;
    public static final int READ_TIME_OUT = 8 * 1000;
    public static final int CONN_TIME_OUT = 8 * 1000;
    // 记录已下载的文件长度
    private static AtomicLong mFinishedLength = new AtomicLong(0);
    // 记录文件总长度
    private static AtomicLong fileTotleLength = new AtomicLong(0);
    // 记录当前任务是否暂停
    private AtomicBoolean isTaskPaused = new AtomicBoolean(true);
    // 下载任务的线程
    private List<DownloadThread> mThreadlist = null;
    // 网上说是生成一个占位文件 -->> setLength()
    private RandomAccessFile mSaveFile = null;
    // 操作数据库的类
    private ThreadDAOImple mThreadDAOImple = null;
    // 保存当前正在下载任务的url
    private String url = null;
    // 保存活动端传递过来的 Handler对象，用于线程间的消息传递
    private Handler mClientHandler = null;

    // 后来新增的一个布尔变量，用于标记url是否初始化下载成功，
    // 初始化失败有两(三)种可能，1. 网络异常 2.连接错误 3.未知错误
    private boolean isInitSuccess = false;

    public void setTaskPaused(boolean isTaskPaused) {
        this.isTaskPaused.set(isTaskPaused);
        mFinishedLength.set(0);
    }

    public DownloadTask(Context context, Handler handler) {
        mThreadDAOImple = new ThreadDAOImple(context);
        mClientHandler = handler;
    }

    public void startDownload(String url) {

        this.url = url;
        List<ThreadInfo> list = mThreadDAOImple.queryThreads(url);
        int size = list.size();
        Log.i(TAG, "startDownload: -->> url = " + url);
        Log.i(TAG, "startDownload: -->> DB_THREAD_NUMS = " + size);

        // 有两种情况会导致 size != THREAD_NUMS
        // 1. 初次执行该 url 下载任务
        // 2. 从暂停中恢复下载
        if (size != THREAD_NUMS) {
            initThreadsInfoAndStart(url);
            Log.i(TAG, "startDownload() -->> after init thread , threadList = " + mThreadlist);
            isTaskPaused.set(false);

            Log.i(TAG, "startDownload() -->> after init thread, isInitThread =  " + isInitSuccess);

            // 之前为了解决一个问题：判断url是否为有效链接，采用的方法是判断 initThreadsInfoAndStart(url); 方法执行完之后，
            // mThreadList 还是否为空，结果实验发现不行，因为就算正常 url 初始化成功，Log输出得到的 mTHreadList 任然为空。
            // 总结：执行此分支时，mThreadList是在子线程中进行初始化的，相当于异步，主线程只是抛了一个任务给子线程，
            // 叫它去初始化下载相关信息，然后立刻又在主线程中继续执行！！！理解这一点非常重要，因为执行到下面注释代码之前，
            // 极有极有可能子线程还没执行完，所以 使用子线程中的变量 或者 在子线程中改变标记位 作为判断是否初始化url下载任务非常不合理！
//            if (mThreadlist == null) {
//                isTaskPaused.set(true);
//                mClientHandler.obtainMessage(DownloadService.DOWNLOAD_FAILED).sendToTarget();
//                return;
//            }

        }

        if (isTaskPaused.get()) {
            // 如果是从暂停恢复下载，或者二次启动程序恢复下载
            // 则重新从数据库中读取ThreadInfo构造DownloadTask
            fileTotleLength.set(list.get(THREAD_NUMS - 1).getFileLength());// 随便找了一个threadInfo获取文件总长度，因为每个线程都保存了
            Log.i(TAG, "startDownload: fileTotleLength -->>" + fileTotleLength.get());

            mThreadlist = new ArrayList<DownloadThread>();

            for (ThreadInfo threadInfo : list) {
                mThreadlist.add(new DownloadThread(threadInfo));
            }

            for (DownloadThread downloadThread : mThreadlist) {
                downloadThread.start();
            }

            isTaskPaused.set(false);
        } else {
            // 由于 MainActivity 在下载时判断了 任务进行时url的唯一性 这一分支暂不会执行，以后可拓展？
            if (size != 0) {
                mClientHandler.obtainMessage(DownloadService.DOWNLOADING).sendToTarget();
            }
        }

    }

    public void pauseDownload() {
        setTaskPaused(true);
    }

    public void cancleDownload(String url) {
        setTaskPaused(true);
        // 删除文件，刷新UI
        if (url != null) {

            File file = getmSaveFile();
            if (file.exists()) {
                file.delete();
            }
            mThreadDAOImple.deleteThread(url);
            mFinishedLength.set(0);
            mClientHandler.obtainMessage(DownloadService.DOWNLOAD_CANCLE).sendToTarget();
        }
    }

    public File getmSaveFile() {
        File dir = new File(DownloadDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String child = null;

        if (url.lastIndexOf("/") != -1) {
            child = url.substring(url.lastIndexOf("/"));
        } else {
            child = fileTotleLength.get() + "";
        }

        File file = new File(dir, child);
        return file;
    }

    /**
     * @param url 利用url
     *            初始化各个线程的ThreadInfo ,并添加到数据库
     *            创建 RandomAccessFile。
     *            该方法在任务期间只会执行一次！
     */
    public void initThreadsInfoAndStart(String url) {
        // 一定记得：访问网络必须在子线程中进行
        new Thread(new Runnable() {
            HttpURLConnection conn = null;

            @Override
            public void run() {

                URL fileUrl = null;
                try {
                    fileUrl = new URL(url);
                    conn = (HttpURLConnection) fileUrl.openConnection();
                    conn.setRequestMethod("GET");

                    long length = -1;
                    long blockSize = -1;

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        length = (long) conn.getContentLength();
                    } else {
                        mClientHandler.obtainMessage(DownloadService.DOWNLOAD_FAILED).sendToTarget();
                        isTaskPaused.set(true);
                        return;
                    }
                    Log.i(TAG, "-->> run: length after conn = " + length);
                    if (length <= 0) {
                        isTaskPaused.set(true);
                        return;
                    }

                    fileTotleLength.set(length);
                    mSaveFile = new RandomAccessFile(getmSaveFile(), "rwd");
                    mSaveFile.setLength(length);
                    blockSize = length / THREAD_NUMS;

                    for (int i = 0; i < THREAD_NUMS; i++) {
                        long start = i * blockSize;
                        long end = (i + 1) * blockSize - 1;
                        if (i == (THREAD_NUMS - 1)) {
                            end = length - 1;
                        }

                        mThreadlist = new ArrayList<DownloadThread>();

                        ThreadInfo threadInfo = new ThreadInfo(i, url, start, end, 0, length);
                        mThreadlist.add(new DownloadThread(threadInfo));
                        mThreadDAOImple.insertThread(threadInfo);
                    }

                    for (DownloadThread downloadThread : mThreadlist) {
                        downloadThread.start();
                    }

                } catch (MalformedURLException e) {
                    // fileUrl = new URL(url); 抛出的异常，我这里用它来检测url无效
                    mFinishedLength.set(0);
                    isTaskPaused.set(true);
                    mClientHandler.obtainMessage(DownloadService.DOWNLOAD_FAILED).sendToTarget();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    mFinishedLength.set(0);
                    isTaskPaused.set(true);
                    mClientHandler.obtainMessage(DownloadService.DOWNLOAD_FAILED).sendToTarget();
                }

            }
        }).start();

        Log.i(TAG, "-->> initThreads Over... ");

    }


    /**
     * 由最后一个完成任务的线程删除数据库中某一url的ThreadInfo
     */
    public synchronized void checkIsAllFinished() {
        boolean isAllFinished = true;
        for (DownloadThread downloadThread : mThreadlist) {
            if (!downloadThread.getIsFinished()) {
                isAllFinished = false;
                break;
            }
        }
        if (isAllFinished) {
            // 先通知activity下载完成，再删除数据库中该url对应的线程信息。
            mClientHandler.obtainMessage(DownloadService.DOWNLOAD_FINISHED).sendToTarget();
            mFinishedLength.set(0);
            mThreadDAOImple.deleteThread(url);
        }

    }

    class DownloadThread extends Thread {
        // ThreadInfo 记录每个线程的下载链接、起始位置
        ThreadInfo threadInfo;

        // 记录该线程所需要下载的片段是否下载完毕
        boolean isFinished = false;

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        public ThreadInfo getThreadInfo() {
            return threadInfo;
        }

        public boolean getIsFinished() {
            return isFinished;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream is = null;
            try {
                URL url = new URL(getThreadInfo().getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(CONN_TIME_OUT);
                conn.setReadTimeout(READ_TIME_OUT);
                conn.setRequestMethod("GET");

                long start = threadInfo.getStart() + threadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());
                raf = new RandomAccessFile(getmSaveFile(), "rwd");
                raf.seek(start);
                mFinishedLength.addAndGet(threadInfo.getFinished());

                if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    is = conn.getInputStream();
                    byte[] bt = new byte[1024];
                    int len = -1;
                    // 定义UI刷新时间
                    long time = System.currentTimeMillis();
                    while ((len = is.read(bt)) != -1) {
                        raf.write(bt, 0, len);
                        // 累计整个文件完成进度
                        mFinishedLength.addAndGet(len);
                        // 保存每个线程完成的进度
                        threadInfo.setFinished(threadInfo.getFinished() + len);
                        // 每500毫秒通知一次activity刷新一次
                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            long progress = (mFinishedLength.get() * 100) / fileTotleLength.get();
                            Log.i(TAG, "run: updateprogress -->> progress = " + progress + " finishedLength = " +
                                    mFinishedLength.get());

                            mClientHandler.obtainMessage(DownloadService.DOWNLOAD_UPDATE_PROGRESS, (int) progress, 0)
                                    .sendToTarget();
                        }

                        // 暂停下载则更新数据库中的ThreadInfo
                        if (isTaskPaused.get()) {
                            mThreadDAOImple.updateThread(threadInfo.getUrl(), threadInfo.getId(), threadInfo
                                    .getFinished());
                            Log.i(TAG, SystemClock.currentThreadTimeMillis() + " -->> Thread Stop" + currentThread()
                                    .getId());
                            return;
                        }
                    }
                }

                // 该线程下载完毕,并检查是否该url任务的所有线程都完成对应任务。
                isFinished = true;
                Log.i(TAG, "-->> Thread " + currentThread().getId() + " Stop" + " finishedLength = " +
                        mFinishedLength.get());
                checkIsAllFinished();

            } catch (Exception e) {
                // 下载过程中出现异常先更新数据库中的线程进度信息
                mThreadDAOImple.updateThread(threadInfo.getUrl(), threadInfo.getId(), threadInfo
                        .getFinished());
                isTaskPaused.set(true);
                mFinishedLength.set(0);
                mClientHandler.obtainMessage(DownloadService.DOWNLOAD_PAUSE).sendToTarget();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
