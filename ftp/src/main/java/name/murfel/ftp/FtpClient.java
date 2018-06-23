package name.murfel.ftp;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 */
public class FtpClient {
    /**
     * Send a request to a server and received a server's response to the list command.
     * The list command shows files contained in a directory specified by {@code dirname}.
     *
     * @param hostName   host name of the server
     * @param portNumber port number where server is running
     * @param dirname    directory name on the server side which should be listed
     * @return a list of file descriptions of files in the dirname directory on server
     * @throws IOException if an IO exception occurred during connection, reading, or writing
     */
    public static List<ServerEntity> processListRequest(@NotNull String hostName, int portNumber, @NotNull String dirname) throws IOException {
        try (
                Socket socket = new Socket(hostName, portNumber);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
        ) {
            sendListRequest(dos, dirname);
            List<ServerEntity> list = receiveListResponse(dis);
            Logger.getAnonymousLogger().info("FtpClient: finish processing list request");
            return list;
        }
    }

    /**
     * Sends a list request to server in the form of <1: int><dirname: UTF string>.
     *
     * @param dos     the server's output stream
     * @param dirname directory name on the server side which should be listed
     * @throws IOException if an IO exception occurred during writing to server
     */
    public static void sendListRequest(@NotNull DataOutputStream dos, @NotNull String dirname) throws IOException {
        Logger.getAnonymousLogger().info("FtpClient: send list request");
        dos.writeInt(1);
        dos.writeUTF(dirname);
        dos.flush();
    }

    /**
     * Receives a list response from server in the form of <size: int>(<name: UTF string><is_dir: boolean>)*.
     *
     * @param dis the server's input stream
     * @return a list of file descriptions of files in the dirname directory on server
     * @throws IOException if an IO exception occurred during reading from server
     */
    public static @NotNull List<ServerEntity> receiveListResponse(@NotNull DataInputStream dis) throws IOException {
        Logger.getAnonymousLogger().info("FtpClient: receive list response");
        int size = dis.readInt();
        List<ServerEntity> files = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            files.add(new ServerEntity(dis.readUTF(), dis.readBoolean()));
        }
        return files;
    }

    /**
     * Send a request to a server and received a server's response to the get command.
     * The get command downloads file {@code filename} from server.
     *
     * @param hostName   host name of the server
     * @param portNumber port number where server is running
     * @param filename   file name on the server side which should be downloaded
     * @param os         where to write to the content of the file received from the server
     * @throws IOException if an IO exception occurred during connection, reading, or writing
     */
    public static void processGetRequest(@NotNull String hostName, int portNumber, @NotNull String filename, @NotNull OutputStream os) throws IOException {
        try (
                Socket socket = new Socket(hostName, portNumber);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
        ) {
            sendGetRequest(dos, filename);
            receiveGetResponse(dis, os);
            Logger.getAnonymousLogger().info("FtpClient: finish processing get request");
        }
    }

    /**
     * Sends a get request to server in the form of <2: int><filename: UTF string>.
     *
     * @param dos      the server's output stream
     * @param filename file name on the server side which should be downloaded
     * @throws IOException if an IO exception occurred during writing to server
     */
    public static void sendGetRequest(@NotNull DataOutputStream dos, @NotNull String filename) throws IOException {
        Logger.getAnonymousLogger().info("FtpClient: send get request");
        dos.writeInt(2);
        dos.writeUTF(filename);
        dos.flush();
    }

    /**
     * Received the get response from server in the form of <size: long><content: bytes>.
     *
     * @param dis the server's input stream
     * @param os  where to write to the content of the file received from the server
     * @throws IOException if an IO exception occurred during reading from server
     */
    public static void receiveGetResponse(@NotNull DataInputStream dis, @NotNull OutputStream os) throws IOException {
        Logger.getAnonymousLogger().info("FtpClient: receive get response");
        long bytes_need = dis.readLong();
        Logger.getAnonymousLogger().info("FtpClient: file size is " + bytes_need);
        BufferedInputStream bis = new BufferedInputStream(dis);
        int BUFFER_SIZE = 4096;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytes_read;
        while ((bytes_read = bis.read(buffer, 0, (int) Math.min((long) BUFFER_SIZE, bytes_need))) > 0) {
            bytes_need -= bytes_read;
            Logger.getAnonymousLogger().info("FtpClient: bytes read: " + bytes_need + ", bytes need: " + bytes_need);
            os.write(buffer, 0, bytes_read);
        }
        os.flush();
        Logger.getAnonymousLogger().info("FtpClient: finish reading bytes ");
    }
}
