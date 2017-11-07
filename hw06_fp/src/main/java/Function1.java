public abstract class Function1<T, R> {

    public abstract R apply(T t);

    public <V> Function1<T, V> compose(Function1<? super R, ? extends V> g) {
        return new Function1<T, V>() {
            @Override
            public V apply(T t) {
                return g.apply(Function1.this.apply(t));
            }
        };
    }
}