import org.junit.Test;
import sp.SecondPartTasks;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

public class SecondPartTasksTest {
    ArrayList<String> files = new ArrayList<>(Arrays.asList(
        "src/main/resources/file1",
        "src/main/resources/file2",
        "src/main/resources/file3",
        "src/main/resources/file4"
    ));


    @Test
    public void testFindQuotes() {
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(
                "Tridek tri bovinoj",
                "The term trie was coined two years later by Edward Fredkin",
                "who pronounces it /ˈtriː/ (as \"tree\")"
        ));
        List<String> actual = SecondPartTasks.findQuotes(files, "tri");
        assertEquals(expected, actual);
    }

    @Test
    public void testPiDividedBy4() {
        double x = SecondPartTasks.piDividedBy4();
        assertTrue(x >= 0);
        assertTrue(x <= 1);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> compositions = new HashMap<>();
        compositions.put("Mary Poppins", new ArrayList<>(Arrays.asList("Tridek tri bovinoj")));
        compositions.put("Wikipedia", new ArrayList<>(Arrays.asList("Tries were first described by René de la Briandais in 1959", "Bugarama is a town in western Rwanda.")));
        compositions.put("Ernst Toch", new ArrayList<>(Arrays.asList("Trinidad!")));
        String expected = "Wikipedia";
        String actual = SecondPartTasks.findPrinter(compositions);
    }

    @Test
    public void testCalculateGlobalOrder() {
        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("sleep", 5);
        expected.put("great sleep", -1);
        expected.put("classes", 555);

        HashMap<String, Integer> monday = new HashMap<>();
        monday.put("sleep", 3);
        monday.put("classes", 5);

        HashMap<String, Integer> tuesday = new HashMap<>();
        tuesday.put("sleep", 2);
        tuesday.put("classes", 50);

        HashMap<String, Integer> wednesday = new HashMap<>();
        wednesday.put("sleep", 0);
        wednesday.put("great sleep", -1);
        wednesday.put("classes", 500);

        List<Map<String, Integer>> list = new ArrayList<>();
        list.add(monday);
        list.add(tuesday);
        list.add(wednesday);

        Map<String, Integer> actual = SecondPartTasks.calculateGlobalOrder(list);

        assertEquals(expected, actual);
    }
}