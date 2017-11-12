import org.junit.Test;

import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static org.junit.Assert.*;

public class CollectionsTest {

    private Predicate<Integer> isDivisibleBy3 = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer integer) {
            return integer % 3 == 0;
        }
    };

    private Function2<Integer, Integer, Integer> exponentiate = new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer base, Integer power) {
            return (int) pow(base, power);
        }
    };

    @Test
    public void map() throws Exception {
        ArrayList<Integer> input =  new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Boolean> expected = new ArrayList<>(Arrays.asList(TRUE, FALSE, FALSE));
        ArrayList<Boolean> result = Collections.map(isDivisibleBy3, input);
        assertEquals(expected, result);
    }

    @Test
    public void filter() throws Exception {
        ArrayList<Integer> input =  new ArrayList<>(Arrays.asList(1, 5, 3, 6, 7, 0));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3, 6, 0));
        ArrayList<Integer> result = Collections.filter(isDivisibleBy3, input);
        assertEquals(expected, result);
    }

    @Test
    public void takeWhile() throws Exception {
        ArrayList<Integer> input =  new ArrayList<>(Arrays.asList(3, 6, 7, 0, 1, 5));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3, 6));
        ArrayList<Integer> result = Collections.takeWhile(isDivisibleBy3, input);
        assertEquals(expected, result);
    }

    @Test
    public void takeUnless() throws Exception {
        ArrayList<Integer> input =  new ArrayList<>(Arrays.asList(1, 5, 3, 6, 7, 0));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 5));
        ArrayList<Integer> result = Collections.takeUnless(isDivisibleBy3, input);
        assertEquals(expected, result);
    }

    @Test
    public void foldr() throws Exception {
        ArrayList<Integer> input =  new ArrayList<>(Arrays.asList(4, 3));
        Integer expected = (int) pow(4, pow(3, 2));  // 262144
        Integer result = Collections.foldr(exponentiate, 2, input);
        assertEquals(expected, result);
    }

    @Test
    public void foldl() throws Exception {
        ArrayList<Integer> input =  new ArrayList<>(Arrays.asList(4, 3));
        Integer expected = (int) pow(pow(2, 4), 3);  // 4096
        Integer result = Collections.foldl(exponentiate, 2, input);
        assertEquals(expected, result);
    }

}