package com.example.four.launchmodetest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TenthActivity extends BaseActivity {
    private static final String TAG = "TenthActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenth_layout);
        Log.d(TAG, "当前活动所属返回栈的id: "+ getTaskId());
        Toast.makeText(TenthActivity.this,"查看完整singleInstance Mode测试结果需返回至第八个活动",Toast.LENGTH_LONG).show();
    }
}
