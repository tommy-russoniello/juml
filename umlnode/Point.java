package umlnode;

import java.util.Vector;


import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
//import juml.Controller.Delta;

public class Point extends Circle implements UMLNode{
  //private Circle circle = new Circle();
  private int radius;
  private Vector<Connector> conections = new Vector<Connector>() ; 
  
  /*
  public Point(double x, double y) {
	  return Point(x, y, 10);	 
  }
  */

public Point(double x, double y, int inRadius) {
	super(x, y, inRadius);
    radius = inRadius;
    enableDrag(this);
  }
  
public void addConnnector(Connector c) {
	conections.addElement(c);
}


public void update() {
	for (int i=0; i<conections.size(); i++ ) {
		conections.get(i).update();
	}
} 

  public double getOriginX() {
	  return this.getCenterX();
  }
  
  public double getOriginY() {
	  return this.getCenterY();
  }
  
  
/*
  public Circle getModel() {
    return circle;
  }

  
  public int getRadius() {
    return radius;
  }
*/  
  
  
  
  static class Delta { double x, y; }
//make a node movable by dragging it around with the mouse.
private void enableDrag(final Point circle) {
final Delta dragDelta = new Delta();
circle.setOnMousePressed(new EventHandler<MouseEvent>() {
 @Override public void handle(MouseEvent mouseEvent) {
   // record a delta distance for the drag and drop operation.
   dragDelta.x = circle.getCenterX() - mouseEvent.getX();
   dragDelta.y = circle.getCenterY() - mouseEvent.getY();
   circle.getScene().setCursor(Cursor.MOVE);
 }
});
circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
 @Override public void handle(MouseEvent mouseEvent) {
   circle.getScene().setCursor(Cursor.HAND);
 }
});
circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
 @Override public void handle(MouseEvent mouseEvent) {
   circle.setCenterX(mouseEvent.getX() + dragDelta.x);
   circle.setCenterY(mouseEvent.getY() + dragDelta.y);
   System.out.println("moved Node");
   update();
 }
});
circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
 @Override public void handle(MouseEvent mouseEvent) {
   if (!mouseEvent.isPrimaryButtonDown()) {
     circle.getScene().setCursor(Cursor.HAND);
   }
 }
});
circle.setOnMouseExited(new EventHandler<MouseEvent>() {
 @Override public void handle(MouseEvent mouseEvent) {
   if (!mouseEvent.isPrimaryButtonDown()) {
     circle.getScene().setCursor(Cursor.DEFAULT);
   }
 }
});
}


public Node getModel() {
	return this;
}



}
