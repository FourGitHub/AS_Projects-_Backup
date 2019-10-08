package com.learn.four.bitmapsample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private TextView tvMemAndDeviceInfo;
    private TextView tvMemOccupy;
    private TextView tvLocSize;
    private ImageView imageView;
    private Switch switchConfig;
    private Switch switchSample;
    private Spinner spinner;
    private Spinner spinnerImgViewSize;


    private int mInDensity = 240;
    private int desireWidth;
    private int desireHeight;

    /* 是否优化 */
    private boolean isConfigOpti;
    private boolean isSampleOpti;

    private Bitmap imgSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tvMemAndDeviceInfo = findViewById(R.id.tv_info);
        tvMemOccupy = findViewById(R.id.tv_bitmap_memOccupy);
        tvLocSize = findViewById(R.id.tv_bitmap_localSize);
        imageView = findViewById(R.id.img_wanzi);
        spinner = findViewById(R.id.spinner);
        spinnerImgViewSize = findViewById(R.id.spinner_img);
        switchConfig = findViewById(R.id.switch_config);
        switchSample = findViewById(R.id.swith_sample);
        isConfigOpti = switchConfig.isChecked();
        isSampleOpti = switchSample.isChecked();

        // ImageView 默认为屏幕宽度
        desireWidth = getResources().getDisplayMetrics().widthPixels;
        desireHeight =getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(desireWidth, desireHeight);
        imageView.setLayoutParams(lp);
        setImgSrc(mInDensity,desireWidth,desireHeight);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setImgSrc(240,desireWidth,desireHeight);
                        break;
                    case 1:
                        setImgSrc(320,desireWidth,desireHeight);
                        break;
                    case 2:
                        setImgSrc(480,desireWidth,desireHeight);
                        break;
                    case 3:
                        setImgSrc(560,desireWidth,desireHeight);
                        break;
                    case 4:
                        setImgSrc(640,desireWidth,desireHeight);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerImgViewSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgSrc = null;
                switch (position) {
                    case 0:
                        desireWidth = getResources().getDisplayMetrics().widthPixels;
                        desireHeight = getResources().getDisplayMetrics().widthPixels;
                        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(desireWidth, desireHeight);
                        imageView.setLayoutParams(lp1);
                        setImgSrc(mInDensity,desireWidth,desireHeight);
                        break;
                    case 1:
                        desireWidth = (int)(getResources().getDisplayMetrics().density*100);
                        desireHeight = (int)(getResources().getDisplayMetrics().density*100);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(desireWidth, desireHeight);
                        imageView.setLayoutParams(lp);
                        setImgSrc(mInDensity,desireWidth,desireHeight);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switchSample.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSampleOpti = isChecked;
                setImgSrc(mInDensity, desireWidth, desireHeight);
            }
        });

        switchConfig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isConfigOpti = isChecked;
                setImgSrc(mInDensity, desireWidth, desireHeight);
            }
        });
    }

    private void setImgSrc(int inDensity, int desireWidth, int desireHeight) {
        imgSrc = null; // 使之前的Bitmap能够及时得到回收
        mInDensity = inDensity;
        imgSrc = decodeBitmap(desireWidth, desireHeight);
        imageView.setImageBitmap(imgSrc);
    }

    private Bitmap decodeBitmap(int desireWidth, int desireHeight){
        /* 优化之前 */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        options.inDensity = mInDensity;
        BitmapFactory.decodeResource(getResources(), R.drawable.ic_wanzi, options);
        showBitmapLocalInfo(options);

        /* 根据选择优化项  进行优化 */
        options.inJustDecodeBounds = false;
        if (isSampleOpti && isConfigOpti) {
            options.inSampleSize = computeScale(desireWidth, desireHeight, options.outWidth, options.outHeight);
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        } else if (isConfigOpti) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        } else if (isSampleOpti) {
            options.inSampleSize = computeScale(desireWidth, desireHeight, options.outWidth, options.outHeight);
        }
        imgSrc = BitmapFactory.decodeResource(getResources(), R.drawable.ic_wanzi, options);
        showBitmapMemInfo(options);
        return imgSrc;
    }

    private void showBitmapLocalInfo(BitmapFactory.Options options) {
        tvLocSize.setText(" Bitmap物理尺寸: " + options.outWidth + "×" + options.outHeight);
    }

    private void showBitmapMemInfo(BitmapFactory.Options options) {
        tvMemAndDeviceInfo.setText("");
        tvMemAndDeviceInfo.append(" Width: " + imgSrc.getWidth() + " pixel\n");
        tvMemAndDeviceInfo.append(" Height: " + imgSrc.getHeight() + " pixel\n");
        tvMemAndDeviceInfo.append(" Config: " + imgSrc.getConfig() + "\n");
        tvMemAndDeviceInfo.append(" mInDensity: " + options.inDensity + "\n");
        tvMemAndDeviceInfo.append(" inScreenDensity: " + options.inScreenDensity + "\n");
        tvMemAndDeviceInfo.append(" inTargetDensity: " + options.inTargetDensity + "\n");
        tvMemAndDeviceInfo.append(" deviceDensityDPI: " + getResources().getDisplayMetrics().densityDpi);
        tvMemOccupy.setText(spannableContent(imgSrc.getAllocationByteCount() + ""));
    }

    private SpannableString spannableContent(String memOccupy) {
        DecimalFormat decimalFormat = new DecimalFormat(",###");
        String decimalMemOccupy = decimalFormat.format(Double.parseDouble(memOccupy));
        int decimalLength = decimalMemOccupy.length();
        String initContent = " 内存: " + decimalMemOccupy + " byte";
        int totalLength = initContent.length();

        /* 富文本处理，需要注意，XxxSpan对象不能在多个区间进行重用，否则，仅最后一次使用生效 */
        SpannableString resultContent = new SpannableString(initContent);

        ForegroundColorSpan fgColorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        resultContent.setSpan(fgColorSpan, 1, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        fgColorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        resultContent.setSpan(fgColorSpan, 5 + decimalLength, totalLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        fgColorSpan = new ForegroundColorSpan(Color.parseColor("#ff0000"));
        resultContent.setSpan(fgColorSpan, 5, 5 + decimalLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
        resultContent.setSpan(styleSpan, 5, 5 + decimalLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return resultContent;
    }

    private int computeScale(int desireWidth, int desireHeight, int outWidth, int outHeight) {
        int scale = 1;
        if (desireWidth < outWidth || desireHeight < outHeight) {
            scale = Math.max(outWidth/desireWidth, outHeight/desireHeight);
        }
        return scale;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.to_compressAc:
                Intent toCompressAc = new Intent(this, CompressActivity.class);
                startActivity(toCompressAc);
                break;
        }
        return true;
    }
}
