package umlobject;

import javafx.scene.shape.Line;
import javafx.scene.Node;

public class UMLConnector extends UMLObject {
  private UMLNode start, stop;
  private Line line;

  public UMLConnector(UMLNode inStart, UMLNode inStop) {
    start = inStart;
    stop = inStop;
    originX = start.getOriginX();
    originY = start.getOriginY();
    line = new Line(start.getOriginX(), start.getOriginY(), stop.getOriginX(), stop.getOriginY());
    inStart.connections.add(this);
    inStop.connections.add(this);
  }

  public Node getModel() {
    return line;
  }

  public UMLNode getStart() {
    return start;
  }

  public UMLNode getStop() {
    return stop;
  }

  public void update() {
    System.out.println("moving line");
	  line.setStartX(start.getOriginX());
	  line.setStartY(start.getOriginY());
	  line.setEndX(stop.getOriginX());
	  line.setEndY(stop.getOriginY());
  }
}
