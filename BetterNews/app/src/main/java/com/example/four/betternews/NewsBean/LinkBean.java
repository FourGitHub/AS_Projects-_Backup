package com.example.four.betternews.NewsBean;

/**
 * Created by Administrator on 2018/4/16 0016.
 * 保存新闻的链接，在展示界面"SDisplayActivity"中用WebView控件显示
 */

public class LinkBean {
    /**
     * type : doc
     * url : http://api.iclient.ifeng.com/api_vampire_article_detail?aid=sub_55057276
     */

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
