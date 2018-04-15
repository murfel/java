package name.murfel.kr1;


import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

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

    /**
     * Calculates MD5 hash of the file contents.
     *
     * @param file a file to compute the hash of
     * @return an MD5 hash of the content, or an empty byte[] array if the file doesn't exist
     */
    private static byte[] visitFile(File file) throws IOException, NoSuchAlgorithmException {
        InputStream in;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("Warning: A file " + file.getName() +
                    " which existed no longer exists. Skipping.");
            return new byte[0];
        }
        DigestInputStream digestInputStream = new DigestInputStream(in, MessageDigest.getInstance("MD5"));

        byte[] buffer = new byte[4096];
        //noinspection StatementWithEmptyBody
        while (digestInputStream.read(buffer) != -1) {
        }
        return digestInputStream.getMessageDigest().digest();
    }

    /**
     * Calculates MD5 hash of the directory as following: it concatenates byte arrays of the directory name,
     * and byte arrays of MD5 hashes of all the entries it contains, and then computes the MD5 hash of the big byte array.
     *
     * @param directory
     * @return an MD5 hash of the directory (as described above), or an empty byte[] array if the directory doesn't exist
     */
    private static byte[] visitDirectory(File directory) throws IOException, NoSuchAlgorithmException {
        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Warning: A directory " + directory.getName() +
                    " which existed no longer exists. Skipping.");
            return new byte[0];
        }
        Arrays.sort(files, Comparator.comparing(File::getName));
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(directory.getName().getBytes());
        for (File entry : files) {
            md.update(visitEntry(entry));
        }
        return md.digest();
    }

    /**
     * Calculates MD5 hash of a File object.
     * <p>
     * A hash of a File object is computed as following:
     * <p>
     * If !file.isDirectory() then MD5(file) = MD5(file content),
     * else MD5(file) = MD5(filename + MD5(file1) + MD5(file2) + ...)
     * where file1, file2, etc. are the files in the directory file sorted alphabetically by name.
     * <p>
     * If such a file doesn't exists, returns an empty byte array.
     *
     * @param entry an arbitrary File object
     * @return an MD5 hash of the File object, or an empty byte array if the File object doesn't represent an existing file
     */
    private static byte[] visitEntry(File entry) throws IOException, NoSuchAlgorithmException {
        if (entry.isDirectory()) {
            return visitDirectory(entry);
        } else {
            return visitFile(entry);
        }
    }

    /**
     * Calculate an MD5 hash of a File object using one thread implementation.
     */
    private static String singleThreadCalculator(File entry) throws IOException, NoSuchAlgorithmException {
        return bytesToHexString(visitEntry(entry));
    }

    /**
     * Calculate an MD5 hash of a File object using multi thread implementation.
     */
    private static String multiThreadCalculator(File entry) {
        byte[] digest = (new ForkJoinPool()).invoke(new EntryHasher(entry));
        return bytesToHexString(digest);
    }

    /**
     * Run two versions of MD5 hashing for files (single and multi thread) and compare performance.
     * <p>
     * It prints out the MD5 hash computed by either of the versions as a hexadecimal number
     * and prints out the running time of first single, and then multi thread versions.
     *
     * @param args first argument is a name of a file to compute the hash of
     */
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

        long startTime1 = System.currentTimeMillis();
        String s1 = multiThreadCalculator(entry);
        long stopTime1 = System.currentTimeMillis();

        if (!s.equals(s1)) {
            System.out.println("Single and multi thread versions computed different MD5 hashes. Terminating.");
            exit(1);
        }
        System.out.println(s);
        System.out.println(stopTime - startTime);
        System.out.println(stopTime1 - startTime1);
    }

    /**
     * A task for fork-join pool calculating MD5 hash of a File object
     * which represents an existing file.
     * <p>
     * A hash of a File object is computed as following:
     * <p>
     * If !file.isDirectory() then MD5(file) = MD5(file content),
     * else MD5(file) = MD5(filename + MD5(file1) + MD5(file2) + ...)
     * where file1, file2, etc. are the files in the directory file sorted alphabetically by name.
     * <p>
     * If such a file doesn't exists, the compute() returns an empty byte array.
     */
    private static class EntryHasher extends RecursiveTask<byte[]> {
        private File entry;

        EntryHasher(File entry) {
            this.entry = entry;
        }

        @Override
        protected byte[] compute() {
            if (entry.isFile()) {
                try {
                    return visitFile(entry);
                } catch (IOException | NoSuchAlgorithmException e) {
                    System.out.println("An exception occurred. Terminating.");
                    exit(1);
                }
            }

            File[] subEntries = entry.listFiles();
            if (subEntries == null) {
                System.out.println("Warning: A directory " + entry.getName() +
                        " which existed no longer exists. Skipping.");
                return new byte[0];
            }

            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                System.out.println("An exception occurred. Terminating.");
                exit(1);
            }
            md.update(entry.getName().getBytes());

            Arrays.sort(subEntries, Comparator.comparing(File::getName));
            List<EntryHasher> subTasks = new LinkedList<>();
            for (File subEntry : subEntries) {
                EntryHasher subTask = new EntryHasher(subEntry);
                subTask.fork();
                subTasks.add(subTask);
            }

            for (EntryHasher subTask : subTasks) {
                md.update(subTask.join());
            }

            return md.digest();
        }
    }
}
