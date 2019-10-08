// IBookManager.aidl
package com.learn.four.ipcsample.aidl;

// Declare any non-default types here with import statements
import com.learn.four.ipcsample.aidl.Book;
import com.learn.four.ipcsample.aidl.IOnNewBookArrivedListener;

interface IBookManager {
   void addBook(in Book book);
   List<Book> getBookList();
   void registerListener(in IOnNewBookArrivedListener listener);
   void unregisterListener(in IOnNewBookArrivedListener listener);
}

