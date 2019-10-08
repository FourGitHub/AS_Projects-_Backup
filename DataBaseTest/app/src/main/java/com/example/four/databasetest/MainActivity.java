package com.example.four.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private MyHandler mHandler;
    private static class MyHandler extends Handler {

        /* 持有Activity的弱引用 */
        WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // 根据消息内容，执行对应逻辑
        }
    }

    private static final String TAG = "MainActivity";
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        Log.i(TAG, "dbhelper: " + dbHelper.toString());
        init();
        Log.i(TAG, "onCreate: ---> " + SystemClock.currentThreadTimeMillis());
        mHandler.postDelayed(() -> {
            Log.i(TAG, "onCreate: ---> " + SystemClock.currentThreadTimeMillis());
        }, 1000);

    }


    private void init() {
        mHandler = new MyHandler(this);
        Button createDatabase = (Button) findViewById(R.id.button_create_db);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        Button addData = (Button) findViewById(R.id.button_add_data);
        addData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // 开始组装第一条数据
                values.put("name", "《第一行代码》（第二版）");
                values.put("author", "郭霖");
                values.put("pages", 570);
                values.put("price", 79.00);
                db.insert("Book", null, values); // 插入第一条数据
                values.clear();
                // 开始组装第二条数据
                values.put("name", "《The Lost Symbol》");
                values.put("author", "CayS.Horstmann");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values); // 插入第二条数据
                values.clear();
                values.put("name", "《Java核心技术卷 1（原书第十版）》");
                values.put("author", "Dan Brown");
                values.put("pages", 711);
                values.put("price", 119.00);
                db.insert("Book", null, values); // 插入第三条数据
                Toast.makeText(MainActivity.this, "添加了: \n" + "《第一行代码》（第二版）\n" + "《The Lost Symbol》\n" + "《Java核心技术卷 1（原书第十版）》", Toast.LENGTH_LONG).show();

            }
        });
        Button updateData = (Button) findViewById(R.id.button_update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 59.90);
                int i = db.update("Book", values, "name = ?", new String[]{"《第一行代码》（第二版）"});
                if (i != 0) {
                    Toast.makeText(MainActivity.this, "《第一行代码》（第二版） 价格修改成功", Toast.LENGTH_LONG).show();
                }
            }
        });


        Button deleteButton = (Button) findViewById(R.id.button_delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                int i = db.delete("Book", "pages > ?", new String[]{"600"});
                if (i != 0) {
                    Toast.makeText(MainActivity.this, "大于600页的书被删除！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button deleteAllButton = (Button) findViewById(R.id.button_delete_all_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                int i = db.delete("Book", null, null);
                if (i != 0) {
                    Toast.makeText(MainActivity.this, "数据库已清空！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button queryButton = (Button) findViewById(R.id.button_query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 查询Book表中所有的数据
                Cursor cursor = db.query(true, "Book", null, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        // 遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Toast.makeText(MainActivity.this, "书名:" + name + "\n作者:" + author + "\n价格:" + price + "\n页数: " + pages, Toast.LENGTH_LONG).show();
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });

        Button isSqliteThreadSafe = findViewById(R.id.id_sqlite_thread_safe);
        isSqliteThreadSafe.setOnClickListener((v) -> {

//            Thread ta = new Thread(() -> {
//                long cur = System.currentTimeMillis();
//                Context context = getApplicationContext();
//                MyDatabaseHelper helper = new MyDatabaseHelper(context, "BookStore.db", null, 2);
//                SQLiteDatabase db = helper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                while (System.currentTimeMillis() - cur < 1000) {
//                    values.put("name", "《第一行代码》（第二版）");
//                    values.put("author", "郭霖");
//                    values.put("pages", 570);
//                    values.put("price", 79.00);
//                    db.insert("Book", null, values);
//                    values.clear();
//                }
//                db.close();
//
//
//            });
//
//            Thread tb = new Thread(() -> {
//                long cur = System.currentTimeMillis();
//                Context context = getApplicationContext();
//                MyDatabaseHelper helper = new MyDatabaseHelper(context, "BookStore.db", null, 2);
//                SQLiteDatabase db = helper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                while (System.currentTimeMillis() - cur < 1000) {
//                    values.put("name", "《第一行代码》（第二版）");
//                    values.put("author", "郭霖");
//                    values.put("pages", 570);
//                    values.put("price", 79.00);
//                    db.insert("Book", null, values);
//                    values.clear();
//                }
//                db.close();
//            });


            Thread ta = new Thread(() -> {
                DatabaseManager dbManager = DatabaseManager.getInstance();
                SQLiteDatabase db = dbManager.openDatabase();
                long cur = System.currentTimeMillis();
                while (System.currentTimeMillis() - cur < 1000) {
                    ContentValues values = new ContentValues();
                    values.put("name", "《第一行代码》（第二版）");
                    values.put("author", "郭霖");
                    values.put("pages", 570);
                    values.put("price", 79.00);
                    db.insert("Book", null, values);
                }
                dbManager.closeDatabase();
            });

            Thread tb = new Thread(() -> {
                DatabaseManager dbManager = DatabaseManager.getInstance();
                SQLiteDatabase db = dbManager.openDatabase();
                long cur = System.currentTimeMillis();
                while (System.currentTimeMillis() - cur < 1000) {
                    ContentValues values = new ContentValues();
                    values.put("name", "《第一行代码》（第二版）");
                    values.put("author", "郭霖");
                    values.put("pages", 570);
                    values.put("price", 79.00);
                    db.insert("Book", null, values);
                }
                dbManager.closeDatabase();
            });

            ta.start();
            tb.start();
        });
    }
}
