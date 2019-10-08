package com.learn.four.ipcsample.aidlBinderPool;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.concurrent.CountDownLatch;

/**
 * Create on 2019/05/14
 *`
 * @author Four
 * @description
 */
public class BinderPool {
    public static final int BINDER_NONE = -1;
    public static final int BINDER_SECURITY = 1;
    public static final int BINDER_COMPUTE = 2;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool sInstance;
    private CountDownLatch mConnectBinderPoolCountLatch;

    private ServiceConnection mBinderPoolConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }finally {
                mConnectBinderPoolCountLatch.countDown();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /* 当远程服务意外终止时，BinderPool会重新建立绑定；
            这时，客户端业务模块中也要重新获取最新的Binder */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBinderPool == null) {
                return;
            }

            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };

    /* 双重检测单例模式，BinderPool在同一进程内是单例的 */
    /* 可以在 Application 中提前对它进行初始化 */
    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    /* BinderPool首先要先绑定远程服务，客户端才能通过它的queryBinder来获取各自的 Binder */
    private synchronized void connectBinderPoolService(){
        mConnectBinderPoolCountLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, mBinderPoolConn, Context.BIND_AUTO_CREATE);
        try {
            // 这里将 bindService 通过 CountDownLatch 转换成了同步操作！
            // 因为必须先要确保 BinderPool是在已经和远程服务建立绑定的情况下
            //      给客户端提供queryBinder接口
            mConnectBinderPoolCountLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        if (mBinderPool != null) {
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

}
