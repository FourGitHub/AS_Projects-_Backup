package com.example.four.materialtest;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/28 0028.
 */

public class Person implements Parcelable {
    private String name;
    private String sex;
    private String single;

    private ArrayList<String> tags = new ArrayList<>();
    private Book book;
    private ArrayList<Book> books = new ArrayList<>();
    private static final String TAG = "Book";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public static String getTAG() {
        return TAG;
    }

    /**
     * 描述包含在这个Parcelable实例的封送表示中的特殊对象的种类。 例如，如果对象将在writeToParcel（Parcel，int）的输出中包含文件描述符，则此方法的返回值必须包含CONTENTS_FILE_DESCRIPTOR位。
     *
     * @return 一个位掩码，指示由此Parcelable对象实例编组的特殊对象类型集合。
     */
    @Override
    public int describeContents() {
        Log.i(TAG, "执行：describeContents()");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i(TAG, "执行：writeToParcel()");
        dest.writeString(name);
        dest.writeString(sex);
        dest.writeString(single);

        // 序列化一个String集合
        dest.writeStringList(tags);

        // 序列化一个实现了Parcelable接口的对象，需要添加一个flag,不过一般为0
        dest.writeParcelable(book, 0);

        // 序列化一个实现了Parcelabel接口的对象的集合，有两种方法，

        //第一种：把类的信息和数据都写入Parcel，在反序列化时使用对应的类装载器重新构造类的实例.因此效率不高
        dest.writeList(books);

        //第二种：不会写入类的信息，取而代之的是：
        // 读取时必须能知道数据属于哪个类，并传入正确的Parcelable.Creator来创建对象(因为这个类也实现了Parcelable接口)而不是直接构造新对象。
        // 更加高效的读写单个Parcelable对象的方法是：
        // 直接调用Parcelable.writeToParcel()和Parcelable.Creator.createFromParcel()
        dest.writeTypedList(books);

    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

        @Override
        public Person createFromParcel(Parcel source) {
            Log.i(TAG, "执行：createFromParcel()");

            Person person = new Person();
            person.name = source.readString();
            person.sex = source.readString();
            person.single = source.readString();
            person.tags = source.createStringArrayList();

            // 读取对象需要提供一个类加载器去读取,因为写入的时候写入了类的相关信息
            person.book = source.readParcelable(Book.class.getClassLoader());

            //读取集合对应之前的序列化，也分为两类:
            //第一类需要向方法中传入相应的类加载器去构造新的类; 对应writeList
            source.readList(person.books, Book.class.getClassLoader());

            //第二类需要使用类的CREATOR去获取; 对应writeTypeList
            // 下面两个方法的结果都是一样的，二选一就行了
            // 第一个是把person.books 传递进去，然后反序列化的结果会存在里面,返回void
            // 第二个方法是返回一个ArrayList<T>
            source.readTypedList(person.books, Book.CREATOR);
            person.books = source.createTypedArrayList(Book.CREATOR);

            //获取类加载器主要有几种方式
            getClass().getClassLoader();
            Thread.currentThread().getContextClassLoader();
            Book.class.getClassLoader();

            return person;
        }

        @Override
        public Person[] newArray(int size) {
            Log.i(TAG, "执行：newArray()");
            return new Person[size];
        }
    };


}
