public abstract class Predicate<T> extends Function1<T, Boolean> {

    public Predicate<T> or(Predicate<T> p) {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T t) {
                return Predicate.this.apply(t) || p.apply(t);
            }
        };
    }

    public Predicate<T> and(Predicate<T> p) {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T t) {
                return Predicate.this.apply(t) && p.apply(t);
            }
        };
    }

    public Predicate<T> not() {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T t) {
                return !Predicate.this.apply(t);
            }
        };
    }

    public static <X> Predicate<X> ALWAYS_TRUE() {
        return new Predicate<X>() {
            @Override
            public Boolean apply(X t) {
                return true;
            }
        };
    }

    public static Predicate ALWAYS_FALSE() {
        return ALWAYS_TRUE().not();
    }
}
