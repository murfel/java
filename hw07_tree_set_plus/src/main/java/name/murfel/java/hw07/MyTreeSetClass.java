package name.murfel.java.hw07;

import java.util.*;

import static java.util.Comparator.nullsFirst;
import static java.util.Comparator.nullsLast;

public class MyTreeSetClass<E> extends AbstractSet<E> implements MyTreeSet<E> {
    private Comparator<? super E> comparator;
    private Tree tree = new Tree();

    /**
     * Create a MyTreeSet using natural order of elements. E should extend Comparable.
     */
    public MyTreeSetClass() {
        @SuppressWarnings("unchecked")
        Comparator<E> naturalOrder = (Comparator<E>) Comparator.naturalOrder();
        comparator = naturalOrder;
    }

    /**
     * Create a MyTreeSet using ordering provided by comparator.
     *
     * @param comparator comparator to order elements.
     */
    public MyTreeSetClass(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    private MyTreeSetClass(Tree tree, Comparator<? super E> comparator) {
        this.tree = new Tree(tree);
        this.comparator = comparator.reversed();
    }

    /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return an iterator over the elements contained in this collection
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Tree.Node prev;
            private int given;

            @Override
            public boolean hasNext() {
                return given != size();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Tree.Node next;
                if (prev == null) {
                    next = tree.root;
                    while (tree.getLeft(next) != null) {
                        next = tree.getLeft(next);
                    }
                } else {
                    if (tree.getRight(prev) != null) {
                        next = tree.getRight(prev);
                        while (tree.getLeft(next) != null) {
                            next = tree.getLeft(next);
                        }
                    } else {
                        next = prev;
                        while (next != tree.getLeft(next.parent)) {
                            next = next.parent;
                        }
                    }
                }
                prev = next;
                given++;
                return prev.element;
            }
        };
    }

    @Override
    public int size() {
        return tree.size();
    }

    /**
     * {@inheritDoc}
     * <p>
     *
     * @param e
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IllegalStateException         {@inheritDoc}
     */
    @Override
    public boolean add(E e) {
        return tree.add(e);
    }

    /**
     * {@link TreeSet#descendingIterator()}
     **/
    @Override
    public Iterator<E> descendingIterator() {
        return descendingSet().iterator();
    }

    /**
     * {@link TreeSet#descendingSet()}
     **/
    @Override
    public MyTreeSet<E> descendingSet() {
        return new MyTreeSetClass<E>(tree, this.comparator);
    }

    /**
     * {@link TreeSet#first()}
     **/
    @Override
    public E first() {
        return tree.getFirst();
    }

    /**
     * {@link TreeSet#last()}
     **/
    @Override
    public E last() {
        return tree.getLast();
    }

    /**
     * {@link TreeSet#lower(E)}
     *
     * @param e
     */
    @Override
    public E lower(E e) {
        return tree.lower(e);
    }

    /**
     * {@link TreeSet#floor(E)}
     *
     * @param e
     */
    @Override
    public E floor(E e) {
        return tree.floor(e);
    }

    /**
     * {@link TreeSet#ceiling(E)}
     *
     * @param e
     */
    @Override
    public E ceiling(E e) {
        return tree.ceiling(e);
    }

    /**
     * {@link TreeSet#higher(E)}
     *
     * @param e
     */
    @Override
    public E higher(E e) {
        return tree.higher(e);
    }

    private class Tree {
        private boolean isReversed;
        private Node root;
        private E first;
        private E last;
        private int size;

        public Tree() {
        }

        public Tree(Tree tree) {
            this.root = tree.root;
            this.isReversed = !tree.isReversed;
            this.first = tree.first;
            this.last = tree.last;
            this.size = tree.size;
        }

        public boolean add(E e) {
            if (nullsLast(comparator).compare(e, first) < 0) {
                first = e;
            }
            if (nullsFirst(comparator).compare(e, last) > 0) {
                last = e;
            }
            if (root == null) {
                root = new Node();
                root.element = e;
                size++;
                return true;
            }
            Node newNode = new Node();
            newNode.element = e;
            Node node = root;
            while (node != null) {
                newNode.parent = node;
                int result = comparator.compare(e, node.element);
                if (result < 0) {
                    node = getLeft(node);
                } else if (result > 0) {
                    node = getRight(node);
                } else {
                    return false;
                }
            }
            if (comparator.compare(e, newNode.parent.element) < 0) {
                setLeft(newNode.parent, newNode);
            } else {
                setRight(newNode.parent, newNode);
            }
            size++;
            return true;
        }

        public E lower(E e) {  // max x : x < e
            E lower = null;
            Node node = root;
            while (node != null) {
                if (comparator.compare(e, node.element) <= 0) {
                    node = getLeft(node);
                } else {
                    if (lower == null || comparator.compare(lower, node.element) < 0) {
                        lower = node.element;
                    }
                    node = getRight(node);
                }
            }
            return lower;
        }

        public E floor(E e) {
            E floor = null;
            Node node = root;
            while (node != null) {
                if (comparator.compare(e, node.element) < 0) {
                    node = getLeft(node);
                } else if (comparator.compare(e, node.element) > 0) {
                    if (floor == null || comparator.compare(floor, node.element) < 0) {
                        floor = node.element;
                    }
                    node = getRight(node);
                } else {
                    return node.element;
                }
            }
            return floor;
        }

        public E higher(E e) {
            E higher = null;
            Node node = root;
            while (node != null) {
                if (comparator.compare(e, node.element) >= 0) {
                    node = getRight(node);
                } else {
                    if (higher == null || comparator.compare(higher, node.element) > 0) {
                        higher = node.element;
                    }
                    node = getLeft(node);
                }
            }
            return higher;
        }

        public E ceiling(E e) {
            E ceiling = null;
            Node node = root;
            while (node != null) {
                if (comparator.compare(e, node.element) > 0) {
                    node = getRight(node);
                } else if (comparator.compare(e, node.element) < 0) {
                    if (ceiling == null || comparator.compare(ceiling, node.element) > 0) {
                        ceiling = node.element;
                    }
                    node = getLeft(node);
                } else {
                    return node.element;
                }
            }
            return ceiling;
        }

        public int size() {
            return size;
        }

        private Node getLeft(Node node) {
            return isReversed ? node.right : node.left;
        }

        private Node getRight(Node node) {
            return isReversed ? node.left : node.right;
        }

        private void setLeft(Node node, Node childNode) {
            if (isReversed) {
                node.right = childNode;
            } else {
                node.left = childNode;
            }
        }

        private void setRight(Node node, Node childNode) {
            if (isReversed) {
                node.left = childNode;
            } else {
                node.right = childNode;
            }
        }

        private E getFirst() {
            return isReversed ? last : first;
        }

        private E getLast() {
            return isReversed ? first : last;
        }

        private class Node {
            private Node parent;
            private Node left;
            private Node right;
            private E element;
        }
    }
}
