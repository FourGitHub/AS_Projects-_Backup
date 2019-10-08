// IMyAidlInterface.aidl
package com.example.four.materialtest;
import com.example.four.materialtest.Book;

interface IMyAidlInterface {
    List<Book> getBookList();
    void addBook(in Book book);
}
