package com.example.four.materialtest;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class CustomiseToast {
    private Context mContext;
    private Toast toast;
    private ImageView imageView;

    public CustomiseToast(Context context) {
        mContext = context;
    }

    public void showToast(int imageViewID, int duration) {
        toast = new Toast(mContext);
        LinearLayout linearLayout = new LinearLayout(mContext);
        imageView = new ImageView(mContext);
        imageView.setImageResource(imageViewID);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(imageView);
        toast.setView(linearLayout);
        toast.setDuration(duration);
        toast.show();
    }

}
