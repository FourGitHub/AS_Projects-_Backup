package com.example.four.cameraalbumtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private static final String TAG = "MainActivity";

    private ImageView picture;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button takePhoto = (Button) findViewById(R.id.take_photo);
        picture = (ImageView) findViewById(R.id.picture);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建File对象，用于存储拍照后的图片,图片命名为output_image.jpg 储存到应用关联缓存目录，这个目录通过getExternalCacheDir()获得。
                // 之所以放到这里是因为从Android6.0开始，读写SD卡被列为危险权限，如果将图片存放在SD卡其他任何目录，都需要运行时权限，而应用关联目录可以跳过这一步。
                // 为了兼容性  需在AndroidManifest.xml;中声明访问SD卡权限android.permission.WRITE_EXTERNAL_STORAGE
                File outputImage = new File(Environment.getExternalStoragePublicDirectory(Environment
                        .DIRECTORY_PICTURES), "output_image.jpg");
                Toast.makeText(MainActivity.this, "Environment.getExternalStoragePublicDirectory(Environment" +
                        ".DIRECTORY_PICTURES):" + Environment.getExternalStoragePublicDirectory(Environment
                        .DIRECTORY_PICTURES).toString(), Toast.LENGTH_LONG).show();
                try {// 检测该目录是否已经存在
                    if (outputImage.exists()) {
                        outputImage.delete();//删除由此路径名表示的文件或目录。 如果这个路径名表示一个目录，那么该目录必须是空的才能被删除。
                    }
                    outputImage.createNewFile();// 创建一个空的文件到应用关联缓存目录
                    Toast.makeText(MainActivity.this, "outputImage: " + outputImage.toString(), Toast.LENGTH_LONG)
                            .show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT < 24) {
                    // 如果运行设备系统版本低于7.0 ， 可以直接使用file:///uri,直接将File对象转换成Uri对象
                    imageUri = Uri.fromFile(outputImage);
                    Toast.makeText(MainActivity.this, "imageUri<24: " + imageUri.toString(), Toast.LENGTH_LONG).show();
                } else {
                    // 否则将File对象转换成一个封装过的Uri对象。核心代码，将file:///uri 转换成content://uri
                    imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.four.cameraalbumtest" +
                            ".fileprovider", outputImage);
                    Toast.makeText(MainActivity.this, "imageUri: " + imageUri.toString(), Toast.LENGTH_LONG).show();
                    // 可以看到格式为：content://authorities/定义的name属性/文件的相对路径，即name隐藏了可存储的文件夹路径。
                }
                Toast.makeText(MainActivity.this, "imageUri>=24: " + FileProvider.getUriForFile(MainActivity.this,
                        "com.example.four.cameraalbumtest.fileprovider", outputImage).toString(), Toast.LENGTH_LONG)
                        .show();
                // 启动相机程序
                openCamera();
            }
        });

        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);

        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission
                            .WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    public void openCamera() {
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 指定图片输出地址，拍出的照片会输出到 output_image.jpg 中,拍照确认完成后会回调onActivityResult(),对拍的照片进行处理后输出到ImageView
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    public void openAlbum() {
        // 打开相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    /*
    参数:
    requestCode: 最初提供给startActivityForResult（）的整数请求代码，允许你识别这个结果来自哪里。
    resultCode: 子活动通过setResult（）返回的整数结果代码。
    data: 一个意图，它可以返回结果数据给调用者（各种数据可以附加到意图“额外”）。
    */

    // 在调用相机程序放回本活动时，会将相机程序的结果数据通过data返回给此活动，
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片解析成BitMap对象显示出来
                        // 在AndroidManifest.xml文件中注册了FileProvider, 所以getContentResolver()得到的是一个FileProvider对象
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();//返回此意图所针对的数据的URI
        Log.d(TAG, "哈哈哈 handleImageOnKitKat:uri:" + uri);
        Log.d(TAG, "哈哈哈 handleImageOnKitKat:uri.getAuthority():" + uri.getAuthority());
        System.out.println("哈哈哈" + DocumentsContract.isDocumentUri(this, uri));
        Toast.makeText(this, "data中的uri: " + uri.toString(), Toast.LENGTH_LONG).show();
        if (DocumentsContract.isDocumentUri(this, uri)) {// 判断uri是否为documents类型
            String docId = DocumentsContract.getDocumentId(uri);

            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {// 判断uri的authority是否为media格式
                String id = docId.split(":")[1];//这个用法很有意思，[1]表示split()后返回的String[]的数组下标，1号单元储存的是id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {//
                // 判断uri的authority是否为downloads格式
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long
                        .valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            System.out.println("哈哈哈" + "content".equalsIgnoreCase(uri.getScheme()));
            Log.d(TAG, "哈哈哈uri:" + uri);
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }

        Toast.makeText(this, "imagePath: " + imagePath, Toast.LENGTH_LONG).show();
        Log.d(TAG, "哈哈哈 handleImageOnKitKat:imagePath:" + imagePath);
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();//4.4以下的系统，选取相册中的图片返回的是图片真实的uri
        Log.d(TAG, "哈哈哈 handleImageBeforeKitKat:uri:" + uri);
        String imagePath = getImagePath(uri, null);
        Log.d(TAG, "哈哈哈 handleImageBeforeKitKat:imagePath:" + imagePath);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        Log.d(TAG, "哈哈哈 getContentResolver:" + getContentResolver().getClass().getName());//
        // 这个是通过ContentProvider访问相机程序的内容提供器
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取相册图片失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.harvest:
                Intent intent = new Intent(this, SummaryActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
