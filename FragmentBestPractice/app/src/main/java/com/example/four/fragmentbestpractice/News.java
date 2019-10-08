package com.example.four.fragmentbestpractice;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class News {
    private String title;
    private String content;

    public News(String title, String content) {
        this.content = content;
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

}
