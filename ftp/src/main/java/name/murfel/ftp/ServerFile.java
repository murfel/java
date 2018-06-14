package name.murfel.ftp;

/**
 * Stores information about a file (file name and whether it is a directory) received from server.
 */
public class ServerFile {
    public String name;
    public boolean is_directory;

    public ServerFile(String name, boolean is_directory) {
        this.name = name;
        this.is_directory = is_directory;
    }

    public String getName() {
        return name;
    }

    public boolean getIsDirectory() {
        return is_directory;
    }
}