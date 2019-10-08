package com.learn.four.ipcsample.aidlBinderPool;

import android.os.RemoteException;

/**
 * Create on 2019/05/14
 *
 * @author Four
 * @description
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }

    /* 下面是 SecurityCenterImpl 的静态内部类单例模式实现 */
    private static class ImplHolder {
        private static final SecurityCenterImpl sInstance = new SecurityCenterImpl();
    }

    static SecurityCenterImpl getInstance() {
        return ImplHolder.sInstance;
    }

    private SecurityCenterImpl() {

    }
}
