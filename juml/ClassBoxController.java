package juml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import umlobject.*;
import umlaction.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassBoxController.
 */
/*
 * Controller class for ClassBox FXML.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class ClassBoxController extends UMLNodeController {

	/** 
	 * The class box name. 
	 */
	@FXML public TextField classBoxName;
	
	/** 
	 * The class box attributes.
	 */ 
	@FXML public TextArea classBoxAttributes;
	
	/** 
	 * The class box methods. 
	 */
	@FXML public TextArea classBoxMethods;
	
	/** 
	 * The class box origin X. 
	 */
	@FXML private TextField classBoxOriginX;
	
	/** 
	 * The class box origin Y. 
	 */
	@FXML private TextField classBoxOriginY;
	
	/** 
	 * The apply button. 
	 */
	@FXML private Button applyButton;

	/** 
	 * The class box. 
	 */
	ClassBox classBox = null;
	
	/** 
	 * The controller. 
	 */
	Controller controller;

	/** 
	 * Gets the value of the class box origin x as a string.
	 */
	public String getOriginXText() {
		return classBoxOriginX.getText();
	}

	/** 
	 * Gets the value of the class box origin y as a string.
	 */
	public String getOriginYText() {
		return classBoxOriginY.getText();
	}

	/** 
	 * Sets the value of the class box coordinates as strings.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void setOriginCoordinatesText(double x, double y) {
		classBoxOriginX.setText(Double.toString(x));
		classBoxOriginY.setText(Double.toString(y));
	}

	/**
	 * Basic Getter to receive the UMLObject
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getClassBox(UMLObject object){
		classBox = (ClassBox) object;

	}

	/**
	 * Updates the classBox.fxml file with the string from the name variable of the classBox
	 * @postcondition sets the text of the classBoxName fx:id with the string of the classBox object variable name
	 */
	public void classBoxNameSetText(){
		classBoxName.setText(classBox.getName());
	}

	/**
	 * Updates the classBox.fxml file with the string from the attributes variable of the classBox
	 * @postcondition sets the text of the classBoxAttributes fx:id with the string of the classBox object variable attributes
	 */
	public void classBoxAttributesSetText(){
		classBoxAttributes.setText(classBox.getAttributes());
	}

	/**
	 * Updates the classBox.fxml file with the string from the methods variable of the classBox
	 * @postcondition sets the text of the classBoxMethods fx:id with the string of the classBox object variable methods
	 */
	public void classBoxMethodsSetText(){
		classBoxMethods.setText(classBox.getMethods());
	}

	/**
	 * Updates the classBox.fxml file with the Origin X coordinate of the classBox
	 * @postcondition sets the text of the classBoxOriginX fx:id with the value of the classBox Origin X coordinate
	 */
	public void classBoxOriginXSetText(){
		classBoxOriginX.setText(Double.toString(classBox.getOriginX()));
	}

	/**
	 * Updates the classBox.fxml file with the Origin Y coordinate of the classBox
	 * @postcondition sets the text of the classBoxOriginY fx:id with the value of the classBox Origin Y coordinate
	 */
	public void classBoxOriginYSetText(){
		classBoxOriginY.setText(Double.toString(classBox.getOriginY()));
	}

	/**
	 * Updates the name of the classbox when the user presses enter.
	 *
	 * @param event is a KeyEvent used to listen for when the user presses enter on the inspector class box name textfield
	 */
	public void updateName(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			controller.ACTIONS.push(new ChangeClassBoxName(classBox, classBoxName.getText()));
		}
	}

	/**
	 * Updates the attributes of the classbox when the user presses enter.
	 *
	 * @param event is a KeyEvent used to listen for when the user presses enter on the inspector attributes textfield
	 */
	public void updateAttributes(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			controller.ACTIONS.push(new ChangeClassBoxAttributes(classBox, classBoxAttributes.getText()));
		}
	}

	/**
	 * Updates the methods of the classbox when the user presses enter.
	 *
	 * @param event is a KeyEvent used to listen for when the user presses enter on the inspector method textfield
	 */
	public void updateMethods(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			controller.ACTIONS.push(new ChangeClassBoxMethods(classBox, classBoxMethods.getText()));
		}
	}

	/**
	 * Moves the classBox to a new location based on the values entered into the inspector Object for X and Y coordinates.
	 * Activates when the user presses Enter
	 * @param event is a KeyEvent used to listen for when the user presses enter on the inspector textfields for X and Y coordinates
	 * @postcondition sets the X and Y coordinates of the classBox to that of the inspector's textfield's X and Y values
	 */
	public void updateCoordinates(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER){
			try{
			  Double x = Double.parseDouble(classBoxOriginX.getText());
				Double y = Double.parseDouble(classBoxOriginY.getText());
				controller.ACTIONS.push(new MoveUMLNode(classBox, x, y));
		    event.consume();
			} catch (Exception e) {
				event.consume();
			}
		}
	}

	/**
	 * Combination of updateCoordinates and updateMethods, updateName, updateAttributes.
	 * Activates when the user clicks the apply changes button
	 * @param event is a ActionEvent used to listen for when the user clicks the button in the inspector
	 * @postcondition sets the X and Y coordinates of the classBox to that of the inspector's textfield's X and Y values
	 * @postcondition sets the text of all three fields of the classBox to that of the inspector's textfields
	 */
	public void applyChanges(ActionEvent event) {
		String name = classBoxName.getText();
		String attributes = classBoxAttributes.getText();
		String methods = classBoxMethods.getText();
		double x = Double.parseDouble(classBoxOriginX.getText());
		double y = Double.parseDouble(classBoxOriginY.getText());

		if ((!name.equals(classBox.getName())) ||
				(!attributes.equals(classBox.getAttributes())) ||
				(!methods.equals(classBox.getMethods())) ||
				x != classBox.getOriginX() ||
				y != classBox.getOriginY()
				) {
			try{
				controller.ACTIONS.push(new UpdateClassBox(classBox, name, attributes, methods, x, y, this));
				event.consume();
			} catch (Exception e){
				controller.ACTIONS.push(new UpdateClassBox(classBox, name, attributes, methods,
					classBox.getOriginX(), classBox.getOriginY(), this));
				event.consume();
			}
		}
	}

	/**
	 * This calls all fxml updating methods in classBoxController to update classBox.fxml with the variables from the object.
	 * Makes it easier on the main controller to activate everything it needs by having this one method.
	 * @param object the UMLObject that is being taken in from the main controller to then be passed to getClassBox
	 * @param inController the controller being used to change the class box fields
	 * @postcondition methods are called and classBox.fxml holds all the up to date information given the object that is passed through.
	 */
	public void loadInspectorInfo(UMLObject object, Controller inController){
		controller = inController;
		getClassBox(object);
		classBoxNameSetText();
		classBoxAttributesSetText();
		classBoxMethodsSetText();
		classBoxOriginXSetText();
		classBoxOriginYSetText();

	}

}
