package com.example.four.nouifragmenttest;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity implements MyTask.MyCallback {
    private static final String TAG = "TestActivity";
    private static final String F_TAG = "RetainFragment";
    private RetainFragment retainFragment;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTextView = findViewById(R.id.textView2);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag(F_TAG) == null) {
            Log.i(TAG, "onCreate --->> retainFragment = null");
            retainFragment = new RetainFragment();

            /* 注意这里不是 addToBackStack, 况且 RetainFragment 不能被添加到返回栈*/
            /* 给 RetainFragment 打个标记，方便得到它的引用 */
            fm.beginTransaction().add(retainFragment, F_TAG).commit();
        } else {
            Log.i(TAG, "onCreate --->> retainFragment != null");
        }

        PackageManager pm = getPackageManager();
        ApplicationInfo ai = null;
        try {
            ai = pm.getApplicationInfo("com.learn.four.ipcsample", PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.i("!!", "!!" + ai.uid);
    }

    @Override
    public void onProgressUpdate(int percnet) {
        mTextView.append(" --> onProgressUpdate: " + percnet + "\n");
    }

}