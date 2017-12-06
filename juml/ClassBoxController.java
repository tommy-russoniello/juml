package juml;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import umlobject.*;
import umlaction.*;

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

	//ClassBox.fxml IDs
	@FXML public TextField classBoxName;
	@FXML public TextArea classBoxAttributes;
	@FXML public TextArea classBoxMethods;
	@FXML private TextField classBoxOriginX;
	@FXML private TextField classBoxOriginY;
	@FXML private Button applyButton;

	//Base variables to pass in classBox object
	ClassBox classBox = null;
	Controller controller;

	public String getOriginXText() {
		return classBoxOriginX.getText();
	}

	public String getOriginYText() {
		return classBoxOriginY.getText();
	}

	public void setOriginCoordinatesText(double x, double y) {
		classBoxOriginX.setText(Double.toString(x));
		classBoxOriginY.setText(Double.toString(y));
	}

	/*
	 * Basic Getter to receive the UMLObject
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getClassBox(UMLObject object){
		classBox = (ClassBox) object;

	}

	/*
	 * Updates the classBox.fxml file with the string from the name variable of the classBox
	 * @postcondition sets the text of the classBoxName fx:id with the string of the classBox object variable name
	 */
	public void classBoxNameSetText(){
		classBoxName.setText(classBox.getName());
	}

	/*
	 * Updates the classBox.fxml file with the string from the attributes variable of the classBox
	 * @postcondition sets the text of the classBoxAttributes fx:id with the string of the classBox object variable attributes
	 */
	public void classBoxAttributesSetText(){
		classBoxAttributes.setText(classBox.getAttributes());
	}

	/*
	 * Updates the classBox.fxml file with the string from the methods variable of the classBox
	 * @postcondition sets the text of the classBoxMethods fx:id with the string of the classBox object variable methods
	 */
	public void classBoxMethodsSetText(){
		classBoxMethods.setText(classBox.getMethods());
	}

	/*
	 * Updates the classBox.fxml file with the Origin X coordinate of the classBox
	 * @postcondition sets the text of the classBoxOriginX fx:id with the value of the classBox Origin X coordinate
	 */
	public void classBoxOriginXSetText(){
		classBoxOriginX.setText(Double.toString(classBox.getOriginX()));
	}

	/*
	 * Updates the classBox.fxml file with the Origin Y coordinate of the classBox
	 * @postcondition sets the text of the classBoxOriginY fx:id with the value of the classBox Origin Y coordinate
	 */
	public void classBoxOriginYSetText(){
		classBoxOriginY.setText(Double.toString(classBox.getOriginY()));
	}

	public void updateName(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			controller.ACTIONS.push(new ChangeClassBoxName(classBox, classBoxName.getText()));
		}
	}

	public void updateAttributes(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			controller.ACTIONS.push(new ChangeClassBoxAttributes(classBox, classBoxAttributes.getText()));
		}
	}

	public void updateMethods(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			controller.ACTIONS.push(new ChangeClassBoxMethods(classBox, classBoxMethods.getText()));
		}
	}

	/*
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

	/*
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

	/*
	 * This calls all fxml updating methods in classBoxController to update classBox.fxml with the variables from the object.
	 * Makes it easier on the main controller to activate everything it needs by having this one method.
	 * @param object the UMLObject that is being taken in from the main controller to then be passed to getClassBox
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
