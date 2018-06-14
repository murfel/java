package name.murfel.ftp;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ServerWorkerTest {

    @Test
    public void sendListResponse() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        ServerWorker.sendListResponse("src/test/resources", dos);

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bos.toByteArray()));

        List<ServerFile> expected = Arrays.asList(
                new ServerFile("dir1", true),
                new ServerFile("file1", false),
                new ServerFile("file2", false));

        int actualLength = dis.readInt();
        assertEquals(expected.size(), actualLength);

        List<ServerFile> actual = new LinkedList<>();
        for (int i = 0; i < actualLength; i++) {
            actual.add(new ServerFile(dis.readUTF(), dis.readBoolean()));
        }

        expected.sort(Comparator.comparing(ServerFile::getName).thenComparing(ServerFile::getIsDirectory));
        actual.sort(Comparator.comparing(ServerFile::getName).thenComparing(ServerFile::getIsDirectory));

        for (int i = 0; i < expected.size(); i++) {
            ServerFile expectedFile = expected.get(i);
            ServerFile actualFile = actual.get(i);
            assertEquals(expectedFile.name, actualFile.name);
            assertEquals(expectedFile.is_directory, actualFile.is_directory);
        }
    }

    @Test
    public void sendGetResponse() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        ServerWorker.sendGetResponse("src/test/resources/file1", dos);

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bos.toByteArray()));
        long actualSize = dis.readLong();
        byte[] actual = new byte[(int) actualSize];
        assertEquals(actualSize, dis.read(actual));

        byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/file1"));
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}