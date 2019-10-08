package com.learn.four.bitmapsample;

import android.app.job.JobInfo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class CompressActivity extends AppCompatActivity {
    private ImageView imgWanzi;
    private TextView barMin;
    private TextView barMax;
    private SeekBar compressValue;
    private Spinner compressType;
    private Bitmap imgSrc;

    private static final String TAG = "CompressActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);
        init();
    }

    private void init() {
        imgWanzi = findViewById(R.id.img_compress_wanzi);
        barMax = findViewById(R.id.tv_max);                                                                                                                             
        barMin = findViewById(R.id.tv_min);
        compressValue = findViewById(R.id.seekbar_compress_value);
        compressType = findViewById(R.id.spinner_compress_type);

        compressType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:// 质量压缩
                        break;
                    case 1:// 矩阵压缩
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        compressValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Bitmap circleBitmap = createCircleBitmap(R.drawable.ic_wanzi, (int) getResources().getDisplayMetrics().density * 100, (int) getResources().getDisplayMetrics().density * 100);
        imgWanzi.setImageBitmap(circleBitmap);
    }

    private Bitmap createCircleBitmap(int resId, int desireWidth, int desireHeight) {
        Log.i(TAG, "createCircleBitmap: --->>" + "desireWidth = " + desireWidth + "desireHeight = " + desireHeight);
        Bitmap sourceBitmap = createSampleBitmap(resId, desireWidth, desireHeight);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap circleBitmap = Bitmap.createBitmap(desireWidth, desireHeight, sourceBitmap.hasAlpha() ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(desireWidth / 2, desireHeight / 2, desireHeight / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        canvas.drawBitmap(sourceBitmap, 0, 0, paint);
        return circleBitmap;

    }

    private Bitmap createSampleBitmap(int resId, int desireWidth, int desireHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeResource(getResources(), resId, options);
        options.inSampleSize = computeScale(desireWidth,desireHeight, options.outWidth, options.outHeight);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inMutable = true;
        options.inDensity = 640;
        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    private int computeScale(int desireWidth, int desireHeight, int outWidth, int outHeight) {
        int scale = 1;
        if (desireWidth < outWidth || desireHeight < outHeight) {
            scale = Math.max(outWidth/desireWidth, outHeight/desireHeight);
        }
        Log.i(TAG, "computeScale: --->>" + scale);
        return scale;
    }


}
