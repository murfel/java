package name.murfel.ftp;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class FtpClient {
    public static void sendGoodbye(@NotNull String hostName, int portNumber) throws IOException {
        try (
                Socket socket = new Socket(hostName, portNumber);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        ) {
            Logger.getAnonymousLogger().info("FtpClient: send goodbye");
            dos.writeInt(0);
            dos.flush();
        }
    }

    public static List<ServerFile> processListRequest(@NotNull String hostName, int portNumber, @NotNull String dirname) throws IOException {
        try (
                Socket socket = new Socket(hostName, portNumber);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
        ) {
            sendListRequest(dos, dirname);
            List<ServerFile> list = receiveListResponse(dis);
            Logger.getAnonymousLogger().info("FtpClient: finish processing list request");
            return list;
        }
    }

    public static void sendListRequest(@NotNull DataOutputStream dos, @NotNull String dirname) throws IOException {
        Logger.getAnonymousLogger().info("FtpClient: send list request");
        dos.writeInt(1);
        dos.writeUTF(dirname);
        dos.flush();
    }

    public static @NotNull List<ServerFile> receiveListResponse(@NotNull DataInputStream dis) throws IOException {
        Logger.getAnonymousLogger().info("FtpClient: receive list response");
        int size = dis.readInt();
        List<ServerFile> files = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            files.add(new ServerFile(dis.readUTF(), dis.readBoolean()));
        }
        return files;
    }

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

    public static void sendGetRequest(@NotNull DataOutputStream dos, @NotNull String filename) throws IOException {
        Logger.getAnonymousLogger().info("FtpClient: send get request");
        dos.writeInt(2);
        dos.writeUTF(filename);
        dos.flush();
    }

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
