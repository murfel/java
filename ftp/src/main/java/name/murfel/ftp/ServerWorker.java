package name.murfel.ftp;

import org.jetbrains.annotations.NotNull;
import sun.rmi.runtime.Log;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerWorker implements Runnable {
    private Socket clientSocket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Boolean inInconsistentState = false;

    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            dos = new DataOutputStream(clientSocket.getOutputStream());
            dis = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            inInconsistentState = true;
            try {
                clientSocket.close();
            } catch (IOException ignored) {
            }
        }
        if (!inInconsistentState) {
            Logger.getAnonymousLogger().info("ServerWorker: created");
        } else {
            Logger.getAnonymousLogger().info("ServerWorker: could not create, left in an inconsistent state");
        }
    }

    public static void sendListResponse(@NotNull String dirname, @NotNull DataOutputStream dos) throws IOException {
        File dir = new File(dirname);
        if (!dir.exists() || !dir.isDirectory()) {
            dos.writeInt(0);
            dos.flush();
            return;
        }

        File[] files = dir.listFiles();

        if (files == null) {
            dos.write(0);
            dos.flush();
            return;
        }

        dos.writeInt(files.length);
        for (File file : files) {
            dos.writeUTF(file.getName());
            dos.writeBoolean(file.isDirectory());
        }
        dos.flush();
    }

    public static void sendGetResponse(@NotNull String filename, @NotNull DataOutputStream dos) throws IOException {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            dos.writeInt(0);
            dos.flush();
            return;
        }

        dos.writeLong(file.length());
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            dos.writeInt(0);
            dos.flush();
            return;
        }
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            dos.write(buffer, 0, bytesRead);
        }
        dos.flush();
    }

    @Override
    public void run() {
        if (inInconsistentState) {
            return;
        }
        Logger.getAnonymousLogger().info("ServerWorker: run");
        try {
            while (!clientSocket.isClosed()) {
                Logger.getAnonymousLogger().info("ServerWorker: start reading a request");
                int orderType;
                try {
                    orderType = dis.readInt();
                } catch (EOFException e) {
                    Logger.getAnonymousLogger().info("ServerWorker: received EOF, closing socket");
                    clientSocket.close();
                    break;
                }
                String pathname = dis.readUTF();
                Logger.getAnonymousLogger().info("ServerWorker: process request type " + orderType + " for " + pathname);
                if (orderType == 1) {
                    sendListResponse(pathname, dos);
                } else if (orderType == 2) {
                    sendGetResponse(pathname, dos);
                } else {
                    Logger.getAnonymousLogger().info("ServerWorker: received unknown order, closing socket");
                    clientSocket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
