package com.example.four.litepaltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createDatabase = (Button) findViewById(R.id.button_create_db);
        Button addData = (Button) findViewById(R.id.button_add_data);
        Button deleteData = (Button) findViewById(R.id.button_delete_data);
        Button updateData = (Button) findViewById(R.id.button_update_data);
        Button queryData = (Button) findViewById(R.id.button_query_data);
        Button deleteAllData = (Button) findViewById(R.id.button_delete_alldata);
        Button averPrice = (Button) findViewById(R.id.button_aver_price);

        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.getDatabase();
                Toast.makeText(MainActivity.this, "数据库创建成功", Toast.LENGTH_SHORT).show();
            }
        });


        deleteAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(Book.class);
                Toast.makeText(MainActivity.this, "数据库Book表中的全部数据删除成功", Toast.LENGTH_SHORT).show();

            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setAuthor("郭霖");
                book.setName("《第一行代码》（第二版）");
                book.setPages(570);
                book.setPrice(79.00);
                book.setPress("人民邮电出版社");
                if (!book.isSaved())
                    book.save();

                Book book1 = new Book();
                book1.setAuthor("（美）盖茨");
                book1.setName("《设计模式之禅》");
                book1.setPages(558);
                book1.setPrice(69.00);
                book1.setPress("机械工业出版社");
                book1.save();

                // LitePal会重复添加这本书  因为isSaved()返回false
                //                Book book2 = new Book();
                //                book2.setAuthor("（美）盖茨");
                //                book2.setName("《设计模式之禅》");
                //                book2.setPages(558);
                //                book2.setPrice(69.00);
                //                book2.setPress("机械工业出版社");
                //                Log.d(TAG, "是否已包含: " + book2.isSaved());
                //                    book2.save();


                Book book3 = new Book();
                book3.setAuthor("（美）盖茨");
                book3.setName("《Java并发编程实战》");
                book3.setPages(598);
                book3.setPrice(69.00);
                book3.setPress("机械工业出版社");
                book3.save();

                Toast.makeText(MainActivity.this, "添加了三本书", Toast.LENGTH_SHORT).show();
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 修改价格
                Book book = new Book();
                book.setPrice(9.99);
                book.updateAll("name = ? and author = ?", "《第一行代码》（第二版）", "郭霖");

                // 更新成默认值
                Book book1 = new Book();
                book1.setToDefault("press");
                book1.updateAll("name = ? and author = ?", "《设计模式之禅》", "（美）盖茨");
                Toast.makeText(MainActivity.this, "信息修改成功", Toast.LENGTH_SHORT).show();
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(Book.class, "name = ?", "《Java并发编程实战》");
                Toast.makeText(MainActivity.this, "《Java并发编程实战》售罄", Toast.LENGTH_SHORT).show();
            }
        });

        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 查询返回的是模型类的 List集合
                List<Book> books = DataSupport.select("name", "author", "price").where("pages > ?", "100").order("price desc").find(Book.class);
                for (Book book : books) {
                    Toast.makeText(MainActivity.this, "\n书名: " + book.getName() + "\n作者: " + book.getAuthor() + "\n价格: " + book.getPrice(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        averPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double aver_price = DataSupport.where("name = ? or name = ?","《第一行代码》（第二版）","《设计模式之禅》").average(Book.class,"price");
                Toast.makeText(MainActivity.this,"《第一行代码》（第二版）和" + "\n" +"《设计模式之禅》"+"\n" +"的平均价格是: " + aver_price, Toast.LENGTH_LONG).show();
            }
        });
    }
}
