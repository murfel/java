package name.murfel.tictactoe;

import javafx.scene.input.MouseEvent;

/**
 * The main menu scene. Allows to start a game with the computer, in a hot seat mode, and view stats.
 */
public class MenuScene {
    public void playClicked(MouseEvent mouseEvent) {
        Main.uiController.playInMenuClicked();
    }

    public void hotSeatClicked(MouseEvent mouseEvent) {
        Main.uiController.hotSeatInMenuClicked();
    }

    public void statsClicked(MouseEvent mouseEvent) {
        Main.uiController.statsInMenuClicked();
    }
}
