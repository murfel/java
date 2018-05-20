package name.murfel.ftp;

import org.junit.Test;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class FtpClientTest {
    @Test
    public void sendListRequest() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        String dirname = " .my/greatest/dirname.txt";
        FtpClient.sendListRequest(dos, dirname);

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bos.toByteArray()));
        assertEquals(1, dis.readInt());
        assertEquals(dirname, dis.readUTF());
    }

    @Test
    public void receiveListResponse() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        int expectedSize = 3;
        List<ServerFile> expected = new LinkedList<>();
        expected.add(new ServerFile("kek cheburek", true));
        expected.add(new ServerFile("meow", false));
        expected.add(new ServerFile("meow", true));

        dos.writeInt(expectedSize);
        for (int i = 0; i < expectedSize; i++) {
            dos.writeUTF(expected.get(i).name);
            dos.writeBoolean(expected.get(i).is_directory);
        }
        dos.flush();

        List<ServerFile> actual = FtpClient.receiveListResponse(
                new DataInputStream(new ByteArrayInputStream(bos.toByteArray())));

        assertEquals(expected.size(), actual.size());

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
    public void sendGetRequest() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        String filename = "hooray icecream jelly bean tuna sandwich  ";
        FtpClient.sendGetRequest(dos, filename);

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bos.toByteArray()));
        assertEquals(2, dis.readInt());
        assertEquals(filename, dis.readUTF());
    }

    @Test
    public void receiveGetResponse() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        Random random = new Random();
        int expectedSize = random.nextInt(100) + 100;
        byte[] expectedBytes = new byte[expectedSize];
        random.nextBytes(expectedBytes);
        dos.writeLong(expectedSize);
        dos.write(expectedBytes);

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bos.toByteArray()));
        OutputStream os = new ByteArrayOutputStream();
        FtpClient.receiveGetResponse(dis, os);

        byte[] actualBytes = ((ByteArrayOutputStream) os).toByteArray();
        assertEquals(expectedSize, actualBytes.length);
        for (int i = 0; i < expectedSize; i++) {
            assertEquals(expectedBytes[i], actualBytes[i]);
        }
    }

    @Test
    public void receiveGetResponseZeroSize() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeLong(0);

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bos.toByteArray()));
        OutputStream os = new ByteArrayOutputStream();
        FtpClient.receiveGetResponse(dis, os);

        byte[] actualBytes = ((ByteArrayOutputStream) os).toByteArray();
        assertEquals(0, actualBytes.length);
    }
}