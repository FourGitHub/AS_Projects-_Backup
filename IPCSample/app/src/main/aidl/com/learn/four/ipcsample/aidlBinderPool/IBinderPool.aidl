// IBinderPool.aidl
package com.learn.four.ipcsample.aidlBinderPool;

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}

