package juml;

import javafx.scene.input.KeyEvent;
import umlobject.*;
import javafx.event.EventHandler;
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

	
	public void getPoint(UMLObject circle){
		pointUML = circle;
		point = (Point)circle;
	
	}
	
	public void circleOriginXSetText(){
		circleOriginX.setText(Double.toString(pointUML.getOriginX()));
	}

	public void circleOriginYSetText(){
		circleOriginY.setText(Double.toString(pointUML.getOriginY()));
	}
	
	public void circleRadiusSetText(){
		circleRadius.setText(Double.toString(point.getRadius()));
	}
	
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
	
	public void loadInspectorInfo(UMLObject circle){
		getPoint(circle);
		circleOriginXSetText();
		circleOriginYSetText();
		circleRadiusSetText();
	}
}
