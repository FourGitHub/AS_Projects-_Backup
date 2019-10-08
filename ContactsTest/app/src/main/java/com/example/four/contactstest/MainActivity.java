package com.example.four.contactstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
// 记得在AndroidManifest.xml文件中进行（读取联系人信息）权限声明  READ_CONTACTS
    ListView contactsListView;
    ArrayAdapter<String> adapter;
    List<String> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactsListView = (ListView) findViewById(R.id.contacts_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
        contactsListView.setAdapter(adapter);
        // 第一步先判断用户是否已经授权应用程序访问联系人信息
        // 未授权就调用ActivityCompat.requestPermissions()方法 向用户申请授权。
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {// 已授权直接访问就OK
            readContacts();
        }
    }

    /* 查询系统联系人信息 */
    private void readContacts() {
        Cursor cursor = null;
        try {
            /*
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI  这是一个SDK类封装好的URI常量，
            它的值等于 content://com.android.contacts/data/phones
            */
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 联系人名字这一列对应的URI常量是 ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    // 联系人手机号这一列对应的URI常量是 ContactsContract.CommonDataKinds.Phone.NUMBER
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(name + "\n" + number);
                }

                //前面相当于对列表资源初始化，之后通知刷新 ListView
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                // cursor使用完毕后，一定要记得关闭cursor
                cursor.close();
        }
    }

    // 当应用程序向用户申请授权后，无论同意与否都会回调onRequestPermissionsResult()方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(MainActivity.this, "您拒绝了此程序读取联系人的请求", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}