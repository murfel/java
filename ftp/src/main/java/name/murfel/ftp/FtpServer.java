package name.murfel.ftp;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpServer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java FtpServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        run(portNumber);
    }

    public static void run(int portNumber) {
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
        ) {
            System.out.println("Received a connection from a client.");
            while (true) {  // TODO: while not closed?
                System.out.println("Start processing a new order");
                ClientOrder clientOrder = new ClientOrder();
                try {
                    clientOrder.query_type = dis.readInt();
                    clientOrder.path = dis.readUTF();
                } catch (EOFException e) {
                    System.err.println("Error: The input stream reaches the end before reading all the required bytes.");
                    dos.writeInt(0);
                }
                processInput(clientOrder, dos);
            }
        } catch (IOException e) {
            // TODO: clarify exception message by listing all possible causes (read javadocs for throwing methods)
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }


    public static void processInput(ClientOrder clientOrder, @NotNull DataOutputStream dos) throws IOException {
        System.out.println("Start processing input: " + clientOrder.query_type + " " + clientOrder.path);

        File path = null;
        if (clientOrder.query_type== 1 || clientOrder.query_type == 2) {
            path = new File(clientOrder.path);
            if (!path.exists()) {
                System.err.println(clientOrder.path);
                System.err.println("The abstract pathname doesn't exist.");
                dos.writeInt(0);
                return;
            }
        }

        if (clientOrder.query_type == 1) {
            if (!path.isDirectory()) {
                System.err.println("The abstract pathname is not a directory.");
                dos.write(0);
                return;
            }
            File[] files = path.listFiles();
            if (files == null) {
                System.err.println("Error: The abstract pathname does not denote a directory, or an I/O error occurred.");
                dos.write(0);
                return;
            }
            dos.writeInt(files.length);
            for (File file : files) {
                dos.writeUTF(file.getName());
                dos.writeBoolean(file.isDirectory());
            }
        } else if (clientOrder.query_type == 2) {
            // directory is not a file
            if (!path.isFile()) {
                System.err.println("The abstract pathname is not a file.");
                dos.writeInt(0);
                return;
            }
            try {
                dos.writeLong(path.length());
                FileInputStream fis = new FileInputStream(path);
                byte[] buffer = new byte[4096];
                int bytesRead = 0;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, bytesRead);
                }
            } catch (FileNotFoundException e) {
                System.err.println("The abstract pathname doesn't exist.");
                dos.writeInt(0);
            }
        } else {
            System.err.println("Error: query type should be either 1 or 2, not " + clientOrder.query_type);
            dos.writeInt(0);
        }
    }
}


