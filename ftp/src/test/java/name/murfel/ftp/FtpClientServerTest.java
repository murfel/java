package name.murfel.ftp;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class FtpClientServerTest {
    private String HOSTNAME = null;

    {
        try {
            HOSTNAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            fail("Couldn't get local host, the tests won't be run correctly.");
        }
    }

    @Test
    public void listFiles() throws InterruptedException {
        Thread serverThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ServerThread: go");
            FtpServer.startServer(4444);
            Logger.getAnonymousLogger().info("ServerThread: reached end");
        });

        List<ServerFile> actual = new LinkedList<>();
        Boolean[] ioExceptionHappened = {false};

        Thread clientThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ClientThread: go");
            try {
                actual.addAll(FtpClient.processListRequest(HOSTNAME, 4444, "src/test/resources"));
                Logger.getAnonymousLogger().info("ClientThread: received response");
            } catch (IOException e) {
                ioExceptionHappened[0] = true;
                Logger.getAnonymousLogger().info("ClientThread: IOException");
                e.printStackTrace();
            }
            Logger.getAnonymousLogger().info("ClientThread: reached end");
        });

        serverThread.start();
        TimeUnit.MILLISECONDS.sleep(500);
        clientThread.start();

        clientThread.join();
        // and the serverThread will terminate with PSVM

        assertFalse(ioExceptionHappened[0]);

        List<ServerFile> expected = Arrays.asList(
                new ServerFile("dir1", true),
                new ServerFile("file1", false),
                new ServerFile("file2", false));

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
    public void getFile() throws InterruptedException, IOException {
        Thread serverThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ServerThread: go");
            FtpServer.startServer(5555);
            Logger.getAnonymousLogger().info("ServerThread: reached end");
        });

        FileOutputStream[] fos = new FileOutputStream[1];

        String filename = "src/test/resources/file1";
        String copyFilename = filename + ".copy";

        try {
            fos[0] = new FileOutputStream(copyFilename );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Logger.getAnonymousLogger().info("Couldn't create file to write to, so can't test properly");
            fail();
        }

        Boolean[] ioExceptionHappened = {false};

        Thread clientThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ClientThread: go");
            try {
                FtpClient.processGetRequest(HOSTNAME, 5555, filename, fos[0]);
            } catch (IOException e) {
                ioExceptionHappened[0] = true;
                Logger.getAnonymousLogger().info("ClientThread: IOException");
                e.printStackTrace();
            }
            Logger.getAnonymousLogger().info("ServerThread: reached end");
        });

        serverThread.start();
        TimeUnit.MILLISECONDS.sleep(500);
        clientThread.start();

        clientThread.join();
        // and the serverThread will terminate with PSVM

        assertFalse(ioExceptionHappened[0]);

        // use Apache commons IO's FileUtils.contentEquals if you want to compare long files
        byte[] expected = Files.readAllBytes(Paths.get(filename));
        byte[] actual = Files.readAllBytes(Paths.get(copyFilename ));
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
        Files.deleteIfExists(Paths.get(copyFilename));
    }
}