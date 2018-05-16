package name.murfel.ftp;

import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class FtpClientServerTest {

    @Test
    public void listFiles() throws InterruptedException {
        Thread serverThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ServerThread: go");
            FtpServer.startServer(4444);
            Logger.getAnonymousLogger().info("ClientThread: reached end");
        });

        Thread clientThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ClientThread: go");
            try {
                List<ServerFile> list = FtpClient.processListRequest(InetAddress.getLocalHost().getHostName(), 4444, "./");
                Logger.getAnonymousLogger().info("ClientThread: received response");
                FtpClient.sendGoodbye("noxi", 4444);
                System.out.println(list.size());
                for (ServerFile file : list) {
                    System.out.println(file.name + " " + file.is_directory);
                }
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("ClientThread: IOException");
                e.printStackTrace();
            }
            Logger.getAnonymousLogger().info("ClientThread: reached end");
        });

        serverThread.start();
        TimeUnit.MILLISECONDS.sleep(500);
        clientThread.start();

        clientThread.join();
        // serverThread will terminate with PSVM
    }
}