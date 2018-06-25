package name.murfel.ftp.gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A minimal FTP GUI Client which connects to a specified server, lazily shows the file hierarchy (e.g. the files
 * lists are downloaded only when a user requests it), and allows to save a file from the server to a preferred location.
 *
 * To run the server for this client, cd to target/classes/ and run {@code java name.murfel.ftp.FtpServer portNumber}.
 */
public class FtpGuiClient extends Application {
    public static UIController uiController;
    public static String host;
    public static int port;
    public static String dir;

    @Override
    public void start(Stage primaryStage) throws Exception {
        uiController = new UIController(primaryStage);
    }
}