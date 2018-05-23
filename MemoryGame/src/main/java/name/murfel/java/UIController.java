package name.murfel.java;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * UI controller receives commands by the logic controller and draws them (shows to the user), and sends the logic
 * controller actions performed by users on the UI.
 *
 * It receives user actions by listening to the UI events (button clicks).
 *
 * In response, it calls appropriate methods on the logic controller.
 *
 * This class (and the {@code LogicController}) should is instantiated once on the application start up and its instance is kept as
 * in a static field. The Logic and UI controller communicate with each other by referencing each other's static fields
 * of the {@code Main} class.
 */
public class UIController {
    private int boardSize;
    private Stage stage;

    private Button[][] board;

    @NotNull
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * Create a game board. The game board is a grid of buttons. During the game, each button can be either closed
     * (no text on it) or can be opened (and either active or disabled) and show a number associated with the button
     * according to the game rules (see {@code LogicController} for the rules).
     *
     * @param boardSize the length of a side of a square board, should be a positive even number
     * @param primaryStage the first stage (which was created by JavaFX)
     */
    public UIController(int boardSize, Stage primaryStage) {
        this.boardSize = boardSize;
        this.stage = primaryStage;

        board = new Button[boardSize][boardSize];

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        double GAP_SIZE = 10;
        double PREF_SIZE = 50;

        gridPane.setHgap(GAP_SIZE);
        gridPane.setVgap(GAP_SIZE);

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                final int row = i;
                final int col = j;
                Button button = new Button();
                button.setPrefSize(PREF_SIZE, PREF_SIZE);
                board[row][col] = button;
                gridPane.add(button, col, row);
                button.setOnMouseClicked(event -> Main.logicController.goToCell(row, col));
            }
        }

        double gridSize = PREF_SIZE * boardSize + GAP_SIZE * (boardSize - 1) + 100;
        primaryStage.setScene(new Scene(gridPane, gridSize, gridSize));
        primaryStage.setTitle("The Memory Game");
        primaryStage.show();
    }

    /**
     * Open the specified cell, that is, show a number associated with the cell on it.
     *
     * Also, disable the button so that the cell remains disabled forever.
     *
     * @param row the row of the cell to open forever
     * @param col the col of the cell to open forever
     * @param value the number which should be shown on the cell
     */
    public void openCellForever(int row, int col, int value) {
        openCell(row, col, value);
        board[row][col].setDisable(true);
    }

    /**
     * Wait for 500 ms and close the cell.
     *
     * @param row the row of the cell to wait and close
     * @param col the col of the cell to wait and close
     */
    public void waitAndCloseCell(int row, int col) {
        executorService.schedule(() ->
                        Platform.runLater(() -> board[row][col].setText("")),
                500, TimeUnit.MILLISECONDS);
    }

    /**
     * Open the specified cell, that is, show a number associated with the cell on it.
     *
     * @param row the row of the cell to open
     * @param col the col of the cell to open
     * @param value the number which should be shown on the cell
     */
    public void openCell(int row, int col, int value) {
        board[row][col].setText(Integer.toString(value));
    }
}
