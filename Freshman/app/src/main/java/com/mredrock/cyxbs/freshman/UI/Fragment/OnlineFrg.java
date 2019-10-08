package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.mredrock.cyxbs.freshman.Adapter.OnlineAdapter;
import com.mredrock.cyxbs.freshman.DataBean.OnlineBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.OnlinePresenter;
import com.mredrock.cyxbs.freshman.MVP.View.OnlineView;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.MyItemDecoration;

import java.util.List;

/**
 * Created by Administrator on 2018/8/16 0016.
 * 其实线上交流页面的 学员群和老乡群都是用的这个Fragment,因为它们逻辑一毛一样
 * new 的时候，传一个type 进来就行了,
 * <p>
 * <p>
 * 未完善部分，监听EditText, 网络请求，当有返回结果后刷新recyclerView:
 * 1.resultList = 最新的List
 * 2.adapter.notifyDataSetChanged();
 */

public class OnlineFrg extends BaseFragment implements OnlineView {
    private EditText searchEdt;
    private RelativeLayout hintLayout;
    private RecyclerView dynamicRecycler;
    private OnlineAdapter adapter;

    public static final String TYPE_COLLEGE_GROUP = "学校群";
    public static final String TYPE_FELLOW_GROUP = "老乡群";

    private String type; // 有了这个type,在网络请求是就可以现获取这个type,然后再请求老乡/学院

    private OnlinePresenter mPresenter;

    // 默认是学院群
    public OnlineFrg() {
        this(TYPE_COLLEGE_GROUP);
    }

    @SuppressLint("ValidFragment")
    public OnlineFrg(String type) {
        this.type = type;
    }

    @Override
    public int getContentViewId() {
        return R.layout.freshman_h_college_group_frg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new OnlinePresenter();
        mPresenter.attachView(this);
        fvb(view);
        initRecycler();
        initListener();
    }

    private static final String TAG = "OnlineFrg";

    private void initListener() {

        searchEdt.addTextChangedListener(new TextWatcher() {
            long t = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (System.currentTimeMillis() - t > 500) {
                    t = System.currentTimeMillis();
                    mPresenter.requetData(type, s.toString());
                }
                if (s.length() == 0) {
                    dynamicRecycler.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 搜索框被选中就隐藏hintlayout
        searchEdt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hintLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showData(List<OnlineBean.GroupBean> groupBeanList) {
        if (groupBeanList == null || groupBeanList.size() <= 0) {
            dynamicRecycler.setVisibility(View.GONE);
        }else {
            dynamicRecycler.setVisibility(View.VISIBLE);
            adapter.updateData(groupBeanList);
        }
    }


    private void fvb(View view) {
        searchEdt = view.findViewById(R.id.search_edt);
        hintLayout = view.findViewById(R.id.search_hint_layout);
        dynamicRecycler = view.findViewById(R.id.dynamic_recycler);
    }

    public String getType() {
        return type;
    }

    // 每次展示当前Fragment的时候，用于Activity回调此方法初始化搜索框样式
    public void initSearchEdt() {
        searchEdt.setText("");
        hintLayout.setVisibility(View.VISIBLE);
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new OnlineAdapter(getActivity());
        dynamicRecycler.setLayoutManager(layoutManager);
        dynamicRecycler.setAdapter(adapter);

        //分割线
        MyItemDecoration itemDecoration = new MyItemDecoration(getActivity(), MyItemDecoration.VERTICAL_LIST);
        itemDecoration.setDividerMode(MyItemDecoration.INSIDE);//最后一个item下没有分割线
        // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
        dynamicRecycler.addItemDecoration(itemDecoration);
    }

    public static OnlineFrg getInstance() {
        return new OnlineFrg();
    }

    public static OnlineFrg getInstance(String type) {
        return new OnlineFrg(type);
    }
}
