package com.example.four.recyclerviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HorizontalActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_layout);
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList,R.layout.subitem_horizontal_layout);
        recyclerView.setAdapter(adapter);
    }
    private void initFruits(){
        for (int i = 0; i < 2; i++) {
            Fruit smile = new Fruit("微笑", R.drawable.expression_1);
            fruitList.add(smile);
            Fruit weiqu = new Fruit("委屈", R.drawable.expression_2);
            fruitList.add(weiqu);
            Fruit se = new Fruit("花痴", R.drawable.expression_3);
            fruitList.add(se);
            Fruit zhengzha = new Fruit("挣扎", R.drawable.expression_4);
            fruitList.add(zhengzha);
            Fruit taoqi = new Fruit("淘气", R.drawable.expression_5);
            fruitList.add(taoqi);
            Fruit liulei = new Fruit("流泪", R.drawable.expression_6);
            fruitList.add(liulei);
            Fruit haixiu = new Fruit("害羞", R.drawable.expression_7);
            fruitList.add(haixiu);
            Fruit siluo = new Fruit("失落", R.drawable.expression_8);
            fruitList.add(siluo);
            Fruit nanguo = new Fruit("难过", R.drawable.expression_9);
            fruitList.add(nanguo);
            Fruit jiujie = new Fruit("纠结", R.drawable.expression_13);
            fruitList.add(jiujie);
            Fruit aoman = new Fruit("傲慢", R.drawable.expression_15);
            fruitList.add(aoman);
            Fruit zayan = new Fruit("眨眼", R.drawable.expression_17);
            fruitList.add(zayan);
            Fruit weixiao = new Fruit("微笑", R.drawable.expression_19);
            fruitList.add(weixiao);
            Fruit tongyi = new Fruit("赞成", R.drawable.expression_20);
            fruitList.add(tongyi);
            Fruit zhishi = new Fruit("知识", R.drawable.expression_21);
            fruitList.add(zhishi);
            Fruit menguan = new Fruit("懵圈儿", R.drawable.expression_25);
            fruitList.add(menguan);
            Fruit feiwen = new Fruit("飞吻", R.drawable.expression_26);
            fruitList.add(feiwen);
            Fruit xiaoku = new Fruit("笑哭", R.drawable.expression_27);
            fruitList.add(xiaoku);
            Fruit anjing = new Fruit("安静", R.drawable.expression_29);
            fruitList.add(anjing);
            Fruit ganga = new Fruit("尴尬", R.drawable.expression_33);
            fruitList.add(ganga);
            Fruit buman = new Fruit("不满", R.drawable.expression_35);
            fruitList.add(buman);
            Fruit tiaodou = new Fruit("挑逗", R.drawable.expression_36);
            fruitList.add(tiaodou);
        }
    }
}
