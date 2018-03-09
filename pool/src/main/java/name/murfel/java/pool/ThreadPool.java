package name.murfel.java.pool;

import com.sun.istack.internal.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Creates a thread pool that reuses a fixed number of threads operating off a shared unbounded queue.
 * At any point, at most nThreads threads will be active processing tasks. If additional tasks are
 * submitted when all threads are active, they will wait in the queue until a thread is available.
 * The threads exist and execute tasks until the shutdown method is called. In such a case all tasks
 * which are already taken out of the queue continue to be executed but no new tasks will be taken out
 * of the queue ever.
 */
public class ThreadPool {
    private final LinkedBlockingQueue<LightFuture> queue = new LinkedBlockingQueue<>();
    private List<PoolThread> threads = new LinkedList<>();

    /**
     * Instantiate a thread pool with the fixed specified number of threads.
     *
     * @param nThreads the number of threads
     */
    ThreadPool(int nThreads) {
        for (int i = 0; i < nThreads; i++) {
            threads.add(new PoolThread());
        }
    }

    /**
     * Add tasks represented as a supplier to the thread pool. Once there will be an unoccupied thread,
     * it will execute the task.
     *
     * @param supplier represents the task
     * @param <T> the type of the task result
     * @return a LightFuture interface representing the task in the thread pool
     */
    @NotNull
    public <T> LightFuture<T> addTask(@NotNull Supplier<T> supplier) {
        LightFutureImpl<T> lightFutureImpl = new LightFutureImpl<>(supplier);
        synchronized (queue) {
            queue.add(lightFutureImpl);
        }
        return lightFutureImpl;
    }

    /**
     * Ask all the threads to terminate as soon as possible. No new tasks are picked from queue anymore at this point
     * but the threads will execute their current tasks until they finish.
     *
     * The tasks which will be left in the queue will have {@code isReady() == False}
     * until someone explicitly calls their {@code get()}.
     */
    void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    private class LightFutureImpl<T> implements LightFuture<T> {
        private Supplier<T> supplier;
        private T result;

        LightFutureImpl(@NotNull Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public boolean isReady() {
            return supplier == null;
        }

        @Override
        public T get() throws LightExecutionException {
            if (!isReady()) {
                try {
                    result = supplier.get();
                    supplier = null;
                } catch (RuntimeException e) {
                    throw new LightExecutionException(e);
                }
            }
            return result;
        }

        @Override
        public <U> LightFuture<U> thenApply(@NotNull Function<T, U> function) {
            LightFutureImpl<U> lightFutureImpl = new LightFutureImpl<>(() -> {
                T result;
                try {
                    result = LightFutureImpl.this.get();
                } catch (LightExecutionException e) {
                    throw new RuntimeException("Cannot get result to apply function to");
                }
                return function.apply(result);
            });
            synchronized (queue) {
                queue.add(lightFutureImpl);
            }
            return lightFutureImpl;
        }
    }

    private class PoolThread extends Thread {
        /**
         * Run forever until interrupted (e.g. by calling {@code shutdown()} on the thread pool),
         * pick a task from queue, execute it, repeat.
         *
         * If a thread gets interrupted, it first finished the task which was already taken out of the queue
         * and only then terminates.
         */
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                LightFuture lightFuture;
                synchronized (queue) {
                    try {
                        lightFuture = queue.take();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                try {
                    lightFuture.get();
                } catch (LightExecutionException e) {
                    throw new RuntimeException("Cannot execute task");
                }
            }
        }
    }

}
