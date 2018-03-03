package name.murfel.java.lazy;

import java.util.function.Supplier;

/**
 * A wrapper for a singleton class, i.e. a class which has only one instance.
 *
 * This singleton class is lazy, meaning that the object is instantiated only when it was requested for the first time.
 *
 * This implementation provides two versions: for non-concurrent, and for concurrent use.
 */
public class LazyFactory {
    /**
     * Create a non-concurrent Lazy Singleton object.
     *
     * @param supplier1 provides a new instance (it is requested either never or once)
     * @param <T> the class which is modelled to be singleton
     * @return Lazy<T> object which represents lazy singleton
     */
    public static <T> Lazy<T> createSimpleLazy(Supplier<T> supplier1) {
        return new Lazy<T>() {
            private T result;
            private Supplier<T> supplier = supplier1;

            @Override
            public T get() {
                if (supplier != null) {
                    result = supplier.get();
                    supplier = null;
                }
                return result;
            }
        };
    }

    /**
     * Create a concurrent Lazy Singleton object.
     *
     * @param supplier1 provides a new instance (it is requested either never or once)
     * @param <T> the class which is modelled to be singleton
     * @return Lazy<T> object which represents lazy singleton
     */
    public static <T> Lazy<T> createConcurrentLazy(Supplier<T> supplier1) {
        return new Lazy<T>() {
            private T result;
            private volatile Supplier<T> supplier = supplier1;

            @Override
            public T get() {
                if (supplier != null) {
                    synchronized (this) {
                        if (supplier != null) {
                            result = supplier.get();
                            supplier = null;
                        }
                    }
                }
                return result;
            }
        };
    }
}

