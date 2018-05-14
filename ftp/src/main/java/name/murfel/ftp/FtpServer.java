package name.murfel.ftp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FtpServer {
    public static void start_server(int portNumber) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (!serverSocket.isClosed()) {  // TODO while what?
                try (
                        Socket clientSocket = serverSocket.accept();
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                ) {
                    executorService.submit(new ServerWorker(dis, dos));
                } catch (IOException ignored) {
                }
            }
        } catch (IOException ignored) {
        }
    }
}