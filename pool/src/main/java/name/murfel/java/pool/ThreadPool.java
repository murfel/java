package name.murfel.java.pool;

import java.util.function.Function;
import java.util.function.Supplier;

public class ThreadPool {
    private Thread[] threads;

    ThreadPool(int threadCount) {
        threads = new Thread[threadCount];
    }

    private class Future<T> implements LightFuture<T> {
        private Supplier<T> supplier;

        public Future(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public T get() throws LightExecutionException {
            return null;
        }

        @Override
        public <U> LightFuture<U> thenApply(Function<T, U> function) {
            return null;
        }
    }

}
