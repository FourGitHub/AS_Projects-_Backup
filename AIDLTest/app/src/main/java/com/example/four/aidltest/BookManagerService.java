package com.example.four.aidltest;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/*
AIDL 方式实现 IPC
 */
public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";
    CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    RemoteCallbackList<IOnNewBookArrivedListener> list = new RemoteCallbackList<>();

    public BookManagerService() {
    }

    PackageManager packageManager = getPackageManager();
    private BookManager.Stub stub = new BookManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            Log.i(TAG, "stub#addBook() -->> currentThread:" + Thread.currentThread());
            mBookList.add(book);
            /*
            beginBroadcast() 和 finishBroadcast()必须配合使用
             */
//            onNewBookArrived(book);
            final int N = list.beginBroadcast();
            for (int i = 0; i < N; i++) {
                IOnNewBookArrivedListener listener = list.getBroadcastItem(i);
                if (listener != null) {
                    listener.onNewBookArrived(book);
                }
            }
            list.finishBroadcast();
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            list.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            list.unregister(listener);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int check = checkCallingOrSelfPermission("com.example.four.aidltest.custom.permission.ACCESS_BOOK_SERVICE");
            if (check == PackageManager.PERMISSION_DENIED) {
                return false;
            }

            String packageName = " ";
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (!packageName.startsWith("com.example.four")) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.example.four.aidltest.custom.permission.ACCESS_BOOK_SERVICE");
        if (check != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Log.i(TAG, "onBind() -->> stub:" + stub.toString());
        Log.i(TAG, "onBind() -->> currentThread:" + Thread.currentThread());
        return stub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(0, "《Java核心技术卷（一）》"));
        mBookList.add(new Book(1, "《第一行代码》"));
    }

//    private void onNewBookArrived(Book book){
//        final int N = list.beginBroadcast();
//        for (int i = 0; i < N; i++) {
//            IOnNewBookArrivedListener listener = list.getBroadcastItem(i);
//            if (listener != null) {
//                try {
//                    listener.onNewBookArrived(book);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        list.finishBroadcast();
//    }
}
