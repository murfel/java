import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class NumberProcessorTest {
    @org.junit.Test
    public void processNumbersBasic() throws Exception {
        File input = new File("test_input.txt");
        File output = new File("test_output.txt");
        FileOutputStream fos = new FileOutputStream(input);
        Writer writer = new OutputStreamWriter(fos);
        writer.write("2\nmeow cow\n3");
        writer.close();

        NumberProcessor.processNumbers(input, output);

        FileInputStream fis = new FileInputStream(output);
        Reader reader = new InputStreamReader(fis);
        Scanner scanner = new Scanner(reader).useDelimiter("\n");
        String[] expected = {"4", "null", "9"};
        int matched = 0;
        assertTrue(scanner.hasNext());
        while (scanner.hasNext()) {
            assertTrue(scanner.next().equals(expected[matched]));
            matched++;
        }
    }

}