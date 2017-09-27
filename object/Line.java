package object;

import javafx.scene.shape.Line;
import javafx.scene.layout.Region;

public class Line extends Region{
  public Region start;
  public Region stop;
  public Line line;

  public Line(Region inStart, Region inStop) {
    start = inStart;
    stop = inStop;
    line = new Line(start.getX(), start.getY(), stop.getX(), stop.getY());
  }

  // public Line(Region inStart) {
  //   start = inStart;
  //   point = new Circle(x, y, radius);
  // }
  //
  // public Line() {
  //   point = new Circle(x, y, radius);
  // }

  public int getStart() {
    return start;
  }

  public int getStop() {
    return stop;
  }
}
