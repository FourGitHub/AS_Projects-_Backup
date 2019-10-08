package com.example.four.listviewtest;

/**
 * Created by Administrator on 2017/10/27 0027.
 * 自定义适配类型
 */

public class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return imageId;
    }
}
