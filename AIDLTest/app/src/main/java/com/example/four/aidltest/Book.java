package com.example.four.aidltest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/5/20 0020.
 */

public class Book implements Parcelable {
    private int id;
    private String name;

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {

        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel source) {
        this.id = source.readInt();
        this.name = source.readString();
    }

    /**
     * 一定要重写此方法，否则MainActivity在调用getBookList()方法获取到list后
     * 会调用 list.toString() 输出list中图书信息，如果不重写就会输出地址
     */
    @Override
    public String toString() {
        return "Id:"+getId() + "   Name:" + getName() + "\n";
    }
}
