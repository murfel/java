import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StackTest {
    @Test
    public void pushOne() {
        Stack<Integer> stack = new Stack<>();
        assertEquals(new Integer(1), stack.push(1));
    }

    @Test
    public void pushMultiple() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 5; i++) {
            assertEquals(new Integer(i), stack.push(i));
        }
    }

    @Test
    public void pushPopOne() {
        Stack<Integer> stack = new Stack<>();
        assertEquals(new Integer(1), stack.push(1));
        assertEquals(new Integer(1), stack.pop());
    }

    @Test
    public void pushPopMultiple() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 5; i++) {
            assertEquals(new Integer(i), stack.push(i));
        }
        for (int i = 4; i >= 0; i--) {
            assertEquals(new Integer(i), stack.pop());
        }
    }

    @Test
    public void pushPopMultipleMixed() {
        Stack<Integer> stack = new Stack<>();
        assertEquals(new Integer(1), stack.push(1));
        assertEquals(new Integer(2), stack.push(2));
        assertEquals(new Integer(2), stack.pop());
        assertEquals(new Integer(3), stack.push(3));
        assertEquals(new Integer(3), stack.pop());
        assertEquals(new Integer(1), stack.pop());
    }
}