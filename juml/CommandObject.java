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
import java.util.Stack;

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

public class CommandObject {

	/*
	 * All UMLNodes currently on the pane.
	 */
	static Map<Node, UMLNode> NODES = new HashMap<>();
	/*
	 * All UMLConnectors currently on the pane
	 */
	static Map<Node, UMLConnector> CONNECTORS = new HashMap<>();

	static Pane pane;

	static Stack<CommandObject> actions = new Stack<>();
	static Stack<CommandObject> undoneActions = new Stack<>();

	public static void setState(Pane p) {
		pane = p;
		if (NODES != null && CONNECTORS != null && pane != null) {
			System.out.println("Set Up complete");
		}
	}

	public static Map<Node, UMLNode> getNodes() {
		return NODES;
	}
	
	public static Map<Node, UMLConnector> getConnectors() {
		return CONNECTORS;
	}


	public static void undo() {
		//System.out.println("Undo called");
		if (!actions.isEmpty()) {
			CommandObject a = actions.pop();
			a.undoAction();
			undoneActions.push(a);
		} else {
			System.out.println("Nothing to undo");
		}
	}

	public static void redo() {
		//System.out.println("Redo called");
		if (!undoneActions.isEmpty()) {
			CommandObject a = undoneActions.pop();
			a.doAction();
			actions.push(a);
		} else {
			System.out.println("Nothing to redo");
		}
	}

	public static void reset() {
		NODES = new HashMap<>();
		CONNECTORS = new HashMap<>();
		actions.clear();
		undoneActions.clear();
	}

	public CommandObject(Boolean addToStack) {
		undoneActions.clear();
		if (addToStack) {
			actions.push(this);
		}
	}

	public void doAction() {
	}

	public void undoAction() {
	}

}
