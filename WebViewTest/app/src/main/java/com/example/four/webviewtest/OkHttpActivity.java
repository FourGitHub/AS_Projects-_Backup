package com.example.four.webviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.four.webviewtest.POJO.APP;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 通过OkHttp 访问网络，获取本机的get_data.json文件，并将该文件解析出来（JSONObject解析 和 GSON解析）
 */
public class OkHttpActivity extends AppCompatActivity {

    private EditText textView;
    private static final String TAG = "OkHttpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        textView = findViewById(R.id.text_view);
    }

    public void sendRequestWithOkHttp(View view) {
        // 10.0.3.2 or 10.0.2.2 对于模拟器来说就是电脑本机的IP地址
        String address = "http://10.0.3.2/get_data.json";
        OkHttpUtils.sendRequestWithOkHttp(address, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body()
                        .string();
                textViewAppend(responseData);
                textViewAppend("\nJSONObject解析结果：");
                parseJSONWithJSONObject(responseData);
                textViewAppend("\nGSON解析结果：");
                parseJSONWithGSON(responseData);
            }
        });
    }

    public void postRequestWithOkHttp(View view) {
        String address = "https://wx.idsbllp.cn/cyxbsMobile/index.php/Home/Person/search";
        RequestBody requestBody = new FormBody.Builder().add("stuNum", "2016211743")
                .add("idNum", "284415")
                .build();
        OkHttpUtils.postResquestWithOkHttp(address, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body()
                        .string();
                textViewAppend(responseData);
            }
        });
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d(TAG, "-->> id is " + id);
                Log.d(TAG, "-->> name is " + name);
                Log.d(TAG, "-->> version is " + version);
                textViewAppend("\n" + "名字: " + name + " " + "ID: " + id + " " + "版本号: " + version + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseJSONWithGSON(String jsonData) {
        List<APP> appList = new Gson().fromJson(jsonData, new TypeToken<List<APP>>() {}.getType());
        for (APP app : appList) {
            Log.d(TAG, "-->> id is " + app.getId());
            Log.d(TAG, "-->> name is " + app.getName());
            Log.d(TAG, "-->> version is " + app.getVersion());
            textViewAppend("\n" + "名字: " + app.getName() + " " + "ID: " + app.getId() + " " + "版本号: " + app
                    .getVersion() + "\n");
        }
    }

    public void textViewAppend(final String dataRespose) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append(dataRespose);
                textView.setSelection(textView.getText()
                        .length());
            }
        });
    }

}

