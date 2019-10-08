package com.learn.four.ipcsample.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.learn.four.ipcsample.aidl.Book;
import com.learn.four.ipcsample.aidl.IBookManager;
import com.learn.four.ipcsample.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";

    /* Binder方法运行在服务端Binder线程池，这里使用并发容器自动解决同步问题 */
    /** 前面说到AIDL中只支持ArrayList，但这里使用了CopyOnWriteArrayList，为什么还是能够正常工作呢？
       这是因为AIDL中所支持的是抽象的List，而List只是一个接口，因此虽然服务端返回的是CopyOnWriteArrayList，
       但是在Binder中会按照List的规范去访问数据并最终形成一个新的ArrayList传递给客户端。
       所以，我们在服务端采用CopyOnWriteArrayList是完全可以的。和此类似的还有ConcurrentHashMap */
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    /* RemoteCallBackList 是系统专门提供的用于 管理任意的AIDL接口 的一个泛型类 */
    private RemoteCallbackList<IOnNewBookArrivedListener> mListeners = new RemoteCallbackList<>();

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListeners.register(listener);
            int count = mListeners.beginBroadcast();
            Log.i(TAG, "registerListenerCount = " + count);
            mListeners.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListeners.unregister(listener);
            int count = mListeners.beginBroadcast();
            Log.i(TAG, "registerListenerCount = " + count);
            mListeners.finishBroadcast();
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // permision权限验证
            if (checkCallingOrSelfPermission("com.learn.four.ipcsample.allowConnectBookManagerService")
                    == PackageManager.PERMISSION_DENIED) {
                return false;
            }

            // 验证包名
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (!packageName.startsWith("com.learn.four")) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    public BookManagerService() { }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(0, "《第一行代码》（第二版）"));
        mBookList.add(new Book(1, "《爱上Android》"));

        new Thread(new AutoAddBookTask()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // permision权限验证
        if (checkCallingOrSelfPermission("com.learn.four.ipcsample.allowConnectBookManagerService")
                == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        Log.i(TAG, "onBind: <<--->> " + mBinder);

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: <<--->>" + "所有绑定断开");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        Log.i(TAG, "onDestroy: <<--->>" + "BookManagerDestory");
        super.onDestroy();
    }

    private void onNewBookArrived(Book newBook) throws RemoteException {
        // beginBroadcast 和 finishBroadcast 必须配对使用
        int listenerCount = mListeners.beginBroadcast();
        while (listenerCount-- > 0) {
            IOnNewBookArrivedListener li = mListeners.getBroadcastItem(listenerCount);
            if (li != null) {
                li.onNewBookArrived(newBook);
            }
        }
        mListeners.finishBroadcast();
    }

    private class AutoAddBookTask implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    Book newBook = new Book(mBookList.size(), "new book#" + mBookList.size());
                    mBookList.add(newBook);
                    onNewBookArrived(newBook);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
