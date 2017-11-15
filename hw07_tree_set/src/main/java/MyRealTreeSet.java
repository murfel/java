import java.util.*;


public class MyRealTreeSet<E> extends AbstractSet<E> implements MyTreeSet<E> {

    public MyRealTreeSet() {
        ;
    }

    public MyRealTreeSet(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * {@link TreeSet#descendingIterator()}
     **/
    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    /**
     * {@link TreeSet#descendingSet()}
     **/
    @Override
    public MyTreeSet<E> descendingSet() {
        return null;
    }

    /**
     * {@link TreeSet#first()}
     **/
    @Override
    public E first() {
        return null;
    }

    /**
     * {@link TreeSet#last()}
     **/
    @Override
    public E last() {
        return null;
    }

    /**
     * {@link TreeSet#lower(E)}
     *
     * @param e
     **/
    @Override
    public E lower(E e) {
        return null;
    }

    /**
     * {@link TreeSet#floor(E)}
     *
     * @param e
     **/
    @Override
    public E floor(E e) {
        return null;
    }

    /**
     * {@link TreeSet#ceiling(E)}
     *
     * @param e
     **/
    @Override
    public E ceiling(E e) {
        return null;
    }

    /**
     * {@link TreeSet#higher(E)}
     *
     * @param e
     **/
    @Override
    public E higher(E e) {
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    public boolean add(E e) {

    }

    public E remove(int index) {

    }

    private class Node {
        Node left, right, parent;
        E value;
    }

    private class MyIterator implements Iterator<E> {

        public boolean hasNext() {
            return false;
        }

        public E next() {
            return null;
        }
    }

    private boolean isIncreasing = true;
    private Comparator<E> comparator;


}
