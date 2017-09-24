package name.murfel.hw01;

/**
 * MyList implement a singly linked list data structure with MyElement objects as node data.
 */
public class MyList {
    private Node head, tail;
    private int size;

    private class Node {
        public Node next;
        public MyElement element;

        public Node(MyElement element) {
            this.element = element;
        }
    }

    /**
     * Add an element to the end of the list.
     *
     * @param element  MyElement object to add
     */
    public void add(MyElement element) {
        Node newNode = new Node(element);
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
     * Get element with the key key or null if there is no such key.
     *
     * @param key  a key to look for in the list
     * @return the element with the key key or null if there is no such key
     */
    public MyElement getElement(String key) {
        if (head == null)
            return null;
        Node node = head;
        while (true) {
            if (node == null)
                return null;
            if (node.element.getKey().equals(key))
                return node.element;
            node = node.next;
        }
    }

    /**
     * Get an element at the index index. Works in O(index) time.
     *
     * @param index  the index of the desired element
     * @return an element at the index index
     */
    public MyElement at(int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.element;
    }

    /**
     * Remove an element with the key.
     * @param key  a key of an element to delete
     * @return MyElement object with the key or null if there were no element with such key.
     */
    public MyElement remove(String key) {
        if (head == null)
            return null;
        if (head.element.getKey().equals(key)) {
            size--;
            MyElement element = head.element;
            head = head.next;
            return element;
        }
        Node prevNode = head;
        Node node = head.next;
        while (true) {
            if (node == null)
                break;
            if (node.element.getKey().equals(key)) {
                size--;
                MyElement element = node.element;
                prevNode.next = node.next;
                return element;
            }
            prevNode = node;
            node = node.next;
        }
        return null;
    }

    /**
     * Check if there is an element with such a element in the list.
     * @param key  a key to look for in the list
     * @return  true if the list contains an element with the key, false otherwise
     */
    public boolean contains(String key) {
        if (head == null)
            return false;
        Node node = head;
        while (true) {
            if (node.element.getKey().equals(key))
                return true;
            if (node.next != null)
                node = node.next;
            else
                break;
        }
        return false;
    }

    public int size() {
        return size;
    }
}
