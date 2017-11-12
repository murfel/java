/**
 * Represents a predicate (boolean-valued function) of one argument.
 *
 * @param <T>  the type of the input to the predicate
 */
public abstract class Predicate<T> extends Function1<T, Boolean> {

    /**
     * Returns a composed predicate that represents a short-circuiting logical OR of this predicate and another.
     * When evaluating the composed predicate, if this predicate is true, then the other predicate is not evaluated.
     *
     * @param p  a predicate that will be logically-ORed with this predicate
     * @return a composed predicate that represents the short-circuiting logical OR
     * of this predicate and the other predicate
     */
    public Predicate<T> or(Predicate<T> p) {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T t) {
                return Predicate.this.apply(t) || p.apply(t);
            }
        };
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical AND of this predicate and another.
     * When evaluating the composed predicate, if this predicate is false, then the other predicate is not evaluated.
     *
     * @param p  a predicate that will be logically-ANDed with this predicate
     * @return a composed predicate that represents the short-circuiting logical AND
     * of this predicate and the other predicate
     */
    public Predicate<T> and(Predicate<T> p) {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T t) {
                return Predicate.this.apply(t) && p.apply(t);
            }
        };
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    public Predicate<T> not() {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T t) {
                return !Predicate.this.apply(t);
            }
        };
    }

    /**
     * A predicate which always returns Boolean.TRUE.
     *
     * @return a predicate which always returns Boolean.TRUE
     */
    public static Predicate ALWAYS_TRUE() {
        return new Predicate() {
            @Override
            public Boolean apply(Object t) {
                return true;
            }
        };
    }

    /**
     * A predicate which always returns Boolean.FALSE.
     *
     * @return a predicate which always returns Boolean.FALSE
     */
    public static Predicate ALWAYS_FALSE() {
        return ALWAYS_TRUE().not();
    }
}
