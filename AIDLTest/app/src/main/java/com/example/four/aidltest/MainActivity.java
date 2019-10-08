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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int GET_BOOKLIST = 0;
    public static final int NEW_BOOK_ARRIVED = 1;
    private static TextView tv;
    private ClientHandler mHandler = new ClientHandler();
    private BookManager bookManagerAIDL;
    private IOnNewBookArrivedListener listener = new IOnNewBookArrivedListener.Stub() {

       /*
       ▶ 如果调用 addBook() 方法的客户端与MainActivity在同一个进程，那么它的 onNewBookArrived() 方法就会运行在 主线程
       ▶ 如果调用 addBook() 方法的客户端与MainActivity不在同一个进程，那么它的 onNewBookArrived() 方法就会运行在 客户端Binder线程池
        */
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Log.i(TAG, "onNewBookArrived() -->> currentThread:" + Thread.currentThread());
            mHandler.obtainMessage(NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {

        /**
         * Binder连接断裂会自动回调此方法在此方法内我们可以尝试重新绑定Service
         * 也可以在 onServiceDisconnected() 方法里执行相关逻辑，区别是 conn 运行在UI线程
         * binderDied()运行在客户端Binder线程池
         */
        @Override
        public void binderDied() {
            if (bookManagerAIDL == null) {
                return;
            }

            tv.append("Binder Unexpected Died...Try Rebind...\n");
            bookManagerAIDL.asBinder().unlinkToDeath(mDeathRecipient, 0);
            bookManagerAIDL = null; // 移除之前绑定的binder代理
            bindService(); // 重新连接，生成新的代理
        }
    };

    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
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
                Log.i(TAG, "conn -->> bookManagerAIDL: " + bookManagerAIDL);
                Log.i(TAG, "conn -->> bookManagerAIDL.asBinder(): " + bookManagerAIDL.asBinder());
                Log.i(TAG, "conn -->> currentThread: " + Thread.currentThread());
                try {
                    bookManagerAIDL.registerListener(listener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    service.linkToDeath(mDeathRecipient, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected: " + "Binder断裂");
                Toast.makeText(getApplicationContext(),"Binder断裂", Toast.LENGTH_SHORT).show();
            }
        };
        bindService(intent, conn, BIND_AUTO_CREATE);
        tv.append("\nBind Service Successful.\n");
    }

    /**
     * 别多次点击这个按钮，重复添加一个内容相同的Book对象，显示结果容易引起思维混乱
     *
     * @param view
     */
    public void addBook(View view) {
        if (bookManagerAIDL != null && bookManagerAIDL.asBinder().isBinderAlive()) {
            try {
                bookManagerAIDL.addBook(new Book(2, "《设计模式之禅》"));
                tv.append("添加了一本书:" + "id = " + 2 + "   Name = " + "《设计模式之禅》\n");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            tv.append("Add Book Failed, Because The \'Binder\' Was Died.\n");
        }
    }

    public void getBookList(View view) {
        if (bookManagerAIDL != null && bookManagerAIDL.asBinder().isBinderAlive()) {
            try {
                List<Book> bookList = bookManagerAIDL.getBookList();
                mHandler.obtainMessage(GET_BOOKLIST, bookList).sendToTarget();
                Log.i(TAG, "query book list, list type: " + bookList.getClass().getCanonicalName());
                Log.i(TAG, "query book list:" + bookList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
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
    private static class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_BOOKLIST:
                    ArrayList<Book> books = (ArrayList<Book>) msg.obj;
                    tv.append(books.toString());
                    break;
                case NEW_BOOK_ARRIVED:
                    tv.append("收到通知: 服务端增加了一本新书\n" + ((Book) msg.obj).getName());
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookManagerAIDL != null && bookManagerAIDL.asBinder().isBinderAlive()) {
            try {
                bookManagerAIDL.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.to_sec:
                Intent toSec = new Intent(this, SecondActivity.class);
                startActivity(toSec);
                break;
            case R.id.to_third:
                Intent toThrid = new Intent(this,TCPClientActivity.class);
                startActivity(toThrid);
                break;
            default:
                break;

        }
        return true;
    }
}
