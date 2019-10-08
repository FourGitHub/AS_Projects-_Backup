package com.example.four.aidltest;

import com.example.four.aidltest.Book;
import com.example.four.aidltest.IOnNewBookArrivedListener;

interface BookManager {
    void addBook(in Book book);
    List<Book> getBookList();
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);

}
