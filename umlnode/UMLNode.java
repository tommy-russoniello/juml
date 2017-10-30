package umlnode;

import java.util.Vector;
import javafx.scene.layout.Region;
import javafx.scene.Node;

public class UMLNode extends Region {

  public Vector<Connector> connections = new Vector<Connector>();
  public double originX;
  public double originY;

  public Vector<Connector> getConnections() {
    return connections;
  }

 // All UMLNodes must implement this method, returning the underlying JavaFX structure
  public Node getModel() {
    return null;
  }

  public double getOriginX() {
    return originX;
  }

  public double getOriginY() {
    return originY;
  }

  public void move(double newX, double newY) {
    originX = newX;
    originY = newY;
  }

  public void update() {}
  
  // this will get called by the controller right before the object is deleted
  // this method should remove all connections that controller does not directly
  // know about
  public void delete() {}
}


