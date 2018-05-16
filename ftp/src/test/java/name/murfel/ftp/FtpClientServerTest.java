package name.murfel.ftp;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class FtpClientServerTest {

    @Test
    public void list_files() throws InterruptedException {
        Thread serverThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ServerThread: go");
            FtpServer.start_server(12345);
        });

        Thread clientThread = new Thread(() -> {
            Logger.getAnonymousLogger().info("ClientThread: go");
            try {
                List<ServerFile> list = FtpClient.process_list_request("noxi", 12345, "./");
                Logger.getAnonymousLogger().info("ClientThread: received response");
//                FtpClient.send_goodbye("noxi", 4444);
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