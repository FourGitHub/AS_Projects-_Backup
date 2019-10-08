package com.example.four.bestfragmetpractice;

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
 * Created by Administrator on 2017/11/5 0005.
 */

public class NewsTitleFragment extends Fragment {

    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_title_frag, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(getNewsList());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public List<News> getNewsList() {
        List<News> newsList = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            News news = new News();
            news.setNewsTitle("This is news " + i + ".");
            news.setNewsContent(getRandomLengthContent("This is news " + i + "."));
            newsList.add(news);
        }
        return newsList;
    }

    public String getRandomLengthContent(String content) {
        StringBuilder newsContent = new StringBuilder();
        Random random = new Random();
        int n = random.nextInt(20) + 1;
        for (int i = 0; i < n; i++) {
            newsContent.append(content);
        }
        return newsContent.toString();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.news_content_fragment) == null)
            isTwoPane = false;
        else
            isTwoPane = true;
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<News> mNewsList;

        public NewsAdapter(List<News> newsList) {
            mNewsList = newsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_title_subitem, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getNewsTitle(), news.getNewsContent());
                    } else {
                        NewsContentActivity.actionStart(getActivity(), news.getNewsTitle(), news.getNewsContent());
                    }
                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.newsTitleItem.setText(news.getNewsTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView newsTitleItem;

            public ViewHolder(View view) {
                super(view);
                newsTitleItem = (TextView) view.findViewById(R.id.news_title_subitem_text);
            }
        }
    }
}
