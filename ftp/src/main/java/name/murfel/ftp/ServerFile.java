package name.murfel.ftp;

public class ServerFile {
    public ServerFile(String name, boolean is_directory) {
        this.name = name;
        this.is_directory = is_directory;
    }
    public String name;
    public boolean is_directory;
}