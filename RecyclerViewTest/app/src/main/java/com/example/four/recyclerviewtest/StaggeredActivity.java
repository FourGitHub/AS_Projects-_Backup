package com.example.four.recyclerviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StaggeredActivity extends AppCompatActivity {
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staggered_layout);
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList,R.layout.subitem_staggered_layout);
        recyclerView.setAdapter(adapter);
    }
    private void initFruits(){
        for (int i = 0; i < 2; i++) {
            Fruit smile = new Fruit(getRandomLengthName("微笑"), R.drawable.expression_1);
            fruitList.add(smile);
            Fruit weiqu = new Fruit(getRandomLengthName("委屈"), R.drawable.expression_2);
            fruitList.add(weiqu);
            Fruit se = new Fruit(getRandomLengthName("花痴"), R.drawable.expression_3);
            fruitList.add(se);
            Fruit zhengzha = new Fruit(getRandomLengthName("挣扎"), R.drawable.expression_4);
            fruitList.add(zhengzha);
            Fruit taoqi = new Fruit(getRandomLengthName("淘气"), R.drawable.expression_5);
            fruitList.add(taoqi);
            Fruit liulei = new Fruit(getRandomLengthName("流泪"), R.drawable.expression_6);
            fruitList.add(liulei);
            Fruit haixiu = new Fruit(getRandomLengthName("害羞"), R.drawable.expression_7);
            fruitList.add(haixiu);
            Fruit siluo = new Fruit(getRandomLengthName("失落"), R.drawable.expression_8);
            fruitList.add(siluo);
            Fruit nanguo = new Fruit(getRandomLengthName("难过"), R.drawable.expression_9);
            fruitList.add(nanguo);
            Fruit jiujie = new Fruit(getRandomLengthName("纠结"), R.drawable.expression_13);
            fruitList.add(jiujie);
            Fruit aoman = new Fruit(getRandomLengthName("傲慢"), R.drawable.expression_15);
            fruitList.add(aoman);
            Fruit zayan = new Fruit(getRandomLengthName("眨眼"), R.drawable.expression_17);
            fruitList.add(zayan);
            Fruit weixiao = new Fruit(getRandomLengthName("微笑"), R.drawable.expression_19);
            fruitList.add(weixiao);
            Fruit tongyi = new Fruit(getRandomLengthName("开心"), R.drawable.expression_20);
            fruitList.add(tongyi);
            Fruit zhishi = new Fruit(getRandomLengthName("知识"), R.drawable.expression_21);
            fruitList.add(zhishi);
            Fruit menguan = new Fruit(getRandomLengthName("懵圈儿"), R.drawable.expression_25);
            fruitList.add(menguan);
            Fruit feiwen = new Fruit(getRandomLengthName("爱你"), R.drawable.expression_26);
            fruitList.add(feiwen);
            Fruit xiaoku = new Fruit(getRandomLengthName("笑哭"), R.drawable.expression_27);
            fruitList.add(xiaoku);
            Fruit anjing = new Fruit(getRandomLengthName("闭嘴"), R.drawable.expression_29);
            fruitList.add(anjing);
            Fruit ganga = new Fruit(getRandomLengthName("尴尬"), R.drawable.expression_33);
            fruitList.add(ganga);
            Fruit buman = new Fruit(getRandomLengthName("不满"), R.drawable.expression_35);
            fruitList.add(buman);
            Fruit tiaodou = new Fruit(getRandomLengthName("挑逗"), R.drawable.expression_36);
            fruitList.add(tiaodou);
        }
    }

    private static String getRandomLengthName(String Name){
        Random random  = new Random();
        StringBuilder name = new StringBuilder();
        int i = random.nextInt(20)+1;
        for(int j = 0; j<i; j++){
            name.append(Name);
        }
        return name.toString();
    }

}
