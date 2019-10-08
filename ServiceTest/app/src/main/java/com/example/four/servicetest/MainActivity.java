package com.example.four.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * 这模拟和服务通信 -> 想表达的意思是拿到binder后可控制下载，真正的下载实现，还有很多状态需要考虑。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            /*
            有了这个MyService.DownloadBinder实例后，活动和服务之间的关系就变得紧密了。
            */
            downloadBinder = (MyService.DownloadBinder)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        Button bindService = (Button) findViewById(R.id.bind_service);
        Button unbindService = (Button) findViewById(R.id.unbind_service);
        Button startIntentService = (Button)findViewById(R.id.start_intent_service);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        startIntentService.setOnClickListener(this);

        // 一创建实例就和Service绑定
        Intent bindIntent = new Intent(this,MyService.class);
        bindService(bindIntent,connection,BIND_AUTO_CREATE);

        // 不能再这里立马就利用 downloadBinder 和服务进行通信, 我试了好几次都NPE，说明绑定服务需要一定的时间
        // 一个正确的地方应该是在ServiceConnection#onServiceConnected()方法中
        // 或者给一个延迟调用，以确保downloadBinder != null ：
        new Handler().postDelayed(() -> downloadBinder.startDownload(), 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                new Handler().postDelayed(() -> {
                    Intent startIntent = new Intent(this, MyService.class);
                    startService(startIntent);
                }, 120*1000);

                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                // BIND_AUTO_CREATE表示在活动和服务绑定后自动创建服务
                // onCreate()会得到执行， 但onStartCommand()不会执行，只有显示的调用startService()才会执行。
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;
            case R.id.start_intent_service:
                Log.d("MainActivity", "哈哈Thread is :" + Thread.currentThread().getId());
                Intent intentService = new Intent(this,MyIntentService.class);
                startService(intentService);
            default:
                break;
        }
    }
}
