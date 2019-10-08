package com.learn.four.ipcsample;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.learn.four.ipcsample.aidlBinderPool.BinderPool;
import com.learn.four.ipcsample.aidlBinderPool.ICompute;
import com.learn.four.ipcsample.aidlBinderPool.ISecurityCenter;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BinderPoolActivity extends AppCompatActivity {
    private static final String TAG = "BinderPoolActivity";

    private TextView tvClient;
    private TextView tvService;

    private static final int ENCRYPT = 1;
    private static final int DECRYPT = 2;
    private static final int COMPUTE = 3;

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;
    private MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        tvClient = findViewById(R.id.tv_client);
        tvService = findViewById(R.id.tv_service);
        tvService.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvClient.setMovementMethod(ScrollingMovementMethod.getInstance());

        new Thread(()->{
            BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);

            IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY);
            mSecurityCenter = ISecurityCenter.Stub.asInterface(securityBinder);
            String content = "hello world - 安卓";
            String password = null;
            try {
                password = mSecurityCenter.encrypt(content);
                mHandler.obtainMessage(ENCRYPT, password).sendToTarget();

                content = mSecurityCenter.decrypt(password);
                mHandler.obtainMessage(DECRYPT, content).sendToTarget();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
            mCompute = ICompute.Stub.asInterface(computeBinder);
            try {
                mHandler.obtainMessage(COMPUTE, mCompute.add(3, 1),0).sendToTarget();
                mCompute.setInt_a_Plus();
                Log.i(TAG, "onCreate: a = " + mCompute.getInt_a());

            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }).start();
    }

    private void onEncrypt(String password) {
        tvClient.append(getNowTime() + "    密文：\n   " + password + "\n");
    }

    private void onDecrypt(String content) {
        tvClient.append(getNowTime() + "    明文：\n   " + content+ "\n") ;
    }

    private void onCompute(int result) {
        tvService.append(getNowTime() + "   3 + 1 = " + result);
    }

    private String getNowTime() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return format.format(now);
    }

    static class MyHandler extends Handler {
        private WeakReference<BinderPoolActivity> mAc;

        MyHandler(BinderPoolActivity binderPoolActivity) {
            mAc = new WeakReference<>(binderPoolActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ENCRYPT:
                    mAc.get().onEncrypt((String) msg.obj);
                    break;
                case DECRYPT:
                    mAc.get().onDecrypt((String) msg.obj);
                    break;
                case COMPUTE:
                    mAc.get().onCompute(msg.arg1);
                    break;

            }

        }
    }

}
