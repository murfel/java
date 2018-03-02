package name.murfel.java.lazy;

import java.util.function.Supplier;

public class LazyFactory {
    private static class SupplierResult<T> {
        SupplierResult(T result) {
            this.result = result;
        }
        private T result;
    }

    public static <T> Lazy<T> createLazy(Supplier<T> supplier) {
        return new Lazy<T>() {
            private SupplierResult<T> result;

            @Override
            public T get() {
                if (result == null) {
                    result = new SupplierResult<>(supplier.get());
                }
                return result.result;
            }
        };
    }

    public static <T> Lazy<T> createConcurrentLazy(Supplier<T> supplier) {
        return new Lazy<T>() {
            private SupplierResult<T> result;

            @Override
            public T get() {
                if (result == null) {
                    result = new SupplierResult<>(supplier.get());
                }
                return result.result;
            }
        };
    }
}
