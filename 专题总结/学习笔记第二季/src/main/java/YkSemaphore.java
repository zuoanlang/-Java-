public class YkSemaphore {

    YkAQS aqs = new YkAQS() {

        @Override
        public int tryAcquireShared() {
            int count = getState().get();
            int n = count - 1;
            if (count <= 0 || n < 0) {
                return -1;
            }
            getState().compareAndSet(count, n);
            return getState().decrementAndGet();
        }

        @Override
        public boolean tryReleaseShared() {
            return getState().incrementAndGet() >= 0;
        }
    };

    /**
     * 许可数量
     *
     * @param count 初始化数量
     */
    public YkSemaphore(int count) {
        aqs.getState().set(count);
    }

    public void acquire() {
        aqs.acquireShared();
    }

    public void release() {
        aqs.releaseShared();
    }
}
