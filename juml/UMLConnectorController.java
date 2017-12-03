package juml;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import umlobject.UMLConnector;
import umlobject.UMLObject;

/*
 * Controller class for UMLConnector FXML.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UMLConnectorController {

	//UMLConnector.fxml IDs
	@FXML TextField UMLConnectorStartOriginX;
	@FXML TextField UMLConnectorStartOriginY;
	@FXML TextField UMLConnectorEndOriginX;
	@FXML TextField UMLConnectorEndOriginY;

	//Base variables to pass in UMLConnector object
	UMLConnector connector = null;
	UMLObject object = null;
	Controller controller;

	/*
	 * Basic Constructor
	 * @param
	 * @postcondition
	 */
	public UMLConnectorController() throws IOException{

	}

	/*
	 * Basic Getter to receive the UMLObject
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getUMLConnector(UMLObject object){
		object = object;
		connector = (UMLConnector)object;
	}

	/*
	 * Updates the UMLConnector.fxml file with the start Origin X coordinate of the UMLConnector
	 * @postcondition sets the text of the UMLConnectorstartOriginX fx:id with the value of the
	 * UMLConnector start Origin X coordinate
	 */
	public void getStartOriginX(){
		UMLConnectorStartOriginX.setText(Double.toString(connector.getStart().getOriginX()));
		UMLConnectorStartOriginX.setDisable(true);
	}

	/*
	 * Updates the UMLConnector.fxml file with the start Origin Y coordinate of the UMLConnector
	 * @postcondition sets the text of the UMLConnectorstartOriginY fx:id with the value of the
	 * UMLConnector start Origin Y coordinate
	 */
	public void getStartOriginY(){
		UMLConnectorStartOriginY.setText(Double.toString(connector.getStart().getOriginY()));
		UMLConnectorStartOriginY.setDisable(true);
	}

	/*
	 * Updates the UMLConnector.fxml file with the end Origin X coordinate of the UMLConnector
	 * @postcondition sets the text of the UMLConnectorEndOriginX fx:id with the value of the
	 * UMLConnector end Origin X coordinate
	 */
	public void getEndOriginX(){
		UMLConnectorEndOriginX.setText(Double.toString(connector.getStop().getOriginX()));
		UMLConnectorEndOriginX.setDisable(true);
	}

	/*
	 * Updates the UMLConnector.fxml file with the end Origin Y coordinate of the UMLConnector
	 * @postcondition sets the text of the UMLConnectorEndOriginY fx:id with the value of the
	 * UMLConnector end Origin Y coordinate
	 */
	public void getEndOriginY(){
		UMLConnectorEndOriginY.setText(Double.toString(connector.getStop().getOriginY()));
		UMLConnectorEndOriginY.setDisable(true);
	}

	/*
	 * This calls all fxml updating methods in UMLConnectorController to update UMLConnector.fxml
	 * with the variables from the object. Makes it easier on the main controller to activate
	 * everything it needs by having this one method.
	 * @param object the UMLObject that is being taken in from the main controller to then be passed
	 * * to getUMLConnector
	 * @postcondition methods are called and UMLConnector.fxml holds all the up to date information
	 * * given the object that is passed through.
	 */
	public void loadInspectorInfo(UMLObject object, Controller inController){
		controller = inController;
		getUMLConnector(object);
		getStartOriginX();
		getStartOriginY();
		getEndOriginX();
		getEndOriginY();
	}

}
