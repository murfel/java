package name.murfel.java.pool;

import java.util.function.Function;

/**
 * A Future represents the result of an asynchronous computation. Methods are provided to check
 * if the computation is complete, to wait for its completion and retrieve the result of the computation,
 * and to create a new asynchronous computation as a function of the result of this one.
 * The result can only be retrieved using method get when the computation has completed, blocking if necessary
 * until it is ready.
 *
 * This interface is intended to be implemented as a nested class inside of a thread pool.
 *
 * @param <T>
 */
public interface LightFuture<T> {
    /**
     * Returns true if this task completed.
     *
     * @return true if this task completed
     */
    boolean isReady();

    /**
     * Waits if necessary for the computation to complete, and then retrieves its result.
     *
     * @return the computed result
     * @throws LightExecutionException if the computation threw an exception
     */
    T get() throws LightExecutionException;

    /**
     * Creates a new computation which is an application of the function to the result of this computation.
     * It also adds the new computation to the thread pool.
     *
     * @param function a function applied to the result which constitutes a new computation
     * @param <U> the result of the function
     * @return a new computation which is already added to the thread pool
     */
    <U> LightFuture<U> thenApply(Function<T, U> function);
}
