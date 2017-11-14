import org.junit.Test;

import java.util.ListIterator;

import static org.junit.Assert.*;

public class MyListIteratorTest {
    @Test
    public void hasNext() throws Exception {
    }

    @Test
    public void next() throws Exception {
    }

    @Test
    public void hasPrevious() throws Exception {
    }

    @Test
    public void previous() throws Exception {
    }

    @Test
    public void nextIndex() throws Exception {
    }

    @Test
    public void previousIndex() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void set() throws Exception {
    }

    @Test
    public void add() throws Exception {
        MyList<Integer> list = new MyList<Integer>();
        ListIterator<Integer> li = list.listIterator(0);
        li.add(7);
        assertEquals(1, list.size());
        assertTrue(li.hasPrevious());
    }

}