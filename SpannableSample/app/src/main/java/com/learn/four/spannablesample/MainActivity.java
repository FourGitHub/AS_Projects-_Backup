package com.learn.four.spannablesample;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tv0;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tv0 = findViewById(R.id.tv_0);
        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);
        tv3 = findViewById(R.id.tv_3);
        tv4 = findViewById(R.id.tv_4);
        tv5 = findViewById(R.id.tv_5);
        tv6 = findViewById(R.id.tv_6);
        tv7 = findViewById(R.id.tv_7);
        tv8 = findViewById(R.id.tv_8);
        tv9 = findViewById(R.id.tv_9);
        tv10 = findViewById(R.id.tv_10);

        setForeground();
        setBackground();
        setRelativeTextSize();
        setStrikethrough();
        setStyle();
        setUnderline();
        setSuperscript();
        setSubscript();
        setImage();
        setClickable();
        setURL();
    }

    /* 设置前景色，效果相当于setTextColor */
    private void setForeground() {
        SpannableString sString = new SpannableString("01234前景色89");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        sString.setSpan(span, 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv0.setText(sString);
    }

    /* 设置背景色，效果相当于setBackgroundColor */
    private void setBackground() {
        SpannableString sString = new SpannableString("01234背景色89");
        BackgroundColorSpan span = new BackgroundColorSpan(Color.parseColor("#0099EE"));
        sString.setSpan(span, 5, 8, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        tv1.setText(sString);
    }

    /* 设置相对字体大小 */
    private void setRelativeTextSize() {
        SpannableString sString = new SpannableString("0123456789");
        RelativeSizeSpan span0 = new RelativeSizeSpan(1.1f);
        RelativeSizeSpan span1 = new RelativeSizeSpan(1.2f);
        RelativeSizeSpan span2 = new RelativeSizeSpan(1.3f);
        RelativeSizeSpan span3 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan span4 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan span5 = new RelativeSizeSpan(1.3f);
        RelativeSizeSpan span6 = new RelativeSizeSpan(1.2f);
        RelativeSizeSpan span7 = new RelativeSizeSpan(1.1f);

        sString.setSpan(span0, 1, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sString.setSpan(span1, 2, 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sString.setSpan(span2, 3, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sString.setSpan(span3, 4, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sString.setSpan(span4, 5, 6, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sString.setSpan(span5, 6, 7, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sString.setSpan(span6, 7, 8, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sString.setSpan(span7, 8, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv2.setText(sString);
    }

    /* 为文字设置删除线 */
    private void setStrikethrough() {
        SpannableString sString = new SpannableString("0123456删除线");
        StrikethroughSpan span = new StrikethroughSpan();
        sString.setSpan(span, 7, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv3.setText(sString);
    }

    /* 为文字设置下划线 */
    private void setUnderline() {
        SpannableString sString = new SpannableString("0123456下划线");
        UnderlineSpan span = new UnderlineSpan();
        sString.setSpan(span, 7, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        tv4.setText(sString);
    }

    /* 为文字设置上标 */
    private void setSuperscript() {
        SpannableString sString = new SpannableString("01234567上标");
        SuperscriptSpan span = new SuperscriptSpan();
        sString.setSpan(span, 8, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        RelativeSizeSpan span1 = new RelativeSizeSpan(0.6f);
        sString.setSpan(span1, 8, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv5.setText(sString);
    }

    /* 为文字设置下标 */
    private void setSubscript() {
        SpannableString sString = new SpannableString("01234567下标");
        SubscriptSpan span = new SubscriptSpan();
        sString.setSpan(span, 8, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        RelativeSizeSpan span1 = new RelativeSizeSpan(0.6f);
        sString.setSpan(span1, 8, 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        tv6.setText(sString);
    }

    /* 为文字设置字体风格 */
    private void setStyle() {
        SpannableString sString = new SpannableString("0123粗体67斜体");
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
        sString.setSpan(boldSpan, 4, 6, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sString.setSpan(italicSpan, 8, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv7.setText(sString);
    }

    /* 在文本中插入图片 */
    private void setImage() {
        SpannableString sString = new SpannableString("012345678 ");
        ImageSpan span = new ImageSpan
                (this, R.drawable.ic_se, DynamicDrawableSpan.ALIGN_BASELINE);
        sString.setSpan(span, 9, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv8.setText(sString);
        tv8.setGravity(Gravity.CENTER);
    }

    /* 可点击文本 */
    private void setClickable() {
        SpannableString sString = new SpannableString("012345点击这里");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                // super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#0099EE"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        sString.setSpan(span, 6, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv9.setMovementMethod(LinkMovementMethod.getInstance());
        tv9.setHighlightColor(Color.parseColor("#36969696"));
        tv9.setText(sString);
    }

    /* 超链接 */
    private void setURL() {
        SpannableString sString = new SpannableString("0123456超链接");
        URLSpan span = new URLSpan("https://www.baidu.com") {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        sString.setSpan(span, 7, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv10.setMovementMethod(LinkMovementMethod.getInstance());
        tv10.setHighlightColor(Color.parseColor("#0099EE"));
        tv10.setText(sString);
    }

//    private void setTextAppearence(){
//        SpannableString sString = new SpannableString("012345Four");
//        TextAppearanceSpan span = new TextAppearanceSpan()
//    }
}
