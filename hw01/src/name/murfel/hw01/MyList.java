package name.murfel.hw01;

public class MyList {
    private class Node {
        public Node(String first, String second) {
            this.first = first;
            this.second = second;
        }
        public Node next;
        public String first, second;
    }

    /**
     * Add a two-element array of first and second to the end of the list.
     *
     * @param first  first element if the two-element array to add
     * @param second  second element if the two-element array to add
     */
    public void add(String first, String second) {
        Node newNode = new Node(first, second);
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Get an element at the index index. Works in O(index) time.
     *
     * @param index  the index of the desired element
     * @return a two-element array at the index index
     */
    public String[] get(int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return new String[] {node.first, node.second};
    }

    /**
     * Remove an element at the index index. The indexes of the elements lying beyond the index index are decreased by 1.
     * Works in O(index) time.
     *
     * @param index  the index of the element to remove
     */
    public void remove(int index) {
        size--;
        if (index == 0) {
            head = head.next;
            return;
        }
        Node prevNode = head;
        Node node = head.next;
        for (int i = 1; i < index; i++) {
            prevNode = node;
            node = prevNode.next;
        }
        prevNode.next = node.next;
    }
    public int size() {
        return size;
    }

    private Node head, tail;
    private int size;
}