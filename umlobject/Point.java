package umlobject;

import javafx.scene.shape.Circle;
import javafx.scene.Node;

/*
 * Simple point class.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.2
 * @since 0.1
 */
public class Point extends UMLNode {
  /*
   * Underlying model.
   */
  public Circle circle;
  private double radius;

  /*
   * Basic Constructor
   * @param x x coordinate for this to be made on.
   * @param y y coordinate for this to be made on.
   * @postcondition Point instance with given coordinates and default radius of 5 is created.
   */
  public Point(double x, double y) {
    this(x, y, 5);
  }

  /*
   * Explicit Constructor
   * @param x x coordinate for this to be made on.
   * @param y y coordinate for this to be made on.
   * @param inRadius Radius for underlying circle model to have.
   * @postcondition Point instance with given coordinates and radius is created.
   */
  public Point(double x, double y, int inRadius) {
    originX = x;
    originY = y;
    radius = inRadius;
    circle = new Circle(originX, originY, radius);
  }

  /*
   * Returns underlying model.
   * @return Underlying circle model.
   */
  public Node getModel() {
    return circle;
  }

  /*
   * Return maintained radius.
   * @return Maintained radius.
   */
  public double getRadius() {
    return radius;
  }

  /*
   * Reassign this at given coordinates.
   * @param newX x coordinate for this to be moved to.
   * @param newY y coordinate for this to be moved to.
   * @postcondition This updates all of its data according to new coordinates.
   */
  public void move(double newX, double newY) {
    circle.setCenterX(newX);
    circle.setCenterY(newY);
    super.move(newX, newY);
  }
  
  
	/*
	 * Returns the x coordinate of the point to which a connector should anchor if
	 * joined to this node.
	 * 
	 * @return returns the calculated x coordinate.
	 */
	public double getAnchorX(double startX, double startY) {
		double actingRadius = radius+5;
		double deltaX = startX - originX;
		double deltaY = originY - startY; 
		double angle = Math.atan(deltaY/deltaX);
		if (startX<originX) 
			angle+= Math.PI;
		double xOffset = actingRadius * Math.cos(angle);
		return originX + xOffset;
}

	/*
	 * Returns the y coordinate of the point to which a connector should anchor if
	 * joined to this node.
	 * 
	 * @return returns the calculated y coordinate.
	 */
	public double getAnchorY(double startX, double startY) {
		double actingRadius = radius+5;
		double deltaX = startX - originX;
		double deltaY = originY - startY; 
		double angle = Math.atan(deltaY/deltaX);
		if (startX<originX) 
			angle+= Math.PI;
		//System.out.println("angle in degrees is "+angle*180/Math.PI);
		double yOffset = -actingRadius * Math.sin(angle);
		return originY + yOffset;
	}
  
}
