package juml;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import umlobject.UMLConnector;
import umlobject.UMLObject;

public class LineController{

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
	
	public void getLine(UMLObject object){
		lineUML = object;
		line = (UMLConnector)lineUML;
	}
	
	public void getStartOriginX(){
		lineStartOriginX.setText(Double.toString(line.getStart().getOriginX()));
	}
	
	public void getStartOriginY(){
		lineStartOriginY.setText(Double.toString(line.getStart().getOriginY()));
	}
	
	public void getEndOriginX(){
		lineEndOriginX.setText(Double.toString(line.getStop().getOriginX()));
	}

	public void getEndOriginY(){
		lineEndOriginY.setText(Double.toString(line.getStop().getOriginY()));
	}
	
	
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
	public void setCheckBox(){
		if (line.isDotted() == true){
			lineDotted.setSelected(true);
		} else {
			lineDotted.setSelected(false);
		}
	}
	
	public void loadInspectorInfo(UMLObject object){
		getLine(object);
		getStartOriginX();
		getStartOriginY();
		getEndOriginX();
		getEndOriginY();
		setCheckBox();
	}

}