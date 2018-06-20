package name.murfel.tictactoe;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

/**
 * UI controller receives commands by the logic controller and draws them (shows to the user), and sends the logic
 * controller actions performed by users on the UI.
 * <p>
 * It receives user actions by listening to the UI events (button clicks).
 * <p>
 * In response, it calls appropriate methods on the logic controller.
 * <p>
 * This class (and the {@code LogicController}) should is instantiated once on the application start up and its instance is kept as
 * in a static field. The Logic and UI controller communicate with each other by referencing each other's static fields
 * of the {@code Main} class.
 */
public class UIController {
    private int boardSize;
    private Stage stage;

    private Button[][] board;

    private Scene menu;
    private Scene chooseDifficulty;
    private Scene stats;
    private Scene game;

    private Text gameSceneGameStateText;
    private HashMap<Mark, String> markToString = new HashMap<>();

    {
        markToString.put(Mark.None, null);
        markToString.put(Mark.Nought, "O");
        markToString.put(Mark.Cross, "X");
    }

    /**
     * Create a game board. A game board is a square grid of buttons. On each button could be a Cross, a Nought, or nothing.
     *
     * @param boardSize the length of a side of a square board, should be a positive even number
     * @param stage     the first stage (which was created by JavaFX)
     */
    public UIController(int boardSize, @NotNull Stage stage) throws IOException {
        this.boardSize = boardSize;
        this.stage = stage;

        board = new Button[boardSize][boardSize];

        menu = new Scene(FXMLLoader.load(getClass().getResource("MenuScene.fxml")));
        chooseDifficulty = new Scene(FXMLLoader.load(getClass().getResource("ChooseDifficultyScene.fxml")));
        stats = new Scene(FXMLLoader.load(getClass().getResource("StatsScene.fxml")));
        createGameScene();

        stage.setMinHeight(400);
        stage.setMinWidth(400);

        stage.setScene(menu);
        stage.setTitle("Tac-Tac-Toe");
        stage.show();
    }

    /**
     * Draw a game board represented by {@code marks}. Each cell is either a Cross, a Nought, or nothing.
     *
     * @param marks a board to draw
     */
    public void drawBoard(Mark[][] marks) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (marks[i][j] == Mark.None) {
                    board[i][j].setText("");
                } else if (marks[i][j] == Mark.Nought) {
                    board[i][j].setText("O");
                } else {
                    board[i][j].setText("X");
                }
            }
        }
    }

    /**
     * Creates the game scene with the board. It is created dynamically because a user can choose the board size length.
     */
    private void createGameScene() {
        VBox vBox = new VBox();
        game = new Scene(vBox);

        GridPane gridPane = new GridPane();
        gameSceneGameStateText = new Text("Game is in progress");
        Button newRoundButton = new Button("New Round");
        Button menuButton = new Button("Menu");

        newRoundButton.setOnMouseClicked(event -> Main.logicController.startNewRound());
        menuButton.setOnMouseClicked(event -> stage.setScene(menu));

        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(gameSceneGameStateText);
        vBox.getChildren().add(newRoundButton);
        vBox.getChildren().add(menuButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        gridPane.setAlignment(Pos.CENTER);

        double gapSize = 10;
        double prefSize = 50;

        gridPane.setHgap(gapSize);
        gridPane.setVgap(gapSize);

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                final int row = i;
                final int col = j;
                Button button = new Button();
                button.setMinSize(prefSize, prefSize);
                board[row][col] = button;
                gridPane.add(button, col, row);
                button.setOnMouseClicked(event -> Main.logicController.goToCell(row, col));
            }
        }
    }

    /**
     * After the play button is clicked, a choose difficulty dialog is opened.
     */
    public void playInMenuClicked() {
        stage.setScene(chooseDifficulty);
    }

    /**
     * After the hot seat button clicked, the game for two human players starts.
     */
    public void hotSeatInMenuClicked() {
        stage.setScene(game);
        Main.logicController.startNewHotSeatGame();
    }

    /**
     * After the stats button clicked, a menu showing statistics for games with the computer is shown.
     */
    public void statsInMenuClicked() {
        stage.setScene(stats);
        // TODO: redraw stats

        int total_games = Main.logicController.games_tied + Main.logicController.games_won_by_player +
                Main.logicController.games_won_by_computer;
        ((Text) stats.lookup("#total_score")).setText("Total games with computer: " + total_games);
        ((Text) stats.lookup("#player_score")).setText("Player: " + Main.logicController.games_won_by_player +
                "/" + total_games);
        ((Text) stats.lookup("#computer_score")).setText("Computer: " + Main.logicController.games_won_by_computer +
                "/" + total_games);
        ((Text) stats.lookup("#tie_score")).setText("Ties: " + Main.logicController.games_tied +
                "/" + total_games);
    }

    /**
     * After the easy diffictulty button is clicked, the game with a computer on easy settings starts.
     */
    public void easyClickedInChooseDifficulty() {
        stage.setScene(game);
        Main.logicController.startNewGameWithDifficulty(true);
    }

    /**
     * After the easy diffictulty button is clicked, the game with a computer on hard settings starts.
     */
    public void hardClickedInChooseDifficulty() {
        stage.setScene(game);
        Main.logicController.startNewGameWithDifficulty(false);
    }

    /**
     * After the back button in choose difficulty dialog is clicked, it returns the user back to the main menu.
     */
    public void backClickedInChooseDifficulty() {
        stage.setScene(menu);
    }

    /**
     * After the back button in stats dialog is clicked, it returns the user back to the main menu.
     */
    public void backClickedInStats() {
        stage.setScene(menu);
    }

    /**
     * Change the text below the game board to a greeting to a winner.
     *
     * @param winner who is a winner (should be either the Cross, or the Nought)
     */
    public void declareWinner(Mark winner) {
        gameSceneGameStateText.setText(markToString.get(winner) + " wins!");
    }

    /**
     * Change the text below the game board to a greeting to both players sharing a tie.
     */
    public void declareTie() {
        gameSceneGameStateText.setText("It's a tie!");
    }

    /**
     * Change the text below the game board to indicate that the game is in progress.
     */
    public void declareGameInProgress() {
        gameSceneGameStateText.setText("Game is in progress");
    }
}
