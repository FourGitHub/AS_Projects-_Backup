package com.example.four.materialtest;

/**
 * Created by Administrator on 2018/1/13 0013.
 */

public class Fruit {
    private String fruitName;
    private int imageId;

    public Fruit(String fruitName, int iamgeId)
    {
        this.fruitName = fruitName;
        this.imageId = iamgeId;
    }
    public String getFruitName() {
        return fruitName;
    }

    public int getImageId() {
        return imageId;
    }

}
