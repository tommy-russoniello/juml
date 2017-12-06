package umlobject;

import javafx.scene.shape.Circle;

import java.util.Scanner;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Simple point class.
 *
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.1
 */
public class Point extends UMLNode {

	/**
	 * The underlying model of Point.
	 */
	public Circle circle;

	/**
	 * The radius.
	 */
	private double radius;

	/**
	 * Build from string method. Stored as "delimiter x y radius"
	 * @param input The scanner from which the object can read in its save string
	 * @postcondition Object will have constructed itself from the information
	 *                provided by input
	 */
	public Point(Scanner input) {
		this(input.nextDouble(), input.nextDouble(), input.nextInt());
	}

	/**
	 * Save method
	 * @postcondition generates a string with the necessary information for the
	 *                object to rebuild itself.
	 */
	public String saveAsString() {
		return "Point: " + originX + " " + originY + " " + radius;
	}

	/**
	 * Basic Constructor
	 * @param x x coordinate for this to be made on.
	 * @param y y coordinate for this to be made on.
	 * @postcondition Point instance with given coordinates and default radius of 5
	 *                is created.
	 */
	public Point(double x, double y) {
		this(x, y, 5);
	}

	/**
	 * Explicit Constructor
	 *
	 * @param x
	 *            x coordinate for this to be made on.
	 * @param y
	 *            y coordinate for this to be made on.
	 * @param inRadius
	 *            Radius for underlying circle model to have.
	 * @postcondition Point instance with given coordinates and radius is created.
	 */
	public Point(double x, double y, int inRadius) {
		originX = x;
		originY = y;
		radius = inRadius;
		circle = new Circle(originX, originY, radius);
	}

	/**
	 * Returns underlying model.
	 *
	 * @return Underlying circle model.
	 */
	public Node getModel() {
		return circle;
	}

	/**
	 * Return maintained radius.
	 *
	 * @return Maintained radius.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Reassign this at given coordinates.
	 *
	 * @param newX
	 *            x coordinate for this to be moved to.
	 * @param newY
	 *            y coordinate for this to be moved to.
	 * @postcondition This updates all of its data according to new coordinates.
	 */
	public void move(double newX, double newY) {
		if (newX - getRadius() < 0) {
			newX = 0 + getRadius();
		}
		if (newY - getRadius() < 0) {
			newY = 0 + getRadius();
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
		double actingRadius = radius;
		double deltaX = startX - originX;
		double deltaY = originY - startY;
		double angle = Math.atan(deltaY / deltaX);
		if (startX < originX) {
			angle += Math.PI;
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
		double actingRadius = radius;
		double deltaX = startX - originX;
		double deltaY = originY - startY;
		double angle = Math.atan(deltaY / deltaX);
		if (startX < originX) {
			angle += Math.PI;
		}
		double yOffset = -actingRadius * Math.sin(angle);
		return originY + yOffset;
	}

  /**
   * Reassign this at given radius.
   * @param newRadius reassigned for this.
   * @postcondition This updates all of its data according to new radius.
   */
  public void setRadius(Double newRadius) {
	  circle.setRadius(newRadius);
	  radius = newRadius;
  }

  /**
   * Changes color of underlying circle model to make the object appear highlighted.
   * @postcondition Color of underlying circle model changed to blue.
   */
  public void highlight() {
    circle.setFill(Color.BLUE);
  }

  /**
   * Changes color of underlying circle model to make the object appear unhighlighted.
   * @postcondition Color of underlying circle model changed to black.
   */
  public void unhighlight() {
    circle.setFill(Color.BLACK);
  }
}
