import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Processor for handling files with int numbers.
 */
public class NumberProcessor {
    /**
     * Read the input file line by line. If a line consist of a single int number, then apply a function (squaring)
     * to it and write the result to the corresponding line in the output file.
     * Otherwise, write "null" on the corresponding line.
     *
     * @param input  name of the input file to read from
     * @param output  name of the output file to write to
     * @throws IOException
     */
    public static void processNumbers(File input, File output) throws IOException {
        FileInputStream fis = new FileInputStream(input);
        Reader reader = new InputStreamReader(fis);
        Scanner scanner = new Scanner(reader).useDelimiter("\\n");
        ArrayList<Maybe<Integer>> list = new ArrayList<>();
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                int x = scanner.nextInt();
                list.add(Maybe.just(x));
            }
            else {
                scanner.next();
                list.add(Maybe.nothing());
            }
        }

        FileOutputStream fos = new FileOutputStream(output);
        Writer writer = new OutputStreamWriter(fos);
        for (Maybe<Integer> maybe : list) {
            Maybe<Integer> processed = maybe.map(i -> i * i);
            writer.append(processed.isPresent() ? processed.get().toString() : "null");
            writer.write('\n');
        }
        writer.close();
    }
}
