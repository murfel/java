import java.io.File;

import static org.junit.Assert.*;

public class ZipProcessorTest {
    @org.junit.Test
    public void testUnzippingSimple() throws Exception {
        String[] shouldBe = {"superfile"};
        String[] shouldNotBe = {"justfile"};
        runSetUp("testUnzippingSimple", "super.*", shouldBe, shouldNotBe);
    }

    @org.junit.Test
    public void testUnzippingIgnoreNonzip() throws Exception {
        String[] shouldBe = {"superfile"};
        String[] shouldNotBe = {"justfile", "superfile-external"};
        runSetUp("testUnzippingIgnoreNonzip", "super.*", shouldBe, shouldNotBe);
    }

    @org.junit.Test
    public void testUnzippingComplex() throws Exception {
        String[] shouldBe = {"superfolder/justfile2", "superfile0", "superfile"};
        String[] shouldNotBe = {"superfile-external", "justfolder/superfile2", "justfile"};
        runSetUp("testUnzippingComplex", "super.*", shouldBe, shouldNotBe);
    }

    private static void runSetUp(String testName, String pattern, String[] shouldBe, String[] shouldNotBe) {
        ZipProcessor.process("src/test/resources/" + testName + "/", pattern);
        String outputDir = "src/test/resources/" + testName + "/output/";
        for (String filename : shouldBe) {
            File file = new File(outputDir + filename);
            assertTrue(file.exists());
        }
        for (String filename : shouldNotBe) {
            File file = new File(outputDir + filename);
            assertFalse(file.exists());
        }
    }
}