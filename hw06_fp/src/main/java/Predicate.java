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

    public Predicate<T> ALWAYS_TRUE() {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T t) {
                return true;
            }
        };
    }

    public Predicate<T> ALWAYS_FALSE() {
        return ALWAYS_TRUE().not();
    }
}
