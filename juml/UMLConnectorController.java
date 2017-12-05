package juml;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import umlobject.Aggregation;
import umlobject.Association;
import umlobject.Composition;
import umlobject.Dependency;
import umlobject.Generalization;
import umlobject.Relationship;
import umlobject.Segment;
import umlobject.UMLConnector;
import umlobject.UMLNode;
import umlobject.UMLObject;
import umlaction.*;

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
	@FXML TextArea startText;
	@FXML TextArea endText;
	@FXML ChoiceBox<String> lineProperties;

	//Base variables to pass in UMLConnector object
	UMLConnector connector = null;
	UMLObject objectID = null;
	Relationship lineType = null;
	Controller controller;
	Boolean isNormalLine = false;
	Boolean activateChoiceBox = false;
	
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
		objectID = object;
		connector = (UMLConnector)object;
		try {
			lineType = (Relationship)connector;
		} catch (ClassCastException e){
			isNormalLine = true;
		}
		
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
	 * Updates the UMLConnector.fxml file with the start point text of the UMLConnector
	 * @postcondition sets the text of the startText fx:id with the value of the
	 * UMLConnector start point text
	 */
	public void getStartText(){
		if (isNormalLine == false){
			startText.setText(lineType.getStartText());
		} else {
			startText.setText("Not applicable for this line type");
			startText.setDisable(true);
		}
	}
	
	/*
	 * Updates the UMLConnector.fxml file with the end point text of the UMLConnector
	 * @postcondition sets the text of the endText fx:id with the value of the
	 * UMLConnector end point text
	 */
	public void getEndText(){
		if (isNormalLine == false){
			endText.setText(lineType.getEndText());
		} else {
			endText.setText("Not applicable for this line type");
			endText.setDisable(true);
		}
	}
	
	/*
	 * Updates the text of the start Point in the UMLConnector with the UMLConnector.fxml text 
	 * @postcondition activates when user presses Enter
	 */
	public void updateStartText(KeyEvent event){
		if(isNormalLine == false){
			if (event.getCode() == KeyCode.ENTER) {
				lineType.hideText();
				lineType.showText();
				controller.ACTIONS.push(new ChangeRelationshipStartText(lineType, startText.getText(), controller));	
			}
		}
	}
	
	/*
	 * Updates the text of the end Point in the UMLConnector with the UMLConnector.fxml text 
	 * @postcondition activates when user presses Enter
	 */
	public void updateEndText(KeyEvent event){
		if(isNormalLine == false){
			if (event.getCode() == KeyCode.ENTER) {
				lineType.hideText();
				lineType.showText();
				controller.ACTIONS.push(new ChangeRelationshipEndText(lineType, endText.getText(), controller));		
			}
		}
	}
	
	/*
	 * Updates all the text of the UMLConnector with the UMLConnector.fxml text 
	 * @postcondition activates when user clicks the apply changes button
	 */
	public void applyChanges(ActionEvent event){
		lineType.hideText();
		lineType.showText();
		
		controller.ACTIONS.push(new ChangeRelationshipEndText(lineType, endText.getText(), controller));	
		controller.ACTIONS.push(new ChangeRelationshipStartText(lineType, startText.getText(), controller));
	}
	
	/*
	 * Flips the direction of the polygon of the line 
	 * @postcondition activates when user clicks the Flip Direction button
	 */
	public void flipDirection(ActionEvent event){
		event.consume();
	}
	

	public void changeLineType(ActionEvent event){
		UMLConnector newConnector = null;
		if(lineProperties.getValue() == "Line"){
			isNormalLine = true;
			newConnector = new Segment(connector.start,connector.stop);
		} else if (lineProperties.getValue() == "Aggregation"){
			isNormalLine = false;
			newConnector = new Aggregation(connector.start,connector.stop);
		} else if (lineProperties.getValue() == "Association"){
			isNormalLine = false;
			newConnector = new Association(connector.start,connector.stop);
		} else if (lineProperties.getValue() == "Composition"){
			isNormalLine = false;
			newConnector = new Composition(connector.start,connector.stop);
		} else if (lineProperties.getValue() == "Dependency"){
			isNormalLine = false;
			newConnector = new Dependency(connector.start,connector.stop);
		} else if (lineProperties.getValue() == "Generalization"){
			isNormalLine = false;
			newConnector = new Generalization(connector.start,connector.stop);
		}
		
		controller.ACTIONS.push(new DeleteUMLConnector(connector, controller));
		controller.ACTIONS.push(new AddUMLConnector(newConnector, controller));
		try{
			lineType = (Relationship)newConnector;
			lineType.hideText();
			if(startText.getText() == "Not applicable for this line type"){
				controller.ACTIONS.push(new ChangeRelationshipEndText(lineType, "text", controller));	
				controller.ACTIONS.push(new ChangeRelationshipStartText(lineType, "text", controller));
			} else {
				lineType.showText();
				controller.ACTIONS.push(new ChangeRelationshipEndText(lineType, endText.getText(), controller));	
				controller.ACTIONS.push(new ChangeRelationshipStartText(lineType, startText.getText(), controller));
			}
		} catch (ClassCastException e){
			
		}
		getStartText();
		getEndText();
		
		System.out.println(lineProperties.getValue());
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
		getStartText();
		getEndText();
		lineProperties.getItems().addAll("Aggregation","Association","Composition","Dependency","Generalization","Line");
		
		/* this if else condition triggers the changeLineType event, I cant find a way have this code run without triggering the event
		if(isNormalLine == true){
			lineProperties.getSelectionModel().select("Line");
		} else {
			lineProperties.getSelectionModel().select(lineType.getLineType());
		}
		*/

	}

}
