package name.murfel.hw01;

public class MyHashMap {
    public int size() {
        return size;
    }

    /**
     * Check whether the element with the key key is present in the hash map.
     * Average-case performance is O(1) and the worst-case performance is O(size()) of String.equal() operations
     * plus one call to the String.hashCode() method.
     *
     * @param key  a key to search for in the hash map
     * @return true if there is an element with the key key, false otherwise
     */
    public boolean contains(String key) {
        MyList list = data[getHash(key)];
        if (list == null) return false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[0].equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the element with the key key.
     * Average-case performance is O(1) and the worst-case performance is O(size()) of String.equal() operations
     * plus one call to the String.hashCode() method.
     *
     * @param key  the key of the desired element
     * @return the value corresponding to the key key or null if there is no such key in the hash map
     */
    public String get(String key) {
        MyList list = data[getHash(key)];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[0].equals(key)) {
                return list.get(i)[1];
            }
        }
        return null;
    }

    /**
     * Put an element with the key key and the value value into the hash map.
     * If there is already an element with the key key, the old element is replaced with the new provided one.
     * <p>
     * Each element is identified by its key. A key is unique within one hash map object.
     * The element value can be arbitrary and is not necessarily unique.
     * <p>
     * The hash map attempts to provide an average-case O(1) lookup time.
     * The elements are stored in buckets based on hashCode of their key.
     * If two elements with different keys have the same hash,
     * the collision is resolved by putting them into a list inside that hash bucket.
     * <p>
     * Average-case performance is O(1) amortized and the worst-case performance is O(size()).
     * Also, if the internal maximum capacity is reached, a rehashing of the whole data structure happens
     * which expands the capacity by the factor of 2, thus slowing down one individual operation.
     *
     * @param key  the key of the element to insert
     * @param value  the value of the element to insert
     * @return the old value of the key key or null if there were no such key
     */
    public String put(String key, String value) {
        String oldValue = null;
        if (contains(key)) {
            oldValue = get(key);
        }
        if (data.length == size()) {
            rehash();
        }
        if (data[getHash(key)] == null)
            data[getHash(key)] = new MyList();
        data[getHash(key)].add(key, value);
        size++;
        return oldValue;
    }

    /**
     * Remove the element with the key key from the hash map and returns the value of the removed element.
     *
     * @param key  the key of the element to remove
     * @return the value of the removed element or null if there were no such element
     */
    public String remove(String key) {
        String value = null;
        if (contains(key)) {
            value = get(key);
            MyList list = data[getHash(key)];
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i)[0].equals(key)) {
                    list.remove(i);
                    break;
                }
            }
            size--;
        }
        return value;
    }

    /**
     * Remove all elements from the hash map and sets the internal capacity to 1.
     * The object becomes identical to a newly constructed MyHashMap.
     */
    public void clear() {
        data = new MyList[1];
        size = 0;
    }

    private MyList[] data = new MyList[1];
    private int size = 0;

    /**
     * Increase the number of buckets by the factor of 2 and recalculate new buckets for all elements.
     */
    private void rehash() {
        MyList[] oldData = data;
        MyList[] data = new MyList[2 * size];
        size = 0;
        for (MyList list : oldData) {
            for (int i = 0; i < list.size(); i++) {
                put(list.get(i)[0], list.get(i)[1]);
            }
        }
    }

    /**
     * Get hash of the key to subscript the internal container.
     * @param key  the key to calculate hash for
     * @return the local hash of the key
     */
    private int getHash(String key) {
        return key.hashCode() % data.length;
    }
}