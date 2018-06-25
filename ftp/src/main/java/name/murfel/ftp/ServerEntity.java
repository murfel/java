package name.murfel.ftp;

import java.nio.file.Paths;

/**
 * Stores information about a file (file name and whether it is a directory) received from server.
 */
public class ServerEntity {
    public String name;
    public boolean is_directory;

    public ServerEntity(String name, boolean is_directory) {
        this.name = name;
        this.is_directory = is_directory;
    }

    public String getName() {
        return name;
    }

    public boolean getIsDirectory() {
        return is_directory;
    }

    public void prependName(String prefix) {
        name = Paths.get(prefix, name).toString();
    }

    @Override
    public String toString() {
        return name;
    }
}