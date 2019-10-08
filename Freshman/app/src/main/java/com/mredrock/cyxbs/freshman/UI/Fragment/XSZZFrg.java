package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.MienPresenter;
import com.mredrock.cyxbs.freshman.MVP.View.MienView;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.Const;
import com.mredrock.cyxbs.freshman.Utility.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15 0015.
 * 学生组织Fragment
 */

public class XSZZFrg extends BaseFragment implements MienView {
    private static final String TAG = "XSZZFrg";

    private TextView itemName;
    private LinearLayout textLayout;
    private ImageButton btnMore;
    private TextView itemMsg;
    private ImageView imageView;
    private boolean isExpand = false;
    //记录当前选中的是哪一个学生组织，用来标记，改变对应组织字体的颜色
    private int current = 0;
    private Button[] buttons;

    private List<CampusBean.DataBean> mDataBeanList;
    private MienPresenter mPresenter;


    @Override
    public int getContentViewId() {
        return R.layout.freshman_h_xszz_frg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttons = new Button[9];
        textLayout = view.findViewById(R.id.text_layout);
        btnMore =  view.findViewById(R.id.more_btn);
        itemMsg =  view.findViewById(R.id.item_msg);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(),100));

                switch (v.getId()) {
                    case R.id.more_btn:
                        if (isExpand) {
                            isExpand = false;
                            textLayout.setLayoutParams(lp2);
                            btnMore.setImageResource(R.drawable.freshman_h_down_more);

                        } else {
                            isExpand = true;
                            btnMore.setImageResource(R.drawable.freshman_h_up_less);
                            textLayout.setLayoutParams(lp);
                        }
                        break;

                }
            }
        });

        // 本来是动态添加的，结果名字长了之后，会换行！！！
        itemName = view.findViewById(R.id.xszz_name_tv);
        imageView = view.findViewById(R.id.xszz_img);
        buttons[0] = view.findViewById(R.id.item_name_btn0);
        buttons[1] = view.findViewById(R.id.item_name_btn1);
        buttons[2] = view.findViewById(R.id.item_name_btn2);
        buttons[3] = view.findViewById(R.id.item_name_btn3);
        buttons[4] = view.findViewById(R.id.item_name_btn4);
        buttons[5] = view.findViewById(R.id.item_name_btn5);
        buttons[6] = view.findViewById(R.id.item_name_btn6);
        buttons[7] = view.findViewById(R.id.item_name_btn7);
        buttons[8] = view.findViewById(R.id.item_name_btn8);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setTag(i + "");
        }

        mPresenter = new MienPresenter();
        mPresenter.attachView(this);
        mPresenter.getData("学生组织");
        TextPaint tp = itemName.getPaint();
        tp.setFakeBoldText(true);

    }

    // 当一个button被点击的时候，做两件事情
    // 1.将它自己的button字体设为蓝色，并且遍历其余button
    // 2.根据“Integer.parseInt(v.getTag().toString())”刷新界面:
    // * expTv1(组织名称)
    // * itemName(组织描述)
    // * imageView(组织图片)
    private void setButtonsClickListener() {
        // 1、已经实现了
        for (int i = 0; i < buttons.length; i++) {
            int finalI = i;
            buttons[i].setOnClickListener(v -> {
                setButtonTextColor(Integer.parseInt(v.getTag().toString()));
                itemName.setText(mDataBeanList.get(finalI).getName());

                itemMsg.setText(mDataBeanList.get(finalI).getContent());

                Glide.with(mContext).load(Const.IMG_PREFIX + mDataBeanList.get(finalI).getPictureList().get(0))
                        .asBitmap().placeholder(R.drawable.freshman_h_zhanwei).error(R.drawable.freshman_h_zhanwei)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT).into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(DensityUtil.dip2px(mContext, 5)); //设置圆角弧度
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });

                // 每切换一次数据，就将可展开的TextView重新置位
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil
                        .dip2px(getActivity(), 100));
                textLayout.setLayoutParams(lp2);
                isExpand = false;
                btnMore.setImageResource(R.drawable.freshman_h_down_more);

            });
        }

    }

    private void setButtonTextColor(int pos) {
        for (int i = 0; i < buttons.length; i++) {
            if (pos == i) {
                buttons[i].setTextColor(Color.parseColor("#54ACFF"));
            } else {
                buttons[i].setTextColor(Color.parseColor("#999999"));
            }
        }

    }

    @Override
    public void showData(List<CampusBean.DataBean> dataBeanList) {
        mDataBeanList = dataBeanList;
        itemMsg.setText(mDataBeanList.get(current).getContent());
        Glide.with(mContext).load(Const.IMG_PREFIX + mDataBeanList.get(current).getPictureList().get(0)).asBitmap()
                .placeholder(R.drawable.freshman_h_zhanwei).error(R.drawable.freshman_h_zhanwei)
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                        .create(mContext.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(DensityUtil.dip2px(mContext, 5)); //设置圆角弧度
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        setButtonTextColor(0);
        setButtonsClickListener();
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }
}
