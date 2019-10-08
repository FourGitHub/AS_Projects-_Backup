package com.example.four.materialtest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class FruitActivity extends AppCompatActivity {

    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image";
    private IMyAidlInterface mService;
    private static final String TAG = "FruitActivity";

    /*
     给Binder设置死亡代理，防止服务端进程由于某种原因异常终止！
     当Binder死亡时，就会收到通知，这时候就会重新发起请求恢复客户端和服务端的连接
      */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mService == null) {
                return;
            }
            mService.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mService = null;

            // 重新绑定远程服务
            FruitActivity.this.bindService(
                    new Intent(FruitActivity.this, MyService.class),
                    conn,
                    BIND_AUTO_CREATE
            );
        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IMyAidlInterface.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, -1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collasping_toolbar);
        ImageView fruitImage = (ImageView) findViewById(R.id.fruit_image_view);
        TextView fruitText = (TextView) findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        collapsingToolbarLayout.setTitle(fruitName);
        if (fruitImageId != -1) {
            Glide.with(this).load(fruitImageId).into(fruitImage);
        }
        String fruitContent = generateFruitContent(fruitName);
        fruitText.setText(fruitContent);
    }

    public String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.receive:
                Person person = (Person) getIntent().getParcelableExtra("post_msg");
                if (person != null) {
                    ToastUtil.showToast(getApplicationContext(), person.getName() + "\n" + person.getSex() + "\n" + person.getSingle(), Toast.LENGTH_SHORT);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fruit_activity_toolbar, menu);
        return true;
    }

    public void bindService(View view) {
        Intent bindService = new Intent(this, MyService.class);
        bindService(bindService, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(conn);
            mService = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        objPersistence_p();
    }

    private void objPersistence_s() {
        Book book = new Book("HeShen", 1111111111);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(openFileOutput("parcelableBook_s.bin", MODE_PRIVATE)));
            oos.writeObject(book);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(openFileInput("parcelableBook_s.bin")));
            Log.i(TAG, "objPersistence_s: " + ois.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void objPersistence_p() {

        try (BufferedOutputStream bos =
                     new BufferedOutputStream(openFileOutput("parcelableBook_p.bin", MODE_PRIVATE));
             BufferedInputStream bis =
                     new BufferedInputStream(openFileInput("parcelableBook_p.bin"));
             ByteArrayOutputStream baos
                     = new ByteArrayOutputStream()) {

            /* 序列化过程 */
            Book bookOut = new Book("Four", 2020202020);
            Parcel parcelOut = Parcel.obtain();
            parcelOut.setDataPosition(0);
            bookOut.writeToParcel(parcelOut, 0);
            byte[] bytesOut = parcelOut.marshall();
            bos.write(bytesOut);
            bos.flush();


            /* 反序列化过程 */
            Book bookIn;
            byte[] bytesIn;
            Parcel parcelIn = Parcel.obtain();
            int temp;
            while ((temp = bis.read()) != -1) {
                baos.write(temp);
            }
            bytesIn = baos.toByteArray();
            parcelIn.unmarshall(bytesIn, 0, bytesIn.length);
            parcelIn.setDataPosition(0);
            // 如果用 parcelIn.readParcelable（），使用时直接 NPE
            bookIn = Book.CREATOR.createFromParcel(parcelIn);

            /* 验证过程 */
            {
                Log.i(TAG, "objPersistence_p: " + bytesOut.length);
                Log.i(TAG, "objPersistence_p: " + bytesIn.length);
                Log.i(TAG, "objPersistence_p: " + bookIn);
                Log.i(TAG, "objPersistence_p: " + bookIn.getId());
                Log.i(TAG, "objPersistence_p: " + bookIn.getName());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
