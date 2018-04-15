package name.murfel.kr1;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class Md5CalculatorTest {

    private void emptyFile(boolean singleThread) throws IOException, NoSuchAlgorithmException {
        String s;
        File emptyFile = new File("src/test/resources/emptyfile");
        if (singleThread) {
            s = Md5Calculator.singleThreadCalculator(emptyFile);
        } else {
            s = Md5Calculator.multiThreadCalculator(emptyFile);
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(new byte[0]);

        assertEquals(Md5Calculator.bytesToHexString(md.digest()), s);
    }

    @Test
    public void emptyFileSingle() throws IOException, NoSuchAlgorithmException {
        emptyFile(true);
    }

    @Test
    public void emptyFileMulti() throws IOException, NoSuchAlgorithmException {
        emptyFile(false);
    }

    private void complexHierarchy(boolean singleThread) throws IOException, NoSuchAlgorithmException {
        String s;
        File emptyFile = new File("src/test/resources/test-dir/");
        if (singleThread) {
            s = Md5Calculator.singleThreadCalculator(emptyFile);
        } else {
            s = Md5Calculator.multiThreadCalculator(emptyFile);
        }

        //test-dir
        //├── dir1
        //│   ├── dir2
        //│   ├── dir3
        //│   └── file3 = "content3"
        //├── file1 = "content1"
        //└── file2 = ""
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update("content1".getBytes());
        byte[] file1 = md.digest();
        md.reset();
        md.update("".getBytes());
        byte[] file2 = md.digest();
        md.reset();
        md.update("content3".getBytes());
        byte[] file3 = md.digest();
        md.reset();
        md.update("dir2".getBytes());
        byte[] dir2 = md.digest();
        md.reset();
        md.update("dir3".getBytes());
        byte[] dir3 = md.digest();
        md.reset();
        md.update("dir1".getBytes());
        md.update(dir2);
        md.update(dir3);
        md.update(file3);
        byte[] dir1 = md.digest();
        md.reset();
        md.update("test-dir".getBytes());
        md.update(dir1);
        md.update(file1);
        md.update(file2);
        byte[] testDir = md.digest();

        assertEquals(Md5Calculator.bytesToHexString(testDir), s);
    }

    @Test
    public void complexHierarchySingle() throws IOException, NoSuchAlgorithmException {
        complexHierarchy(true);
    }

    @Test
    public void complexHierarchyMulti() throws IOException, NoSuchAlgorithmException {
        complexHierarchy(false);
    }
}