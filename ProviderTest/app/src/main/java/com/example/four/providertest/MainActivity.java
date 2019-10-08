package com.example.four.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData = (Button) findViewById(R.id.button_add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加数据
                Uri uri = Uri.parse("content://com.example.four.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name", "《A Clash of Kings》");
                values.put("author", "George Martin");
                values.put("price", 55.55);
                values.put("pages", 1040);
                getContentResolver().insert(uri, values);
                Toast.makeText(MainActivity.this, "添加了: \n" + "名字: 《A Clash of Kings》\n" + "作者: George Martin\n" + "价格: 55.55\n" + "页数: 1040", Toast.LENGTH_LONG).show();
            }
        });
        Button queryData = (Button) findViewById(R.id.button_query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询数据
                Uri uri = Uri.parse("content://com.example.four.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Toast.makeText(MainActivity.this, "书名: " + name + "\n作者: " + author + "\n价格: " + price + "\n页数: " + pages, Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                }
            }
        });
        Button updateData = (Button) findViewById(R.id.button_update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新数据
                Uri uri = Uri.parse("content://com.example.four.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("price", 79.00);
                int numsOfUpdate = getContentResolver().update(uri, values, "name =  ? and price = ?", new String[]{"《第一行代码》（第二版）", "59.90"});
                if (numsOfUpdate != 0)
                    Toast.makeText(MainActivity.this, "《第一行代码》（第二版）价格修改成功", Toast.LENGTH_SHORT).show();
            }
        });
        Button deleteData = (Button) findViewById(R.id.button_delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除数据
                Uri uri = Uri.parse("content://com.example.four.databasetest.provider/book");
                int numsOfDelete = getContentResolver().delete(uri, "name = ?", new String[]{"《A Clash of Kings》"});
                if (numsOfDelete != 0)
                    Toast.makeText(MainActivity.this, "《A Clash of Kings》从数据库中删除", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
