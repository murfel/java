package name.murfel.java.lazy;

import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;

public class LazyFactoryTest {

    @Test
    public void simpleLazy() {
        Supplier<Integer> supplier = new Supplier<Integer>() {
            private int callCounter = 0;

            @Override
            public Integer get() {
                return callCounter++;
            }
        };

        Lazy<Integer> lazy = LazyFactory.createLazy(supplier);
        assertEquals(new Integer(0), lazy.get());
        assertEquals(new Integer(0), lazy.get());
        assertEquals(new Integer(0), lazy.get());
    }

    @Test
    public void simpleConcurrentLazy() {
        Supplier<Integer> supplier = new Supplier<Integer>() {
            private int callCounter = 0;

            @Override
            public Integer get() {
                return callCounter++;
            }
        };

        Lazy<Integer> lazy = LazyFactory.createLazy(supplier);

        Lazy<Integer> stupidLazy = LazyFactory.createLazy(supplier);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lazy.get();
            }
        };



        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();


    }

}





























































