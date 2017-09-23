package UMLEditor;

import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;

import javax.naming.spi.InitialContextFactory;

//Logic
public class Controller{
    FileChooser fileChooser = new FileChooser();
    static Stage window;

    //Gets the Primary Stage
    public static void getPrimaryStage(Stage primaryStage) {

        window = primaryStage;

    }


//MenuBar Action Controller --------------------------------------------------------------------------------------------
    //Action used to Close the program: assigned to Exit
    public void menuExitClicked(){
        window.close();
    }

    //Action to Open file
    public void menuOpenClicked(){
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All UML Diagrams", "*.UML"));
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(window);
        window.setTitle("Team Rocket UML Editor: " + fileChooser.getTitle());
    }

    public void menuSaveClicked() {
        //temp
        String content = new String("test");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ALL UML Diagrams", "*.UML"));
        File file = fileChooser.showSaveDialog(window);
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    //WIP Menu Bar Actions
    public void menuNewClicked(){}
    public void menuCloseClicked(){}
    public void menuCopyClicked(){}
    public void menuPasteClicked(){}
    public void menuDeleteClicked(){}
    public void menuSelectAllClicked(){}
    public void menuSpecificationsClicked(){
        File file = new File("https://github.com/tommy-russoniello/juml/Iteration1Specifications.docx");
        //HostServices hostServices = Main.getHostServices();
        //hostServices.showDocument(file.getAbsolutePath());
    }
	
}//---------------------------------------------------------------------------------------------------------------------
