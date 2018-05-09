package name.murfel.java;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static LogicController logicController;
    public static UIController uiController;

    /**
     * Parse args and launch JavaFX.
     *
     * @param args args[0] should represent a positive even int number which is the board size (the length of a side)
     */
    public static void main(String[] args) {
        int boardSize = Integer.valueOf(args[0]);
        if (boardSize <= 0 || boardSize % 2 != 0) {
            System.out.println("Size of the board should be a positive even number. Exiting.");
        }

        launch(args);
    }

    /**
     * Instantiate logic and UI controllers with the same boardSize parameter which will immediately start the game.
     *
     * This method should only be called by JavaFX.
     *
     * @param primaryStage the first stage, created by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        int boardSize = Integer.valueOf(getParameters().getRaw().get(0));

        logicController = new LogicController(boardSize);
        uiController = new UIController(boardSize, primaryStage);
    }
}
