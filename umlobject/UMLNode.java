package umlobject;

import java.util.Vector;

/*
 * connectable UML object representation (the "things").
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.2
 * @since 0.1
 */
public class UMLNode extends UMLObject {
  /*
   * Set of all UMLConnectors connected to this.
   */
  public Vector<UMLConnector> connections = new Vector<>();

  /*
   * Returns list of UMLConnectors connected to this.
   * @return list of UMLConnectors connected to this.
   */
  public Vector<UMLConnector> getConnections() {
    return connections;
  }

  /*
   * Updates all UMLConnectors connected to this.
   * @postcondition All UMLConnectors connected to this update the coordinates for their lines.
   */
  public void update() {
    for (UMLConnector c: connections) {
  		c.update();
  	}
  }
}
