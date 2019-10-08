package com.mredrock.cyxbs.freshman.UI.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mredrock.cyxbs.freshman.R;

public class IWannaSayActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView;
    private View paddingView;
    private ImageView titleBackImv;
    private TextView mTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshman_h_iwanna_say);
        init();
        fusionStatusBar(paddingView);

    }

    private void init() {
        paddingView = findViewById(R.id.padding_view);
        titleBackImv = findViewById(R.id.title_back);
        mTitleTv = findViewById(R.id.title_tv);
        imageView = findViewById(R.id.i_wanna_say_img);
        titleBackImv.setOnClickListener(this);
        TextPaint tp = mTitleTv.getPaint();
        tp.setFakeBoldText(true);

//        Glide.with(this).load(R.drawable.freshman_h_i_wanna_say).into(imageView);

        // 为了让图片不变形也是日了狗了！
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888; // 默认是 Bitmap.Config.ARGB_8888
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeResource(getResources(),R.drawable.freshman_h_i_wanna_say, options);
        int bmpWidth = options.outWidth; // 获取图片宽度（像素）
        int bmpHeight = options.outHeight; // 获取图片高度（像素）

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;         // 屏幕宽度（像素）
        int screenHeight = dm.heightPixels;  // 屏幕宽度（像素）

        if (screenWidth < bmpWidth || screenHeight < bmpHeight) {
            options.inSampleSize = (int) Math.max(bmpWidth / screenWidth, bmpHeight / screenHeight);
        }
        options.inJustDecodeBounds = false;
        Glide.with(this).load(BitmapFactory.decodeResource(getResources(), R.drawable.freshman_h_i_wanna_say, options))
             .into(imageView);
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.freshman_h_i_wanna_say, options));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
