package umlnode;

import java.util.Vector;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;


import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import umlnode.Connector;
//import umlnode.Point.Delta;
import javafx.scene.paint.*;

public class ClassBox extends UMLNode{

	 Group group;
		Circle c1;
		Circle c2;
		Rectangle r;
		double centerX;
		double centerY;
		double width;
		double height;
	    
		
		public ClassBox() {
			centerX = 100;
			centerY = 300;
			width = 100;
			height = 300;
			c1 = new Circle(0, 0, 10);
			c2 = new Circle(0, 0,10);
			c1.setFill(Color.AZURE);
			c2.setFill(Color.YELLOW);
			r = new Rectangle(0, 0, width, height);
			r.setFill(Color.GREEN);
			group = new Group();
			group.getChildren().add(r);
			group.getChildren().add(c1);
			group.getChildren().add(c2);
			update();
			enableDrag(group);
		}
	    
		public void update() {
			c1.setCenterX(centerX);
			c1.setCenterY(centerY);
			c2.setCenterX(centerX+35);
			c2.setCenterY(centerY+10);
			r.setX(centerX-width/2);
			r.setY(centerY-height/2);
		}
		
	    public Group getModel() {
	    	return group;
	    }
		
		
	    static class Delta { double x, y; }
	  //make a node movable by dragging it around with the mouse.
	  private void enableDrag(final Group circle) {
	  final Delta dragDelta = new Delta();
	  circle.setOnMousePressed(new EventHandler<MouseEvent>() {
	   @Override public void handle(MouseEvent mouseEvent) {
	     // record a delta distance for the drag and drop operation.
	     dragDelta.x = centerX - mouseEvent.getX();
	     dragDelta.y = centerY - mouseEvent.getY();
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
		 centerX = (mouseEvent.getX() + dragDelta.x);
		 centerY = (mouseEvent.getY() + dragDelta.y);
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
	    
	    
		@Override
		public double getOriginX() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getOriginY() {
			// TODO Auto-generated method stub
			return 0;
		}

			
		

}
