import java.io.*;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Unzip all files in the directory specified as the first command-line argument
 * that match a regex specified as the second command-line argument.
 */
public class ZipProcessor {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Not enough arguments specified.");
            System.out.println("Terminating.");
            return;
        }
        process(args[0], args[1]);
    }

    public static void process(String path, String pattern) throws IOException {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.out.println("The first argument is not a correct directory name.");
            System.out.println("Terminating.");
            return;
        }
        String outputDirName = path + "output";
        (new File(outputDirName)).mkdirs();
        for (File file : listOfFiles) {
            if (!file.isFile())
                continue;
            try (ZipFile zipFile = new ZipFile(file)) {
                Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
                while (enumeration.hasMoreElements()) {
                    ZipEntry zipEntry = enumeration.nextElement();
                    if (zipEntry.isDirectory())
                        continue;
                    if (!Pattern.matches(pattern, zipEntry.getName())) {
                        continue;
                    }
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    File outputFile = new File(outputDirName + File.separator + zipEntry.getName());
                    outputFile.getParentFile().mkdirs();
                    OutputStream outputStream = new FileOutputStream(outputFile);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                }
            }
            catch (ZipException e) {
                continue;
            }
            catch (IOException e) {
                System.out.println("Oh no, some IO exception: " + e.getMessage());
                System.out.println("Terminating.");
                return;
            }
        }

    }

}
