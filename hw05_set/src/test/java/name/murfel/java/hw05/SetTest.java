package name.murfel.java.hw05;

import static org.junit.Assert.*;

public class SetTest {
    @org.junit.Test
    public void addOne() throws Exception {
        Set<Integer> set = new Set<Integer>();
        set.add(5);
        assertEquals(1, set.size());
        assertTrue(set.contains(5));
        assertFalse(set.contains(0));
    }

    @org.junit.Test
    public void addSameTwice() throws Exception {
        Set<Integer> set = new Set<Integer>();
        set.add(5);
        set.add(5);
        assertEquals(1, set.size());
        assertTrue(set.contains(5));
        assertFalse(set.contains(0));
    }

    @org.junit.Test
    public void addMany() throws Exception {
        Set<Integer> set = new Set<Integer>();
        Integer[] data = {1, 2};
        for (Integer x : data)
            set.add(x);
        assertEquals(data.length, set.size());
        for (Integer x : data)
            assertTrue(set.contains(x));
        assertFalse(set.contains(0));
    }

    @org.junit.Test
    public void containsNegativeEmpty() throws Exception {
        Set<Integer> set = new Set<Integer>();
        assertFalse(set.contains(0));
        assertFalse(set.contains(1));
    }

    @org.junit.Test
    public void sizeEmpty() throws Exception {
        Set<Integer> set = new Set<Integer>();
        assertEquals(0, set.size());
    }

}