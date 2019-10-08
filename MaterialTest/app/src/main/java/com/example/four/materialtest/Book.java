package com.example.four.materialtest;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class Book implements Parcelable {
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /* 绝大多数情况下直接返回0 */
    @Override
    public int describeContents() {
        return 0;
    }

    /* 序列化 */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    /* 反序列化 */
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public Book() {
    }

    protected Book(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
    }
}
