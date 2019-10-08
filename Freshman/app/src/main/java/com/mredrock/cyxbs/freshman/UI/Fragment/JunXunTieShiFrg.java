package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.JunXunTipsPresenter;
import com.mredrock.cyxbs.freshman.MVP.View.JunXunTipsView;
import com.mredrock.cyxbs.freshman.R;

import java.util.List;

/**
 * Created by Administrator on 2018/8/12 0012.
 */

public class JunXunTieShiFrg extends BaseFragment implements JunXunTipsView {
    private TextView junxunTieshiTv;
    private JunXunTipsPresenter mPresenter;


    @Override
    public int getContentViewId() {
        mPresenter = new JunXunTipsPresenter();
        mPresenter.attachView(this);
        return R.layout.freshman_h_jxts_frg;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        junxunTieshiTv = view.findViewById(R.id.junxun_tieshi_tv);
        mPresenter.getTips();
    }

    @Override
    public void setTips(List<CampusBean.DataBean> campusBeanList) {
        CampusBean.DataBean b;
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < campusBeanList.size(); i++) {
            b = campusBeanList.get(i);
            String content = b.getContent().trim();
            Log.d("TEXTTEXT", "TEXTTEXT: i = " + i + b.getContent());
            if (b.getProperty().equals("加粗")) {
                RelativeSizeSpan sizeSpan = new RelativeSizeSpan(16f / 14f);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#404040"));
                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                SpannableString spannableString = new SpannableString("\n" + content + "\n");
                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(sizeSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(boldSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(spannableString);
            } else {
                builder.append(content);
            }
        }
        junxunTieshiTv.setText(builder);
    }
}

