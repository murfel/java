package name.murfel.kr1;


import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;

import static java.lang.System.exit;

public class Md5Calculator {

    /**
     * Converts a byte array into a String of hexadecimal characters.
     *
     * @param bytes an array of bytes
     * @return hex string representation of bytes array
     */
    private static String bytesToHexString(byte[] bytes) {
        if (bytes == null) return null;
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte aByte : bytes) {
            int b;
            b = 0x0f & (aByte >> 4);
            hexString.append("0123456789abcdef".charAt(b));
            b = 0x0f & aByte;
            hexString.append("0123456789abcdef".charAt(b));
        }
        return hexString.toString();
    }

    private static byte[] visitFile(File file) throws IOException, NoSuchAlgorithmException {
        InputStream in;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("Warning: A file " + file.getName() +
                    " which existed no longer exists. Skipping.");
            return new byte[0];
        }
        MessageDigest digest = MessageDigest.getInstance("MD5");
        DigestInputStream digestInputStream = new DigestInputStream(in, digest);

        byte[] buffer = new byte[4096];
        //noinspection StatementWithEmptyBody
        while (digestInputStream.read(buffer) != -1) {
        }
        return digestInputStream.getMessageDigest().digest();
    }

    private static byte[] visitDirectory(File directory) throws IOException, NoSuchAlgorithmException {
        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Warning: A directory " + directory.getName() +
                    " which existed no longer exists. Skipping.");
            return new byte[0];
        }
        Arrays.sort(files, Comparator.comparing(File::getName));
        ByteArrayOutputStream hashes = new ByteArrayOutputStream();
        hashes.write(directory.getName().getBytes());
        for (File entry : files) {
            byte[] hash = visitEntry(entry);
            hashes.write(hash);
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(hashes.toByteArray());
        return md.digest();
    }

    private static byte[] visitEntry(File entry) throws IOException, NoSuchAlgorithmException {
        if (entry.isDirectory()) {
            return visitDirectory(entry);
        } else {
            return visitFile(entry);
        }
    }

    private static String singleThreadCalculator(File entry) throws IOException, NoSuchAlgorithmException {
        return bytesToHexString(visitEntry(entry));
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Incorrect number of arguments. Terminating.");
            exit(1);
        }

        File entry = new File(args[0]);
        if (!entry.exists()) {
            System.out.println("File or directory " + entry.getName() + " doesn't exist. Terminating.");
            exit(1);
        }

        long startTime = System.currentTimeMillis();
        String s = null;
        try {
            s = singleThreadCalculator(entry);
        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println("An exception occurred. Terminating.");
            exit(1);
        }
        long stopTime = System.currentTimeMillis();

        System.out.println(s);
        System.out.println(stopTime - startTime);
    }
}
