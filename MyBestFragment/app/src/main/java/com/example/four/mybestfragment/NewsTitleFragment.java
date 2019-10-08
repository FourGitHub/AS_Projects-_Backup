package com.example.four.mybestfragment;

import android.os.Bundle;
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
 * Created by Administrator on 2017/11/5 0005.
 */

public class NewsTitleFragment extends Fragment {

    private boolean isTwoPane;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(getNewsList());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public List<News> getNewsList() {
        List<News> newsList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            News news = new News();
            news.setNewsTitle("This is title " + i);
            news.setNewsContent(getRandomLengthContent("This is title " + i + "."));
            newsList.add(news);
        }
        return newsList;
    }

    public String getRandomLengthContent(String content) {
        Random random = new Random();
        int n = random.nextInt(20) + 1;
        StringBuilder Content = new StringBuilder();
        for (int i = 0; i < n; i++) {
            Content.append(content);
        }
        return Content.toString();
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) == null)
            isTwoPane = false;
        else
            isTwoPane = true;
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<News> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titleText;

            public ViewHolder(View view) {
                super(view);
                titleText = (TextView) view.findViewById(R.id.news_title_text_view_subitem);
            }
        }

        public NewsAdapter(List<News> newsList) {
            mNewsList = newsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 实例化RecyclerView的子项布局，并添加到RecyclerView中
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_title_subitem, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            // 这里非常重要！！！
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if (isTwoPane) {// 如果是双页模式，则刷新NewsContentView中的内容
                        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getNewsTitle(), news.getNewsContent());
                    } else {// 如果是单页模式，则启动NewsContentActivity
                        NewsContentActivity.actionStart(getActivity(), news.getNewsTitle(), news.getNewsContent());
//                        Log.d(TAG, "onClick:啊哈哈哈哈哈啊哈");
                    }
                }
            });
            return holder;
        }

        private static final String TAG = "NewsAdapter";

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.titleText.setText(news.getNewsTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }
}
