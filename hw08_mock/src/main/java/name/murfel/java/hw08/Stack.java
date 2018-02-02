package name.murfel.java.hw08;

/**
 * The Stack class represents a last-in-first-out (LIFO) stack of objects.
 * The usual push and pop operations are provided.
 */
public class Stack<E> {
    @SuppressWarnings("unchecked")
    private E[] stack = (E[]) new Object[1];
    private int size;

    /**
     * Removes the object at the top of this stack and returns that object as the value of this function.
     *
     * @return The object at the top of this stack.
     */
    E pop() {
        return stack[--size];
    }

    /**
     * Pushes an item onto the top of this stack.
     *
     * @param item the item to be pushed onto this stack
     * @return the item argument
     */
    E push(E item) {
        if (size == stack.length) {
            E[] oldStack = stack;
            @SuppressWarnings("unchecked")
            E[] newStack = (E[]) new Object[oldStack.length * 2];
            stack = newStack;
            System.arraycopy(oldStack, 0, stack, 0, oldStack.length);
        }
        return stack[size++] = item;
    }
}
