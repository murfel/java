package name.murfel.java.lazy;

/**
 * An interface representing an object getter for implementing Singleton Lazy pattern.
 *
 * @param <T> type of stored object
 */
public interface Lazy<T> {
    T get();
}
