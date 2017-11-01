package juml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import umlobject.*;

// ---------------------------------------------------------------------------------------------- \\

public class Controller {

	// Draw modes
	public enum Mode {
		SELECT,
		LINE,
		POINT,
		CLASSBOX,
		DELETE
	}
	
	// All UMLNodes currently on the pane
	Map<Node, UMLNode> NODES = new HashMap<>();
	// All UMLConnectors currently on the pane
	Map<Node, UMLConnector> CONNECTORS = new HashMap<>();

	// Main node that contains all drawn nodes as children.
	@FXML private Pane pane;
	@FXML private AnchorPane inspectorObject;
	@FXML private TextField circleOriginX;

	// Current mode. Defaulted to SELECT.
	Mode MODE = Mode.SELECT;
	// Collection of all "selected" UMLNodes
	Deque<UMLNode> SELECTED = new LinkedList<>();
	FileChooser fileChooser = new FileChooser();
	static Stage window;

// ---------------------------------------------------------------------------------------------- \\

	// Sets the Primary Stage. Called from Main.
	public static void setPrimaryStage(Stage primaryStage) {
		window = primaryStage;
	}

	// Returns pane.
	public Pane getPane() {
		return pane;
	}

	// Changes mode according to event source.
	// Triggered by object builder buttons.
	public void modeClick(ActionEvent event) {
		String newMode = ((Button) event.getSource()).getId();
		newMode = newMode.substring(0, newMode.length() - 4).toUpperCase();
		MODE = Mode.valueOf(newMode);
		SELECTED.clear();
		System.out.println("Draw mode changed to \"" + MODE + "\"");
	}

	// Performs draw action relative to current draw mode.
	// Triggered by pane.
	public void paneClick(MouseEvent event) throws IOException {
		System.out.println("Pane clicked at " + event.getX() + " " + event.getY());

		switch (MODE) {
			// Adds Point UMLNode to pane coordinates that were clicked on.
			case POINT:
				addObject(new Point(event.getX(), event.getY()));

				break;

			// Adds any UMLNode clicked to SELECTED
			// If theres a UMLNode already in SELECTED, draws a line from it to currently clicked UMLNode.
			case LINE:
				UMLNode node = (UMLNode) getObject(event.getTarget());
					// If relevant node was selected.
					if (node != null) {
						if (SELECTED.isEmpty()) {
							SELECTED.addLast(node);
						}
						// If selected node wasn't just clicked on again.
						else if (node != SELECTED.getLast()) {
							addObject(new UMLConnector(SELECTED.getLast(), node));
							SELECTED.clear();
						}
					}

				break;

			// Adds ClassBox UMLNode to pane coordinates that were clicked on.
			case CLASSBOX:
	        addObject(new ClassBox(event.getX(), event.getY()));

        break;

			// Displays information of any UMLObject clicked on in the Inspector.
			case SELECT:
				UMLObject test = getObject(event.getTarget());

				if (test instanceof UMLConnector) {
					inspectorObject.getChildren().clear();
					inspectorObject.getChildren().add(FXMLLoader.load(getClass().getResource("Line.fxml")));
				} else if (test instanceof Point) {
					inspectorObject.getChildren().clear();
					inspectorObject.getChildren().add(FXMLLoader.load(getClass().getResource("Circle.fxml")));
					//circleOriginX(getObject(event.getTarget()));

				} else if (test instanceof ClassBox) {
					inspectorObject.getChildren().clear();
					inspectorObject.getChildren().add(
						FXMLLoader.load(getClass().getResource("ClassBox.fxml")));
				} else {
					inspectorObject.getChildren().clear();
				}
				// TODO:
				// line.getStrokeDashArray().addAll(25d, 10d);
				// event.getTarget().toString().startsWith("T") ||
				//   event.getTarget().toString().startsWith("R")
				break;

			// Remove any UMLObject clicked on.
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

// ---------------------------------------------------------------------------------------------- \\

	// Removes given UMLObject from pane.
	// Disconnects any present connections (UMLConnectors).
	// * If a UMLNode is given, removes any UMLConnectors connected to it.
	// Remove relevant map entry for the given UMLObject
	public void deleteObject(UMLObject target) {
		// If UMLConnector is given, disconnect it on both ends and remove it.
		if (target instanceof UMLConnector) {
			UMLConnector connector = (UMLConnector) target;
			CONNECTORS.remove(connector.getModel());
			connector.disconnect();
			pane.getChildren().remove(connector.getModel());
		}
		// If UMLNode is given, removes it and any UMLConnectors connected to it.
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

	// Adds already instantiated UMLObject to pane.
	// Defines any relevant handlers on UMLObject.
	public void addObject(UMLObject obj) {
		Node model = obj.getModel();
		pane.getChildren().add(model);

		// If UMLConnector is being added, add it to CONNECTOR map.
		if (obj instanceof UMLConnector) {
			UMLConnector connector = (UMLConnector) obj;
			CONNECTORS.put(model, connector);
		}
		// If UMLNode is being added, add it to NODES map and define relevant handlers.
		else {
			UMLNode node = (UMLNode) obj;
      NODES.put(model, node);

				// Records mouse drag source coordinates accross handlers.
        class DragSource { double x, y; }
        final DragSource dragSource = new DragSource();

				// Records dragging coordinate information
        model.setOnMousePressed(new EventHandler<MouseEvent>() {
         @Override public void handle(MouseEvent mouseEvent) {
					 // Record mouse click coordinates if UMLObject is a Parent.
           if (MODE == Mode.SELECT) {
             if (model instanceof Parent) {
               dragSource.x = mouseEvent.getX();
               dragSource.y = mouseEvent.getY();
             }
						 // Records delta of mouse click coordinates and UMLObject's origin if UMLObject is not
						 // * a Parent.
             else {
               dragSource.x = node.getOriginX() - mouseEvent.getX();
               dragSource.y = node.getOriginY() - mouseEvent.getY();
             }
            model.getScene().setCursor(Cursor.MOVE);
            System.out.println("begin moving Node");
           }
         }
        });

				// Moves UMLObject to new position it was dragged to.
        model.setOnMouseDragged(new EventHandler<MouseEvent>() {
         @Override public void handle(MouseEvent mouseEvent) {
           if (MODE == Mode.SELECT) {
             if (model instanceof Parent) {
               node.move(node.getOriginX() + (mouseEvent.getX() - dragSource.x),
							 	node.getOriginY() + (mouseEvent.getY() - dragSource.y));
             } else {
               node.move(mouseEvent.getX() + dragSource.x, mouseEvent.getY() + dragSource.y);
             }
             node.update();
             System.out.println("moved Node");
           }
         }
        });

				// Sets cursor back to default when mouse is released on the UMLObject.
        model.setOnMouseReleased(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (MODE == Mode.SELECT) {
              model.getScene().setCursor(Cursor.HAND);
            }
          }
        });

				// Sets cursor to HAND when mouse enters the UMLObject.
        model.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            if (MODE == Mode.SELECT) {
              if (!mouseEvent.isPrimaryButtonDown()) {
                model.getScene().setCursor(Cursor.HAND);
              }
            }
          }
        });

				// Sets cursor back to default when mouse exits the UMLObject.
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

		// Returns recognzed UMLObject for given Object (typically one's underlying structure).
		// If given Object is not recognized, but has an ancestor that is, finds that
		// ancestor and returns it.
		public UMLObject getObject (Object inModel) {
      Node model = (Node) inModel;
      UMLObject returnNode = NODES.get(model);
			// If model is not a key in NODES (returns null to returnNode), it's UMLObject must be a
			// * UMLConnector
      if (returnNode == null) {
        returnNode = CONNECTORS.get(model);
      }
			// Follow ancestry line until recognized UMLObject is found or until pane is reached or until
			// * end of ancestry line is reached.
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

// ---------------------------------------------------------------------------------------------- \\
	// MenuBar Action Controller

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
		fileChooser.getExtensionFilters().add(
			new FileChooser.ExtensionFilter("JUML txt files", "*.txt"));
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(window);
		window.setTitle("Team Rocket UML Editor: " + file.toString());
		try {
			// Clears current pane contents NODES = new HashMap<>(file);
			pane.getChildren().clear();
			BufferedReader input = new BufferedReader(new FileReader(new File(file.toString())));
			String hashy = input.readLine();
			System.out.println(hashy);
			input.close();
			// TODO: Load file text as pane children.

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

	// Action to Save File WIP:
	public void menuSaveClicked() throws IOException {
		String hashMap = NODES.toString();
		String fxmlContent = pane.getChildren().toString();

		fileChooser.getExtensionFilters().add(
			new FileChooser.ExtensionFilter("JUML txt files", "*.txt"));
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

	//Inspector Properties WIP
	public void circleOriginX(UMLObject target){
		circleOriginX.setText(String.valueOf(target.originX));
	}

}
// ---------------------------------------------------------------------------------------------- \\
