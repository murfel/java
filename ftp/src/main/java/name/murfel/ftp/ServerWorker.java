package name.murfel.ftp;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.logging.Logger;

public class ServerWorker implements Runnable {
    DataInputStream dis;
    DataOutputStream dos;

    public ServerWorker(DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        Logger.getAnonymousLogger().info("ServerWorker: created");
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
        Logger.getAnonymousLogger().info("ServerWorker: run");
        try {
            while (true) {
                Logger.getAnonymousLogger().info("ServerWorker: start reading a request");
                int orderType = dis.readInt();
                String pathname = dis.readUTF();
                Logger.getAnonymousLogger().info("ServerWorker: process request type " + orderType + " for " + pathname);
                if (orderType == 1) {
                    sendListResponse(pathname, dos);
                } else if (orderType == 2) {
                    sendGetResponse(pathname, dos);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
