package com.example.four.customviewtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.four.customviewtest.MyCustomView.CustomCircleHead;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private CustomCircleHead circleHead;
    private Uri imageUri;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        Log.i(TAG, "onCreate: -> ActionBar:" + actionBar.getClass().getName());
        setContentView(R.layout.test_2);
        circleHead = (CustomCircleHead) findViewById(R.id.circle_head_view);

        circleHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleHead.randomScrollBy();
                Log.i(TAG, "onClick: -->>");
            }
        });
    }

    public void chooseFromAlbum(View view) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    public void takePhoto(View view) {
        // 创建File对象，用于存储拍照后的图片,图片命名为output_image.jpg 储存到应用关联缓存目录，这个目录通过getExternalCacheDir()获得。
        // 之所以放到这里是因为从Android6.0开始，读写SD卡被列为危险权限，如果将图片存放在SD卡其他任何目录，都需要运行时权限，而应用关联目录可以跳过这一步。
        // 为了兼容性  需在AndroidManifest.xml;中声明访问SD卡权限android.permission.WRITE_EXTERNAL_STORAGE
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT < 24) {
            // 如果运行设备系统版本低于7.0 ， 可以直接使用file:///uri,直接将File对象转换成Uri对象
            imageUri = Uri.fromFile(outputImage);
        } else {
            // 否则将File对象转换成一个封装过的Uri对象。核心代码，将file:///uri 转换成content://uri
            imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.four.cameraalbum.fileprovider", outputImage);
        }
        openCamera();
    }

    /**
     * 启动相机程序
     */
    public void openCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.GET_CONTENT");
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

    //    /**
    //     * @param requestCode
    //     * @param resultCode
    //     * @param data 在调用相机程序返回本活动时，会将相机程序的结果数据uri封装在一个Intent里返回给此活动
    //     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 拍照完成后，把照片的的uri传递给CustomCirclrHead
                        circleHead.setHeadImageUri(imageUri);
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
        Toast.makeText(this, "data中的uri: " + uri.toString(), Toast.LENGTH_LONG).show();
        if (DocumentsContract.isDocumentUri(this, uri)) {// 判断uri是否为documents类型
            String docId = DocumentsContract.getDocumentId(uri);

            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {// 判断uri的authority是否为media格式
                String id = docId.split(":")[1];// 1号单元储存的是id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {// 判断uri的authority是否为downloads格式
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    /**
     * @param data 4.4以下的系统，选取相册中的图片返回的是图片真实的uri
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
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
            circleHead.setHeadImagePath(imagePath);
        } else {
            Toast.makeText(this, "获取相册图片失败", Toast.LENGTH_LONG).show();
        }
    }
}
