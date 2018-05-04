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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        int boardSize = Integer.valueOf(getParameters().getRaw().get(0));
        controller.setSize(boardSize);

        Button button = new Button();
        button.setText("abba");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setText("acdc");
                System.out.println("hi");
            }
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(layout, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        int boardSize = Integer.valueOf(args[0]);
        if (boardSize <= 0 || boardSize % 2 != 0) {
            System.out.println("Size of the board should be a positive even number. Exiting.");
        }

        launch(args);
    }
}
