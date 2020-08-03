import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * �������ͬ����
 * 1.state
 * 2.owner
 * 3.waiters
 */
public class YkAQS {
    //1.��״̬��ӵ���ߣ������жϵ�ǰ��û����
    volatile AtomicReference<Thread> owner = new AtomicReference<Thread>();
    //2.�ȴ����У��������ڵȴ��Ķ���
    volatile LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<Thread>();

    // 3.��Դ״̬
    volatile AtomicInteger state = new AtomicInteger(0);

    //��Դ����
    public void acquire() {
        boolean addQ = true;
        while (!tryAcquire()) {
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

    public boolean tryAcquire() {
        throw new UnsupportedOperationException();
    }

    public void release() {
        if (tryRelease()) {
            // �ͷųɹ���֪ͨ�ȴ���
            for (Thread next : waiters) {
                //����
                LockSupport.unpark(next);
            }
        }
    }

    public boolean tryRelease() {
        throw new UnsupportedOperationException();
    }
}
