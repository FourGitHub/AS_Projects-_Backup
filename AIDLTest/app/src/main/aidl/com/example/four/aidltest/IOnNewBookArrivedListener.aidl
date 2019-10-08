// IOnNewBookArrivedListener.aidl
package com.example.four.aidltest;
import com.example.four.aidltest.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
