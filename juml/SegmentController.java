package juml;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import umlobject.*;

/*
 * Controller class for Segment FXML.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class SegmentController {

	//UMLConnector.fxml IDs
	@FXML TextField segmentStartOriginX;
	@FXML TextField segmentStartOriginY;
	@FXML TextField segmentEndOriginX;
	@FXML TextField segmentEndOriginY;

	//Base variables to pass in Segment object
	Segment connector = null;
<<<<<<< HEAD
<<<<<<< HEAD
	Controller controller;

	/*
=======
	UMLObject object = null;
	Controller controller;

	/*
	 * Basic Constructor
	 * @param
	 * @postcondition
	 */
	public SegmentController() throws IOException{

	}

	/*
>>>>>>> Update inspector for new functionality
=======
	Controller controller;

	/*
>>>>>>> Complete inspector functionality
	 * Basic Getter to receive the UMLObject
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getSegment(UMLObject object){
<<<<<<< HEAD
<<<<<<< HEAD
		connector = (Segment) object;
=======
		object = object;
		connector = (Segment)object;
>>>>>>> Update inspector for new functionality
=======
		connector = (Segment) object;
>>>>>>> Complete inspector functionality
	}

	/*
	 * Updates the Segment.fxml file with the start Origin X coordinate of the Segment
	 * @postcondition sets the text of the segmentStartOriginX fx:id with the value of the
	 * Segment start Origin X coordinate
	 */
	public void getStartOriginX(){
		segmentStartOriginX.setText(Double.toString(connector.getStart().getOriginX()));
		segmentStartOriginX.setDisable(true);
	}

	/*
	 * Updates the Segment.fxml file with the start Origin Y coordinate of the Segment
	 * @postcondition sets the text of the segmentStartOriginY fx:id with the value of the
	 * Segment start Origin Y coordinate
	 */
	public void getStartOriginY(){
		segmentStartOriginY.setText(Double.toString(connector.getStart().getOriginY()));
		segmentStartOriginY.setDisable(true);
	}

	/*
	 * Updates the Segment.fxml file with the end Origin X coordinate of the Segment
	 * @postcondition sets the text of the segmentEndOriginX fx:id with the value of the
	 * Segment end Origin X coordinate
	 */
	public void getEndOriginX(){
		segmentEndOriginX.setText(Double.toString(connector.getStop().getOriginX()));
		segmentEndOriginX.setDisable(true);
	}

	/*
	 * Updates the Segment.fxml file with the end Origin Y coordinate of the Segment
	 * @postcondition sets the text of the segmentEndOriginY fx:id with the value of the
	 * Segment end Origin Y coordinate
	 */
	public void getEndOriginY(){
		segmentEndOriginY.setText(Double.toString(connector.getStop().getOriginY()));
		segmentEndOriginY.setDisable(true);
	}

	/*
	 * This calls all fxml updating methods in SegmentController to update Segment.fxml
	 * with the variables from the object. Makes it easier on the main controller to activate
	 * everything it needs by having this one method.
	 * @param object the UMLObject that is being taken in from the main controller to then be passed
	 * * to getSegment
	 * @postcondition methods are called and Segment.fxml holds all the up to date information
	 * * given the object that is passed through.
	 */
	public void loadInspectorInfo(UMLObject object, Controller inController){
		controller = inController;
		getSegment(object);
		getStartOriginX();
		getStartOriginY();
		getEndOriginX();
		getEndOriginY();
	}

}
