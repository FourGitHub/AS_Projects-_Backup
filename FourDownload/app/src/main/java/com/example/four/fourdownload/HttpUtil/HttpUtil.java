package com.example.four.fourdownload.HttpUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.four.fourdownload.ToastUtil.ToastUtil;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class HttpUtil {

    private static final String TAG = "HttpUtil";
    private static URL url;
    private static HttpURLConnection con;
    private static int state = -1;

    /**
     * 功能：检测当前URL是否可连接或是否有效,
     * 描述：最多连接网络 5 次, 如果 5 次都不成功，视为该地址不可用
     *
     * @param urlStr 指定URL网络地址
     * @return URL
     */
    public static synchronized URL isConnect(String urlStr) {
        int counts = 0;
        if (urlStr == null || urlStr.length() <= 0) {
            return null;
        }
        while (counts < 5) {
            try {
                url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                state = con.getResponseCode();
                Log.i(TAG, counts + "= " + state);
                if (state == 200) {
                    System.out.println("URL可用！");
                }
                break;
            } catch (Exception ex) {
                counts++;
                Log.i(TAG, "URL不可用，连接第 " + counts + " 次");
                urlStr = null;
                continue;
            }
        }
        return url;
    }


    /**
     * @return 发起网络请求之前检查网络状态是否可用
     */
    public static boolean networkStatus(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        }
        ToastUtil.showToast(context, "请检查您的网络连接...", Toast.LENGTH_SHORT);
        return false;
    }
}
