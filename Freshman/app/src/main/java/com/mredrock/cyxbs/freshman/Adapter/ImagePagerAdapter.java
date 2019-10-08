package com.mredrock.cyxbs.freshman.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mredrock.cyxbs.freshman.CustomView.CircleViewPager;
import com.mredrock.cyxbs.freshman.DataBean.JunXunFCBean;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.Const;

import java.util.List;

/**
 * Created by Administrator on 2018/8/12 0012.
 */

public class ImagePagerAdapter extends CirclePagerAdapter<CircleViewPager> {

    private Context mContext;

    private View[] views ;
    private List<JunXunFCBean.PictureBean> mBeanList;

    public ImagePagerAdapter(Context context, CircleViewPager viewPager,List<JunXunFCBean.PictureBean> mBeanList) {
        super(viewPager);
        mContext = context;
        this.mBeanList = mBeanList;
        views = new View[mBeanList.size()];
    }

    @Override
    public Object instantiateRealItem(ViewGroup container, int position) {
        views[position] = LayoutInflater.from(mContext).inflate(R.layout.freshman_h_junxun_lunbo_pic, null);
        ImageView junxuImageMsgImv = views[position].findViewById(R.id.junxu_image_msg_imv);
        TextView junxuImageMsgTv = views[position].findViewById(R.id.junxu_image_msg_tv);
        Glide.with(container.getContext()).load(Const.IMG_PREFIX+mBeanList.get(position).getUrl()).into(junxuImageMsgImv);
        junxuImageMsgImv.setOnClickListener(v -> {
            Dialog dialog = new Dialog(container.getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.freshman_h_custom_dialog_img,null,false);
            ImageView imgView = view.findViewById(R.id.dialog_image_view);
            Glide.with(container.getContext()).load(Const.IMG_PREFIX+mBeanList.get(position).getUrl()).into(imgView);
            dialog.setContentView(view);
            dialog.show();
            imgView.setOnClickListener(v1 -> dialog.dismiss());

        });
        junxuImageMsgTv.setText(mBeanList.get(position).getName());
        container.addView(views[position]);
        return views[position];
    }

    @Override
    public int getRealDataCount() {
        return mBeanList.size();
    }



}
