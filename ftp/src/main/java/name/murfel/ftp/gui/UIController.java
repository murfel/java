package name.murfel.ftp.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import name.murfel.ftp.ServerEntity;

import java.io.File;
import java.io.IOException;

/**
 * Sets up different stages.
 */
public class UIController {
    private Stage primaryStage;

    private Scene connectionMenu = new Scene(FXMLLoader.load(getClass().getResource("/connectionMenu.fxml")));
    private Scene treeViewScene = new Scene(FXMLLoader.load(getClass().getResource("/treeView.fxml")));
    private TreeView<ServerEntity> treeView = (TreeView<ServerEntity>) treeViewScene.lookup("#treeView");

    public UIController(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        showConnectionMenu();
        primaryStage.setTitle("Ftp Client");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }

    /**
     * Show the tree view menu with the tree of files and an interface to save a file.
     */
    public void showTreeView() {
        FileTreeItem rootItem = new FileTreeItem(new ServerEntity(FtpGuiClient.dir, true), true);
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
        primaryStage.setScene(treeViewScene);
    }

    /**
     * Show the main menu where user can enter server details and connect to it.
     */
    public void showConnectionMenu() {
        primaryStage.setScene(connectionMenu);
    }

    /**
     * Show the dialog for choosing the location of a file to be saved.
     *
     * @return the file object where user would like to save their requested file
     */
    public File showSaveDialog() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showSaveDialog(primaryStage);
    }
}
