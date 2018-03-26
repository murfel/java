package name.murfel.java.hw06;

import org.junit.Test;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;

public class PredicateTest {
    private Predicate<Integer> isDivisibleBy3 = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer integer) {
            return integer % 3 == 0;
        }
    };

    /**
     * A perfect number is a positive integer that is equal to the sum
     * of its positive divisors excluding the number itself.
     * <p>
     * For example, 6 and 28 are perfect since 1 + 2 + 3 = 6
     * and 1 + 2 + 4 + 7 + 14 = 28.
     */
    private Predicate<Integer> isPerfect = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer integer) {
            int sum = 0;
            ArrayList<Integer> list = getDivisors(integer);
            for (Integer d : list)
                sum += d;
            return sum == integer;
        }

        private ArrayList<Integer> getDivisors(Integer integer) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int div = 1; div < integer; div++)
                if (integer % div == 0)
                    list.add(div);
            return list;
        }
    };

    @Test
    public void or() throws Exception {
        Predicate<Integer> p = isDivisibleBy3.or(isPerfect);
        assertEquals(FALSE, p.apply(8));
        assertEquals(TRUE, p.apply(28));
        assertEquals(TRUE, p.apply(9));
        assertEquals(TRUE, p.apply(6));
    }

    @Test
    public void and() throws Exception {
        Predicate<Integer> p = isDivisibleBy3.and(isPerfect);
        assertEquals(FALSE, p.apply(8));
        assertEquals(FALSE, p.apply(28));
        assertEquals(FALSE, p.apply(9));
        assertEquals(TRUE, p.apply(6));
    }

    @Test
    public void not() throws Exception {
        Predicate<Integer> p = isDivisibleBy3.not();
        assertEquals(FALSE, p.apply(0));
        assertEquals(TRUE, p.apply(1));
        assertEquals(TRUE, p.apply(2));
    }

    @Test
    public void ALWAYS_TRUE() throws Exception {
        assertEquals(TRUE, Predicate.ALWAYS_TRUE().apply(1));
    }

    @Test
    public void ALWAYS_FALSE() throws Exception {
        assertEquals(FALSE, Predicate.ALWAYS_FALSE().apply("meow"));
    }

}