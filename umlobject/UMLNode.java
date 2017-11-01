package umlobject;

import java.util.Vector;

// connectable UML object representation (the "things").
public class UMLNode extends UMLObject {
  // Set of all UMLConnectors connected to this.
  public Vector<UMLConnector> connections = new Vector<>();

  // Returns list of UMLConnectors connected to this.
  public Vector<UMLConnector> getConnections() {
    return connections;
  }

  // Updates all UMLConnectors connected to this.
  public void update() {
    for (UMLConnector c: connections) {
  		c.update();
  	}
  }
}
