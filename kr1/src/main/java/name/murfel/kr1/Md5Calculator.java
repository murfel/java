package name.murfel.kr1;


import java.io.*;
import java.nio.file.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.System.exit;

public class Md5Calculator {

    /**
     * Converts a byte array into a String of hexadecimal characters.
     *
     * @param bytes an array of bytes
     *
     * @return hex string representation of bytes array
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) return null;
        StringBuilder ret = new StringBuilder(2*bytes.length);

        for (int i = 0; i < bytes.length; i++) {
            int b;
            b = 0x0f & (bytes[i] >> 4);
            ret.append("0123456789abcdef".charAt(b));
            b = 0x0f & bytes[i];
            ret.append("0123456789abcdef".charAt(b));
        }

        return ret.toString();
    }

    private static InputStream visitFile(Path file, InputStream in) {
        InputStream newInputStream = in;
        try {
            newInputStream = new SequenceInputStream(newInputStream, new FileInputStream(file.toFile()));
        } catch (FileNotFoundException e) {
            System.out.println("An exception occurred: couldn't find a file. Terminating.");
            exit(0);
        }
        return newInputStream;
    }

    private static InputStream visitDir(Path dir, InputStream in) {
        InputStream newInputStream = in;

        ArrayList<Path> dirs = new ArrayList<>();
        ArrayList<Path> files = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry: stream) {
                if (entry.toFile().isDirectory()) {
                    dirs.add(entry);
                } else {
                    files.add(entry);
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println(x);
            System.out.println("An exception occurred. Terminating.");
            exit(0);
        }

        dirs.sort(Comparator.comparing(o -> o.toFile().getName()));
        files.sort(Comparator.comparing(o -> o.toFile().getName()));

        for (Path inner_dir : dirs) {
            InputStream dirName = new ByteArrayInputStream(inner_dir.toFile().getName().getBytes());
            newInputStream = new SequenceInputStream(newInputStream, dirName);
            newInputStream = visitDir(inner_dir, newInputStream);
        }

        for (Path file : files) {
            newInputStream = visitFile(file, newInputStream);
        }

        return newInputStream;
    }

    private static InputStream visitEntry(Path entry, InputStream in) {
        if (entry.toFile().isDirectory()) {
            return visitDir(entry, in);
        } else {
            return visitFile(entry, in);
        }
    }


    private static String singleThreadCalculator(Path entry) {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        in = visitEntry(entry, in);
        DigestInputStream dis = null;
        try {
            dis = new DigestInputStream(in, MessageDigest.getInstance("MD5"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Internal program error occurred: NoSuchAlgorithmException. Terminating.");
            exit(1);
        }

        byte[] buffer = new byte[4096];
        try {
            while (dis.read(buffer) != -1) {}
        } catch (IOException e) {
            System.out.println("An exception occurred. Terminating.");
            exit(0);
        }

        byte[] digest = dis.getMessageDigest().digest();
        return bytesToHexString(digest);
    }


    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Incorrect number of arguments. Terminating.");
            exit(1);
        }

        String fileName = args[1];

        Path p = Paths.get(fileName);
        if (!p.toFile().exists()) {
            System.out.println("File or directory " + p + "doesn't exist. Terminating.");
            exit(1);
        }
        long startTime = System.currentTimeMillis();
        String s = singleThreadCalculator(p);
        long stopTime = System.currentTimeMillis();

        System.out.println(s);
        System.out.println(stopTime - startTime);
    }
}
