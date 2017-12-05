package umlobject;

import java.lang.Math;
import javafx.scene.shape.Circle;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Class for pivot points in a UMLConnector.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class Pivot extends UMLNode {

  /**
   * Underlying model.
   */
  public Circle circle;

  /**
   * Parent UMLConnector of this.
   */
  UMLConnector connector;

  /**
   * Basic Constructor
   * @param x x coordinate for this to be made on.
   * @param y y coordinate for this to be made on.
   * @postcondition Pivot instance with given coordinates and default radius of 3 is created.
   */
  public Pivot(UMLConnector inConnector, double x, double y) {
    originX = x;
    originY = y;
    circle = new Circle(originX, originY, 2);
    circle.setFill(Color.WHITE);
    circle.setStroke(Color.BLACK);
    connector = inConnector;
  }

  /**
   * Returns underlying model.
   * @return Underlying circle model.
   */
  public Node getModel() {
    return circle;
  }

  /**
   * Updates all Segments connected to this and UMLConnector containing this.
   * @postcondition All Segments connected to this, and UMLConnector containing this update the
   * * coordinates for their lines.
   */
  public void update() {
    connector.update();
    super.update();
  }

  /**
   * Updates all Segments connected to this.
   * @postcondition All Segments connected to this update the coordinates for their lines.
   */
  public void updateSegments() {
    super.update();
  }

  /**
   * Reassign this at given coordinates.
   * @param newX x coordinate for this to be moved to.
   * @param newY y coordinate for this to be moved to.
   * @postcondition This updates all of its data according to new coordinates.
   */
  public void move(double newX, double newY) {
    if(newX < 0){
  		newX = 0;
    }
  	if(newY < 0){
  		newY = 0;
    }
	  circle.setCenterX(newX);
    circle.setCenterY(newY);
    super.move(newX, newY);
  }

  /**
   * Returns the x coordinate of the point to which a connector should anchor if
   * joined to this node.
   *
   * @return returns the calculated x coordinate.
   */
  public double getAnchorX(double startX, double startY) {
  	double actingRadius = 3;
  	double deltaX = startX - originX;
  	double deltaY = originY - startY;
  	double angle = Math.atan(deltaY/deltaX);
  	if (startX<originX) {
  		angle+= Math.PI;
  	}
  	double xOffset = actingRadius * Math.cos(angle);
  	return originX + xOffset;
  }

  /**
   * Returns the y coordinate of the point to which a connector should anchor if
   * joined to this node.
   *
   * @return returns the calculated y coordinate.
   */
  public double getAnchorY(double startX, double startY) {
  	double actingRadius = 3;
  	double deltaX = startX - originX;
  	double deltaY = originY - startY;
  	double angle = Math.atan(deltaY/deltaX);
  	if (startX<originX) {
  		angle+= Math.PI;
  	}
  	double yOffset = -actingRadius * Math.sin(angle);
  	return originY + yOffset;
  }

  /**
   * Changes color of underlying circle model to make the object appear highlighted.
   * @postcondition Color of underlying circle model changed to blue.
   */
  public void highlight() {
    circle.setStroke(Color.BLUE);
  }

  /**
   * Changes color of underlying circle model to make the object appear unhighlighted.
   * @postcondition Color of underlying circle model changed to black.
   */
  public void unhighlight() {
    circle.setStroke(Color.BLACK);
  }
}
