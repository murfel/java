package name.murfel.ftp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FtpClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        run(hostName, portNumber);
    }

    public static DataOutputStream query(DataInputStream dis) {
        return null;
    }

    public static @Nullable ClientOrder parseUserInput(@NotNull String input) {
        Scanner scanner = new Scanner(input);
        if (!scanner.hasNextInt()) {
            System.err.println("Error parsing user input: it doesn't start with an int. Skipping.");
            return null;
        }
        int order_type = scanner.nextInt();
        String filename = scanner.nextLine().trim();
        if (scanner.hasNext()) {
            System.err.println("Warning: user input contains extra information: '" + scanner.next() + "'. Skipping.");
        }

        return new ClientOrder(order_type, filename);
    }

    private static void printClientPrompt() {
        System.out.print("Client: ");
    }


    public static void go() {

    }


    public static void run(String hostName, int portNumber) {
        try (
                Socket socket = new Socket(hostName, portNumber);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            printClientPrompt();



            while ((userInput = stdIn.readLine()) != null) {
                ClientOrder clientOrder = parseUserInput(userInput);
                if (clientOrder == null) {
                    System.out.println("Incorrect input. Ignoring.");
                    printClientPrompt();
                    continue;
                }
                dos.writeInt(clientOrder.query_type);
                dos.writeUTF(clientOrder.path);
                if (clientOrder.query_type == 1) {
                    System.out.println("User requested order type 1");
                    List<ServerFile> list = new LinkedList<>();
                    try {
                        int size = dis.readInt();
                        System.out.println("Server sent size = " + size);
                        for (int i = 0; i < size; i++) {
                            System.out.println("Reading " + (i + 1) + "th file");
                            list.add(new ServerFile(dis.readUTF(), dis.readBoolean()));
                        }
                    } catch (EOFException e) {
                        System.out.println("Couldn't verify server's response.");
                    }
                    System.out.print("Server: " + list.size() + " ");
                    for (ServerFile serverFile : list) {
                        System.out.print(serverFile.name + " " + serverFile.is_directory + " ");
                    }
                    System.out.println();
                } else if (clientOrder.query_type == 2) {
                    System.out.println("User requester order type 2");
                    long size;
                    byte[] bytes;
                    int bytesRead;
                    try {
                        size = dis.readLong();
                        bytes = new byte[(int)size];
                        bytesRead = dis.read(bytes);
                    } catch (EOFException e) {
                        System.out.println("Couldn't verify server's response. Couldn't read the file size. Skipping.");
                        printClientPrompt();
                        continue;
                    }
                    if (bytesRead != size) {
                        System.out.println("Couldn't verify server's response. " +
                                "Server sent too little number of bytes for the file content: " + bytesRead + " instead of " + size + ".");
                    }
                    System.out.print("Server: " + size + " ");
                    for (byte aByte : bytes) {
                        System.out.print(aByte);
                    }
                    System.out.println('\n');
                } else {
                    System.out.println("Query type should be either 1 or 2, not " + clientOrder.query_type + ". Ignoring.");
                }
                System.out.print("Client: ");
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
