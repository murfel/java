package name.murfel.ftp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class FtpServer {
    public static void startServer(int portNumber) {
        startServer(portNumber, 4);
    }

    public static void startServer(int portNumber, int nThreads) {
        Logger.getAnonymousLogger().info("FtpServer: starting");
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                ) {
                    executorService.submit(new ServerWorker(dis, dos));
                    Logger.getAnonymousLogger().info("FtpServer: submitted a worker");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}