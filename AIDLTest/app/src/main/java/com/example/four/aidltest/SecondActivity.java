package com.example.four.aidltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    public static final int GET_BOOKLIST = 0;
    public static final int ADD_BOOK = 1;
    private static TextView tv;
    private SecondActivity.SecClientHandler mHandler = new SecondActivity.SecClientHandler();
    private BookManager bookManagerAIDL;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (bookManagerAIDL == null) {
                return;
            }

            tv.append("Binder Died...Try Rebind...\n");
            bookManagerAIDL.asBinder().unlinkToDeath(mDeathRecipient, 0);
            bookManagerAIDL = null;
            bindService();
        }
    };

    private ServiceConnection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv = (TextView)findViewById(R.id.tv_sec);
    }

    public void bindService(View view) {
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, BookManagerService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "conn -->> service: " + service.toString());
                bookManagerAIDL = (BookManager) BookManager.Stub.asInterface(service);
                Log.i(TAG, "conn -->> bookManagerAIDL: " + bookManagerAIDL.toString());
                Log.i(TAG, "conn -->> bookManagerAIDL.asBinder(): " + bookManagerAIDL.asBinder());
                Log.i(TAG, "conn -->> currentThread: " + Thread.currentThread());

                try {
                    service.linkToDeath(mDeathRecipient, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent, conn, BIND_AUTO_CREATE);
        tv.append("\nBind Service Successful.\n" );
    }

    /**
     * 别多次点击这个按钮，重复添加一个内容相同的Book对象，显示结果容易引起思维混乱
     * @param view
     */
    public void addBook(View view) {
        if (bookManagerAIDL != null && bookManagerAIDL.asBinder().isBinderAlive()) {
            try {
                bookManagerAIDL.addBook(new Book(2, "《设计模式之禅》"));
                tv.append("添加了一本书:" + "id = " + 2 + "   Nmae = " + "《设计模式之禅》\n");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else{
            tv.append("Add Book Failed, Because The \'conn\' Is Unavilable.\n");
        }
    }

    public void getBookList(View view) {
        if (bookManagerAIDL != null) {
            try {
                List<Book> bookList = bookManagerAIDL.getBookList();
                mHandler.obtainMessage(GET_BOOKLIST, bookList).sendToTarget();
                Log.i(TAG, "query book list, list type: " + bookList.getClass().getCanonicalName());
                Log.i(TAG, "query book list:" + bookList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else{
            tv.append("Get Book List Failed, Because The \'conn\' Is Unavilable.\n");
        }
    }

    public void unbindService(View view) {
        if (conn != null) {
            unbindService(conn);
            conn = null;
            bookManagerAIDL = null;
            tv.append("\nUnbind Service Successful.\n");
            Log.i(TAG, "unbindService successful.");
        } else {
            tv.append("You Needn't unBind Service\n");
        }
    }

    public void clearScreen(View view) {
        tv.setText("");
    }

    /**
     * 自定义Handler，从客户端Binder线程池切换回UI线程,更新UI
     */
    private static class SecClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_BOOKLIST:
                    ArrayList<Book> books = (ArrayList<Book>) msg.obj;
                    tv.append(books.toString());
                    break;
                case ADD_BOOK:
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }
}
