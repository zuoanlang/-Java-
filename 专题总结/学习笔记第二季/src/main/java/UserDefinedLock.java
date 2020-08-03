import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 基于Lock实现自己的独享锁
 */
public class UserDefinedLock implements Lock {

    public boolean tryLock() {
        return owner.compareAndSet(null,Thread.currentThread());
    }

    //1.锁状态的拥有者，用于判断当前有没有锁
    volatile AtomicReference<Thread> owner = new AtomicReference<Thread>();
    //2.等待队列：保存正在等待的队列
    volatile LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<Thread>();
    public void lock() {
        boolean addQ = true;
        while (!tryLock()){
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

    public void unlock() {
        //释放owner
        if (owner.compareAndSet(Thread.currentThread(),null)){
            // 释放成功，通知等待者
            Iterator<Thread> iterator = waiters.iterator();
            while (iterator.hasNext()) {
                Thread next = iterator.next();
                //唤醒
                LockSupport.unpark(next);
            }
        }
    }
    public void lockInterruptibly() throws InterruptedException {

    }



    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    public Condition newCondition() {
        return null;
    }
}
