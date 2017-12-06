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
import javafx.collections.FXCollections;
import umlobject.*;
import umlaction.*;

/*
 * Controller class for Relationship FXML.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class RelationshipController {

	//Relationship.fxml IDs
	@FXML TextField relationshipStartOriginX;
	@FXML TextField relationshipStartOriginY;
	@FXML TextField relationshipEndOriginX;
	@FXML TextField relationshipEndOriginY;
	@FXML public TextArea startText;
	@FXML public TextArea endText;
	@FXML ChoiceBox<String> relationshipType;

	//Base variables to pass in Relationship object
	Relationship relationship = null;
<<<<<<< HEAD
	Controller controller;
=======
	UMLObject objectID = null;
	Controller controller;
	Boolean activateChoiceBox = false;

	/*
	 * Basic Constructor
	 * @param
	 * @postcondition
	 */
	public RelationshipController() throws IOException{

	}
>>>>>>> Update inspector for new functionality

	/*
	 * Basic Getter to receive the UMLObject
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getRelationship(UMLObject object){
<<<<<<< HEAD
=======
		objectID = object;
>>>>>>> Update inspector for new functionality
		relationship = (Relationship)object;
	}

	/*
	 * Updates the Relationship.fxml file with the start Origin X coordinate of the Relationship
	 * @postcondition sets the text of the relationshipstartOriginX fx:id with the value of the
	 * Relationship start Origin X coordinate
	 */
	public void getStartOriginX(){
		relationshipStartOriginX.setText(Double.toString(relationship.getStart().getOriginX()));
		relationshipStartOriginX.setDisable(true);
	}

	/*
	 * Updates the Relationship.fxml file with the start Origin Y coordinate of the Relationship
	 * @postcondition sets the text of the relationshipstartOriginY fx:id with the value of the
	 * Relationship start Origin Y coordinate
	 */
	public void getStartOriginY(){
		relationshipStartOriginY.setText(Double.toString(relationship.getStart().getOriginY()));
		relationshipStartOriginY.setDisable(true);
	}

	/*
	 * Updates the Relationship.fxml file with the end Origin X coordinate of the Relationship
	 * @postcondition sets the text of the relationshipEndOriginX fx:id with the value of the
	 * Relationship end Origin X coordinate
	 */
	public void getEndOriginX(){
		relationshipEndOriginX.setText(Double.toString(relationship.getStop().getOriginX()));
		relationshipEndOriginX.setDisable(true);
	}

	/*
	 * Updates the Relationship.fxml file with the end Origin Y coordinate of the Relationship
	 * @postcondition sets the text of the relationshipEndOriginY fx:id with the value of the
	 * Relationship end Origin Y coordinate
	 */
	public void getEndOriginY(){
		relationshipEndOriginY.setText(Double.toString(relationship.getStop().getOriginY()));
		relationshipEndOriginY.setDisable(true);
	}

	/*
	 * Updates the Relationship.fxml file with the start point text of the Relationship
	 * @postcondition sets the text of the startText fx:id with the value of the
	 * Relationship start point text
	 */
	public void getStartText(){
		startText.setText(relationship.getStartText());
	}

	/*
	 * Updates the Relationship.fxml file with the end point text of the Relationship
	 * @postcondition sets the text of the endText fx:id with the value of the
	 * Relationship end point text
	 */
	public void getEndText(){
		endText.setText(relationship.getEndText());
	}

	/*
	 * Updates the text of the start Point in the Relationship with the Relationship.fxml text
	 * @postcondition activates when user presses Enter
	 */
	public void updateStartText(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) {
			relationship.hideText();
			relationship.showText();
			controller.ACTIONS.push(new ChangeRelationshipStartText(relationship, startText.getText(), controller));
		}
	}

	/*
	 * Updates the text of the end Point in the Relationship with the Relationship.fxml text
	 * @postcondition activates when user presses Enter
	 */
	public void updateEndText(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) {
			relationship.hideText();
			relationship.showText();
			controller.ACTIONS.push(new ChangeRelationshipEndText(relationship, endText.getText(), controller));
		}
	}

	/*
	 * Updates all the text of the Relationship with the Relationship.fxml text
	 * @postcondition activates when user clicks the apply changes button
	 */
	public void applyChanges(ActionEvent event){
		relationship.hideText();
		relationship.showText();

		if(!(startText.getText().equals(relationship.getStartText())) ||
			 !(endText.getText().equals(relationship.getEndText()))) {
			controller.ACTIONS.push(new UpdateRelationship(relationship, startText.getText(),
				endText.getText(), this, controller));
			}
	}

	/*
	 * Flips the direction of the polygon of the line
	 * @postcondition activates when user clicks the Flip Direction button
	 */
	public void flipDirection(ActionEvent event){
<<<<<<< HEAD
		controller.ACTIONS.push(new ChangeRelationshipDirection(relationship, controller));
=======
>>>>>>> Update inspector for new functionality
		event.consume();
	}


	public void changeLineType(ActionEvent event){
<<<<<<< HEAD
		controller.ACTIONS.push(new ChangeRelationshipType(relationship, relationshipType.getValue(), controller));
		event.consume();
	}

=======
		Relationship newRelationship = null;
		if (relationshipType.getValue() == "Aggregation"){
			newRelationship = new Aggregation(relationship.start,relationship.stop);
		} else if (relationshipType.getValue() == "Association"){
			newRelationship = new Association(relationship.start,relationship.stop);
		} else if (relationshipType.getValue() == "Composition"){
			newRelationship = new Composition(relationship.start,relationship.stop);
		} else if (relationshipType.getValue() == "Dependency"){
			newRelationship = new Dependency(relationship.start,relationship.stop);
		} else if (relationshipType.getValue() == "Generalization"){
			newRelationship = new Generalization(relationship.start,relationship.stop);
		}

		controller.ACTIONS.push(new DeleteUMLConnector(relationship, controller));
		controller.ACTIONS.push(new AddUMLConnector(newRelationship, controller));
		try {
			// lineType.hideText();
			relationship.showText();
			controller.ACTIONS.push(new ChangeRelationshipEndText(relationship, endText.getText(), controller));
			controller.ACTIONS.push(new ChangeRelationshipStartText(relationship, startText.getText(), controller));
		} catch (ClassCastException e){

		}
		getStartText();
		getEndText();

		System.out.println(relationshipType.getValue());
	}
>>>>>>> Update inspector for new functionality
	/*
	 * This calls all fxml updating methods in RelationshipController to update Relationship.fxml
	 * with the variables from the object. Makes it easier on the main controller to activate
	 * everything it needs by having this one method.
	 * @param object the UMLObject that is being taken in from the main controller to then be passed
	 * * to getRelationship
	 * @postcondition methods are called and Relationship.fxml holds all the up to date information
	 * * given the object that is passed through.
	 */
	public void loadInspectorInfo(UMLObject object, Controller inController){
		controller = inController;
		getRelationship(object);
		getStartOriginX();
		getStartOriginY();
		getEndOriginX();
		getEndOriginY();
		getStartText();
		getEndText();
		relationshipType.setItems(FXCollections.observableArrayList("Aggregation","Association","Composition","Dependency","Generalization"));
<<<<<<< HEAD
	}
=======

		/* this if else condition triggers the changeLineType event, I cant find a way have this code run without triggering the event
		if(isNormalLine == true){
			relationshipType.getSelectionModel().select("Line");
		} else {
			relationshipType.getSelectionModel().select(lineType.getLineType());
		}
		*/

	}

>>>>>>> Update inspector for new functionality
}
