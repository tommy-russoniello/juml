package umlnode;

import javafx.scene.shape.Line;

public class Connector extends UMLNode {
  private UMLNode start;
  private UMLNode stop;
  private Line line;

  public Connector(UMLNode inStart, UMLNode inStop) {
    start = inStart;
    stop = inStop;
    originX = start.getOriginX();
    originY = start.getOriginY();
    line = new Line(start.getOriginX(), start.getOriginY(), stop.getOriginX(), stop.getOriginY());
  }

  public Line getModel() {
    return line;
  }

  public UMLNode getStart() {
    return start;
  }

  public UMLNode getStop() {
    return stop;
  }
}
