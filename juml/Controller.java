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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import umlobject.*;
import umlaction.*;

// ---------------------------------------------------------------------------------------------- \\

/*
 * MVC styled controller between model and view.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.2
 * @since 0.1
 */
public class Controller {

	// Draw modes
	public enum Mode {
		SELECT,
		LINE,
		POINT,
		CLASSBOX,
		DELETE
	}

	/*
	 * All UMLNodes currently on the pane.
	 */
	public Map<Node, UMLNode> NODES = new HashMap<>();
	/*
	 * All UMLConnectors currently on the pane
	 */
	public Map<Node, UMLConnector> CONNECTORS = new HashMap<>();

	// Main node that contains all drawn nodes as children.
	@FXML public Pane pane;
	@FXML public AnchorPane inspectorObject;

	/*
	 * Current mode. Defaulted to SELECT.
	 */
	public Mode MODE = Mode.SELECT;
	/*
	 * Collection of all "selected" UMLNodes
	 */
	Deque<UMLObject> SELECTED = new LinkedList<>();
	FileChooser fileChooser = new FileChooser();

	public Stack<UMLAction> ACTIONS = new Stack<>();
	public Stack<UMLAction> UNDONE_ACTIONS = new Stack<>();


	Stage window;
	Scene scene;

// ---------------------------------------------------------------------------------------------- \\

	/*
	 * Sets the Primary Stage. Called from Main.
	 * @param primaryStage Primary stage of application instance.
	 */
	public void setPrimaryStage(Stage primaryStage) {
		window = primaryStage;
		scene = window.getScene();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent event) {
				if (System.getProperty("os.name").contains("Mac")) {
	        if ((new KeyCodeCombination(KeyCode.Z, KeyCombination.META_DOWN)).match(event)) {
	          undo();
						event.consume();
	        }
					if ((new KeyCodeCombination(KeyCode.Y, KeyCombination.META_DOWN)).match(event)) {
	          redo();
						event.consume();
	        }
					if ((new KeyCodeCombination(KeyCode.P, KeyCombination.META_DOWN)).match(event)) {
						printState();
						event.consume();
					}
				} else {
					if ((new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN)).match(event)) {
	          undo();
						event.consume();
	        }
					if ((new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN)).match(event)) {
	          redo();
						event.consume();
	        }
					if ((new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN)).match(event)) {
						printState();
						event.consume();
					}
				}
      }
  	});
	}

	/*
	 * Returns pane.
	 * @return Main pane being used for application instance.
	 */
	public Pane getPane() {
		return pane;
	}

	/*
	 * Sets pane.
	 */
	public void setPane(Pane p) {
		pane = p;
	}

	public void undo() {
		if (!ACTIONS.isEmpty()) {
			deselectAll();
			UMLAction a = ACTIONS.pop();
			a.undoAction();
			UNDONE_ACTIONS.push(a);
		} else {
			System.out.println("Nothing to undo");
		}
	}

	public void redo() {
		if (!UNDONE_ACTIONS.isEmpty()) {
			deselectAll();
			UMLAction a = UNDONE_ACTIONS.pop();
			a.doAction();
			ACTIONS.push(a);
		} else {
			System.out.println("Nothing to redo");
		}
	}

	public void reset() {
		NODES = new HashMap<>();
		CONNECTORS = new HashMap<>();
		ACTIONS.clear();
		UNDONE_ACTIONS.clear();
	}

	/*
	 * Changes mode according to event source.
	 * @precondition Called by event on object builder button.
	 * @param event Event triggering this method.
	 * @postcondition MODE is updated according to button that received event triggering this method.
	 */
	public void modeClick(ActionEvent event) {
		deselectAll();
		String newMode = ((Button) event.getSource()).getId();
		newMode = newMode.substring(0, newMode.length() - 4).toUpperCase();
		MODE = Mode.valueOf(newMode);
		SELECTED.clear();
		System.out.println("Draw mode changed to \"" + MODE + "\"");
	}

	/*
	 * Performs draw action relative to current draw mode.
	 * @precondition Called by a MouseEvent on the pane.
	 * @param event Event triggering this method.
	 * @postcondition Mode-defined operation is performed on coordinate of event.
	 */
	public void paneClick(MouseEvent event) throws IOException {
		System.out.println("Pane clicked at " + event.getX() + " " + event.getY());

		switch (MODE) {
			// Adds Point UMLNode to pane coordinates that were clicked on.
			case POINT:
				deselectAll();
				Point point = new Point(event.getX(), event.getY());
				addObjects(point);
				selectObject(point);

				break;

			// Adds any UMLNode clicked to SELECTED
			// If theres a UMLNode already in SELECTED, draws a line from it to currently clicked UMLNode.
			case LINE:
				UMLObject object = getObject(event.getTarget());
					deselectAllConnectors();
					// If relevant node was selected.
					if (object != null && !(object instanceof UMLConnector)) {
						UMLNode node = (UMLNode) object;
						if (SELECTED.isEmpty()) {
							selectObject(node);
						}
						// If selected node wasn't just clicked on again.
						else if (node != SELECTED.getLast()) {
							UMLConnector connector = new UMLConnector((UMLNode) SELECTED.getLast(), node);
							deselectAll();
							addObjects(connector);
							selectObject(connector);
						}
					}

				break;

			// Adds ClassBox UMLNode to pane coordinates that were clicked on.
			case CLASSBOX:
					deselectAll();
					ClassBox classBox = new ClassBox(event.getX(), event.getY());
	        addObjects(classBox);
					selectObject(classBox);

        break;

			// Displays information of any UMLObject clicked on in the Inspector.
			case SELECT:
				deselectAll();
				UMLObject selectedObject = getObject(event.getTarget());
				if (selectedObject != null) {
					selectObject(selectedObject);
				}
				break;

			// Remove any UMLObject clicked on.
			case DELETE:
				UMLObject target = getObject(event.getTarget());
				if (target != null) {
					deleteObjects(target);
				}

				break;

			default:

				break;
		}
	}

// ---------------------------------------------------------------------------------------------- \\

	/*
	 * Removes given UMLObject from pane.
	 * @precondition target is recognized UMLObject that is a child of the pane.
	 * @param target UMLObject to be removed.
	 * @postcondition target is removed from pane and relevant map.
	 * @postcondition If target is a UMLNode, any UMLConnectors connected to it are also removed.
	 * @postcondition If target is a UMLConnector, it is removed from the list of connectors for the
	 * * UMLNodes that it connects.
	 */
	public void deleteObjects(UMLObject... objects) {
		for (UMLObject object : objects) {
			if (object instanceof UMLConnector) {
				ACTIONS.push(new DeleteUMLConnector((UMLConnector) object, this));
			} else {
				ACTIONS.push(new DeleteUMLNode((UMLNode) object, this));
			}
			if (inspectorObject != null) {
				inspectorObject.getChildren().clear();
			}
		}
	}

	/*
	 * Adds already instantiated UMLObject to pane.
	 * @precondition obj is instantiated UMLObject.
	 * @param obj UMLObject to be added.
	 * @postcondition obj is added to pane and relevant map.
	 * @postcondition Any relevant handlers are defined on obj.
	 */
	public void addObjects(UMLObject... objects) {
		for (UMLObject object : objects) {
			if (object instanceof UMLConnector) {
				ACTIONS.push(new AddUMLConnector((UMLConnector) object, this));
			} else {
				ACTIONS.push(new AddUMLNode((UMLNode) object, this));
			}
		}
  }

    /*
	 * Returns recognized UMLObject for given Object (typically one's underlying model).
	 * @param inModel Object that will have its UMLObject (if it has one) searched for.
	 * @return UMLObject that contains inModel as part of its underlying model. If given Object is
	 * * not recognized, but has an ancestor that is, that ancestor is the return.
	 */
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

		public void selectObject(UMLObject object) {
			SELECTED.addLast(object);
			object.highlight();
			if (object instanceof UMLConnector) {
				loadUMLConnectorFXML(object);
			} else if (object instanceof Point) {
				loadPointFXML(object);
			} else if (object instanceof ClassBox) {
				loadClassBoxFXML(object);
			} else if (inspectorObject != null) {
				inspectorObject.getChildren().clear();
			}
		}

		public void deselectObject(UMLObject object) {
			SELECTED.remove(object);
			object.unhighlight();
		}

		public void deselectAll() {
			while (!SELECTED.isEmpty()) {
				SELECTED.peekLast().unhighlight();
				SELECTED.removeLast();
			}
		}

		public void deselectAllNodes() {
			for (UMLObject object : SELECTED) {
				if (object instanceof UMLNode) {
					object.unhighlight();
					SELECTED.remove(object);
				}
			}
		}

		public void deselectAllConnectors() {
			for (UMLObject object : SELECTED) {
				if (object instanceof UMLConnector) {
					object.unhighlight();
					SELECTED.remove(object);
				}
			}
		}

		public void printState() {
			Iterator iter;
			System.out.println("--------------------------\nPRINTING STATE");
			System.out.println("\nMODE: "+ MODE);
			System.out.println("\nPANE: " + pane);
			System.out.println("\nSTAGE: " + window);
			System.out.println("\nSCENE: " + scene);
			System.out.println("\nINSPECTOR OBJECT: " + inspectorObject);
			System.out.println("\nFILE CHOOSER: " + fileChooser);
			System.out.println("\nSELECTED:     size: " + SELECTED.size());
			for (UMLObject node : SELECTED) {
				System.out.println(node);
			}
			System.out.println("\nNODES:        size: " + NODES.size());
			iter = NODES.entrySet().iterator();
			while (iter.hasNext()) {
					Map.Entry pair = (Map.Entry) iter.next();
					System.out.println(pair.getKey() + " = " + pair.getValue());
					iter.remove();
			}
			System.out.println("\nCONNECTORS:   size: " + CONNECTORS.size());
			iter = CONNECTORS.entrySet().iterator();
			while (iter.hasNext()) {
					Map.Entry pair = (Map.Entry) iter.next();
					System.out.println(pair.getKey() + " = " + pair.getValue());
					iter.remove();
			}
			System.out.println("\nUNDO STACK:   size: " + ACTIONS.size() + " (bottom to top)");
			for (UMLAction action : ACTIONS) {
				System.out.println(action);
			}
			System.out.println(
				"\nREDO STACK:   size: " + UNDONE_ACTIONS.size() + " (bottom to top)");
			for (UMLAction action : UNDONE_ACTIONS) {
				System.out.println(action);
			}
			System.out.println("\n--------------------------\n");
		}

// ---------------------------------------------------------------------------------------------- \\
	// MenuBar Action Controller

	/*
	 *  Action used to Close the program: assigned to Exit.
	 * @postcondition Application is exited.
	 */
	public void menuExitClicked() {
		window.close();
	}

	/*
	 * Opens new window with blank file.
	 * @postcondition all maintained variables are reset.
	 */
	public void menuNewClicked() {
		pane.getChildren().clear();
		if (inspectorObject != null) {
			inspectorObject.getChildren().clear();
		}
		NODES = new HashMap<>();
	}

	/*
	 * Action to Open file WIP.
	 */
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

	/*
	 * Action to Save File WIP.
	 */
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

	public void menuUndoClicked() {
		undo();
	}

	public void menuRedoClicked() {
		redo();
	}

	public void menuSpecificationsClicked() {
		File file = new File("https://github.com/tommy-russoniello/juml/Iteration1Specifications.docx");
		// HostServices hostServices = Main.getHostServices();
		// hostServices.showDocument(file.getAbsolutePath());
	}

	/*
	 * Function to Save a Hashmap to a txt file WIP.
	 */
	public void saveFile(Map<Node, UMLNode> NODES) throws IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(
			new FileOutputStream("C:/Users/xTmil_000/Desktop/eclipse/Workspace/juml-master/Hashmap.txt"));
		outputStream.writeObject(NODES);
		outputStream.close();
	}

	/*
	 * Inspector Properties WIP.
	 */


    /*
	 * loads inspector fxml and allows user to change properties.
	 * @param point instance of current event target.
	 * @postcondition This loads the dynamic instance of the given circle fxml and listens to given key/mouse events to change
	 * the inspector/circle properties.
	 */
	public void loadPointFXML(UMLObject point){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Point.fxml"));
			Parent root = loader.load();
			PointController pointFXML = loader.getController();
			inspectorObject.getChildren().setAll(root);
			pointFXML.loadInspectorInfo(point, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /*
	 * loads inspector fxml and allows user to change properties.
	 * @param classBox instance of current event target.
	 * @postcondition This loads the dynamic instance of the given classBox fxml and listens to given key/mouse events to change
	 * the inspector/circle properties.
	 */
	public void loadClassBoxFXML(UMLObject classBox){
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassBox.fxml"));
			Parent root = loader.load();
			ClassBoxController classBoxFXML = loader.getController();
			inspectorObject.getChildren().setAll(root);
			classBoxFXML.loadInspectorInfo(classBox, this);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

    /*
	 * loads inspector fxml and allows user to change properties.
	 * @param line instance of current event target.
	 * @postcondition This loads the dynamic instance of the given line fxml and listens to given key/mouse events to change
	 * the inspector/circle properties.
	 */
	public void loadUMLConnectorFXML(UMLObject connector){
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UMLConnector.fxml"));
			Parent root = loader.load();
			UMLConnectorController UMLConnectorFXML = loader.getController();
			inspectorObject.getChildren().setAll(root);
			UMLConnectorFXML.loadInspectorInfo(connector, this);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

}
// ---------------------------------------------------------------------------------------------- \\
