import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyHashMapAgain<K, V> implements Map<K, V> {
    public class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V v) {
            return value = v;
        }
    }

    private MySet<K> keySet;
    private Collection<V> valueCollection;
    private MyList[] data;
    private Set<Map.Entry<K, V>> entries;

    {
        clear();
    }

    /**
     * Increase the number of buckets by the factor of 2 and recalculate new buckets for all elements.
     */
    private void rehash() {
        MyList<K, V>[] oldData = data;
        MyList<K, V>[] data = new MyList[2 * keySet.size()];
        for (MyList<K, V> list : oldData) {
            for (int i = 0; i < list.size(); i++) {
                MyElement<K, V> element = list.at(i);
                put(element.getKey(), element.getValue());
            }
        }
    }

    /**
     * Get hash of the key to subscript the internal container.
     *
     * @param o the key to calculate hash for
     * @return the local hash of the key
     */
    private int getHash(Object o) {
        return (o.hashCode() + data.length) % data.length;
    }

    public int size() {
        return keySet.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object o) {
        return keySet.contains(o);
    }

    public boolean containsValue(Object o) {
        return valueCollection.contains(o);
    }

    public V get(Object o) {
        if (!keySet.contains(o))
            return null;
        else {
            MyList<K, V> list = data[getHash(o)];
            if (list == null)
                return null;
            MyElement<K, V> element = list.getElement(o);
            if (element != null)
                return element.getValue();
            return null;
        }
    }

    public V put(K k, V v) {
        V oldValue = null;
        if (keySet.contains(k)) {
            oldValue = get(k);
        }
        if (data.length == size()) {
            rehash();
        }
        if (data[getHash(k)] == null)
            data[getHash(k)] = new MyList<K, V>();
        data[getHash(k)].add(new MyElement<K, V>(k, v));
        keySet.add(k);
        valueCollection.add(v);
        entries.add(new Entry<K, V>(k, v));
        return oldValue;
    }

    public V remove(Object o) {
        if (keySet.contains(o)) {
            keySet.remove(o);
            MyElement<K, V> element = data[getHash(o)].remove(o);
            if (element != null)
                return element.getValue();
            return null;
        }
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        MySet<K> keySet = new MySet<K>();
        Collection<V> valueCollection = new MyCollection<V>();
        MyList<K, V>[] data = new MyList[1];
        entries = new MySet<java.util.Map.Entry<K, V>>();
    }

    public Set<K> keySet() {
        return keySet;
    }

    public Collection<V> values() {
        return valueCollection;
    }

    public Set<Entry<K, V>> entrySet() {
        return entries;
    }
}
