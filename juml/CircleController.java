package juml;

import javafx.scene.input.KeyEvent;
import umlobject.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class CircleController{
	
	//Circle.fxml IDs
	@FXML private TextField circleOriginX;
	@FXML private TextField circleOriginY;
	@FXML private TextField circleRadius;

	//Base variables to pass in Line object
	UMLObject pointUML = null; 
	Point point = null;
	
	/*
	 * Basic Constructor
	 * @param 
	 * @postcondition
	 */
	public CircleController() throws IOException{
		
	}

	/*
	 * Basic Getter to receive the UMLObject 
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getPoint(UMLObject circle){
		pointUML = circle;
		point = (Point)circle;
	
	}
	
	/*
	 * Updates the Circle.fxml file with the Origin X coordinate of the circle
	 * @postcondition sets the text of the circleOriginX fx:id with the value of the circle Origin X coordinate
	 */
	public void circleOriginXSetText(){
		circleOriginX.setText(Double.toString(pointUML.getOriginX()));
	}

	/*
	 * Updates the Circle.fxml file with the Origin Y coordinate of the circle
	 * @postcondition sets the text of the circleOriginY fx:id with the value of the circle Origin Y coordinate
	 */
	public void circleOriginYSetText(){
		circleOriginY.setText(Double.toString(pointUML.getOriginY()));
	}
	
	/*
	 * Updates the Circle.fxml file with the radius of the circle
	 * @postcondition sets the text of the circleRadius fx:id with the value of the circle radius
	 */
	public void circleRadiusSetText(){
		circleRadius.setText(Double.toString(point.getRadius()));
		circleRadius.setDisable(true);
	}
	
	/*
	 * Moves the point to a new location based on the values entered into the inspector Object for X and Y coordinates.
	 * Activates when the user presses Enter
	 * @param event is a KeyEvent used to listen for when the user presses enter on the inspector textfields for X and Y coordinates
	 * @postcondition sets the X and Y coordinates of the circle to that of the inspector's textfield's X and Y values
	 */
	public void circleChange(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) {
			try{
				Double x = Double.parseDouble(circleOriginX.getText());
				Double y = Double.parseDouble(circleOriginY.getText());
				Double radius = Double.parseDouble(circleRadius.getText());
				pointUML.move(x,y);
				point.setRadius(radius);
		    	event.consume(); // Consume Event
			}catch (Exception e){
				event.consume();
			}
		}
	}	
	
	/*
	 * This calls all fxml updating methods in CircleController to update Cirle.fxml with the variables from the object.
	 * Makes it easier on the main controller to activate everything it needs by having this one method.  
	 * @param circle the UMLObject that is being taken in from the main controller to then be passed to getPoint
	 * @postcondition methods are called and circle.fxml holds all the up to date information given the object that is passed through.
	 */
	public void loadInspectorInfo(UMLObject circle){
		getPoint(circle);
		circleOriginXSetText();
		circleOriginYSetText();
		circleRadiusSetText();
	}
}
