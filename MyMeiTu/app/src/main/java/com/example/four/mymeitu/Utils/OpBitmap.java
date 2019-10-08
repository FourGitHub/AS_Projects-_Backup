package com.example.four.mymeitu.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.misc.BASE64Encoder;

/**
 * Created by Administrator on 2018/4/18 0018.
 */

public class OpBitmap {
    /**
     * @param bmp            将要被缩放的Bitmap
     * @param widthMultiple  宽度的缩放倍数(int)
     * @param heightMultiple 高度的缩放倍数(int)
     * @return 缩放后的新的Bitmap
     */
    public static Bitmap scale(Bitmap bmp, int widthMultiple, int heightMultiple) {
        Matrix matrix = new Matrix();
        Bitmap newBmp = Bitmap.createBitmap(
                (int) bmp.getWidth() * widthMultiple,
                (int) bmp.getHeight() * heightMultiple,
                bmp.hasAlpha() ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(newBmp);
        float[] values = new float[]{
                widthMultiple, 0, 0,
                0, heightMultiple, 0,
                0, 0, 1
        };
        matrix.setValues(values);
        canvas.drawBitmap(bmp, matrix, null);
        return newBmp;
    }

    /**
     * @param file         待压缩图片的文件路径
     * @param desireWidth  期望压缩后的宽度
     * @param desireHeight 期望压缩后的高度
     * @return 压缩后的图片
     */
    public static Bitmap sampleCompressBitmap(File file, int desireWidth, int desireHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565; // 默认是 Bitmap.Config.ARGB_8888
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int bmpWidth = options.outWidth;
        int bmpHeight = options.outHeight;
        if (desireWidth < bmpWidth || desireHeight < bmpHeight) {
            options.inSampleSize = (int) Math.max(bmpWidth / desireWidth, bmpHeight / desireHeight);
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    /**
     * @return 从资源中获取Bitmap
     */
    public static Bitmap getBitmapFromResource(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    /**
     * @return 从资源中获取Drawable
     */
    public static Drawable getDrawableFromResource(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }

    /**
     * @return Bimap 转换为 Drawable
     */
    public static Drawable Bimap2Drawable(Context context, Bitmap bmp) {
        return new BitmapDrawable(context.getResources(), bmp);
    }

    /**
     * @return Drawable 转换为 Bitmap
     */
    public static Bitmap Drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() == PixelFormat.OPAQUE ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // 为drawable指定一个矩形区域，当Drawable的draw()方法调用时确定绘制区域
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * @param context        传入一个Context,用于打开IntenalStorage目录
     * @param bmp            想要存储的位图
     * @param compressFormat 指定压缩格式
     * @param name           指定保存后位图的文件名
     * @throws IOException
     */
    public static void storeOnInternalStorage(Context context, Bitmap bmp, Bitmap.CompressFormat compressFormat, String name) throws IOException {
        File file = new File(context.getFilesDir(), name);
        FileOutputStream fos = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            bmp.compress(compressFormat, 90, fos);
            fos.flush();// 立即将缓冲区中的字节写入目标。
        } catch (IOException e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * @param bmp            想要存储的位图
     * @param compressFormat 指定压缩格式
     * @param name           指定保存后位图的文件名
     * @throws IOException
     */
    public static void storeOnExternalPublicStorage(Bitmap bmp, Bitmap.CompressFormat compressFormat, String name) throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), name);
        FileOutputStream fos = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            bmp.compress(compressFormat, 90, fos);
            fos.flush();// 立即将缓冲区中的字节写入目标。
        } catch (IOException e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    public static Bitmap createCircleBitmap(Bitmap bmp, float cx, float cy, float radius) {
        if (cx <= 0 || cy <= 0) {
            return null;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true); // 如果是画棱角分明的图，比如矩形，就不用设置这个抗锯齿属性
        Bitmap bitmap = Bitmap.createBitmap(
                (int) (2 * cx),
                (int) (2 * cy),
                bmp.hasAlpha() ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(cx, cy, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, 0, 0, paint);
        return bitmap;
    }

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] buffer = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * string转成bitmap
     *
     * @param string
     */
    public static Bitmap convertStringToBitmap(String string) {
        Bitmap bitmap = null;
        byte[] bitmapArray;
        bitmapArray = Base64.decode(string, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                bitmapArray.length);
        return bitmap;
    }

    /**
     * 图片文件转换为指定编码的字符串
     *
     * @param imgFile 图片文件
     */
    public static String file2String(File imgFile) throws IOException {
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组; in.available()的作用是读取输入流的长度
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
           throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        //对字节数组Base64编码并返回编码结果
        BASE64Encoder encoder = new BASE64Encoder();
        String result = encoder.encode(data);
        return result;
    }
}

