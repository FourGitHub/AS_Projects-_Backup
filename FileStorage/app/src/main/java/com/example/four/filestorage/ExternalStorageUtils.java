package com.example.four.filestorage;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/31 0031.
 */


public class ExternalStorageUtils {
    /**
     * Environment.MEDIA_MOUNTED SD卡状态可读可写
     * Environment.MEDIA_MOUNTED_READ_ONLY  SD卡状态仅可读
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ? true : false;
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param albumName 待返回的用于存储照片的目录。
     */
    public File getAlbumStoragePublicDirectory(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {

            // 若子目录albumName还未创建，则创建
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
