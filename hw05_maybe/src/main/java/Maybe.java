import com.sun.istack.internal.NotNull;

import java.util.function.Function;

/**
 * A wrapper for a data type.
 *
 * @param <T>  the type of stored data
 */
public class Maybe<T> {
    private boolean isPresent;
    private T data;

    private Maybe() {
    }

    private Maybe(T t) {
        data = t;
        isPresent = true;
    }

    /**
     * Instantiate a new Maybe object with t as data.
     *
     * @param t  data to store
     * @return new Maybe object wrapping t
     */
    @NotNull
    public static <T> Maybe<T> just(T t) {
        return new Maybe<>(t);
    }

    /**
     * Instantiate a new empty Maybe object.
     *
     * @return new empty Maybe object
     */
    public static <T> Maybe<T> nothing() {
        return new Maybe<>();
    }

    /**
     * Get the data stored in Maybe.
     *
     * @return the data stored in Maybe
     * @throws MaybeException if no data is present in this
     */
    public T get() throws MaybeException {
        if (!isPresent) {
            throw new MaybeException();
        }
        return data;
    }


    /**
     * Check the presents of data.
     *
     * @return  true if there are data, false otherwise
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * Apply mapper to the data stored in Maybe. If there are no data, i.e. not isPresent(), return a new empty Maybe object.
     *
     * @param mapper
     * @param <U>
     * @return new Maybe object storing the result of mapper application to data stored in this
     * or new empty Maybe object if there are no data in this.
     */
    public <U> Maybe<U> map(Function<? super T, ? extends U> mapper) {
        if (isPresent) {
            return new Maybe<U>(mapper.apply(data));
        }
        return new Maybe<>();
    }
}
