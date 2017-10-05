package name.murfel.hw01;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyHashMapTest {
    @Test
    public void sizeEmpty() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        assertEquals(0, hashMap.size());
    }

    @Test
    public void containsEmpty() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        assertFalse(hashMap.contains("meow"));
    }

    @Test
    public void containsSimple() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("meow", "mur");
        assertTrue(hashMap.contains("meow"));
    }

    @Test
    public void containsSimpleNegative() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("meow", "mur");
        assertFalse(hashMap.contains("mur"));
    }

    @Test
    public void containsMany() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        int numberOfElements = 100;
        for (int i = 0; i < numberOfElements; i++)
            hashMap.put("k" + i, "v" + i);
        for (int i = 0; i < numberOfElements; i++)
            assertTrue(hashMap.contains("k" + i));
    }

    @Test
    public void getFromEmpty() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        assertNull(hashMap.get("meow"));
    }

    @Test
    public void getSimple() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("meow", "mur");
        assertEquals("mur", hashMap.get("meow"));
    }

    @Test
    public void getSimpleByValueNegative() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("meow", "mur");
        assertNull(hashMap.get("mur"));
    }

    @Test
    public void getSimpleTwice() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("meow", "mur");
        assertEquals("mur", hashMap.get("meow"));
    }

    @Test
    public void getMany() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        int numberOfElements = 100;
        for (int i = 0; i < numberOfElements; i++)
            hashMap.put("k" + i, "v" + i);
        for (int i = 0; i < numberOfElements; i++)
            assertEquals("v" + i, hashMap.get("k" + i));
    }

    @Test
    public void putSimple() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        assertNull(hashMap.put("meow", "mur"));
        assertEquals(1, hashMap.size());
    }

    @Test
    public void putTheSameTwice() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("meow", "mur");
        assertEquals("mur", hashMap.put("meow", "tweet"));
        assertEquals(1, hashMap.size());
    }

    @Test
    public void putMany() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        int numberOfElements = 17;
        for (int i = 0; i < numberOfElements; i++)
            hashMap.put("k" + i, "v" + i);
        assertEquals(numberOfElements, hashMap.size());
    }

    @Test
    public void removeFromEmpty() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        assertNull(hashMap.remove("meow"));
    }

    @Test
    public void removeSimple() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("meow", "mur");
        assertEquals("mur", hashMap.remove("meow"));
        assertEquals(0, hashMap.size());
        assertNull(hashMap.get("meow"));
    }

    @Test
    public void removeSimpleNegative() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("meow", "mur");
        assertNull(hashMap.remove("tweet"));
        assertEquals(1, hashMap.size());
        assertEquals("mur", hashMap.get("meow"));
    }

    @Test
    public void removeMany() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        int numberOfElements = 100;
        for (int i = 0; i < 100; i++)
            hashMap.put("k" + 1, "v" + 1);
        for (int i = 0; i < 100; i++) {
            assertEquals("v" + i, hashMap.remove("k" + i));
            assertNull(hashMap.get("k" + i));
            assertEquals(100 - i - 1, hashMap.size());
        }
    }

    @Test
    public void clearEmpty() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.clear();
        assertEquals(0, hashMap.size());
    }

    @Test
    public void clearTwice() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.clear();
        hashMap.clear();
        assertEquals(0, hashMap.size());
    }

    @Test
    public void clearSimple() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("meow", "mur");
        hashMap.clear();
        assertEquals(0, hashMap.size());
        assertFalse(hashMap.contains("meow"));
    }

    @Test
    public void clearMany() throws Exception {
        MyHashMap hashMap = new MyHashMap();
        int numberOfElements = 100;
        for (int i = 0; i < numberOfElements; i++)
            hashMap.put("k" + i, "v" + i);
        hashMap.clear();
        assertEquals(0, hashMap.size());
        for (int i = 0; i < numberOfElements; i++)
            assertFalse(hashMap.contains("k" + i));
    }

}