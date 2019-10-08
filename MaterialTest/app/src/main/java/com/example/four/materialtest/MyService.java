package com.example.four.materialtest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private List<Book> bookList = new ArrayList<>();

    /*
     Messenger
      */
//    public static final int MH_MAG_FROM_CLIENT = 0;
//
//    private static class MessengerHandker extends Handler{
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MH_MAG_FROM_CLIENT:
//                   // 根据what字段执行相应逻辑
//                    break;
//                default:
//                    super.handleMessage(msg);
//            }
//        }
//    }
//
//    private Messenger mMessenger = new Messenger(new MessengerHandker());
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mMessenger.getBinder();
//    }

    /*
    AIDL
    */
    private Binder mBinder = new IMyAidlInterface.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        return START_STICKY;
    }
}
