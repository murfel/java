import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SmartListTest {
    @Test
    public void testConstantSize() throws Exception {
        for (int i = 0; i <= 10; i++) {
            SmartList<Integer> list = new SmartList<>(new ArrayList<>(Arrays.asList(new Integer[i])));
            assertEquals(i, list.size());
        }
    }

    @Test
    public void testNonConstantSize() throws Exception {
        SmartList<Integer> list = new SmartList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
            assertEquals(i, list.size());
        }
        for (int i = 9; i >= 0; i--) {
            list.remove(0);
            assertEquals(i, list.size());
        }
    }

    @Test
    public void testGetSetEdgeCases() throws Exception {
        Integer[] array = new Integer[10];
        for (int i = 0; i < 10; i++) {
            array[i] = i + 1;
        }
        SmartList<Integer> list = new SmartList<>(new ArrayList<>(Arrays.asList(array)));
        for (int i = 0; i < 10; i++) {
            assertEquals(new Integer(i + 1), list.get(i));
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(new Integer(i + 1), list.set(i, 2 * i + 5));
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(new Integer(2 * i + 5), list.get(i));
        }
    }

    @Test
    public void testRemoveReturnValue() throws Exception {
        Integer[] array = new Integer[10];
        for (int i = 0; i < 10; i++) {
            array[i] = i + 1;
        }
        SmartList<Integer> list = new SmartList<>(new ArrayList<>(Arrays.asList(array)));
        for (int i = 0; i < 10; i++) {
            assertEquals(new Integer(i + 1), list.remove(0));
        }
    }

    @Test
    public void testRemoveFrom6() throws Exception {
        Integer[] array = new Integer[6];
        for (int i = 0; i < 6; i++) {
            array[i] = i + 1;
        }
        SmartList<Integer> list = new SmartList<>(new ArrayList<>(Arrays.asList(array)));
        assertEquals(new Integer(2), list.remove(1));
        assertEquals(new Integer(1), list.remove(0));
        assertEquals(new Integer(3), list.remove(0));
        assertEquals(new Integer(4), list.remove(0));
        assertEquals(new Integer(5), list.remove(0));
        assertEquals(new Integer(6), list.remove(0));
    }
}