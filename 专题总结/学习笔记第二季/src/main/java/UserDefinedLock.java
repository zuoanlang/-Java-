import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * ����Lockʵ���Լ��Ķ�����
 */
public class UserDefinedLock implements Lock {

    public boolean tryLock() {
        return owner.compareAndSet(null,Thread.currentThread());
    }

    //1.��״̬��ӵ���ߣ������жϵ�ǰ��û����
    volatile AtomicReference<Thread> owner = new AtomicReference<Thread>();
    //2.�ȴ����У��������ڵȴ��Ķ���
    volatile LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<Thread>();
    public void lock() {
        boolean addQ = true;
        while (!tryLock()){
            if (addQ) {
                // û�õ��������뵽�ȴ�����
                waiters.offer(Thread.currentThread());
                addQ = false;
            } else {
                // ������ǰ���̣߳���Ҫ��������������
                LockSupport.park();
            }
        }
    }

    public void unlock() {
        //�ͷ�owner
        if (owner.compareAndSet(Thread.currentThread(),null)){
            // �ͷųɹ���֪ͨ�ȴ���
            Iterator<Thread> iterator = waiters.iterator();
            while (iterator.hasNext()) {
                Thread next = iterator.next();
                //����
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
