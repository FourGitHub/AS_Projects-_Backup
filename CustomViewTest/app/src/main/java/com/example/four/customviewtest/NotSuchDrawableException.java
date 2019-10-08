package com.example.four.customviewtest;

/**
 * Created by Administrator on 2018/4/6 0006.
 */

public class NotSuchDrawableException extends RuntimeException {
    public NotSuchDrawableException(){

    }

    public NotSuchDrawableException(String msg) {
        super(msg);
    }

}
