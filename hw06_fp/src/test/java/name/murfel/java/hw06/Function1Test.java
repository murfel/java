package name.murfel.java.hw06;

import static org.junit.Assert.assertEquals;

public class Function1Test {
    @org.junit.Test
    public void apply() throws Exception {
        Function1<Integer, Boolean> f = new Function1<Integer, Boolean>() {
            public Boolean apply(Integer integer) {
                return integer % 2 == 0;
            }
        };
        assertEquals(Boolean.TRUE, f.apply(0));
        assertEquals(Boolean.FALSE, f.apply(1));
    }

    @org.junit.Test
    public void compose() throws Exception {
        Function1<Integer, Boolean> f = new Function1<Integer, Boolean>() {
            public Boolean apply(Integer integer) {
                return integer % 2 == 0;
            }
        };

        Function1<Boolean, String> g = new Function1<Boolean, String>() {
            @Override
            public String apply(Boolean aBoolean) {
                return aBoolean.toString();
            }
        };

        assertEquals("true", f.compose(g).apply(0));
        assertEquals("false", f.compose(g).apply(1));
    }

}