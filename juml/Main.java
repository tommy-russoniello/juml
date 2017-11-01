package juml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    Stage window;
    BorderPane layout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("juml.fxml"));
        // Variable will update based on current file name, new file will be called untitled.
        String fileName = new String ("Untitled");

        window = primaryStage;
        window.setTitle("Team Rocket UML Editor: " + fileName);

        Scene scene = new Scene(root, 1000, 500);
        window.getIcons().add(new Image(Main.class.getResourceAsStream(
          "/images/Team_Rocket_Logo.jpg")));
        window.setScene(scene);
        window.show();

        // Give primary stage to controller.
        Controller.setPrimaryStage(window);
    }

}
