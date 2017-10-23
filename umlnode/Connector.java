package umlnode;
import javafx.scene.Node;

import javafx.scene.shape.Line;

public class Connector {
  private UMLNode start;
  private UMLNode stop;
  private Line line;

  public Connector(UMLNode inStart, UMLNode inStop) {
    start = inStart;
    stop = inStop;  
    double originX = start.getOriginX();
    double originY = start.getOriginY();
    line = new Line(start.getOriginX(), start.getOriginY(), stop.getOriginX(), stop.getOriginY());
  }
  
  public void update() {
	  System.out.println("moving line");
	  line.setStartX(start.getOriginX());
	  line.setStartY(start.getOriginY());
	  line.setEndX(stop.getOriginX());
	  line.setEndY(stop.getOriginY());
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
