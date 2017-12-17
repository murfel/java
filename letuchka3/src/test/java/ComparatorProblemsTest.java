import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class ComparatorProblemsTest {
    @Test
    public void task6() throws Exception {
        Comparator<Point> cmp = ComparatorProblems.task6();
        Point p1 = new Point(0, 0, 1, 0);
        Point p2 = new Point(0, 0, 2, 0);
        Point p3 = new Point(1, 1, 1, 0);

        assertTrue(cmp.compare(p1, p2) < 0);
        assertTrue(cmp.compare(p1, p3) > 0);
    }

}