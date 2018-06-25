package name.murfel.ftp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Ftp Server stores a hierarchy of files and allows a client to list or download them.
 */
public class FtpServer {
    /**
     * Run a server from the command line.
     *
     * @param args first argument is a port number
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide a port number");
            return;
        }
        try {
            int port = Integer.valueOf(args[0]);
            if (port < 0 || port > 65535) {
                System.out.println("Please provide a port number in the range from 0 to 65535 inclusive");
                return;
            }
            startServer(port);
        } catch (NumberFormatException e) {
            System.out.println("Please provide a port number");
            return;
        }
    }

    /**
     * Starts server with 4 threads, i.e. max number of client connections is 4, on port {@code portNumber}.
     *
     * @param portNumber port number where server should be run
     */
    public static void startServer(int portNumber) {
        startServer(portNumber, 4);
    }

    /**
     * Start server with {@code nThreads} number of threads on port {@code portNumber}.
     * <p>
     * When a client connects to the server, the server starts waiting for client to send one of the supported requests:
     * <p>
     * <1: int><dirname: UTF string> for listing the files stored in the dirname directory. The server answers in the form of:
     * <size: int>(<name: UTF string><is_dir: boolean>)*. If no such directory exists, server sends 0 for size.
     * <p>
     * <2: int><filename: UTF string> for sending the content of the file to the client. The server answers in the form of:
     * <2: int><filename: UTF string>. If no such file exists, server sends 0 for size.
     * <p>
     * If server receives something not conforming to the orders listed above, it immediately terminates the connection
     * with the client without making any attempts to recuperate.
     *
     * @param portNumber port number where server should be run
     * @param nThreads   number of thread, i.e. max number of client connections
     */
    public static void startServer(int portNumber, int nThreads) {
        Logger.getAnonymousLogger().info("FtpServer: starting");
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();

                    executorService.submit(new ServerWorker(clientSocket));
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