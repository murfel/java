import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Resizable-array implementation of the List interface.
 * Implements all optional list operations, and permits all elements, including null.
 *
 * Optimized to store a small number of elements while still allowing to store any number of elements.
 *
 * @param <E> type of stored elements
 */
public class SmartList<E> extends AbstractList<E> {
    /**
     * Constructs an empty list.
     */
    public SmartList() {
    }

    /**
     * Constructs a list containing the elements of the specified collection,
     * in the order they are returned by the collection's iterator.
     *
     * @param collection  the collection whose elements are to be placed into this list
     * @throws NullPointerException  if the specified collection is null
     */
    public SmartList(Collection<? extends E> collection) {
        size = collection.size();
        if (size == 1) {
            data = collection.iterator().next();
        } else if (size > 0 && size <= 5) {
            data = collection.toArray();
        } else if (size > 0){
            data = new ArrayList<>(collection);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param index
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            @SuppressWarnings("unchecked")
            E element = (E) data;
            return element;
        }
        if (size <= 5) {
            @SuppressWarnings("unchecked")
            E[] elements = (E[]) data;
            return elements[index];
        }
        @SuppressWarnings("unchecked")
        ArrayList<E> arrayList = (ArrayList<E>) data;
        return arrayList.get(index);
    }

    /**
     * {@inheritDoc}
     * <p>
     *
     * @param index
     * @param element
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            @SuppressWarnings("unchecked")
            E oldElement = (E) data;
            data = element;
            return oldElement;
        }
        if (size <= 5) {
            @SuppressWarnings("unchecked")
            E[] array = (E[]) data;
            E oldElement = array[index];
            array[index] = element;
            data = array;
            return oldElement;
        }
        @SuppressWarnings("unchecked")
        ArrayList<E> arrayList = (ArrayList<E>) data;
        return arrayList.set(index, element);
    }

    /**
     * {@inheritDoc}
     * <p>
     *
     * @param index
     * @param element
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0) {
            data = element;
        } else if (size == 1) {
            Object[] newData = new Object[5];
            newData[index] = element;
            newData[1 - index] = data;
            data = newData;
        } else if (size < 5) {
            @SuppressWarnings("unchecked")
            E[] array = (E[]) data;
            for (int i = size - 1; i >= index; size++) {
                array[i + 1] = array[i];
            }
            System.arraycopy(array, index, array, index + 1, size - index);
            array[index] = element;
            data = array;
        } else if (size == 5) {
            @SuppressWarnings("unchecked")
            E[] array = (E[]) data;
            ArrayList<E> arrayList = new ArrayList<>(Arrays.asList(array));
            arrayList.add(index, element);
            data = arrayList;
        } else {
            @SuppressWarnings("unchecked")
            ArrayList<E> arrayList = (ArrayList<E>) data;
            arrayList.add(index, element);
        }
        size++;
    }

    /**
     * {@inheritDoc}
     * <p>
     *
     * @param index
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            size--;
            @SuppressWarnings("unchecked")
            E element = (E) data;
            data = null;
            return element;
        }
        E element;
        if (size <= 5) {
            @SuppressWarnings("unchecked")
            E[] array = (E[]) data;
            element = array[index];
            if (size == 2) {
                data = array[1 - index];
            } else {
                System.arraycopy(array, index + 1, array, index, size - index - 1);
            }
        } else {
            @SuppressWarnings("unchecked")
            ArrayList<E> arrayList = (ArrayList<E>) data;
            element = arrayList.remove(index);
            if (size == 6) {
                data = arrayList.toArray();
            }
        }
        size--;
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    private int size;
    private Object data;
}
