package com.example.four.fourdownload;

import java.util.List;

/**
 * Created by Administrator on 2018/6/6 0006.
 * 数据库操作类
 */
public interface ThreadDAO {
    // 增加线程
    void insertThread(ThreadInfo info);

    // 刪除数据库中该url对应的所有线程信息
    void deleteThread(String url);

    // 更新线程
    void updateThread(String url, int thread_id, long finished);

    // 查询针对某一URL的线程信息
    List<ThreadInfo> queryThreads(String url);

    // 判断针对某一URL下载任务的线程是否存在
    boolean isExists(String url, int threadId);

    long queryProgress(String url);
}
