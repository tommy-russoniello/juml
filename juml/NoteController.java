package juml;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
public class NoteController extends UMLNodeController {


	//ClassBox.fxml IDs
	@FXML private TextField originX;
	@FXML private TextField originY;
	@FXML public TextArea noteText;
	@FXML private Button applyButton;

	//Base variables to pass in classBox object
	UMLObject noteUML = null;
	Note note = null;
	Controller controller;

	/*
	 * Basic Constructor
	 * @param
	 * @postcondition
	 */
	public NoteController() throws IOException{

	}

	public String getOriginXText() {
		return originX.getText();
	}

	public String getOriginYText() {
		return originY.getText();
	}

	public void setOriginCoordinatesText(double x, double y) {
		originX.setText(Double.toString(x));
		originY.setText(Double.toString(y));
	}

	/*
	 * Basic Getter to receive the UMLObject
	 * @param object the UMLObject that is being observed and changed
	 * @postcondition assigns the UMLObject and its underlying model to variables
	 */
	public void getNote(UMLObject object){
		noteUML = object;
		note = (Note) noteUML;
	}

	/*
	 * Updates the Note.fxml file with the string from the name variable of the Note
	 * @postcondition sets the text of the noteText fx:id with the string of the Note object variable name
	 */
	public void getText(){
		noteText.setText(note.getText());
	}

	/*
	 * Updates the Note.fxml file with the Origin X coordinate of the Note
	 * @postcondition sets the text of the originY fx:id with the value of the Note Origin Y coordinate
	 */
	public void getOriginX(){
		originX.setText(Double.toString(noteUML.getOriginX()));
	}

	/*
	 * Updates the Note.fxml file with the Origin Y coordinate of the Note
	 * @postcondition sets the text of the originY fx:id with the value of the Note Origin Y coordinate
	 */
	public void getOriginY(){
		originY.setText(Double.toString(noteUML.getOriginY()));
	}

	public void updateText(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) {
			controller.ACTIONS.push(new ChangeNoteText(note, noteText.getText()));
		}
	}


	/*
	 * Moves the note to a new location based on the values entered into the inspector Object for X and Y coordinates.
	 * Activates when the user presses Enter
	 * @param event is a KeyEvent used to listen for when the user presses enter on the inspector textfields for X and Y coordinates
	 * @postcondition sets the X and Y coordinates of the note to that of the inspector's textfield's X and Y values
	 */
	public void updateCoordinates(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER){
			try{
			  Double x = Double.parseDouble(originX.getText());
				Double y = Double.parseDouble(originY.getText());
				controller.ACTIONS.push(new MoveUMLNode(note, x, y));
		    event.consume();
			} catch (Exception e) {
				event.consume();
			}
		}
	}

	/*
	 * Combination of updateCoordinates and updateText .
	 * Activates when the user clicks the apply changes button
	 * @param event is a ActionEvent used to listen for when the user clicks the button in the inspector
	 * @postcondition sets the X and Y coordinates of the note to that of the inspector's textfield's X and Y values
	 * @postcondition sets the text of the note to that of the inspector's textfield
	 */
	public void applyChanges(ActionEvent event){
		String text = noteText.getText();
		double x = Double.parseDouble(originX.getText()), y = Double.parseDouble(originY.getText());

		if((!text.equals(note.getText())) || x != note.getOriginX() || y != note.getOriginY()) {
			try {
				controller.ACTIONS.push(new UpdateNote(note, text, x, y, this));
				event.consume();
			} catch (Exception e) {
				controller.ACTIONS.push(new ChangeNoteText(note, noteText.getText(), this));
				event.consume();
			}
		}
	}
	/*
	 * This calls all fxml updating methods in NoteController to update note.fxml with the variables from the object.
	 * Makes it easier on the main controller to activate everything it needs by having this one method.
	 * @param object the UMLObject that is being taken in from the main controller to then be passed to getNote
	 * @postcondition methods are called and note.fxml holds all the up to date information given the object that is passed through.
	 */
	public void loadInspectorInfo(UMLObject object, Controller inController){
		controller = inController;
		getNote(object);
		getText();
		getOriginX();
		getOriginY();

	}
}
