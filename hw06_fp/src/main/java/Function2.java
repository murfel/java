public abstract class Function2<A, B, C> {
    public abstract C apply(A a, B b);

    public <D> Function2<A, B, D> compose(Function1<C, D> g) {
        return new Function2<A, B, D>() {
            @Override
            public D apply(A a, B b) {
                return g.apply(Function2.this.apply(a, b));
            }
        };
    }

    public Function1<B, C> bind1(A a) {
        return new Function1<B, C>() {
            @Override
            public C apply(B b) {
                return Function2.this.apply(a, b);
            }
        };
    }

    public Function1<A, C> bind2(B b) {
        return new Function1<A, C>() {
            @Override
            public C apply(A a) {
                return Function2.this.apply(a, b);
            }
        };
    }

    public Function1<A, Function1<B, C>> curry() {
        return new Function1<A, Function1<B, C>>() {
            @Override
            public Function1<B, C> apply(A a) {
                return new Function1<B, C>() {
                    @Override
                    public C apply(B b) {
                        return Function2.this.apply(a, b);
                    }
                };
            }
        };
    }
}
