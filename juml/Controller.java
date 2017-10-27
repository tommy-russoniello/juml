package juml;

import java.awt.geom.Point2D.Double;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.Vector;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.naming.spi.InitialContextFactory;
import javafx.scene.layout.Pane;
import javafx.event.*;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import umlnode.*;

//Logic
public class Controller{
    public enum Mode {
      LINE, POINT, SELECT, DELETE
    }

    Map<Node, UMLNode> NODES = new HashMap<>();

    @FXML private Pane pane;

    Mode MODE = Mode.SELECT;
    Deque<UMLNode> SELECTED = new LinkedList<>();
    FileChooser fileChooser = new FileChooser();
    static Stage window;

    //Sets the Primary Stage
    public static void setPrimaryStage(Stage primaryStage) {
        window = primaryStage;
    }

    public Pane getPane () {
      return pane;
    }


    public void modeClick (ActionEvent event) {
      String newMode = ((Button)event.getSource()).getId();
      newMode = newMode.substring(0, newMode.length() - 4).toUpperCase();
      MODE = Mode.valueOf(newMode);
      SELECTED.clear();
      System.out.println ("Draw mode changed to \"" + MODE + "\"");
    }

    public void paneClick (MouseEvent event) {
      System.out.println ("Pane clicked at " + event.getX() + " " + event.getY());

      switch (MODE) {
        case POINT:
          addNode(new Point(event.getX(), event.getY()));

          break;

        case LINE:
          if (pane.getChildren().contains(event.getTarget()) &&
            !(event.getTarget() instanceof Line)) {
            if (SELECTED.isEmpty()) {
              SELECTED.addLast(NODES.get(event.getTarget()));
            }
            else if (event.getTarget() != SELECTED.getLast().getModel()) {
              Connector newConnector =
                new Connector(SELECTED.getLast(), NODES.get(event.getTarget()));
              pane.getChildren().add(newConnector.getModel());
              NODES.put(newConnector.getModel(), newConnector);
              SELECTED.clear();
            }
          }

          break;

        case SELECT:
          // inspect(event.getTarget());
          break;
          
        case DELETE:
			if (pane.getChildren().contains(event.getTarget())) {
				UMLNode target = NODES.get(event.getTarget());
				Vector<Connector> conections = target.getConnections();
				for (int i = 0; i < conections.size(); i++) {
					deleteNode(conections.get(i).getModel());
				}
				if (target == null || target.getModel() == null) {
					System.out.println("target not found in map");
					return;
				}
				target.delete();
				deleteNode(target.getModel());
			}
			break;
        default:

          break;
      }
    }

    public void deleteNode(Node target) {
		ObservableList<Node> children = pane.getChildren();
		for (int i = 0; i < children.size(); ++i) {
			if (children.get(i) == target) {
				children.remove(i);
				System.out.println("Node deleted from Pane");
				return;
			}
		}
		System.out.println("Error! connector could not remove itself from end ponts");
	}
    
    public void addNode (UMLNode node) {
      Node model = node.getModel();
      pane.getChildren().add(model);
      NODES.put(model, node);

      class Delta { double x, y; }
      final Delta dragDelta = new Delta();

      
      // The code for making an object draggable was originally written by jewelsea and copied from
      // https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0
      // have since modified parts of it to use in out project
      model.setOnMousePressed(new EventHandler<MouseEvent>() {
       @Override public void handle(MouseEvent mouseEvent) {
         if (MODE == Mode.SELECT) {
           // record a delta distance for the drag and drop operation.
           dragDelta.x = node.getOriginX() - mouseEvent.getX();
           dragDelta.y = node.getOriginY() - mouseEvent.getY();
           model.getScene().setCursor(Cursor.MOVE);
           System.out.println("begin moving Node");
         }
       }
      });

      model.setOnMouseDragged(new EventHandler<MouseEvent>() {
       @Override public void handle(MouseEvent mouseEvent) {
         if (MODE == Mode.SELECT) {
           node.move(mouseEvent.getX() + dragDelta.x, mouseEvent.getY() + dragDelta.y);
           System.out.println("moved Node");
           node.update();
         }
       }
      });

      model.setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          if (MODE == Mode.SELECT) {
            model.getScene().setCursor(Cursor.HAND);
          }
        }
      });

      model.setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          if (MODE == Mode.SELECT) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              model.getScene().setCursor(Cursor.HAND);
            }
          }
        }
      });

      model.setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          if (MODE == Mode.SELECT) {
            if (!mouseEvent.isPrimaryButtonDown()) {
              model.getScene().setCursor(Cursor.DEFAULT);
            }
          }
        }
      });
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
