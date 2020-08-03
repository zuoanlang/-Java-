import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *
 */
public class YkLock implements Lock {

    //抽象工具类AQS
    YkAQS aqs= new YkAQS(){
        @Override
        public boolean tryAcquire() {
            return super.tryAcquire();
        }

        @Override
        public boolean tryRelease() {
            return super.tryRelease();
        }
    };

    public void lock() {

    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {

    }

    public Condition newCondition() {
        return null;
    }
}
