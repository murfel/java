package name.murfel.java.hw07;

import java.util.*;

import static org.junit.Assert.*;

public class MyTreeSetClassTest {
    @org.junit.Test
    public void iterator() throws Exception {
        MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
        assertFalse(set.iterator().hasNext());
        List<Integer> list = Arrays.asList(1, 2, 3);
        set.addAll(list);
        int i = 0;
        assertTrue(set.iterator().hasNext());
        for (Integer element : set) {
            assertEquals(list.get(i), element);
            i++;
        }
    }

    @org.junit.Test
    public void addBasic() throws Exception {
        MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
        for (int i = 0; i < 3; i++) {
            set.add(i);
        }
    }

    @org.junit.Test
    public void addSize() throws Exception {
        MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
        for (int i = 0; i < 3; i++) {
            set.add(i);
            assertEquals(i + 1, set.size());
        }
    }

    @org.junit.Test
    public void descendingIterator() throws Exception {
    }

    @org.junit.Test
    public void descendingSetSizeFirstLastLowerFloorHigherCeiling() throws Exception {
        MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 1, 8, 4, 9));
        Comparator<Integer> comparator = Comparator.naturalOrder();
        Comparator<Integer> reversedComparator = comparator.reversed();
        list.sort(reversedComparator);
        set.addAll(list);
        MyTreeSet<Integer> descendingSet = set.descendingSet();
        assertEquals(list.size(), descendingSet.size());
        assertEquals(9, (int) descendingSet.first());
        assertEquals(1, (int) descendingSet.last());
        assertEquals(5, (int) descendingSet.lower(4));
        assertEquals(8, (int) descendingSet.lower(5));
        assertEquals(4, (int) descendingSet.floor(4));
        assertEquals(8, (int) descendingSet.floor(6));
        assertEquals(3, (int) descendingSet.higher(4));
        assertEquals(5, (int) descendingSet.higher(6));
        assertEquals(4, (int) descendingSet.ceiling(4));
        assertEquals(5, (int) descendingSet.ceiling(6));
    }

    @org.junit.Test
    public void descendingSetIterator() throws Exception {
        MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 1, 8, 4, 9));
        Comparator<Integer> comparator = Comparator.naturalOrder();
        Comparator<Integer> reversedComparator = comparator.reversed();
        list.sort(reversedComparator);
        set.addAll(list);
        MyTreeSet<Integer> descendingSet = set.descendingSet();
        list.sort(reversedComparator);
        int i = 0;
        for (Integer element : descendingSet) {
            assertEquals(list.get(i), element);
            i++;
        }
        assertEquals(list.size(), i);
    }

    @org.junit.Test
    public void firstLast() throws Exception {
        MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 1, 8, 4, 9));
        set.addAll(list);
        list.sort(Comparator.naturalOrder());
        assertEquals(list.get(0), set.first());
        assertEquals(list.get(list.size() - 1), set.last());
    }

    @org.junit.Test
    public void firstLastReversed() throws Exception {
        @SuppressWarnings("unchecked")
        MyTreeSetClass<Integer> set = new MyTreeSetClass<>((Comparator<Integer>) Comparator.naturalOrder().reversed());
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 1, 8, 4, 9));
        set.addAll(list);
        list.sort(Comparator.naturalOrder());
        assertEquals(list.get(list.size() - 1), set.first());
        assertEquals(list.get(0), set.last());
    }

    @org.junit.Test
    public void lower() throws Exception {
        Random random = new Random();
        random.setSeed(2018);
        for (int dummy = 0; dummy < 10; dummy++) {
            MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
            int size = random.nextInt(20);
            ArrayList<Integer> list = new ArrayList<>();
            while (list.size() < size) {
                int element = random.nextInt(20);
                if (!list.contains(element)) {
                    list.add(element);
                }
            }
            set.addAll(list);
            list.sort(Comparator.naturalOrder());
            int i = random.nextInt(list.size() - 1) + 1;
            assertEquals(list.get(i - 1), set.lower(list.get(i)));
            if (list.get(i - 1) < list.get(i) - 1) {
                assertEquals(list.get(i - 1), set.lower(list.get(i)));
            }
        }
    }

    @org.junit.Test
    public void floor() throws Exception {
        MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 1, 8, 4, 9));
        set.addAll(list);
        for (Integer element : list) {
            assertEquals(element, set.floor(element));
        }
        assertNull(set.floor(0));
        assertEquals(1, (int) set.floor(2));
        assertEquals(5, (int) set.floor(6));
        assertEquals(5, (int) set.floor(7));
        assertEquals(9, (int) set.floor(10));
    }

    @org.junit.Test
    public void higher() throws Exception {
        Random random = new Random();
        random.setSeed(2018);
        for (int dummy = 0; dummy < 10; dummy++) {
            MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
            int size = random.nextInt(20);
            ArrayList<Integer> list = new ArrayList<>();
            while (list.size() < size) {
                int element = random.nextInt(20);
                if (!list.contains(element)) {
                    list.add(element);
                }
            }
            set.addAll(list);
            list.sort(Comparator.naturalOrder());
            int i = random.nextInt(list.size() - 1);
            assertEquals(list.get(i + 1), set.higher(list.get(i)));
            if (list.get(i + 1) > list.get(i) + 1) {
                assertEquals(list.get(i + 1), set.higher(list.get(i)));
            }
        }
    }

    @org.junit.Test
    public void ceiling() throws Exception {
        MyTreeSetClass<Integer> set = new MyTreeSetClass<>();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 1, 8, 4, 9));
        set.addAll(list);
        for (Integer element : list) {
            assertEquals(element, set.ceiling(element));
        }
        assertEquals(3, (int) set.ceiling(2));
        assertEquals(8, (int) set.ceiling(6));
        assertEquals(8, (int) set.ceiling(7));
        assertNull(set.ceiling(10));
    }
}