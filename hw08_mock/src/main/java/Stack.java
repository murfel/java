import java.util.ArrayList;

/**
 * The Stack class represents a last-in-first-out (LIFO) stack of objects.
 * The usual push and pop operations are provided.
 */
public class Stack<E> {
    private ArrayList<E> stack = new ArrayList<>();

    /**
     * Removes the object at the top of this stack and returns that object as the value of this function.
     *
     * @return The object at the top of this stack.
     */
    E pop() {
        return stack.remove(stack.size() - 1);
    }

    /**
     * Pushes an item onto the top of this stack.
     *
     * @param item  the item to be pushed onto this stack
     * @return the item argument
     */
    E push(E item) {
        stack.add(item);
        return item;
    }

    /**
     * Removes all of the elements from this Stack.
     */
    void clear() {
        stack.clear();
    }
}
