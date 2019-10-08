package com.learn.four.ipcsample.aidlBinderPool;

import android.os.RemoteException;

/**
 * Create on 2019/05/14
 *
 * @author Four
 * @description
 *
 * @Think 如果远程访问涉及到远程数据访问，而不是像本例情况这样：客户端提供输入数据然后由服务端运算后返回，
 * 是否或许会由于非单例导致不同客户端访问到的数据不一致。
 * 例如 IComputeImpl 中有一个int类型成员变量 a，初始值为0，客户端C1通过远程方法调用将其设置为1，
 * 此时如果客户端C1再通过远程方法访问a时，获取到的值应该是1，符合正常逻辑；
 * 现在假设客户端C2随后也通过远程方法访问这个成员变量，会不会由于不是单例而导致数据不一致呢？
 *
 * 为了验证这个想法，我在该Binder类中进行了上述测试：现在只需要修改 BinderPoolService 中的 queryBinder 方法，是否提供 ComputeImpl 单例
 * 然后通过 观察对比 MainActivity 和 BinderPoolActivity Log 输出的a的值
 */
public class ComputeImpl extends ICompute.Stub {
    private static volatile ComputeImpl sInstance;
    private int a = 0;

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

    @Override
    public synchronized int getInt_a() throws RemoteException {
        return a;
    }

    @Override
    public synchronized void setInt_a_Plus() throws RemoteException {
        a++;
    }

    /* ComputeImpl 的双重检测单例模式实现 */
    static ComputeImpl getInstance() {
        if (sInstance == null) {
            synchronized (ComputeImpl.class) {
                if (sInstance == null) {
                    sInstance = new ComputeImpl();
                }
            }
        }
        return sInstance;
    }

    ComputeImpl() {

    }
}
