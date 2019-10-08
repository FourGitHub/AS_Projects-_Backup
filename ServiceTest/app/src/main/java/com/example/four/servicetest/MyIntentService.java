package com.example.four.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/12/26 0026.
 */
// Android中的IntentService是继承自Service类的,先想一下Service的特点: Service的回调方法(onCreate、onStartCommand、onBind、onDestroy)都是运行在主线程中的。
// 当我们通过startService启动Service之后，我们就需要在Service的onStartCommand方法中写代码完成工作，但是onStartCommand是运行在主线程中的，
// 如果我们需要在此处完成一些网络请求或IO等耗时操作，这样就会阻塞主线程UI无响应，从而出现ANR现象。
// 为了解决这种问题，最好的办法就是在onStartCommand中创建一个新的线程，并把耗时代码放到这个新线程中执行。
// 由此看来，创建一个带有工作线程的Service是一种很常见的需求（因为工作线程不会阻塞主线程），所以Android为了简化开发带有工作线程的Service，Android额外开发了一个类——–IntentService。
public class MyIntentService extends IntentService {

    /* 这个构造方法是必须的 */
    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * 像在onStartCommand()方法中一样，取出intent中携带的启动任务所需的参数，然后开始执行任务
     * 好处是执行（耗时）任务时，不用程序员手动创建子线程
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

