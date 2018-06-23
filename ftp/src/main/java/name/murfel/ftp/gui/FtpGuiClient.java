package name.murfel.ftp.gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A minimal FTP GUI Client which connects to a specified server, lazily shows the file hierarchy (e.g. the files
 * lists are downloaded only when a user requests it), and allows to save a file from the server to a preferred location.
 */
public class FtpGuiClient extends Application {
    public static UIController uiController;
    public static String host = "noxi";
    public static int port = 44444;
    public static String dir = "/home";

    @Override
    public void start(Stage primaryStage) throws Exception {
        uiController = new UIController(primaryStage);
    }
}