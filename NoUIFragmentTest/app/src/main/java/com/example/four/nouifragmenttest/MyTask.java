package com.example.four.nouifragmenttest;

/**
 * Create on 2019/03/20
 *
 * @author Four
 * @description
 */

import android.os.AsyncTask;
import android.os.SystemClock;

public class MyTask extends AsyncTask<Void, Integer, Void> {

    private MyCallback mwCallback;

    void setMwCallback(MyCallback mCallback) {
        this.mwCallback = mCallback;
    }

    MyTask(MyCallback callback) {
        mwCallback = callback;
    }

    @Override/* workThread */
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i <= 100; i++) {
            SystemClock.sleep(500);
            publishProgress(i);
        }
        return null;
    }

    @Override/* MainThread */
    protected void onProgressUpdate(Integer... values) {
        if (mwCallback != null) {
            mwCallback.onProgressUpdate(values[0]);
        }
    }

    interface MyCallback {
        void onProgressUpdate(int percent);
    }
}

