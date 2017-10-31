package umlobject;

import javafx.scene.shape.Circle;
import javafx.scene.Node;
import java.util.Vector;

public class Point extends UMLNode {
  public Circle circle;
  private double radius;

  public Point(double x, double y) {
    this(x, y, 5);
  }

  public Point(double x, double y, int inRadius) {
    originX = x;
    originY = y;
    radius = inRadius;
    circle = new Circle(originX, originY, radius);
  }

  public Node getModel() {
    return circle;
  }

  public double getRadius() {
    return radius;
  }

  public void move(double newX, double newY) {
    circle.setCenterX(newX);
    circle.setCenterY(newY);
    super.move(newX, newY);
  }
}
