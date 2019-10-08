package com.learn.four.ipcsample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MessengerActivity extends AppCompatActivity {
    public static final int MSG_FROM_SERVER = 2;

    private TextView tvClient;
    private TextView tvService;
    private Messenger mRemoteServiceMessenger;
    private ClientMessengerHandler mHandler = new ClientMessengerHandler(this);
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            tvClient.setText("Hi, I'm Client.   -->>");
            mRemoteServiceMessenger = new Messenger(service);
            Message message = Message.obtain();
            message.replyTo = new Messenger(mHandler);
            message.what = RemoteService.MSG_FROM_CLIENT;
            Bundle bundle = new Bundle();
            bundle.putString("send", "Hi, I'm Client.");
            message.setData(bundle);
            try {
                mRemoteServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        tvClient = findViewById(R.id.tv_client);
        tvService = findViewById(R.id.tv_service);

        Intent i = new Intent(this, RemoteService.class);
        bindService(i, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteServiceMessenger != null && mRemoteServiceMessenger.getBinder().isBinderAlive()) {
            unbindService(conn);
        }
        super.onDestroy();
    }

    private void onRemoteServiceReply(String msg) {
        tvService.setText("-->>    " + msg);
    }


    static class ClientMessengerHandler extends Handler {
        WeakReference<MessengerActivity> mAc;

        ClientMessengerHandler(MessengerActivity messengerActivity) {
            mAc = new WeakReference<>(messengerActivity);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_FROM_SERVER:
                    mAc.get().onRemoteServiceReply(msg.getData().getString("reply"));
                    break;

            }
        }
    }
}
