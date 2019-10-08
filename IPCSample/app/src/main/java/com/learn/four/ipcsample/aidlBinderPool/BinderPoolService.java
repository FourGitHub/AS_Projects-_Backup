package com.learn.four.ipcsample.aidlBinderPool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import static com.learn.four.ipcsample.aidlBinderPool.BinderPool.BINDER_COMPUTE;
import static com.learn.four.ipcsample.aidlBinderPool.BinderPool.BINDER_SECURITY;

public class BinderPoolService extends Service {
    private Binder mBinderPool = new BinderPoolImpl();

    public BinderPoolService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }

    static class BinderPoolImpl extends IBinderPool.Stub {
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_COMPUTE:
                    binder = ComputeImpl.getInstance();
                    break;
                case BINDER_SECURITY:
                    binder = SecurityCenterImpl.getInstance();
                    break;
            }
            return binder;
        }
    }
}
