package name.murfel.java.hw06;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function2Test {
    private Function1<String, Character> g = new Function1<String, Character>() {
        @Override
        public Character apply(String s) {
            return !s.isEmpty() ? s.charAt(0) : null;
        }
    };

    private Function2<Integer, Boolean, String> f = new Function2<Integer, Boolean, String>() {
        @Override
        public String apply(Integer integer, Boolean aBoolean) {
            return integer.toString() + aBoolean.toString();
        }
    };

    @Test
    public void apply() throws Exception {
        assertEquals("2true", f.apply(2, Boolean.TRUE));
    }

    @Test
    public void compose() throws Exception {
        assertEquals(new Character('2'), f.compose(g).apply(2, Boolean.TRUE));
    }

    @Test
    public void bind1() throws Exception {
        assertEquals("2true", f.bind1(2).apply(Boolean.TRUE));
    }

    @Test
    public void bind2() throws Exception {
        assertEquals("4false", f.bind2(Boolean.FALSE).apply(4));
    }

    @Test
    public void curry() throws Exception {
        assertEquals("4false", f.curry().apply(4).apply(Boolean.FALSE));
    }

}