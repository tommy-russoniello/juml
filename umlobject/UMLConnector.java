package umlobject;

import javafx.scene.shape.Line;
import javafx.scene.Node;

// UML relationship representation.
public class UMLConnector extends UMLObject {
  // 2 objects that this UMLConnector connects.
  private UMLNode start, stop;
  // underlying model.
  private Line line;

  // Basic Constructor
  // Creates UMLConnector instance with given starting and stopping UMLNodes.
  public UMLConnector(UMLNode inStart, UMLNode inStop) {
    start = inStart;
    stop = inStop;
    originX = start.getOriginX();
    originY = start.getOriginY();
    // Line between the 2 UMLNodes.
    line = new Line(start.getOriginX(), start.getOriginY(), stop.getOriginX(), stop.getOriginY());
    inStart.connections.add(this);
    inStop.connections.add(this);
  }

  // Return underlying model (Line).
  public Node getModel() {
    return line;
  }

  // Return the UMLNode that the underlying Line model starts at.
  public UMLNode getStart() {
    return start;
  }

  // Return the UMLNode that the underlying Line model stops at.
  public UMLNode getStop() {
    return stop;
  }

  // "Redraws" underlying Line model to between starting and stopping UMLNodes.
  // Used when the starting and or the stopping UMLNode has been moved.
  public void update() {
    System.out.println("moving line");
	  line.setStartX(start.getOriginX());
	  line.setStartY(start.getOriginY());
	  line.setEndX(stop.getOriginX());
	  line.setEndY(stop.getOriginY());
  }

  // Removes this from starting and stopping UMLNodes' list of UMLConnectors.
  public void disconnect() {
    start.connections.remove(this);
    stop.connections.remove(this);
    System.out.println("connector has removed self from endpoints");
  }
}
