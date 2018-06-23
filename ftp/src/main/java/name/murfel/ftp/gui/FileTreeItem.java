package name.murfel.ftp.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import name.murfel.ftp.FtpClient;
import name.murfel.ftp.ServerEntity;

import java.io.IOException;
import java.util.List;

/**
 * Change the standard notion of leaf: now a leaf is a file and everything else is a directory.
 *
 * Load children lazily by requesting file list of a directory from the server.
 */
public class FileTreeItem extends TreeItem<ServerEntity> {
    private boolean isFirstTimeChildren = true;
    private boolean isRootTreeItem;

    FileTreeItem(ServerEntity s, boolean isRootTreeItem) {
        this(s);
        this.isRootTreeItem = isRootTreeItem;
    }

    FileTreeItem(ServerEntity s) {
        super(s);
    }

    @Override
    public boolean isLeaf() {
        return !super.getValue().is_directory;
    }

    @Override
    public ObservableList<TreeItem<ServerEntity>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren());
        }
        return super.getChildren();
    }

    /**
     * Request file list from the directory represented by this tree item and add them as children of this tree item.
     *
     * @return the list of children
     */
    private ObservableList<FileTreeItem> buildChildren() {
        ServerEntity entity = super.getValue();
        if (entity.is_directory) {
            ObservableList<FileTreeItem> children = FXCollections.observableArrayList();
            try {
                List<ServerEntity> list = FtpClient.processListRequest(FtpGuiClient.host, FtpGuiClient.port, entity.getName());
                for (ServerEntity subentity : list) {
                    subentity.prependName(entity.name);
                    children.add(new FileTreeItem(subentity));
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't get list of files: " + e.getMessage(), ButtonType.OK);
                alert.showAndWait();
                if (isRootTreeItem) {
                    FtpGuiClient.uiController.showConnectionMenu();
                }
            }
            return children;
        }
        return FXCollections.emptyObservableList();
    }

}
