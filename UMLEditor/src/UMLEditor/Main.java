package UMLEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    Stage window;
    BorderPane layout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UMLEditor.fxml"));

        window = primaryStage;
        window.setTitle("UML Editor");

        Scene scene = new Scene(root, 300, 275);
        window.setScene(scene);
        window.show();
    }

}
