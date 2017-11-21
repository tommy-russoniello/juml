package juml;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import umlobject.UMLConnector;
import umlobject.UMLObject;

public class LineController extends InspectorController{

	//Line.fxml IDs
	@FXML TextField lineStartOriginX;
	@FXML TextField lineStartOriginY;
	@FXML TextField lineEndOriginX;
	@FXML TextField lineEndOriginY;
	@FXML CheckBox lineDotted;
	
	//Base variables to pass in Line object
	UMLConnector line = null;
	UMLObject lineUML = null;
	
	/*
	 * Basic Constructor
	 * @param 
	 * @postcondition
	 */
	public LineController() throws IOException{
		
	}
	
	/*
	 * Basic Getter to receive the UMLObject 
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getLine(UMLObject object){
		lineUML = object;
		line = (UMLConnector)lineUML;
	}
	
	/*
	 * Updates the Line.fxml file with the start Origin X coordinate of the line
	 * @postcondition sets the text of the linestartOriginX fx:id with the value of the line start Origin X coordinate
	 */
	public void getStartOriginX(){
		lineStartOriginX.setText(Double.toString(line.getStart().getOriginX()));
		lineStartOriginX.setDisable(true);
	}
	
	/*
	 * Updates the Line.fxml file with the start Origin Y coordinate of the line
	 * @postcondition sets the text of the linestartOriginY fx:id with the value of the line start Origin Y coordinate
	 */
	public void getStartOriginY(){
		lineStartOriginY.setText(Double.toString(line.getStart().getOriginY()));
		lineStartOriginY.setDisable(true);
	}
	
	/*
	 * Updates the Line.fxml file with the end Origin X coordinate of the line
	 * @postcondition sets the text of the lineEndOriginX fx:id with the value of the line end Origin X coordinate
	 */	
	public void getEndOriginX(){
		lineEndOriginX.setText(Double.toString(line.getStop().getOriginX()));
		lineEndOriginX.setDisable(true);
	}

	/*
	 * Updates the Line.fxml file with the end Origin Y coordinate of the line
	 * @postcondition sets the text of the lineEndOriginY fx:id with the value of the line end Origin Y coordinate
	 */	
	public void getEndOriginY(){
		lineEndOriginY.setText(Double.toString(line.getStop().getOriginY()));
		lineEndOriginY.setDisable(true);
	}
	
	
	/*
	 * updates the StrokeArray of this line to either dotted or solid.
	 * if line is dotted, this makes the line solid. If line is solid, this makes the line dotted.
	 * @param event is a MouseEvent that is used to activate this method if the mouse button pressed was PRIMARY
	 * @postcondition the line is changed to either solid or dotted
	 */	
	public void lineStatusToggle(MouseEvent event){
		if (event.getButton() == MouseButton.PRIMARY){
			if(line.isDotted() == true){
				line.makeSolid();
				event.consume();
			} else if (line.isDotted() == false){
				line.makeDotted();
				event.consume();
			}
		}
	}
	
	
	/*
	 * updates the CheckBox of lineDotted depending on if the current line is dotted.
	 * true if dotted
	 * false if solid
	 * @postcondition sets the selected to true or false for lineDotted fx:id depending on if Line.isDotted() returns true or false
	 */	
	public void setCheckBox(){
		if (line.isDotted() == true){
			lineDotted.setSelected(true);
		} else {
			lineDotted.setSelected(false);
		}
	}
	
	/*
	 * This calls all fxml updating methods in LineController to update Line.fxml with the variables from the object.
	 * Makes it easier on the main controller to activate everything it needs by having this one method.  
	 * @param object the UMLObject that is being taken in from the main controller to then be passed to getLine
	 * @postcondition methods are called and Line.fxml holds all the up to date information given the object that is passed through.
	 */
	public void loadInspectorInfo(UMLObject object){
		getLine(object);
		getStartOriginX();
		getStartOriginY();
		getEndOriginX();
		getEndOriginY();
		setCheckBox();
	}

}