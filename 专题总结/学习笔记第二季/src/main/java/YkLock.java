import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *
 */
public class YkLock implements Lock {

    //抽象工具类AQS
    YkAQS aqs = new YkAQS() {
        @Override
        public boolean tryAcquire() {
            return owner.compareAndSet(null, Thread.currentThread());
        }

        @Override
        public boolean tryRelease() {
            return owner.compareAndSet(Thread.currentThread(), null);
        }
    };

    public void lock() {
        aqs.acquire();
    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        return aqs.tryAcquire();
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {
        aqs.release();
    }

    public Condition newCondition() {
        return null;
    }
}
