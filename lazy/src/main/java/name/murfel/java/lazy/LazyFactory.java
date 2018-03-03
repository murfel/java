package name.murfel.java.lazy;

import java.util.function.Supplier;

public class LazyFactory {
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

