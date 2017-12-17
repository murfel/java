import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class StreamProblemsTest {
    @org.junit.Test
    public void task1() throws Exception {
        // 1 2 2 2 1 1 2 3 → 1 2 1 2 3 → 9 / 5 = 1.8
        Stream<String> input = Stream.of("one 1 is the loneliest number", "22", "21 pilots", "1 2 3 vier fünf");
        double expected = 1.8;
        double actual = StreamProblems.task1(input);
        assertEquals(expected, actual, 1e-5);
    }

    @org.junit.Test
    public void task2() throws Exception {
        Collection<Point> input = new ArrayList<>();
        input.add(new Point(0, 0,1));
        input.add(new Point(0, 1,2));
        input.add(new Point(1, 0,1));

        Point expected = new Point(0, 2.0 / 3, 3);
        Point actual = StreamProblems.task2(input.iterator());

        // System.out should be: 0.0 1.0 1.0

        assertNotNull(actual);
        assertEquals(expected.x, actual.x, 1e-5);
        assertEquals(expected.y, actual.y, 1e-5);
        assertEquals(expected.mass, actual.mass, 1e-5);
    }

    @org.junit.Test
    public void task5() throws Exception {
        String filename = "src/test/resources/numbers.txt";
        int[] expected = new int[10];
        expected[1] = 1;
        expected[4] = 1;
        expected[5] = 2;
        int[] actual = StreamProblems.task5(filename);
//        assertEquals(expected, actual);
    }

}