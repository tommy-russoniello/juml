package juml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.collections.FXCollections;
import umlobject.*;
import umlaction.*;


/**
 * Controller class for Relationship FXML.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class RelationshipController {

	/** 
	 * The relationship start origin X. 
	 */
	@FXML TextField relationshipStartOriginX;
	
	/** 
	 * The relationship start origin Y. 
	 */
	@FXML TextField relationshipStartOriginY;
	
	/** 
	 * The relationship end origin X. 
	 */
	@FXML TextField relationshipEndOriginX;
	
	/** 
	 * The relationship end origin Y. 
	 */
	@FXML TextField relationshipEndOriginY;
	
	/** 
	 * The text field at the start of the relationship. 
	 */
	@FXML public TextArea startText;
	
	/** 
	 * The text field at the end of the relationship. 
	 */
	@FXML public TextArea endText;
	
	/** 
	 * The relationship type. 
	 */
	@FXML ChoiceBox<String> relationshipType;

	/** 
	 * The relationship. 
	 */
	Relationship relationship = null;
	
	/** 
	 * The controller. 
	 */
	Controller controller;

	/**
	 * Basic Getter to receive the UMLObject
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getRelationship(UMLObject object){
		relationship = (Relationship)object;
	}

	/**
	 * Updates the Relationship.fxml file with the start Origin X coordinate of the Relationship
	 * @postcondition sets the text of the relationshipstartOriginX fx:id with the value of the
	 * Relationship start Origin X coordinate
	 */
	public void getStartOriginX(){
		relationshipStartOriginX.setText(Double.toString(relationship.getStart().getOriginX()));
		relationshipStartOriginX.setDisable(true);
	}

	/**
	 * Updates the Relationship.fxml file with the start Origin Y coordinate of the Relationship
	 * @postcondition sets the text of the relationshipstartOriginY fx:id with the value of the
	 * Relationship start Origin Y coordinate
	 */
	public void getStartOriginY(){
		relationshipStartOriginY.setText(Double.toString(relationship.getStart().getOriginY()));
		relationshipStartOriginY.setDisable(true);
	}

	/**
	 * Updates the Relationship.fxml file with the end Origin X coordinate of the Relationship
	 * @postcondition sets the text of the relationshipEndOriginX fx:id with the value of the
	 * Relationship end Origin X coordinate
	 */
	public void getEndOriginX(){
		relationshipEndOriginX.setText(Double.toString(relationship.getStop().getOriginX()));
		relationshipEndOriginX.setDisable(true);
	}

	/**
	 * Updates the Relationship.fxml file with the end Origin Y coordinate of the Relationship
	 * @postcondition sets the text of the relationshipEndOriginY fx:id with the value of the
	 * Relationship end Origin Y coordinate
	 */
	public void getEndOriginY(){
		relationshipEndOriginY.setText(Double.toString(relationship.getStop().getOriginY()));
		relationshipEndOriginY.setDisable(true);
	}

	/**
	 * Updates the Relationship.fxml file with the start point text of the Relationship
	 * @postcondition sets the text of the startText fx:id with the value of the
	 * Relationship start point text
	 */
	public void getStartText(){
		startText.setText(relationship.getStartText());
	}

	/**
	 * Updates the Relationship.fxml file with the end point text of the Relationship
	 * @postcondition sets the text of the endText fx:id with the value of the
	 * Relationship end point text
	 */
	public void getEndText(){
		endText.setText(relationship.getEndText());
	}

	/**
	 * Updates the text of the start Point in the Relationship with the Relationship.fxml text
	 * @param event is a KeyEvent used to listen for when the user presses enter on the inspector relationship start label textfield
	 * @postcondition activates when user presses Enter
	 */
	public void updateStartText(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) {
			relationship.hideText();
			relationship.showText();
			controller.ACTIONS.push(new ChangeRelationshipStartText(relationship, startText.getText(), controller));
		}
	}

	/**
	 * Updates the text of the end Point in the Relationship with the Relationship.fxml text
	 * @param event is a KeyEvent used to listen for when the user presses enter on the inspector relationship end label textfield
	 * @postcondition activates when user presses Enter
	 */
	public void updateEndText(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) {
			relationship.hideText();
			relationship.showText();
			controller.ACTIONS.push(new ChangeRelationshipEndText(relationship, endText.getText(), controller));
		}
	}

	/**
	 * Updates all the text of the Relationship with the Relationship.fxml text
	 * @param event is an action event used to listen for when the user clicks on the apply changes button.
	 * @postcondition activates when user clicks the apply changes button
	 */
	public void applyChanges(ActionEvent event){
		if (relationship.startTextVisible()) {
			relationship.hideStartText();
			relationship.showStartText();
		}
		if (relationship.endTextVisible()) {
			relationship.hideEndText();
			relationship.showEndText();
		}

		if(!(startText.getText().equals(relationship.getStartText())) ||
			 !(endText.getText().equals(relationship.getEndText()))) {
			controller.UNDONE_ACTIONS.clear();
			UpdateRelationship action = new UpdateRelationship(relationship, startText.getText(),
				endText.getText(), this, controller);
			controller.ACTIONS.push(action);
			if (action.noChange()) {
				controller.ACTIONS.pop();
			}
		}
	}

	/**
	 * Flip direction.
	 *
	 * @param event the event
	 */
	/**
	 * Flips the direction of the polygon of the line
	 * @param event is a action used to listen for when the user presses the flip direction button on the inspector
	 * @postcondition activates when user clicks the Flip Direction button
	 */
	public void flipDirection(ActionEvent event){
		controller.UNDONE_ACTIONS.clear();
		controller.ACTIONS.push(new ChangeRelationshipDirection(relationship, controller));
		event.consume();
	}


	/**
	 * Change line type.
	 *
	 * @param event is a action used to listen for when the user clicks the relationship option in the choice box
	 *  in the inspector.
	 */
	public void changeLineType(ActionEvent event){
		controller.UNDONE_ACTIONS.clear();
		controller.ACTIONS.push(new ChangeRelationshipType(relationship, relationshipType.getValue(), controller));
		event.consume();
	}

	/**
	 * This calls all fxml updating methods in RelationshipController to update Relationship.fxml
	 * with the variables from the object. Makes it easier on the main controller to activate
	 * everything it needs by having this one method.
	 * @param object the UMLObject that is being taken in from the main controller to then be passed
	 *  to getRelationship
	 * @param inController the controller being used to change the relationship fields
	 * @postcondition methods are called and Relationship.fxml holds all the up to date information
	 *  given the object that is passed through.
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
	}
}
