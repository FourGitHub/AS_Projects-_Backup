package com.example.four.fragmentbestpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class NewsTitleFragment extends Fragment {
    boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);// 实例化标题碎片的布局，得到view
        RecyclerView newsTitleRecyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);// 通过view得到布局中的RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());// 默认的垂直布局
        newsTitleRecyclerView.setLayoutManager(layoutManager);// 指定Recycler的布局方式
        NewsAdapter adapter = new NewsAdapter(getNews());// 创建适配器，通过getNews()得到待匹配资源
        newsTitleRecyclerView.setAdapter(adapter);// 传入适配器
        return view;// 这里返回的是碎片的view，由于RecyclerView在碎片中，先将它给适配好，再返回碎片的布局
    }

    public List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            News news = new News("This is news title " + i, getRandomLengthContent("This is news title " + i + "."));
            newsList.add(news);
        }
        return newsList;
    }

    public String getRandomLengthContent(String content) {
        Random random = new Random();
        int x = random.nextInt(20) + 1;
        StringBuilder Content = new StringBuilder();
        for (int i = 0; i < x; i++) {
            Content.append(content);
        }
        return Content.toString();
    }

    @Override
    //    在确保与碎片相关联的活动一定已经创建完毕的时候调用
    // 与碎片关联的活动是MainActivity,所以在此时判断MainActivity中是否有新闻的内容布局（由最小宽度限定符决定系统加载哪一个activity_main.xml）
    // 无论怎样，显示标题的左侧碎片是需要显示的，所以当确保与碎片相关联的活动被创建时就能够判断此时活动中是否有显示内容的碎片。
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) == null) {
            isTwoPane = false;
        } else {
            isTwoPane = true;
        }
    }

    // 自定义适配器继承自 Recycler.Adapter,泛型参数限制为<......>
    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<News> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;

            public ViewHolder(View view) {
                super(view);// 在写这个函数的时候一定记得super();
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
            }
        }

        public NewsAdapter(List<News> newsList) {
            mNewsList = newsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 实例化RecyclerView的子项布局，并添加到RecyclerView中
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            // 这里非常重要！！！
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if (isTwoPane) {// 如果是双页模式，则刷新NewsContentView中的内容（这个时候NewsContentActivity没有用处，总的来说就是为了给程序增加兼容性）
                        NewsContentFragment newsContentFragment = (NewsContentFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(), news.getContent());
                    } else {// 如果是单页模式，则启动NewsContentActivity
                        NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }
}



