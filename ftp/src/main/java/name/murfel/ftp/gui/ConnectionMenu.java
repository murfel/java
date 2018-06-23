package name.murfel.ftp.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class ConnectionMenu {
    public TextField host;
    public TextField port;
    public TextField dir;

    /**
     * Do little validation (only that the port is a non-negative number) and show the tree.
     *
     * The tree is shown with the first layer of the file hierarchy expanded.
     *
     * @param actionEvent ignored
     */
    public void handleConnectButtonAction(ActionEvent actionEvent) {
        if (!port.getText().matches("[0-9]*") || port.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Port should be a non-negative number", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        FtpGuiClient.host = host.getText();
        FtpGuiClient.port = Integer.valueOf(port.getText());
        FtpGuiClient.dir = dir.getText();
        FtpGuiClient.uiController.showTreeView();
    }
}
