package UMLEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
        
        //variable will update based on current file name, new file will be called untitled
        String fileName = new String ("untitled");
        
        window = primaryStage;
        window.setTitle("Team Rocket UML Editor: " + fileName);

        Scene scene = new Scene(root, 300, 275);
        window.getIcons().add(new Image("file:images/Team_Rocket_Logo.jpg"));
        window.setScene(scene);
        window.show();
    }

}
