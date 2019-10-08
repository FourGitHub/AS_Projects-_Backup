package com.example.four.betternews.NewsBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16 0016.
 * 这个Bean保存图片URL
 */

public class StyleBean {

    private List<String> images;

    // 如果不为空的话 加载一张就行了
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
