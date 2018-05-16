package name.murfel.ftp;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class FtpClientServerTest {

    @Test
    public void listFiles() throws InterruptedException {
        Thread serverThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ServerThread: go");
            FtpServer.startServer(1222);
        });

        Thread clientThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ClientThread: go");
            try {
                List<ServerFile> list = FtpClient.processListRequest("noxi", 1222, "./");
                Logger.getAnonymousLogger().info("ClientThread: received response");
//                FtpClient.sendGoodbye("noxi", 4444);
                System.out.println(list.size());
                for (ServerFile file : list) {
                    System.out.println(file.name + " " + file.is_directory);
                }
            } catch (IOException ignored) {
                Logger.getAnonymousLogger().info("ClientThread: IOException");
                ignored.printStackTrace();
            }
        });

        serverThread.start();
        TimeUnit.SECONDS.sleep(1);
        clientThread.start();

        clientThread.join();
        serverThread.join();
    }
}