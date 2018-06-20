package name.murfel.test3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A synchronization aid that allows one or more threads to wait until
 */
public class CountDownLatch {
    private final Lock counterLock = new ReentrantLock();
    private final Condition equalsZero = counterLock.newCondition();
    private final Condition isPositive = counterLock.newCondition();
    private long count;

    /**
     * Constructs a CountDownLatch initialized with the given count.
     *
     * @param count initial non-negative value of the count
     * @throws IllegalArgumentException if count is negative
     */
    public CountDownLatch(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count < 0");
        }
        this.count = count;
    }


    /**
     * Causes the current thread to wait until the latch has counted down to zero, unless the thread is interrupted.
     * If the current count is zero then this method returns immediately.
     * <p>
     * If the current count is greater than zero then the current thread becomes disabled for thread scheduling purposes
     * and lies dormant until one of two things happen:
     * <ul>
     * <li>The count reaches zero due to invocations of the {@code countDown()} method; or</li>
     * <li>Some other thread interrupts the current thread.</li>
     * <ul/>
     * <p>
     * If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or</li>
     * <li>is interrupted while waiting,</li>
     * </ul>
     * then {@code InterruptedException} is thrown and the current thread's interrupted status is cleared.
     *
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public void await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        counterLock.lock();
        while (count != 0) {  // in case a spurious wake-up occurs
            equalsZero.await();
        }
        counterLock.unlock();
    }


    /**
     * Causes the current thread to wait until the latch has counted down to zero, unless the thread is interrupted.
     * If the current count is zero then this method returns immediately.
     * <p>
     * If the current count is greater than zero then the current thread becomes disabled for thread scheduling purposes
     * and lies dormant until one of two things happen:
     * <ul>
     * <li>The count reaches zero due to invocations of the {@code countDown()} method; or<li/>
     * <li>Some other thread interrupts the current thread.<li/>
     * <ul/>
     * <p>
     * If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or<li/>
     * <li>is interrupted while waiting,<li/>
     * <ul/>
     * then {@code InterruptedException} is thrown and the current thread's interrupted status is cleared.
     *
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public void countDown() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        counterLock.lock();
        while (count == 0) {
            isPositive.await();
        }
        count--;
        if (count == 0) {
            equalsZero.signalAll();
        }
        counterLock.unlock();
    }

    /**
     * Increments the counter.
     * <p>
     * If the counter become equal one after being zero, wakes up one thread locked during the call
     * to the {@code countDown} method.
     */
    public void countUp() {
        counterLock.lock();
        count++;
        if (count == 1) {
            isPositive.signal();
        }
        counterLock.unlock();
    }

    /**
     * Returns the current count.
     * <p>
     * This method is typically used for debugging and testing purposes.
     *
     * @return the current count
     */
    public long getCount() {
        return count;
    }
}
