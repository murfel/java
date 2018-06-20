package name.murfel.tictactoe;

import javafx.scene.input.MouseEvent;

/**
 * The stats scene shows statistics on games played with the computer.
 */
public class StatsScene {
    public void backClicked(MouseEvent mouseEvent) {
        Main.uiController.backClickedInStats();
    }
}
