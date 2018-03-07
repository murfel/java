package name.murfel.java.lazy;

import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LazyFactoryTest {
    @Test
    public void simpleLazySimpleTest() {
        CountingSupplier<Integer> supplier = new CountingSupplier<Integer>() {
            @Override
            public Integer get() {
                return callCounter++;
            }
        };

        Lazy<Integer> simpleLazy = LazyFactory.createSimpleLazy(supplier);
        assertEquals(0, supplier.getCallCounter());
        assertEquals(new Integer(0), simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
        assertEquals(new Integer(0), simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
        assertEquals(new Integer(0), simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
    }

    @Test
    public void simpleLazyReturningNull() {
        CountingSupplier<Integer> supplier = new CountingSupplier<Integer>() {
            @Override
            public Integer get() {
                callCounter++;
                return null;
            }
        };

        Lazy<Integer> simpleLazy = LazyFactory.createSimpleLazy(supplier);
        assertEquals(0, supplier.getCallCounter());
        assertNull(simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
        assertNull(simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
        assertNull(simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
    }

    @Test
    public void concurrentLazySimpleTest() {
        CountingSupplier<Integer> supplier = new CountingSupplier<Integer>() {
            @Override
            public Integer get() {
                assertEquals(0, callCounter);
                return callCounter++;
            }
        };

        Lazy<Integer> simpleLazy = LazyFactory.createConcurrentLazy(supplier);
        assertEquals(0, supplier.getCallCounter());
        assertEquals(new Integer(0), simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
        assertEquals(new Integer(0), simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
        assertEquals(new Integer(0), simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
    }

    @Test
    public void concurrentLazyReturningNull() {
        CountingSupplier<Integer> supplier = new CountingSupplier<Integer>() {
            @Override
            public Integer get() {
                assertEquals(0, callCounter);
                callCounter++;
                return null;
            }
        };

        Lazy<Integer> simpleLazy = LazyFactory.createConcurrentLazy(supplier);
        assertEquals(0, supplier.getCallCounter());
        assertNull(simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
        assertNull(simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
        assertNull(simpleLazy.get());
        assertEquals(1, supplier.getCallCounter());
    }

    @Test
    public void concurrentLazyManyThreads() throws InterruptedException {
        CountingSupplier<Integer> supplier = new CountingSupplier<Integer>() {
            @Override
            public Integer get() {
                assertEquals(0, callCounter);
                return callCounter++;
            }
        };

        Lazy<Integer> concurrentLazy = LazyFactory.createConcurrentLazy(supplier);

        Runnable runnable = concurrentLazy::get;

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(runnable);
        }

        assertEquals(0, supplier.getCallCounter());
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        assertEquals(1, supplier.getCallCounter());
    }

    private abstract class CountingSupplier<T> implements Supplier<T> {
        protected int callCounter = 0;

        public int getCallCounter() {
            return callCounter;
        }
    }

}





























































