// IOnNewBookArrivedListener.aidl
package com.learn.four.ipcsample.aidl;

// Declare any non-default types here with import statements
import com.learn.four.ipcsample.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}

