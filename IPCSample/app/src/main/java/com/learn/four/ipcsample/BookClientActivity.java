package com.learn.four.ipcsample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.learn.four.ipcsample.aidl.Book;
import com.learn.four.ipcsample.aidl.BookManagerService;
import com.learn.four.ipcsample.aidl.IBookManager;
import com.learn.four.ipcsample.aidl.IOnNewBookArrivedListener;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookClientActivity extends AppCompatActivity {
    private static final String TAG = "BookClientActivity";
    private static final int GET_BOOKLIST_REPLY = 1;
    private static final int NEW_BOOK_ARRIVED = 2;

    private TextView tvClient;
    private TextView tvService;

    private BookHandler mHandler;
    private IBookManager mBookManager;
    private IOnNewBookArrivedListener mOnBookArrivedListener = new IOnNewBookArrivedListener.Stub(){
        /* 该方法运行在 客户端Binder线程池，所以:
                1、不能访问UI
                2、服务端在调用时应当小心导致「服务端无响应」*/
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            Log.i(TAG, "onNewBookArrived: " + Thread.currentThread());

            if (book != null) {
                mHandler.obtainMessage(NEW_BOOK_ARRIVED, book).sendToTarget();
            }
        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 绑定成功后，将服务端的Binder对象转换成客户端所需的AIDL接口类型对象
            mBookManager = IBookManager.Stub.asInterface(service);
            Log.i(TAG, "onServiceConnected: <<--->> IBookManager = " + mBookManager);
            Log.i(TAG, "onServiceConnected: <<--->> IBinder = " + service);

            // 避免在UI线程中发起远程方法请求
            new Thread(() -> {
                try {
                    mBookManager.registerListener(mOnBookArrivedListener);

                    List<Book> remoteBookList = mBookManager.getBookList();
                    mHandler.obtainMessage(GET_BOOKLIST_REPLY, remoteBookList).sendToTarget();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
            // Binder断裂，重新绑定服务
            Intent i = new Intent(BookClientActivity.this, BookManagerService.class);
            bindService(i, conn, BIND_AUTO_CREATE);
            tvClient.append(getNowTime() + "    「reBindService」\n");
        }
    };

//    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
//        @Override
//        public void binderDied() {
//            if (mBookManager == null) {
//                return;
//            }
//            mBookManager.asBinder().unlinkToDeath(mDeathRecipient,0 );
//            mBookManager = null;
//            Intent i = new Intent(BookClientActivity.this, BookManagerService.class);
//            bindService(i, conn, BIND_AUTO_CREATE);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_client);
        tvClient = findViewById(R.id.tv_client);
        tvService = findViewById(R.id.tv_service);
        tvClient.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvService.setMovementMethod(ScrollingMovementMethod.getInstance());

        mHandler = new BookHandler(this);

        // 绑定服务
        Intent i = new Intent(this, BookManagerService.class);
        bindService(i, conn, BIND_AUTO_CREATE);
    }


    public void bindService(View view) {
        Intent i = new Intent(this, BookManagerService.class);
        bindService(i, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        // 客户端 适时取消监听
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                mBookManager.unregisterListener(mOnBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            // 客户端 适时解绑服务
            unbindService(conn);
        }

        super.onDestroy();
    }

    /* 更新UI */
    private void onGetBookListReply(ArrayList<Book> books) {
        String time = getNowTime();
        tvClient.append(time + "    getBookList():\n");
        for (Book b : books) {
            tvClient.append(b.getBookName() + "\n");
        }
    }
    /* 更新UI */
    private void onNewBookArrived(Book newbook) {
        String time = getNowTime();
        tvService.append(time + "    onNewBookArrived():\n");
        tvService.append(newbook.getBookName()+"\n");
    }

    private String getNowTime() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return format.format(now);
    }

    /* 在子线程中发起远程方法请求，方法返回后，将执行逻辑切回UI线程 */
    static class BookHandler extends Handler {
        private WeakReference<BookClientActivity> mAc;

        BookHandler(BookClientActivity bookClientActivity) {
            mAc = new WeakReference<>(bookClientActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mAc.get() != null) {
                switch (msg.what) {
                    case GET_BOOKLIST_REPLY:
//                        mAc.get().onGetBookListReply((ArrayList<Book>) msg.obj);
                        break;
                    case NEW_BOOK_ARRIVED:
                        mAc.get().onNewBookArrived((Book) msg.obj);
                        break;
                }
            }

        }
    }
}
