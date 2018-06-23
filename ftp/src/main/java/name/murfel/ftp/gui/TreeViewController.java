package name.murfel.ftp.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import name.murfel.ftp.FtpClient;
import name.murfel.ftp.ServerEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TreeViewController {
    public TreeView treeView;

    /**
     * Checks whether the current selection is a file and if so, launches a saving dialog.
     *
     * After the location is chosen, it downloads the file to the chosen location.
     *
     * @param actionEvent ignored
     */
    public void handleSaveFile(ActionEvent actionEvent) {
        TreeItem<ServerEntity> treeItem = (TreeItem<ServerEntity>) treeView.getSelectionModel().getSelectedItem();
        if (treeItem == null || treeItem.getValue().is_directory) {
            return;
        }

        File file = FtpGuiClient.uiController.showSaveDialog();
        if (file == null) {
            return;
        }

        try {
            FtpClient.processGetRequest(FtpGuiClient.host, FtpGuiClient.port, treeItem.getValue().name, new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't save file: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Finish working with this server and go to the initial menu
     * where a user can connect to another server of their choice.
     *
     * @param actionEvent ignored
     */
    public void handleDisconnect(ActionEvent actionEvent) {
        FtpGuiClient.uiController.showConnectionMenu();
    }
}
