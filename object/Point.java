package object;

import javafx.scene.shape.Circle;
import javafx.scene.layout.Region;

public class Point extends Region{
  public Circle point;
  public int radius;
  public int x;
  public int y;

  public Point(double inX, double inY) {
    x = (int) inX;
    y = (int) inY;
    radius = 2;
    point = new Circle(x, y, radius);
  }

  public Point(int inX, int inY, int inRadius) {
    x = inX;
    y = inY;
    radius = inRadius;
    point = new Circle(x, y, radius);
  }

  public int getRadius() {
    return radius;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
