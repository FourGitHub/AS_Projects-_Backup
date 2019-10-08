package com.example.four.mybestfragment;

/**
 * Created by Administrator on 2017/11/5 0005.
 */

public class News {
    private String newsTitle;
    private String newsContent;

    public void setNewsTitle(String title){
        newsTitle = title;
    }

    public void setNewsContent(String content){
        newsContent = content;
    }

    public String getNewsTitle(){
        return newsTitle;
    }

    public String getNewsContent(){
        return newsContent;
    }
}

