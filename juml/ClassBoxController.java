package juml;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextArea;
import umlobject.*;

public class ClassBoxController{

	//ClassBox.fxml IDs
	@FXML private TextField classBoxName;
	@FXML private TextArea classBoxAttributes;
	@FXML private TextArea classBoxMethods;
	@FXML private TextField classBoxOriginX;
	@FXML private TextField classBoxOriginY;
	
	//Base variables to pass in classBox object
	UMLObject classBoxUML = null;
	ClassBox classBox = null;
	
    /*
	 * Basic Constructor
	 * @param 
	 * @postcondition
	 */
	public ClassBoxController() throws IOException{
		
	}
	
	public void getClassBox(UMLObject object){
		classBoxUML = object;
		classBox = (ClassBox)classBoxUML;

	}
	
	public void classBoxNameSetText(){
		classBoxName.setText(classBox.getName());
	}
	
	public void classBoxAttributesSetText(){
		classBoxAttributes.setText(classBox.getAttributes());
	}
	
	public void classBoxMethodsSetText(){
		classBoxMethods.setText(classBox.getMethods());
	}
	
	public void classBoxOriginXSetText(){
		classBoxOriginX.setText(Double.toString(classBoxUML.getOriginX()));
	}
	
	public void classBoxOriginYSetText(){
		classBoxOriginY.setText(Double.toString(classBoxUML.getOriginY()));
	}
	
	public void updateText(KeyEvent event){
		String name = classBoxName.getText();
		String attributes = classBoxAttributes.getText();
		String methods = classBoxMethods.getText();
		classBox.setName(name);
		classBox.setAttributes(attributes);
		classBox.setMethods(methods);
		event.consume(); // Consume Event
	}
	
	public void classBoxOriginChange(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER){
			try{
				Double x = Double.parseDouble(classBoxOriginX.getText());
				Double y = Double.parseDouble(classBoxOriginY.getText());
				classBoxUML.move(x,y);
		    	event.consume(); // Consume Event
			}catch (Exception e){
				event.consume();
			}
		}
	}
	
	public void loadInspectorInfo(UMLObject object){
		getClassBox(object);
		classBoxNameSetText();
		classBoxAttributesSetText();
		classBoxMethodsSetText();
		classBoxOriginXSetText();
		classBoxOriginYSetText();
			
	}
	
}
