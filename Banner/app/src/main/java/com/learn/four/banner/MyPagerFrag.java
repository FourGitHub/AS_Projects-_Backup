package com.learn.four.banner;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create on 2019/07/20
 *
 * @author Four
 * @description
 */
public class MyPagerFrag extends Fragment {


    @BindView(R.id.frg_pager_tv)
    TextView frgPagerTv;
    @BindView(R.id.frg_pager_layout)
    RelativeLayout frgPagerLayout;
    Unbinder unbinder;

    public static MyPagerFrag getInstance(int pos) {
        MyPagerFrag frag = new MyPagerFrag();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        frag.setArguments(bundle);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_pager, container, false);
        unbinder = ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        int pos = bundle.getInt("pos");
        switch (pos) {
            case 0:
                frgPagerLayout.setBackgroundColor(getResources().getColor(R.color.frag_pager_0));
                frgPagerTv.setTextColor(Color.WHITE);
                frgPagerTv.setText("One");
                break;
            case 1:
                frgPagerLayout.setBackgroundColor(getResources().getColor(R.color.frag_pager_1));
                frgPagerTv.setTextColor(Color.WHITE);
                frgPagerTv.setText("Two");
                break;
            case 2:
                frgPagerLayout.setBackgroundColor(getResources().getColor(R.color.frag_pager_2));
                frgPagerTv.setTextColor(Color.WHITE);
                frgPagerTv.setText("Three");
                break;
            case 3:
                frgPagerLayout.setBackgroundColor(getResources().getColor(R.color.frag_pager_3));
                frgPagerTv.setTextColor(Color.WHITE);
                frgPagerTv.setText("Four");
                break;
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
