/**
 * Represents a function that accepts one argument and produces a result.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
public abstract class Function1<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    public abstract R apply(T t);

    /**
     * Returns a composed function that first applies the this function to its input,
     * and then applies the function g to the result.
     *
     * @param g   the function to apply after this function is applied
     * @param <V> the type of output of the function g, and of the composed function
     * @return a composed function that first applies this function and then applies the function g
     */
    public <V> Function1<T, V> compose(Function1<? super R, ? extends V> g) {
        return new Function1<T, V>() {
            @Override
            public V apply(T t) {
                return g.apply(Function1.this.apply(t));
            }
        };
    }
}