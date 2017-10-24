package umlnode;

import javafx.scene.shape.Circle;
import javafx.scene.Node;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class Point extends UMLNode {
  public Circle circle = new Circle();
  private int radius;

  public Point(double x, double y) {
    originX = x;
    originY = y;
    radius = 5;
    circle = new Circle(originX, originY, radius);
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

  public int getRadius() {
    return radius;
  }

  public void move(double newX, double newY) {
    super.move(newX, newY);
    circle.setCenterX(newX);
    circle.setCenterY(newY);
  }

  public void update() {
  	for (Connector c: connections) {
  		c.update();
  	}
  }
}
