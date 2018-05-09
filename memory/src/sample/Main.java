package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MemoryGame extends Application {
    public static LogicController logicController;
    public static UIController uiController;
    public int boardSize;

    @Override
    public void start(Stage primaryStage) throws Exception {
        boardSize = Integer.valueOf(getParameters().getRaw().get(0));

        logicController = new LogicController(boardSize);
        uiController = new UIController(boardSize, primaryStage);
    }


    public static void main(String[] args) {
        int boardSize = Integer.valueOf(args[0]);
        if (boardSize <= 0 || boardSize % 2 != 0) {
            System.out.println("Size of the board should be a positive even number. Exiting.");
        }

        launch(args);
    }
}
