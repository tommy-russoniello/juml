package juml;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import umlobject.*;
import umlaction.*;

// TODO: Auto-generated Javadoc
// ---------------------------------------------------------------------------------------------- \\

/**
 * MVC styled controller between model and view.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.1
 */
public class Controller {

	/**
	 * The different draw modes that the program can take on.
	 */
	// Draw modes
	public enum Mode {

		SELECT,

		POINT,

		CLASSBOX,

		DELETE,

		ASSOCIATION,

		DEPENDENCY,

		AGGREGATION,

		COMPOSITION,

		GENERALIZATION,

		LINESPLIT,

		LINE,

		NOTE
	}

	/**
	 * All UMLNodes currently on the pane.
	 */
	public Map<Node, UMLNode> NODES = new HashMap<>();

	/**
	 * All UMLConnectors currently on the pane
	 */
	public Map<Node, UMLConnector> CONNECTORS = new HashMap<>();

	/**
	 * Main pane that contains all drawn nodes as children.
	 */
	@FXML public Pane pane;

	/**
	 * Overarching pane that defines the layout for the program.
	 */
	@FXML public AnchorPane inspectorObject;

	/**
	 * The scroll pane that allows the pane to be scrolled.
	 */
	@FXML public ScrollPane scrollPane;

	/**
	 * Current mode. Defaulted to SELECT.
	 */
	public Mode MODE = Mode.SELECT;

	/**
	 * Collection of all "selected" UMLNodes
	 */
	Deque<UMLObject> SELECTED = new LinkedList<>();

	/**
	 * The file chooser.
	 */
	FileChooser fileChooser = new FileChooser();

	/**
	 * The stack of actions that have been performed.
	 */
	public Stack<UMLAction> ACTIONS = new Stack<>();

	/**
	 * The stack of undone actions.
	 */
	public Stack<UMLAction> UNDONE_ACTIONS = new Stack<>();

	/**
	 * The clip board.
	 */
	public Set<String> CLIP_BOARD = new HashSet<>();


	/**
	 * The window.
	 */
	Stage window;

	/**
	 * The scene.
	 */
	Scene scene;

// ---------------------------------------------------------------------------------------------- \\

	/**
	 * Sets the Primary Stage. Called from Main.
	 * @param primaryStage Primary stage of application instance.
	 */
	public void setPrimaryStage(Stage primaryStage) {
		window = primaryStage;
		scene = window.getScene();

		//Handles keyboard shortcut and performs corresponding action.
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
	          if ((new KeyCodeCombination(KeyCode.R, KeyCombination.META_DOWN)).match(event)) {
	            refresh();
	            event.consume();
	          }
						if ((new KeyCodeCombination(KeyCode.C, KeyCombination.META_DOWN)).match(event)) {
	            copy();
	            event.consume();
	          }
						if ((new KeyCodeCombination(KeyCode.V, KeyCombination.META_DOWN)).match(event)) {
	            paste();
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
	          if ((new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN)).match(event)) {
	            refresh();
	            event.consume();
	          }
						if ((new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN)).match(event)) {
	            copy();
	            event.consume();
	          }
						if ((new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN)).match(event)) {
	            paste();
	            event.consume();
	          }
	        }
					if (event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE) {
						UMLObject [] selected = SELECTED.toArray(new UMLObject [0]);
						deselectAll();
						deleteObjects(selected);
					}
	      }
	    });

		//Handles a mouse pressed event and either sets the pane to be pannable, or handles scrolling the pane.
		scene.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			if(MODE == Mode.SELECT){
				if(e.isSecondaryButtonDown()){
					scrollPane.setPannable(true);
				}
				else{
					scrollPane.setPannable(false);
					try{
						UMLObject node = getObject(e.getTarget());
						double rightBound = scrollPane.getContent().getLayoutBounds().getWidth()*scrollPane.getHvalue();
						if (rightBound < 604){
							rightBound = 604;
						}
						double leftBound = rightBound - 604;
						double bottomBound = scrollPane.getContent().getLayoutBounds().getHeight()*scrollPane.getVvalue();
						if (bottomBound < 460){
							bottomBound = 460;
						}
						double topBound = bottomBound - 460;
						if(node instanceof UMLNode){
							if(node instanceof ClassBox){
								double width = node.getOriginX() + (((ClassBox) node).getWidth()/2);
								double height = node.getOriginY() + (((ClassBox) node).getHeight()/2);
								if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){

								}
								else {
									if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
										scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
									}
									if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
										scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
									}
								}
							}
							else if(node instanceof Note){
								double width = node.getOriginX() + (((Note) node).getWidth()/2);
								double height = node.getOriginY() + (((Note) node).getHeight()/2);
								if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){

								}
								else {
									if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
										scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
									}
									if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
										scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
									}
								}
							}
							else if(node instanceof Point){
								double radius = ((Point) node).getRadius();
								double width = ((Point) node).getOriginX() + radius;
								double height = ((Point) node).getOriginY() + radius;
								if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){

								}
								else{
									if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
										scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
									}
									if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
										scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
									}
								}
							}
						}
						else if (node instanceof Relationship && e.getTarget() instanceof Circle){
							Relationship relationship = (Relationship) node;
							Pivot pivot = getPivot(relationship, (Circle) e.getTarget());
							double width = pivot.getOriginX();
							double height = pivot.getOriginY();
							if((((leftBound < width) && ( width < rightBound)) || ((topBound < height) && (height < bottomBound)))){

							}
							else{
								if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
									scrollPane.setHvalue(pivot.getOriginX() / scrollPane.getContent().getLayoutBounds().getWidth());
								}
								if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
									scrollPane.setVvalue(pivot.getOriginY() / scrollPane.getContent().getLayoutBounds().getHeight());
								}
							}
						}
					}
					catch(NullPointerException npe){
					}
				}
			}
			else{
				scrollPane.setPannable(false);
			}
		});

		//Handles scrolling the pane as the mouse is dragged.
		scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
			if(MODE == Mode.SELECT){
				try{
					UMLObject node = getObject(e.getTarget());
					double rightBound = scrollPane.getContent().getLayoutBounds().getWidth()*scrollPane.getHvalue();
					if (rightBound < 604){
						rightBound = 604;
					}
					double leftBound = rightBound - 604;
					double bottomBound = scrollPane.getContent().getLayoutBounds().getHeight()*scrollPane.getVvalue();
					if (bottomBound < 460){
						bottomBound = 460;
					}
					double topBound = bottomBound - 460;
					if(node instanceof UMLNode){
						if(node instanceof ClassBox){
							double width = node.getOriginX() + (((ClassBox) node).getWidth()/2);
							double height = node.getOriginY() + (((ClassBox) node).getHeight()/2);
							if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){
								scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
								scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
							}
							else{
								if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
									scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
								}
								if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
									scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
								}
							}
						}
						else if(node instanceof Note){
							double width = node.getOriginX() + (((Note) node).getWidth()/2);
							double height = node.getOriginY() + (((Note) node).getHeight()/2);
							if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){
								scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
								scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
							}
							else {
								if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
									scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
								}
								if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
									scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
								}
							}
						}
						else if(node instanceof Point){
							double radius = ((Point) node).getRadius();
							double width = ((Point) node).getOriginX() + radius;
							double height = ((Point) node).getOriginY() + radius;
							if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){
								scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
								scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
							}
							else {
								if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
									scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
								}
								if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
									scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
								}
							}
						}
					}
					else if (node instanceof Relationship && e.getTarget() instanceof Circle){
						Relationship relationship = (Relationship) node;
						Pivot pivot = getPivot(relationship, (Circle) e.getTarget());
						double width = pivot.getOriginX();
						double height = pivot.getOriginY();
						if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){
							scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
							scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
						}
						else{
							if((pivot.getOriginX() > scrollPane.getViewportBounds().getWidth()) || (pivot.getOriginX() > 0)){
								scrollPane.setHvalue(pivot.getOriginX() / scrollPane.getContent().getLayoutBounds().getWidth());
							}
							if((pivot.getOriginY() > scrollPane.getViewportBounds().getHeight()) || (pivot.getOriginY() > 0)){
								scrollPane.setVvalue(pivot.getOriginY() / scrollPane.getContent().getLayoutBounds().getHeight());
							}
						}
					}
				}
				catch(NullPointerException npe){
				}
			}
		});

		//Handles setting the scrollbars in the correct position after an object is released from dragging
		scene.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
			if(MODE == Mode.SELECT){
				try{
					UMLObject node = getObject(e.getTarget());
					double rightBound = scrollPane.getContent().getLayoutBounds().getWidth()*scrollPane.getHvalue();
					if (rightBound < 604){
						rightBound = 604;
					}
					double leftBound = rightBound - 604;
					double bottomBound = scrollPane.getContent().getLayoutBounds().getHeight()*scrollPane.getVvalue();
					if (bottomBound < 460){
						bottomBound = 460;
					}
					double topBound = bottomBound - 460;
					if(node instanceof UMLNode){
						 if(node instanceof ClassBox){
							double width = node.getOriginX() + (((ClassBox) node).getWidth()/2);
							double height = node.getOriginY() + (((ClassBox) node).getHeight()/2);
							if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){

							}
							else{
								if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
									scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
								}
								if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
									scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
								}
							}
						}
						else if(node instanceof Note){
							double width = node.getOriginX() + (((Note) node).getWidth()/2);
							double height = node.getOriginY() + (((Note) node).getHeight()/2);
							if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){

							}
							else{
								if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
									scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
								}
								if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
									scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
								}
							}
						}
						else if(node instanceof Point){
							double radius = ((Point) node).getRadius();
							double width = ((Point) node).getOriginX() + radius;
							double height = ((Point) node).getOriginY() + radius;
							if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){

							}
							else{
								if((width > scrollPane.getViewportBounds().getWidth()) || (width > 0)){
									scrollPane.setHvalue(width / scrollPane.getContent().getLayoutBounds().getWidth());
								}
								if((height > scrollPane.getViewportBounds().getHeight()) || (height > 0)){
									scrollPane.setVvalue(height / scrollPane.getContent().getLayoutBounds().getHeight());
								}
							}
						}
					}
					else if (node instanceof Relationship && e.getTarget() instanceof Circle){
						Relationship relationship = (Relationship) node;
						Pivot pivot = getPivot(relationship, (Circle) e.getTarget());
						double width = pivot.getOriginX();
						double height = pivot.getOriginY();
						if((((leftBound < width) && (width < rightBound)) || ((topBound < height) && (height < bottomBound)))){

						}
						else{
							if((pivot.getOriginX() > scrollPane.getViewportBounds().getWidth()) || (pivot.getOriginX() > 0)){
								scrollPane.setHvalue(pivot.getOriginX() / scrollPane.getContent().getLayoutBounds().getWidth());
							}
							if((pivot.getOriginY() > scrollPane.getViewportBounds().getHeight()) || (pivot.getOriginY() > 0)){
								scrollPane.setVvalue(pivot.getOriginY() / scrollPane.getContent().getLayoutBounds().getHeight());
							}
						}
					}
				}
				catch(NullPointerException npe){
				}
			}
		});
	}

	/**
	 * Returns pane.
	 * @return Main pane being used for application instance.
	 */
	public Pane getPane() {
		return pane;
	}

	/**
	 * Sets the pane.
	 *
	 * @param p the new pane
	 */
	public void setPane(Pane p) {
		pane = p;
	}

	/**
	 * Undoes actions by removing the most recent action from the ACTIONS stack and calling its undo action.
	 *  The undone action is then pushed to the UNDONE_ACTIONS stack.
	 */
	public void undo() {
		if (!ACTIONS.isEmpty()) {
			deselectAll();
			UMLAction a = ACTIONS.pop();
			a.undoAction();
			UNDONE_ACTIONS.push(a);
			refresh();
		} else {
			System.out.println("Nothing to undo");
		}
	}

	/**
	 * Redoes actions by removing the most recent action from the UNDONE_ACTIONS stack and calling its do action.
	 *  The redone action is then pushed to the ACTIONS stack.
	 */
	public void redo() {
		if (!UNDONE_ACTIONS.isEmpty()) {
			deselectAll();
			UMLAction a = UNDONE_ACTIONS.pop();
			a.doAction();
			ACTIONS.push(a);
			refresh();
		} else {
			System.out.println("Nothing to redo");
		}
	}

	/**
	 * Resets the containers for all nodes, connectors, actions, and undone actions.
	 */
	public void reset() {
		NODES = new HashMap<>();
		CONNECTORS = new HashMap<>();
		ACTIONS.clear();
		UNDONE_ACTIONS.clear();
	}

	/**
	 * Saves the attributes of the selected object to a string that is added to the clipboard.
	 */
	public void copy() {
		CLIP_BOARD.clear();
		for (UMLObject object : SELECTED) {
			CLIP_BOARD.add(object.saveAsString());
		}
	}

	/**
	 * Takes the string in the clipboard and places the appropriate object on the screen at coordinates 0,0
	 */
	public void paste() {
		for (String string : CLIP_BOARD) {
			Scanner scanner = new Scanner(string);
			if (scanner.hasNextLine()) {
				String nextNode = scanner.next();
				UMLNode node = null;
				if (nextNode.equals("Point:")) {
					node = new Point(scanner);
				} else if (nextNode.equals("ClassBox:")) {
					node = new ClassBox(scanner);
				} else if (nextNode.equals("Note:")) {
					node = new Note(scanner);
				} else {
					System.out.println("Error! Unknown object type: " + nextNode);
					return;
				}
				node.move(0, 0);
				deselectAll();
				addObjects(node);
				selectObject(node);
			}
		}
	}

	/**
	 * Changes mode according to event source.
	 * @precondition Called by event on object builder button.
	 * @param event Event triggering this method.
	 * @postcondition MODE is updated according to button that received event triggering this method.
	 */
	public void modeClick(ActionEvent event) {
		String newModeString = ((Button) event.getSource()).getId();
		newModeString = newModeString.substring(0, newModeString.length() - 4).toUpperCase();
		Mode newMode = Mode.valueOf(newModeString);
		if (newMode != MODE && newMode != Mode.SELECT) {
			deselectAll();
		}
		MODE = newMode;
		System.out.println("Draw mode changed to \"" + MODE + "\"");
	}

	/**
	 * Performs draw action relative to current draw mode.
	 * @precondition Called by a MouseEvent on the pane.
	 * @param event Event triggering this method.
	 * @postcondition Mode-defined operation is performed on coordinate of event.
	 */
	public void paneClick(MouseEvent event) throws IOException {
		double xOff = getXOffset();
		double yOff = getYOffset();
		double xClick = xOff + event.getX();
		double yClick = yOff + event.getY();

		System.out.println("Pane clicked at " + xClick + " " + yClick);

		switch (MODE) {
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
					if (target instanceof Relationship && event.getTarget() instanceof Circle) {
						Relationship relationship = (Relationship) target;
						Pivot pivot = getPivot(relationship, (Circle) event.getTarget());
						if (pivot != null) {
							ACTIONS.push(new DeletePivot(relationship, pivot));
							UNDONE_ACTIONS.clear();
							return;
						}
					}
					deleteObjects(target);
				}

				break;

			// Adds Point UMLNode to pane coordinates that were clicked on.
			case POINT:
				deselectAll();
				Point newPoint = new Point(xClick, yClick);
				addObjects(newPoint);
				selectObject(newPoint);

				break;

			// Adds ClassBox UMLNode to pane coordinates that were clicked on.
			case CLASSBOX:
				deselectAll();
				ClassBox newClassBox = new ClassBox(xClick, yClick);
				addObjects(newClassBox);
				selectObject(newClassBox);

				break;

			// Adds any UMLNode clicked to SELECTED
			// If theres a UMLNode already in SELECTED, draws a line from it to currently clicked UMLNode.
			case ASSOCIATION: case DEPENDENCY: case AGGREGATION: case COMPOSITION: case GENERALIZATION:
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
							switch (MODE) {
								case ASSOCIATION:
									Association newAssociation = new Association((UMLNode) SELECTED.getLast(), node);
									deselectAll();
									addObjects(newAssociation);
									selectObject(newAssociation);
									break;

								case DEPENDENCY:
									Dependency newDependency = new Dependency((UMLNode) SELECTED.getLast(), node);
									deselectAll();
									addObjects(newDependency);
									selectObject(newDependency);
									break;

								case AGGREGATION:
									Aggregation newAggregation = new Aggregation((UMLNode) SELECTED.getLast(), node);
									deselectAll();
									addObjects(newAggregation);
									selectObject(newAggregation);
									break;

								case COMPOSITION:
									Composition newComposition = new Composition((UMLNode) SELECTED.getLast(), node);
									deselectAll();
									addObjects(newComposition);
									selectObject(newComposition);
									break;

								case GENERALIZATION:
									Generalization newGeneralization = new Generalization((UMLNode) SELECTED.getLast(), node);
									deselectAll();
									addObjects(newGeneralization);
									selectObject(newGeneralization);
									break;
								case LINE:
									Segment newSegment = new Segment((UMLNode) SELECTED.getLast(), node);
									deselectAll();
									addObjects(newSegment);
									selectObject(newSegment);
									break;
							};
						}
					}

				break;

			case LINESPLIT:
				UMLObject splitPoint = getObject(event.getTarget());
				deselectAllNodes();
				// If relevant node was selected.
				if (splitPoint != null && splitPoint instanceof Relationship) {
					Relationship relationship = (Relationship) splitPoint;
					if (SELECTED.isEmpty()) {
						selectObject(relationship);
					}
					else if (relationship == SELECTED.getLast() && event.getTarget() instanceof Line) {
						Line line = (Line) event.getTarget();
						Segment segment = getSegment(relationship, line);
						if (segment != null) {
							UNDONE_ACTIONS.clear();
							ACTIONS.push(new SplitLine(relationship, segment, xClick, yClick, this));
							deselectAll();
							selectObject(relationship);
						}
					} else {
						deselectAll();
					}
				}

				break;

			case NOTE:
				deselectAll();
				Note note = new Note(xClick, yClick);
				addObjects(note);
				selectObject(note);

			default:

				break;
		}
	}

// ---------------------------------------------------------------------------------------------- \\

	/**
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
			UNDONE_ACTIONS.clear();
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

	/**
	 * Adds already instantiated UMLObject to pane.
	 * @precondition obj is instantiated UMLObject.
	 * @param obj UMLObject to be added.
	 * @postcondition obj is added to pane and relevant map.
	 * @postcondition Any relevant handlers are defined on obj.
	 */
	public void addObjects(UMLObject... objects) {
		UNDONE_ACTIONS.clear();
		for (UMLObject object : objects) {
			if (object instanceof UMLConnector) {
				UMLConnector connector = (UMLConnector) object;
				ACTIONS.push(new AddUMLConnector(connector, this));
			} else {
				UMLNode node = (UMLNode) object;
				ACTIONS.push(new AddUMLNode(node, this));
			}
		}
 	}

	/**
	 * Returns recognized UMLObject for given Object (typically one's underlying model).
	 * @param inModel Object that will have its UMLObject (if it has one) searched for.
	 * @return UMLObject that contains inModel as part of its underlying model. If given Object is
	 *  not recognized, but has an ancestor that is, that ancestor is the return.
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

		/**
		 * Finds the Segment containing the given Line out of all Segments in given UMLConnector. If
		 *  none of the given UMLConnector's Segments contain the given Line, returns null.
		 * @param connector UMLConnector that's Segments will be searched through for containing given
		 *  Line.
		 * @param model Line that's corresponding Segment is being searched for.
		 * @return Segment containing the given Line out all Segments in given UMLConnector. If none of
		 *  the given UMLConnector's Segments contain the given Line, then null.
		 */
		public Segment getSegment(Relationship relationship, Line model) {
			for(Segment segment : relationship.getSegments()) {
				if (segment.getModel() == model) {
					return segment;
				}
			}
			return null;
		}

		/**
		 * Finds the Pivot containing the given Circle out of all Pivots in given UMLConnector. If
		 *  none of the given UMLConnector's Pivots contain the given Circle, returns null.
		 * @param connector UMLConnector that's Pivots will be searched through for containing given
		 *  Circle.
		 * @param model Circle that's corresponding Pivot is being searched for.
		 * @return Pivot containing the given Circle out all Pivots in given UMLConnector. If none of
		 *  the given UMLConnector's Pivots contain the given Circle, then null.
		 */
		public Pivot getPivot(Relationship relationship, Circle model) {
			for(Pivot pivot : relationship.getPivots()) {
				if (pivot.getModel() == model) {
					return pivot;
				}
			}
			return null;
		}

		/**
		 * The passed in object is added to the SELECTED deque and is highlighted. The appropriate inspector is displayed.
		 *
		 * @param object the object to be selected
		 */
		public void selectObject(UMLObject object) {
			SELECTED.addLast(object);
			object.highlight();
			// if window is null then loading FXML crashes the program (should only happen in tests).
			if (window != null) {
				if (object instanceof Segment) {
					loadSegmentFXML(object);
				} else if (object instanceof Relationship) {
					loadRelationshipFXML(object);
				} else if (object instanceof Point) {
					loadPointFXML(object);
				} else if (object instanceof ClassBox) {
					loadClassBoxFXML(object);
				} else if (object instanceof Note) {
					loadNoteFXML(object);
				} else if (inspectorObject != null) {
					inspectorObject.getChildren().clear();
				}
			}
		}

		/**
		 * The passed in object is removed from the SELECTED deque and is unhighlighted.
		 *
		 * @param object the object to be deselected
		 */
		public void deselectObject(UMLObject object) {
			SELECTED.remove(object);
			object.unhighlight();
		}

		/**
		 * All objects in the SELECTED deque are removed. Any inspectors currently displayed are cleared.
		 */
		public void deselectAll() {
			while (!SELECTED.isEmpty()) {
				SELECTED.peekLast().unhighlight();
				SELECTED.removeLast();
			}

			if (inspectorObject != null) {
				inspectorObject.getChildren().clear();
			}
		}

		/**
		 * Any nodes in the SELECTED deque are deselected and unhighlighted.
		 */
		public void deselectAllNodes() {
			for (UMLObject object : SELECTED) {
				if (object instanceof UMLNode) {
					object.unhighlight();
					SELECTED.remove(object);
				}
			}
		}

		/**
		 * Any connectors in the SELECTED deque are deselected and unhighlighted.
		 */
		public void deselectAllConnectors() {
			for (UMLObject object : SELECTED) {
				if (object instanceof UMLConnector) {
					object.unhighlight();
					SELECTED.remove(object);
				}
			}
		}

		/**
		 * Refreshes the values on a UMLNode to allow for relationships to update their coordinates.
		 */
		public void refresh() {
			Iterator iter = NODES.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pair = (Map.Entry) iter.next();
				UMLNode node = (UMLNode) pair.getValue();
				node.move(node.getOriginX(), node.getOriginY());
			}
			iter = CONNECTORS.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pair = (Map.Entry) iter.next();
				if (pair.getValue() instanceof Relationship) {
					Relationship relationship = (Relationship) pair.getValue();
					relationship.update(true);
				}
			}
		}

		/**
		 * Gets the difference between where the pane thinks the user clicked and where the user actually
		 *  clicked along the x axis.
		 * @return the x offset
		 */
		public double getXOffset(){
			double viewWidth = scrollPane.getViewportBounds().getWidth();
			double contentWidth = scrollPane.getContent().getLayoutBounds().getWidth();
			double hValue = scrollPane.getHvalue();
			double hMax= scrollPane.getHmax();
			double hValueRel = hValue / hMax;
			double xLoc = (contentWidth - viewWidth) * hValueRel;
			if(xLoc < 0){
				xLoc = 0;
			}
			return xLoc;
		}

		/**
		 * Gets the difference between where the pane thinks the user clicked and where the user actually
		 *  clicked along the y axis.
		 * @return the y offset
		 */
		public double getYOffset(){
			double viewHeight = scrollPane.getViewportBounds().getHeight();
			double contentHeight = scrollPane.getContent().getLayoutBounds().getHeight();
			double vValue = scrollPane.getVvalue();
			double vMax = scrollPane.getVmax();
			double vValueRel = vValue / vMax;
			double yLoc = (contentHeight - viewHeight) * vValueRel;
			if(yLoc < 0){
				yLoc = 0;
			}
			return yLoc;
		}

		/**
		 * Prints the state of all the different types of objects in the scene.
		 */
		public void printState() {
			Iterator iter;
			System.out.println("--------------------------\nPRINTING STATE");
			System.out.println("\nMODE: "+ MODE);
			System.out.println("\nPANE: " + pane);
			System.out.println("\nSTAGE: " + window);
			System.out.println("\nSCENE: " + scene);
			System.out.println("\nINSPECTOR OBJECT: " + inspectorObject);
			System.out.println("\nFILE CHOOSER: " + fileChooser);
			System.out.println("\nCLIP BOARD:   size: " + CLIP_BOARD.size());
			for (String string : CLIP_BOARD) {
				System.out.println("\n" + string);
			}
			System.out.println("\nSELECTED:     size: " + SELECTED.size());
			for (UMLObject node : SELECTED) {
				System.out.println(node);
			}
			System.out.println("\nNODES:        size: " + NODES.size());
			iter = NODES.entrySet().iterator();
			while (iter.hasNext()) {
					Map.Entry pair = (Map.Entry) iter.next();
					System.out.print(pair.getKey() + " = " + pair.getValue());
					UMLNode node = (UMLNode) pair.getValue();
					System.out.println("   connectors: " + node.getConnections().size());
			}
			System.out.println("\nCONNECTORS:   size: " + CONNECTORS.size());
			iter = CONNECTORS.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pair = (Map.Entry) iter.next();
				System.out.print(pair.getKey() + " = " + pair.getValue());
				if (pair.getValue() instanceof Relationship) {
					Relationship relationship = (Relationship) pair.getValue();
					System.out.println("   segments: " + relationship.segments.size() + "   pivots: " +
						relationship.pivots.size());
				} else {
					System.out.println();
				}
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


	/**
	 *  Action used to Close the program: assigned to Exit.
	 * @postcondition Application is exited.
	 */
	public void menuExitClicked() {
		window.close();
	}

	/**
	 * Opens new window with blank file.
	 * @postcondition all maintained variables are reset.
	 */
	public void menuNewClicked() {
		deselectAll();
		pane.getChildren().clear();
		if (inspectorObject != null) {
			inspectorObject.getChildren().clear();
		}
		NODES = new HashMap<>();
		CONNECTORS = new HashMap<>();
		CLIP_BOARD.clear();
		ACTIONS.clear();
		UNDONE_ACTIONS.clear();
	}

	/**
	 * Action to Open file while in progress.
	 */
	public void menuOpenClicked() {
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JUML txt files", "*.juml"));
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(window);
		if (file == null) {
			return;
		}
		window.setTitle("Team Rocket UML Editor: " + file.toString());
		try {
			menuNewClicked();
			ACTIONS = new Stack<>();
			UNDONE_ACTIONS = new Stack<>();
			// Clears current pane contents NODES = new HashMap<>(file);
			//pane.getChildren().clear();
			Scanner input = new Scanner(file);
			Vector <UMLNode> allNodes  = new Vector<UMLNode>();
			while (input.hasNextLine()) {
				if (!input.hasNext()) {
					break;
				}
				String nextNode = input.next();
				UMLNode node = null;
				if (nextNode.equals("Point:")) {
					node = new Point(input);
				} else if (nextNode.equals("ClassBox:")) {
					node = new ClassBox(input);
				} else if (nextNode.equals("Note:")) {
					node = new Note(input);
				} else if (nextNode.equals("Connectors:")){
					input.nextLine();
					break;
				} else {
					System.out.println("Error! Unknown object type: " + nextNode);
					return;
				}
				allNodes.add(node);
				deselectAll();
				addObjects(node);
				selectObject(node);
			}
			Vector <UMLConnector> allConnectors  = new Vector<UMLConnector>();
			while (input.hasNextLine()) {
				if (!input.hasNext()) {
					break;
				}
				String textConnector = input.next();
				UMLConnector connector = null;
				if (textConnector.equals("Aggregation:")) {
					connector = new Aggregation(input, allNodes, this);
				} else if (textConnector.equals("Association:")) {
					connector = new Association(input, allNodes, this);
				} else if (textConnector.equals("Composition:")) {
					connector = new Composition(input, allNodes, this);
				} else if (textConnector.equals("Dependency:")) {
					connector = new Dependency(input, allNodes, this);
				} else if (textConnector.equals("Generalization:")) {
					connector = new Generalization(input, allNodes, this);
				} else if (textConnector.equals("Line:")) {
					System.out.println("Importing a line");
					connector = new Segment(input, allNodes);
				}
				allConnectors.add(connector);
				deselectAll();
				addObjects(connector);
				selectObject(connector);
				printState();
				input.nextLine();
			}

			deselectAll();
			refresh();
			ACTIONS = new Stack<>();
			UNDONE_ACTIONS = new Stack<>();
			System.out.println(allNodes.toString());
			System.out.println("Got to the end of load");

			input.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}


	/**
	 * When the user clicks the save menu option, all objects in the scene have their attributes written to a file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void menuSaveClicked() throws IOException {
		System.out.println("Save Called");
		String hashMap = NODES.toString();
		String fxmlContent = pane.getChildren().toString();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JUML txt files", "*.juml"));
		File file = fileChooser.showSaveDialog(window);
		if (file == null) {
			return;
		}
		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file, true));

			Vector<UMLNode> allNodes = new Vector<UMLNode>();
			for (UMLNode n : NODES.values()) {
				allNodes.add(n);
			}
			for (int i=0; i<allNodes.size(); i++){
				System.out.println(allNodes.get(i));
			}
			for (int i = 0; i < allNodes.size(); i++) {
				UMLNode node = allNodes.get(i);
				if (node instanceof Point) {
					printSaveString(output, (((Point) node).saveAsString()));
				} else if (node instanceof ClassBox) {
					printSaveString(output, (((ClassBox) node).saveAsString()));
				} else if (node instanceof Note) {
					printSaveString(output, (((Note) node).saveAsString()));
				}
			}
			output.write("Connectors:");
			output.newLine();
			Vector<UMLConnector> allConnectors = new Vector<UMLConnector>();
			for (UMLConnector c : CONNECTORS.values()) {
				allConnectors.add(c);
			}
			for (int i=0; i<allConnectors.size(); i++){
				System.out.println(allConnectors.get(i));
			}
			for (int i = 0; i < allConnectors.size(); i++) {
				UMLConnector connector = allConnectors.get(i);
				if (connector instanceof Aggregation) {
					output.write("Aggregation: ");
				} else if (connector instanceof Association) {
					output.write("Association: ");
				} else if (connector instanceof Composition) {
					output.write("Composition: ");
				} else if (connector instanceof Dependency) {
					output.write("Dependency: ");
				} else if (connector instanceof Generalization) {
					output.write("Generalization: ");
				} else {
					output.write("Line: ");
				}
				output.write(connector.saveAsString(allNodes));
				if (connector instanceof Relationship) {
					printSaveString(output, ((Relationship)connector).saveAsString());
				}
				output.newLine();
			}
			output.close();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Prints the save string.
	 *
	 * @param output the BufferedWriter that prints each line of the save string
	 * @param saveString the save string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void printSaveString(BufferedWriter output, String saveString) throws IOException {
		Scanner s = new Scanner(saveString);
		while (s.hasNextLine()) {
			output.write(s.nextLine());
			output.newLine();
		}
	}

	/**
	 * When the user clicks the export menu option, a snapshot of the content of the pane and saves it as an image.
	 *  This image is then saved to a pdf file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws NullPointerException the null pointer exception
	 * @throws FileNotFoundException the file not found exception
	 */
	public void menuExportClicked() throws IOException, NullPointerException, FileNotFoundException{

		deselectAll();
		WritableImage snapshot = scrollPane.getContent().snapshot(new SnapshotParameters(), null);
		PDDocument doc = new PDDocument();
		fileChooser.getExtensionFilters().add(
		new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		String fileName = "";
		try{
		File file = fileChooser.showSaveDialog(window);
		fileName = file.getPath();
		} catch (NullPointerException npe){
			System.out.println("Cancelled");
		}
		BufferedImage image;
		image = SwingFXUtils.fromFXImage(snapshot, null);
		PDPage page = new PDPage(new PDRectangle (image.getWidth(),image.getHeight()));

    try {
    	doc.addPage(page);
    	PDImageXObject img = LosslessFactory.createFromImage(doc, image);

    	try {
				PDPageContentStream contents = new PDPageContentStream(doc, page);
				contents.drawImage(img, 0, 0);
				contents.close();
				doc.save(fileName);
    	}

			catch (FileNotFoundException fnfe) {
				System.out.println("Cancelled");
			}
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * When the user clicks the copy menu option, copy is called.
	 */
	public void menuCopyClicked() {
		copy();
	}

	/**
	 * When the user clicks the paste menu option, paste is called.
	 */
	public void menuPasteClicked() {
		paste();
	}

	/**
	 * When the user clicks the delete menu option, the selected object is deleted.
	 */
	public void menuDeleteClicked() {
		UMLObject [] selected = SELECTED.toArray(new UMLObject [0]);
		deselectAll();
		deleteObjects(selected);
	}

	/**
	 * When the user clicks the select all menu option, all objects are selected.
	 */
	public void menuSelectAllClicked() {
	}

	/**
	 * When the user clicks the select all menu option, the selected object is moved in front of all other objects in the pane.
	 */
	public void menuMoveToFrontClicked() {
		ACTIONS.push(new MoveToFront(this, SELECTED.toArray(new UMLObject [0])));
	}

	/**
	 * When the user clicks the select all menu option, the selected object is moved behind all other objects in the pane.
	 */
	public void menuMoveToBackClicked() {
		ACTIONS.push(new MoveToBack(this, SELECTED.toArray(new UMLObject [0])));
	}

	/**
	 * When the user clicks the undo menu option, undo is called.
	 */
	public void menuUndoClicked() {
		undo();
	}

	/**
	 * When the user clicks the redo menu option, redo is called.
	 */
	public void menuRedoClicked() {
		redo();
	}

	/**
	 * When the user clicks the specifications menu option, the specifications document is displayed.
	 */
	public void menuSpecificationsClicked() {
		File file = new File("https://github.com/tommy-russoniello/juml/Iteration1Specifications.docx");
		// HostServices hostServices = Main.getHostServices();
		// hostServices.showDocument(file.getAbsolutePath());
	}

	/**
	 * Writes all the nodes to an output stream
	 *
	 * @param NODES the map of all nodes in the scene
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void saveFile(Map<Node, UMLNode> NODES) throws IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(
			new FileOutputStream("C:/Users/xTmil_000/Desktop/eclipse/Workspace/juml-master/Hashmap.txt"));
		outputStream.writeObject(NODES);
		outputStream.close();
	}

	/**
	 * Loads inspector fxml and allows user to change properties.
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

    /**
	 * Loads inspector fxml and allows user to change properties.
	 * @param classBox instance of current event target.
	 * @postcondition This loads the dynamic instance of the given classBox fxml and listens to given key/mouse events to change
	 * the inspector/vbox properties.
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

  /**
   * Loads inspector fxml and allows user to change properties.
   *
   * @param connector instance of current event target.
   * @postcondition This loads the dynamic instance of the given line fxml and listens to given key/mouse events to change
   * the inspector/line properties.
   */
	public void loadSegmentFXML(UMLObject connector){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Segment.fxml"));
			Parent root = loader.load();
			SegmentController segmentFXML = loader.getController();
			inspectorObject.getChildren().setAll(root);
			segmentFXML.loadInspectorInfo(connector, this);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Loads inspector fxml and allows user to change properties.
	 *
	 * @param connector instance of current event target.
	 * @postcondition This loads the dynamic instance of the given line fxml and listens to given key/mouse events to change
	 * the inspector/line properties.
	 */
	public void loadRelationshipFXML(UMLObject connector){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Relationship.fxml"));
			Parent root = loader.load();
			RelationshipController relationshipFXML = loader.getController();
			inspectorObject.getChildren().setAll(root);
			relationshipFXML.loadInspectorInfo(connector, this);
		} catch (IOException e){
			e.printStackTrace();
		}
	}


	/**
	 * Loads inspector fxml and allows user to change properties.
	 * @param note instance of current event target.
	 * @postcondition This loads the dynamic instance of the given line fxml and listens to given key/mouse events to change
	 * the inspector/Note properties.
	 */
	public void loadNoteFXML(UMLObject Note) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Note.fxml"));
			Parent root = loader.load();
			NoteController NoteFXML = loader.getController();
			inspectorObject.getChildren().setAll(root);
			NoteFXML.loadInspectorInfo(Note, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
// ---------------------------------------------------------------------------------------------- \\
