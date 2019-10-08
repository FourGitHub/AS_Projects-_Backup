// ISecurityCenter.aidl
package com.learn.four.ipcsample.aidlBinderPool;

interface ISecurityCenter {
    String encrypt(in String content);
    String decrypt(in String password);
}

