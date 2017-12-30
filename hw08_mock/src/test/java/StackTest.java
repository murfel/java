import org.junit.Test;

import static org.junit.Assert.*;

public class StackTest {
    @Test
    public void pushOne() throws Exception {
        Stack<Integer> stack = new Stack<>();
        assertEquals(new Integer(1), stack.push(1));
    }

    @Test
    public void pushMultiple() throws Exception {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 5; i++) {
            assertEquals(new Integer(i), stack.push(i));
        }
    }

    @Test
    public void pushPopOne() throws Exception {
        Stack<Integer> stack = new Stack<>();
        assertEquals(new Integer(1), stack.push(1));
        assertEquals(new Integer(1), stack.pop());
    }

    @Test
    public void pushPopMultiple() throws Exception {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 5; i++) {
            assertEquals(new Integer(i), stack.push(i));
        }
        for (int i = 4; i >= 0; i--) {
            assertEquals(new Integer(i), stack.pop());
        }
    }

    @Test
    public void pushPopMultipleMixed() throws Exception {
        Stack<Integer> stack = new Stack<>();
        assertEquals(new Integer(1), stack.push(1));
        assertEquals(new Integer(2), stack.push(2));
        assertEquals(new Integer(2), stack.pop());
        assertEquals(new Integer(3), stack.push(3));
        assertEquals(new Integer(3), stack.pop());
        assertEquals(new Integer(1), stack.pop());
    }
}