package com.learn.four.ipcsample;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.lang.ref.WeakReference;

public class RemoteService extends Service {
    public static final int MSG_FROM_CLIENT = 1;

    private final Messenger mMessenger = new Messenger(new MessengerHandler(this));

    static class MessengerHandler extends Handler {

        private WeakReference<RemoteService> mService;

        MessengerHandler(RemoteService remoteService) {
            mService = new WeakReference<>(remoteService);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    String msgFromClient = msg.getData().getString("send");

                    Message message = Message.obtain();
                    message.what = MessengerActivity.MSG_FROM_SERVER;
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "Ha ha，I'm Service.");
                    message.setData(bundle);
                    try {
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
