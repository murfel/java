/**
 * MyElement is a wrapper for two Strings representing key and value.
 */
public class MyElement<K, V> {
    private K key;
    private V value;

    public MyElement(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}