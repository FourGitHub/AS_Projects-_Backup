package com.mredrock.cyxbs.freshman.UI.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.mredrock.cyxbs.freshman.Adapter.OnlineAdapter;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private EditText searchEdt;
    private RelativeLayout hintLayout;
    private RecyclerView dynamicRecycler;
    private List<?> resultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshman_h_college_group_frg);

        resultList = new ArrayList<>();

        searchEdt = findViewById(R.id.search_edt);
        hintLayout = findViewById(R.id.search_hint_layout);
        dynamicRecycler = findViewById(R.id.dynamic_recycler);

        // 搜索框被选中就隐藏hintlayout
        searchEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hintLayout.setVisibility(View.GONE);
                }
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        OnlineAdapter adapter = new OnlineAdapter(this);
        dynamicRecycler.setLayoutManager(layoutManager);
        dynamicRecycler.setAdapter(adapter);

        //...
        //分割线
        MyItemDecoration itemDecoration = new MyItemDecoration(this, MyItemDecoration.VERTICAL_LIST);
        itemDecoration.setDividerMode(MyItemDecoration.INSIDE);//最后一个item下没有分割线
        // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
        dynamicRecycler.addItemDecoration(itemDecoration);
    }

}
