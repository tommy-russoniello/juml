package umlobject;

import java.util.Vector;

public class UMLNode extends UMLObject {

  public Vector<UMLConnector> connections = new Vector<>();

  public Vector<UMLConnector> getConnections() {
    return connections;
  }

  public void update() {
    for (UMLConnector c: connections) {
  		c.update();
  	}
  }
}
