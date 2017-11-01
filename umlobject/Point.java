package umlobject;

import javafx.scene.shape.Circle;
import javafx.scene.Node;

// Simple point class.
public class Point extends UMLNode {
  // Underlying model.
  public Circle circle;
  private double radius;

  // Basic Constructor
  // Creates Point instance with given coordinates and default radius of 5.
  public Point(double x, double y) {
    this(x, y, 5);
  }

  // Explicit Constructor
  // Creates Point instance with given coordinates and radius.
  public Point(double x, double y, int inRadius) {
    originX = x;
    originY = y;
    radius = inRadius;
    circle = new Circle(originX, originY, radius);
  }

  // Returns underlying model (Circle).
  public Node getModel() {
    return circle;
  }

  // Return maintained radius.
  public double getRadius() {
    return radius;
  }

  // Reassign this at given coordinates.
  public void move(double newX, double newY) {
    circle.setCenterX(newX);
    circle.setCenterY(newY);
    super.move(newX, newY);
  }
}
