package name.murfel.tictactoe;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * The main class to run the application from.
 */
public class Main extends Application {
    public static LogicController logicController;
    public static UIController uiController;

    /**
     * Parse args and launch JavaFX.
     * <p>
     * The first command line argument {@code args[0]} should represent a positive even int number which is
     * the square board size (the length of a side). If none is given, defaulting to 3.
     *
     * @param args args[0] is a positive even int, or none
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No board size provided as an argument. Defaulting to 3.");
        } else {
            try {
                int boardSize = Integer.valueOf(args[0]);
                if (boardSize < 3) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Size of the board should be a positive number greater or equal to 3. Exiting.");
                System.exit(1);
            }
        }
        launch(args);
    }

    /**
     * Instantiate logic and UI controllers with the same boardSize parameter which will immediately start the game.
     * <p>
     * This method should only be called by JavaFX.
     *
     * @param primaryStage the first stage, created by JavaFX
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        int boardSize;
        if (getParameters().getRaw().size() == 0) {
            boardSize = 3;
        } else {
            boardSize = Integer.valueOf(getParameters().getRaw().get(0));

        }
        logicController = new LogicController(boardSize);
        uiController = new UIController(boardSize, primaryStage);
    }
}
