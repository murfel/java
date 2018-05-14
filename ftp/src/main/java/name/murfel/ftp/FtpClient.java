package name.murfel.ftp;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class FtpClient {
    public static List<ServerFile> process_list_request(@NotNull String hostName, int portNumber, @NotNull String dirname) throws IOException {
        try (
                Socket socket = new Socket(hostName, portNumber);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
        ) {
            send_list_request(dos, dirname);
            return receive_list_response(dis);
        }
    }

    public static void send_list_request(@NotNull DataOutputStream dos, @NotNull String dirname) throws IOException {
        dos.writeInt(1);
        dos.writeUTF(dirname);
    }

    public static @NotNull List<ServerFile> receive_list_response(@NotNull DataInputStream dos) throws IOException {
        int size = dos.readInt();
        List<ServerFile> files = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            files.add(new ServerFile(dos.readUTF(), dos.readBoolean()));
        }
        return files;
    }

    public static void process_get_request(@NotNull String hostName, int portNumber, @NotNull String filename, @NotNull OutputStream os) throws IOException {
        try (
                Socket socket = new Socket(hostName, portNumber);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
        ) {
            send_get_request(dos, filename);
            receive_get_response(dis, os);
        }
    }

    public static void send_get_request(@NotNull DataOutputStream dos, @NotNull String filename) throws IOException {
        dos.writeInt(2);
        dos.writeUTF(filename);
    }

    public static void receive_get_response(@NotNull DataInputStream dis, @NotNull OutputStream os) throws IOException {
        long bytes_need = dis.readLong();
        BufferedInputStream bis = new BufferedInputStream(dis);
        int BUFFER_SIZE = 4096;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytes_read;
        while ((bytes_read = bis.read(buffer, 0, (int) Math.min((long) BUFFER_SIZE, bytes_need))) != -1) {
            bytes_need -= bytes_read;
            os.write(buffer, 0, bytes_read);
        }
    }
}
