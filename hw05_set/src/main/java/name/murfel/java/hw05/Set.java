/**
 * A container that contains no duplicate elements. It is implemented as a binary search tree without balancing.
 *
 * @param <T>  the type of elements stored in the set
 */
public class Set<T extends Comparable<T>> {
    private Node root;
    private int size;

    private class Node {
        private Node left, right;
        private T data;

        Node(T data) {
            this.data = data;
        }
    }

    /**
     * Add elem to the set. If there is already such an element, do nothing.
     *
     * @param elem  an element to add to the set
     */
    public void add(T elem) {
        if (contains(elem))
            return;
        size++;
        if (root == null) {
            root = new Node(elem);
            return;
        }
        Node parent = root;
        while (true) {
            if (elem.compareTo(parent.data) < 0)
                if (parent.left == null) {
                    parent.left = new Node(elem);
                    break;
                }
                else
                    parent = parent.left;
            else
                if (parent.right == null) {
                    parent.right = new Node(elem);
                    break;
                }
                else
                    parent = parent.left;
        }
    }

    /**
     * Check whether elem is present in the set.
     *
     * @param elem  element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     */
    public boolean contains(T elem) {
        if (size == 0) {
            return false;
        }
        Node node = root;
        while (true) {
            if (node == null) {
                return false;
            }
            if (elem.compareTo(node.data) == 0) {
                return true;
            }
            node = (elem.compareTo(node.data) < 0 ? node.left : node.right);
        }
    }

    public int size() {
        return size;
    }
}
