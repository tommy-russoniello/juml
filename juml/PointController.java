package juml;

import javafx.scene.input.KeyEvent;
import umlobject.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import umlaction.*;

/*
 * Controller class for Point FXML.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class PointController{

	//Point.fxml IDs
	@FXML private TextField pointOriginX;
	@FXML private TextField pointOriginY;
	@FXML private TextField pointRadius;

	//Base variables to pass in UMLConnector object
	UMLObject pointUML;
	Point point;
	Controller controller;

	/*
	 * Basic Constructor
	 * @param
	 * @postcondition
	 */
	public PointController() throws IOException{

	}

	/*
	 * Basic Getter to receive the UMLObject
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getPoint(UMLObject inPoint){
		pointUML = inPoint;
		point = (Point)inPoint;

	}

	/*
	 * Updates the Point.fxml file with the Origin X coordinate of the Point
	 * @postcondition sets the text of the pointOriginX fx:id with the value of the Point Origin X coordinate
	 */
	public void pointOriginXSetText(){
		pointOriginX.setText(Double.toString(pointUML.getOriginX()));
	}

	/*
	 * Updates the Point.fxml file with the Origin Y coordinate of the Point
	 * @postcondition sets the text of the pointOriginY fx:id with the value of the Point Origin Y coordinate
	 */
	public void pointOriginYSetText(){
		pointOriginY.setText(Double.toString(pointUML.getOriginY()));
	}

	/*
	 * Updates the Point.fxml file with the radius of the Point
	 * @postcondition sets the text of the pointRadius fx:id with the value of the Point radius
	 */
	public void pointRadiusSetText(){
		pointRadius.setText(Double.toString(point.getRadius()));
		pointRadius.setDisable(true);
	}

	/*
	 * Moves the point to a new location based on the values entered into the inspector Object for X and Y coordinates.
	 * Activates when the user presses Enter
	 * @param event is a KeyEvent used to listen for when the user presses enter on the inspector textfields for X and Y coordinates
	 * @postcondition sets the X and Y coordinates of the Point to that of the inspector's textfield's X and Y values
	 */
	public void pointChange(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) {
			try{
				Double x = Double.parseDouble(pointOriginX.getText());
				Double y = Double.parseDouble(pointOriginY.getText());
				Double radius = Double.parseDouble(pointRadius.getText());
				point.setRadius(radius);
				controller.ACTIONS.push(new MoveUMLNode(point, x, y));
		    event.consume();
			}catch (Exception e){
				event.consume();
			}
		}
	}

	/*
	 * This calls all fxml updating methods in PointController to update Cirle.fxml with the variables from the object.
	 * Makes it easier on the main controller to activate everything it needs by having this one method.
	 * @param point the UMLObject that is being taken in from the main controller to then be passed to getPoint
	 * @postcondition methods are called and Point.fxml holds all the up to date information given the object that is passed through.
	 */
	public void loadInspectorInfo(UMLObject inPoint, Controller inController){
		controller = inController;
		getPoint(inPoint);
		pointOriginXSetText();
		pointOriginYSetText();
		pointRadiusSetText();
	}
}
