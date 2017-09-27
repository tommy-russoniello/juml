package umlnode;

import javafx.scene.shape.Circle;

public class Point extends UMLNode {
  private Circle circle = new Circle();
  private int radius;

  public Point(double x, double y) {
    originX = (int) x;
    originY = (int) y;
    radius = 3;
    circle = new Circle(originX, originY, radius);
  }

  public Point(double x, double y, int inRadius) {
    originX = (int) x;
    originY = (int) y;
    radius = inRadius;
    circle = new Circle(originX, originY, radius);
  }

  public Circle getModel() {
    return circle;
  }

  public int getRadius() {
    return radius;
  }
}
