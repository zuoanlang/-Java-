import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 抽象队列同步器
 * 1.state
 * 2.owner
 * 3.waiters
 */
public class YkAQS {
    //1.锁状态的拥有者，用于判断当前有没有锁
    volatile AtomicReference<Thread> owner = new AtomicReference<Thread>();
    //2.等待队列：保存正在等待的队列
    volatile LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<Thread>();

    // 3.资源状态
    volatile AtomicInteger state = new AtomicInteger(0);

    //资源争用
    public void acquire() {
        boolean addQ = true;
        while (!tryAcquire()) {
            if (addQ) {
                // 没拿到锁，加入到等待集合
                waiters.offer(Thread.currentThread());
                addQ = false;
            } else {
                // 阻塞当前的线程，不要继续再往下跑了
                LockSupport.park();
            }
        }
    }

    public boolean tryAcquire() {
        throw new UnsupportedOperationException();
    }

    public void release() {
        if (tryRelease()) {
            // 释放成功，通知等待者
            for (Thread next : waiters) {
                //唤醒
                LockSupport.unpark(next);
            }
        }
    }

    public boolean tryRelease() {
        throw new UnsupportedOperationException();
    }
}
