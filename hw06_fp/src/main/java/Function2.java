import org.jetbrains.annotations.NotNull;

/**
 * Represents a function that accepts two arguments and produces a result.
 * This is the two-arity specialization of Function1.
 *
 * @param <A> the type of the first argument to the function
 * @param <B> the type of the second argument to the function
 * @param <C> the type of the result of the function
 */
public abstract class Function2<A, B, C> {
    /**
     * Applies this function to the given arguments.
     *
     * @param a the first function argument
     * @param b the second function argument
     * @return the function result
     */
    public abstract C apply(A a, B b);

    /**
     * Returns a composed function that first applies this function to its input,
     * and then applies the after function to the result.
     *
     * @param g   the function to apply after this function is applied
     * @param <D> the type of output of the after function, and of the composed function
     * @return a composed function that first applies this function and then applies the after function
     */
    @NotNull
    public <D> Function2<A, B, D> compose(@NotNull final Function1<C, D> g) {
        return new Function2<A, B, D>() {
            @Override
            public D apply(A a, B b) {
                return g.apply(Function2.this.apply(a, b));
            }
        };
    }

    /**
     * Apply this function partially binding its first argument to always equal to a.
     *
     * @param a the value to bind the first argument to
     * @return a one argument function based on this function with its first argument bound to a
     */
    @NotNull
    public Function1<B, C> bind1(final A a) {
        return new Function1<B, C>() {
            @Override
            public C apply(B b) {
                return Function2.this.apply(a, b);
            }
        };
    }

    /**
     * Apply this function partially binding its second argument to always equal to b.
     *
     * @param b the value to bind the second argument to
     * @return a one argument function based on this function with its second argument bound to b
     */
    @NotNull
    public Function1<A, C> bind2(final B b) {
        return new Function1<A, C>() {
            @Override
            public C apply(A a) {
                return Function2.this.apply(a, b);
            }
        };
    }

    /**
     * Curry the function transforming it into a one-argument function.
     * For example, currying f = (x, y) -> x + y gives f = (x) -> y -> x + y.
     *
     * @return a one-argument function which returns a one-argument function
     */
    @NotNull
    public Function1<A, Function1<B, C>> curry() {
        return new Function1<A, Function1<B, C>>() {
            @Override
            public Function1<B, C> apply(A a) {
                return bind1(a);
            }
        };
    }
}
