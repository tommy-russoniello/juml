package juml;

import java.awt.geom.Point2D.Double;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
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
import javafx.scene.layout.*;

import javafx.event.*;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import umlobject.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class Controller {

	public enum Mode {
		LINE, POINT, SELECT, DELETE, CLASSBOX
	}

	Map<Node, UMLNode> NODES = new HashMap<>();
	Map<Node, UMLConnector> CONNECTORS = new HashMap<>();

	@FXML private Pane pane;
	@FXML private AnchorPane inspectorObject;

	Mode MODE = Mode.SELECT;
	Deque<UMLNode> SELECTED = new LinkedList<>();
	FileChooser fileChooser = new FileChooser();
	static Stage window;

	// Sets the Primary Stage
	public static void setPrimaryStage(Stage primaryStage) {
		window = primaryStage;
	}

	public Pane getPane() {
		return pane;
	}

	public void modeClick(ActionEvent event) {
		String newMode = ((Button) event.getSource()).getId();
		newMode = newMode.substring(0, newMode.length() - 4).toUpperCase();
		MODE = Mode.valueOf(newMode);
		SELECTED.clear();
		System.out.println("Draw mode changed to \"" + MODE + "\"");
	}

	public void paneClick(MouseEvent event) throws IOException {
		System.out.println("Pane clicked at " + event.getX() + " " + event.getY());

		switch (MODE) {
		case POINT:
			addObject(new Point(event.getX(), event.getY()));

			break;

		case LINE:
		UMLNode node = (UMLNode) getObject(event.getTarget());
				if (node != null) {
					if (SELECTED.isEmpty()) {
						SELECTED.addLast(node);
					}
					else if (node != SELECTED.getLast()) {
						addObject(new UMLConnector(SELECTED.getLast(), node));
						SELECTED.clear();
					}
				}

			break;

			case CLASSBOX:
        addObject(new ClassBox(event.getX(), event.getY()));

      break;

		case SELECT:
			if (pane.getChildren().contains(event.getTarget())) {
				if (event.getTarget() instanceof Line) { // updates inspector to display properties of Line
					// inspectorObject.getChildren().clear(); // Clears current inspectorObject children
					// inspectorObject.getChildren().add(FXMLLoader.load(getClass().getResource("Line.fxml"))); // Pushes Line properties to InspectorObject
				} else if (event.getTarget() instanceof Circle) { // updates inspector to display properties of Point
					// inspectorObject.getChildren().clear(); // Clear current inspectorObject children
					// inspectorObject.getChildren().add(FXMLLoader.load(getClass().getResource("Circle.fxml"))); // Pushes Circle properties to InspectorObect

				}
			}

			else {
				// inspectorObject.getChildren().clear();
			}
			// code for later: line.getStrokeDashArray().addAll(25d, 10d);
			/*
			 * Note to group: tried using instanceof Object but the if
			 * statements never triggered when they should have still need to
			 * look up why this is happening. Using the first word of the string
			 * as workaround for now. (event.getTarget() instanceof Line)
			 *
			 */
			break;

		case DELETE:
			UMLObject target = getObject(event.getTarget());
			if (target != null) {
				deleteObject(target);
			}

			break;

		default:

			break;
		}
	}

	public void deleteObject(UMLObject target) {
		if (target instanceof UMLConnector) {
			UMLConnector connector = (UMLConnector) target;
			CONNECTORS.remove(connector.getModel());
			connector.disconnect();
			pane.getChildren().remove(connector.getModel());
		}
		else {
			UMLNode node = (UMLNode) target;
			while (!node.getConnections().isEmpty()) {
				UMLConnector connector = node.getConnections().lastElement();
				CONNECTORS.remove(connector.getModel());
				pane.getChildren().remove(connector.getModel());
				connector.disconnect();
			}
			NODES.remove(node.getModel());
			pane.getChildren().remove(node.getModel());
		}
	}

	public void addObject(UMLObject obj) {
		Node model = obj.getModel();
		pane.getChildren().add(model);

		if (obj instanceof UMLConnector) {
			UMLConnector connector = (UMLConnector) obj;
			CONNECTORS.put(model, connector);
		}
		else {
			UMLNode node = (UMLNode) obj;
      NODES.put(model, node);

        class DragSource { double x, y; }
        final DragSource dragSource = new DragSource();

        model.setOnMousePressed(new EventHandler<MouseEvent>() {
         @Override public void handle(MouseEvent mouseEvent) {
           if (MODE == Mode.SELECT) {
             // record a delta distance for the drag and drop operation.
             if (model instanceof Parent) {
               dragSource.x = mouseEvent.getX();
               dragSource.y = mouseEvent.getY();
             }
             else {
               dragSource.x = node.getOriginX() - mouseEvent.getX();
               dragSource.y = node.getOriginY() - mouseEvent.getY();
             }
            model.getScene().setCursor(Cursor.MOVE);
            System.out.println("begin moving Node");
           }
         }
        });

        model.setOnMouseDragged(new EventHandler<MouseEvent>() {
         @Override public void handle(MouseEvent mouseEvent) {
           if (MODE == Mode.SELECT) {
             if (model instanceof Parent) {
               node.move(node.getOriginX() + (mouseEvent.getX() - dragSource.x), node.getOriginY() + (mouseEvent.getY() - dragSource.y));
             } else {
               node.move(mouseEvent.getX() + dragSource.x, mouseEvent.getY() + dragSource.y);
             }
             node.update();
             System.out.println("moved Node");
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
    }

		public UMLObject getObject (Object inModel) {
      Node model = (Node) inModel;
      UMLObject returnNode = NODES.get(model);
      if (returnNode == null) {
        returnNode = CONNECTORS.get(model);
      }
      while (returnNode == null) {
        if (model == pane || model == null) {
          break;
        }
        model = model.getParent();
        returnNode = NODES.get(model);
        if (returnNode == null) {
          returnNode = CONNECTORS.get(model);
        }
      }
      return returnNode;
    }

	// MenuBar Action Controller
	// --------------------------------------------------------------------------------------------
	// Action used to Close the program: assigned to Exit
	public void menuExitClicked() {
		window.close();
	}

	public void menuNewClicked() {
		pane.getChildren().clear();
		inspectorObject.getChildren().clear();
		NODES = new HashMap<>();
	}

	// Action to Open file WIP:
	public void menuOpenClicked() {
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JUML txt files", "*.txt"));
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(window);
		window.setTitle("Team Rocket UML Editor: " + file.toString());
		try {
			pane.getChildren().clear(); // Clears current pane contents NODES = new HashMap<>(file);
			BufferedReader input = new BufferedReader(new FileReader(new File(file.toString())));
			String hashy = input.readLine();
			System.out.println(hashy);
			// NODES = new Map<Node, UMLNode>(hashy) ;
			input.close();
			// pane.getChildren().add(FXMLLoader.load(getClass().getResource("/test.fxml")));
			//Loads file text as pane children.

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

	// Action to Save File WIP:
	public void menuSaveClicked() throws IOException {
		String hashMap = NODES.toString();
		String fxmlContent = pane.getChildren().toString();

		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JUML txt files", "*.txt"));
		File file = fileChooser.showSaveDialog(window);
		try {
			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);
			fileWriter.write(hashMap);
			fileWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// WIP Menu Bar Actions
	public void menuCloseClicked() {
	}

	public void menuCopyClicked() {
	}

	public void menuPasteClicked() {
	}

	public void menuDeleteClicked() {
	}

	public void menuSelectAllClicked() {
	}

	public void menuSpecificationsClicked() {
		File file = new File("https://github.com/tommy-russoniello/juml/Iteration1Specifications.docx");
		// HostServices hostServices = Main.getHostServices();
		// hostServices.showDocument(file.getAbsolutePath());
	}

	// Function to Save a Hashmap to a txt file WIP:
	public void saveFile(Map<Node, UMLNode> NODES) throws IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(
				new FileOutputStream("C:/Users/xTmil_000/Desktop/eclipse/Workspace/juml-master/Hashmap.txt"));
		outputStream.writeObject(NODES);
		outputStream.close();
	}

}// ---------------------------------------------------------------------------------------------------------------------
