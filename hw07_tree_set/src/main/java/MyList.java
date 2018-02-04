import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyList<E> extends AbstractSequentialList<E> {

    private class Node {

        Node() {}

        Node(E value) {
            this.value = value;
        }

        Node previous, next;
        E value;
    }

    private int size;
    private Node beginning = new Node();
    private Node ending = new Node();

    {
        beginning.next = ending;
        ending.previous = beginning;
    }

    private class MyListIterator implements ListIterator<E> {
        int nextIndex;
        Node afterCursor;
        Node lastReturned;

        public MyListIterator(int i) {
            afterCursor = beginning.next;
            nextIndex = i;
            while (i-- != 0) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            return nextIndex != size;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            afterCursor = afterCursor.next;
            nextIndex++;
            lastReturned = afterCursor;
            return afterCursor.value;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex != 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            afterCursor = afterCursor.previous;
            nextIndex--;
            lastReturned = afterCursor.previous;
            return afterCursor.previous.value;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();
            lastReturned.previous.next = lastReturned.next;
            lastReturned.next.previous = lastReturned.previous;
            lastReturned = null;
        }

        @Override
        public void set(E e) {
            if (lastReturned == null)
                throw new IllegalStateException();
            lastReturned.value = e;
        }

        @Override
        public void add(E e) {
            Node newNode = new Node(e);
            if (afterCursor == null) {
                newNode.previous = ending.previous;
                newNode.next = ending;
                ending.previous.next = newNode;
                ending.previous = newNode;
            }
            else {
                newNode.previous = afterCursor.previous;
                newNode.next = afterCursor;
                afterCursor.previous.next = newNode;
                afterCursor.previous = newNode;
            }
            nextIndex++;
            size++;
            lastReturned = null;
        }
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        if (i < 0 || i > size)
            throw new IndexOutOfBoundsException();
        return new MyListIterator(i);
    }

    @Override
    public int size() {
        return size;
    }
}
