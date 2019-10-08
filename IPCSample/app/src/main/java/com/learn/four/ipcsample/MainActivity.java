package com.learn.four.ipcsample;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.learn.four.ipcsample.aidlBinderPool.BinderPool;
import com.learn.four.ipcsample.aidlBinderPool.ICompute;
import com.learn.four.ipcsample.aidlBinderPool.ISecurityCenter;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        User.sUserId = 2;
        tv.setText("User.sUserId = " + User.sUserId);
        Log.i(TAG, "-->> User.sUserId = " + User.sUserId);



    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(()->{
            BinderPool binderPool = BinderPool.getInstance(getApplicationContext());



            IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
           ICompute mCompute = ICompute.Stub.asInterface(computeBinder);
            try {
                Log.i(TAG, "onResume: a = " + mCompute.getInt_a());

            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.toSecAc:
                i = new Intent(MainActivity.this, SecActivity.class);
                startActivity(i);
                return true;
            case R.id.toMessengerAc:
                i = new Intent(MainActivity.this, MessengerActivity.class);
                startActivity(i);
                return true;
            case R.id.toBookClientAc:
                i = new Intent(MainActivity.this, BookClientActivity.class);
                startActivity(i);
                return true;
            case R.id.toBinderPoolAc:
                i = new Intent(MainActivity.this, BinderPoolActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void dumpStack(View view) {
       // 死循环不是导致 ANR 的充分条件
//        long l = 0;
//        while (true) {
//            l++;
//        }

    }

}
