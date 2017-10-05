package name.murfel.hw01;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;



public class MyListTest {
    public static String getRandomString(Random random, int length) {
        char[] buf = new char[length];
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++)
            buf[i] = symbols.charAt(random.nextInt(symbols.length()));
        return new String(buf);
    }

    @Test
    public void addSimple() throws Exception {
        MyList list = new MyList();
        list.add(new MyElement("meow", "miu"));
        assertTrue(list.size() == 1);
    }

    public void addManyRandom(int numbetOfElements, int maxLength) throws Exception {
        MyList list = new MyList();
        Random random = new Random();
        for (int i = 0; i < numbetOfElements; i++) {
            list.add(new MyElement(getRandomString(random, random.nextInt(maxLength)),
                    getRandomString(random, random.nextInt(maxLength))));
        }
        assertTrue(list.size() <= numbetOfElements);
    }

    @Test
    public void addManyShortRandom() throws Exception {
        addManyRandom(100, 4);
    }

    @Test
    public void addManyLongRandom() throws Exception {
        addManyRandom(100, 20);
    }

    @Test
    public void addMany() throws Exception {
        MyList list = new MyList();
        int numberOfElements = 100;
        for (int i = 0; i < numberOfElements; i++)
            list.add(new MyElement("k" + i, "v" + i));
        assertTrue(list.size() == numberOfElements);
    }

    @Test
    public void getElementSimple() throws Exception {
        MyList list = new MyList();
        list.add(new MyElement("meow", "miu"));
        assertEquals(list.getElement("meow").getKey(), "meow");
        assertEquals(list.getElement("meow").getValue(), "miu");
    }

    @Test
    public void getElementMultiple() throws Exception {
        MyList list = new MyList();
        int numberOfElements = 100;
        for (int i = 0; i < numberOfElements; i++)
            list.add(new MyElement("k" + i, "v" + i));
        for (int i = 0; i < numberOfElements; i++) {
            assertEquals(list.getElement("k" + i).getKey(), "k" + i);
            assertEquals(list.getElement("k" + i).getValue(), "v" + i);
        }
    }

    @Test
    public void atSimple() throws Exception {
        MyList list = new MyList();
        list.add(new MyElement("meow", "miu"));
        assertEquals("meow", list.at(0).getKey());
        assertEquals("miu", list.at(0).getValue());
    }

    @Test
    public void atMany() throws Exception {
        MyList list = new MyList();
        int numberOfElements = 100;
        for (int i = 0; i < numberOfElements; i++)
            list.add(new MyElement("k" + i, "v" + i));
        for (int i = 0; i < numberOfElements; i++) {
            assertEquals("k" + i, list.at(i).getKey());
            assertEquals("v" + i, list.at(i).getValue());
        }
    }

    @Test
    public void removeSimple() throws Exception {
        MyList list = new MyList();
        list.add(new MyElement("meow", "miu"));
        MyElement element = list.remove("meow");
        assertEquals("meow", element.getKey());
        assertEquals("miu", element.getValue());
        assertTrue(list.size() == 0);
    }

    @Test
    public void removeFromEmpty() throws Exception {
        MyList list = new MyList();
        assertNull(list.remove("meow"));
    }

    @Test
    public void removeNonPresent() throws Exception {
        MyList list = new MyList();
        list.add(new MyElement("Ducks say", "quack"));
        list.add(new MyElement("And fish go", "blub"));
        list.add(new MyElement("And the seal goes", "ow ow ow"));
        assertNull(list.remove("What does the fox say?"));
    }

    @Test
    public void removeMultiple() throws Exception {
        MyList list = new MyList();
        int numberOfElements = 100;
        for (int i = 0; i < numberOfElements; i++)
            list.add(new MyElement("k" + i, "v" + i));
        for (int i = 0; i < numberOfElements; i++) {
            MyElement element = list.remove("k" + i);
            assertTrue(list.size() == numberOfElements - i - 1);
            assertNotNull(element);
            assertEquals("k" + i, element.getKey());
            assertEquals("v" + i, element.getValue());
        }
    }

    @Test
    public void containsSimple() throws Exception {
        MyList list = new MyList();
        list.add(new MyElement("meow", "miu"));
        assertTrue(list.contains("meow"));
    }

    @Test
    public void containsMany() throws Exception {
        MyList list = new MyList();
        int numberOfElements = 100;
        for (int i = 0; i < numberOfElements; i++)
            list.add(new MyElement("k" + i, "v" + i));
        for (int i = 0; i < numberOfElements; i++)
            assertTrue(list.contains("k" + i));
    }

    @Test
    public void sizeSimple() throws Exception {
        addSimple();
    }

    @Test
    public void size() throws Exception {
        MyList list = new MyList();
        list.add(new MyElement("Dog goes", "woof"));
        assertTrue(list.size() == 1);
        list.add(new MyElement("Cat goes", "meow"));
        assertTrue(list.size() == 2);
        list.remove("Dog goes");
        assertTrue(list.size() == 1);
        list.remove("What does the fox say?");
        assertTrue(list.size() == 1);
        list.remove("Cat goes");
        assertTrue(list.size() == 0);
    }

}