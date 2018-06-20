package name.murfel.test3;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountDownLatchTest {

    @Test
    public void countUpAndDown() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(0);
        assertEquals(0, latch.getCount());
        latch.countUp();
        assertEquals(1, latch.getCount());
        latch.countUp();
        assertEquals(2, latch.getCount());
        latch.countDown();
        assertEquals(1, latch.getCount());
        latch.countDown();
        assertEquals(0, latch.getCount());
    }

    @Test
    public void initilizeCounter() {
        CountDownLatch latch = new CountDownLatch(13);
        assertEquals(13, latch.getCount());
    }

    @Test
    public void zeroCounterSimple() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(0);
        latch.await();
        assertEquals(0, latch.getCount());
    }

    @Test
    public void zeroCounterAwaitSeveralTimes() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(0);
        latch.await();
        latch.await();
        latch.await();
        assertEquals(0, latch.getCount());
    }

    @Test
    public void counterOneSimple() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        boolean[] interruptedExceptionOccurred = {false};
        Thread thread = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                interruptedExceptionOccurred[0] = true;
            }
        });
        thread.start();
        latch.countDown();

        thread.join();
        assertFalse(interruptedExceptionOccurred[0]);
        assertEquals(0, latch.getCount());
    }

    @Test
    public void counterZeroBlockOnCountDownSimple() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(0);
        boolean[] interruptedExceptionOccurred = {false};
        Thread thread = new Thread(() -> {
            try {
                latch.countDown();
            } catch (InterruptedException e) {
                interruptedExceptionOccurred[0] = true;
            }
        });
        thread.start();
        Thread.sleep(200);
        latch.countUp();

        thread.join();
        assertFalse(interruptedExceptionOccurred[0]);
        assertEquals(0, latch.getCount());
    }
}