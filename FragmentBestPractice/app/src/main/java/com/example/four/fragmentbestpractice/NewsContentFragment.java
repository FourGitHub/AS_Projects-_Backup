package com.example.four.fragmentbestpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class NewsContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }

    // 获得布局，查找得到布局中的控件，为控件绑定资源。主要是用于双页模式下刷新内容面板
    public void refresh(String title, String content){
        View visibilityLayout = view.findViewById(R.id.visible_layout);
        visibilityLayout.setVisibility(View.VISIBLE);// 这是由于在news_content_frag.xml中把android:visibility="invisible"
        TextView newsTitle = (TextView) view.findViewById(R.id.news_title);
        TextView newsContent  = (TextView) view.findViewById(R.id.news_content);
        newsTitle.setText(title);
        newsContent.setText(content);
    }
}
