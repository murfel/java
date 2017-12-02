import java.util.ArrayList;

public class Stack<E> {
    ArrayList<E> stack = new ArrayList<>();

    boolean isEmpty() {
        return stack.isEmpty();
    }

    E pop() {
        return stack.remove(stack.size() - 1);
    }

    E push(E e) {
        stack.add(e);
        return e;
    }

    void clear() {
        stack.clear();
    }
}
