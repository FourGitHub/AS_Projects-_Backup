package com.example.four.mybestfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/5 0005.
 */

public class NewsContentFragment extends Fragment {

    private  View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }

    public void refresh(String newsTitle,String newsContent){
        View invisibleLayout = view.findViewById(R.id.invisible_layout);
        invisibleLayout.setVisibility(View.VISIBLE);
        TextView titleText = (TextView) view.findViewById(R.id.title_in_news_content_frag);
        TextView contentText = (TextView) view.findViewById(R.id.content_in_news_content_frag);
        titleText.setText(newsTitle);
        contentText.setText(newsContent);
    }
}
