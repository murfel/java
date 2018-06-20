package name.murfel.tictactoe;

import javafx.scene.input.MouseEvent;

/**
 * A scene for choosing difficulty of a game with the computer.
 */
public class ChooseDifficultyScene {
    public void easyClicked(MouseEvent mouseEvent) {
        Main.uiController.easyClickedInChooseDifficulty();
    }

    public void hardClicked(MouseEvent mouseEvent) {
        Main.uiController.hardClickedInChooseDifficulty();
    }

    public void backClicked(MouseEvent mouseEvent) {
        Main.uiController.backClickedInChooseDifficulty();
    }
}
