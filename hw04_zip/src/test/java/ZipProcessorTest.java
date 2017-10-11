import java.io.File;

import static org.junit.Assert.*;

public class ZipProcessorTest {
    @org.junit.Test
    public void process() throws Exception {
        ZipProcessor.process("src/test/resources/test_unzipping/", "super.*");
        String outputDir = "src/test/resources/test_unzipping/output/";
        String[] files = {"superfolder/justfile2", "superfile0", "superfile"};
        for (String filename : files) {
            File file = new File(outputDir + filename);
            assertTrue(file.exists());
        }
    }
}