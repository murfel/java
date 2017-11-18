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
        return new MyIterator();  // TODO: need to change isIncreasing constant somehow
    }

    /**
     * {@link TreeSet#descendingSet()}
     **/
    @Override
    public MyTreeSet<E> descendingSet() {
        return this;  // TODO: need to change isIncreasing constant somehow
    }

    /**
     * {@link TreeSet#first()}
     **/
    @Override
    public E first() {
        return beggining.value;
    }

    /**
     * {@link TreeSet#last()}
     **/
    @Override
    public E last() {
        return ending.value;
    }

    /**
     * {@link TreeSet#lower(E)}
     *
     * @param e
     **/
    @Override
    public E lower(E e) {
        return null;  // TODO: implement after implementing higher
    }

    /**
     * {@link TreeSet#floor(E)}
     *
     * @param e
     **/
    @Override
    public E floor(E e) {
        if (find(e) != null)
            return e;
        return lower(e);
    }

    /**
     * {@link TreeSet#higher(E)}
     *
     * @param e
     **/
    @Override
    public E higher(E e) {
        if (size == 0)
            return null;
        Node node = root;

        if (less(node.value, e)) {
            if (node.right == null)
                return null;
            node = node.right;
        }
        else if (less(e, node.value)) {
            if (node.left == null)
                return node.value;
            node = node.left;  // TODO: something went wrong here
        }
        else {
            if (node.next == null)
                return null;
            return node.next.value;
        }
        return null;
    }

    /**
     * {@link TreeSet#ceiling(E)}
     *
     * @param e
     **/
    @Override
    public E ceiling(E e) {
        if (find(e) != null)
            return e;
        return higher(e);
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public int size() {
        return size;
    }

    public boolean add(E e) {
        if (contains(e))
            return false;
        size++;
        if (root == null)
            root = new Node(null, e);
        else
            add(root, e);
        return true;
    }

    public E remove(int index) {
        if (index >= size())
            return null;
        size--;
        Node node = beggining;
        while (index-- != 0)
            node = node.next;
        if (node.right == null) {
            if (node.equals(root))
                root = node.left;
            else
                node.setReferenceToMe(null);
        }
        else {
            swap(node, node.next);
            node.setReferenceToMe(null);
        }
        return node.value;
    }

    private boolean isIncreasing = true;
    private Comparator<E> comparator;
    private Node root, beggining, ending;
    private int size;

    private class Node {

        private Node (Node parent, E value) {
            this.parent = parent;
            this.value = value;
        }

        private void setReferenceToMe(Node node) {
            if (parent.left.equals(this))
                parent.left = node;
            else
                parent.right = node;
        }

        private Node next() {
            return isIncreasing ? next : previous;
        }

        private Node previous() {
            return isIncreasing ? previous : next;
        }

        private Node parent, left, right, next, previous;
        private E value;
    }

    private Node find(E e) {
        if (!contains(e))
            return null;
        return find(e, root);
    }

    private Node find(E e, Node node) {
        if (less(e, node.value))
            return find(e, node.left);
        else if (less(node.value, e))
            return find(e, node.right);
        else
            return node;
    }

    private void swap(Node n, Node n1) {
        E value = n.value;
        Node next = n.next;
        Node previous = n.previous;
        n.value = n1.value;
        n.next = n1.next;
        n.previous = n1.previous;
        n1.value = value;
        n1.next = next;
        n1.previous = previous;
    }

    private boolean add(Node node, E e) {
        if (less(e, node.value))
            if (node.left == null)
                node.left = new Node(node, e);
            else
                add(node.left, e);
        else
            if (node.right == null)
                node.right = new Node(node, e);
            else
                add(node.right, e);
        return true;
    }

    private class MyIterator implements Iterator<E> {
        private Node cursor;

        public boolean hasNext() {
            return cursor != null || beggining != null;
        }

        public E next() {
            if (cursor == null)
                cursor = beggining;
            else
                cursor = cursor.next;
            return cursor.value;
        }
    }

    private boolean less(E t, E t1) {
        if (comparator != null)
            if (isIncreasing)
                return comparator.compare(t, t1) < 0;
            else
                return comparator.compare(t, t1) > 0;
        else
            if (isIncreasing)
                return ((Comparable<E>) t).compareTo(t1) < 0;
            else
                return ((Comparable<E>) t).compareTo(t1) > 0;
    }
}