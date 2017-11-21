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
	  System.out.println("Updateing connections");
    for (UMLConnector c: connections) {
  		c.update();
  	}
  }
  
 /*
   * Returns the x coordinate of the point to which a connector should anchor if
   * joined to this node.
   * 
   * @return returns the calculated x coordinate.
   */
  public double getAnchorX(double startX, double startY) {
  	return originX;
  }
  
  /*
   * Returns the y coordinate of the point to which a connector should anchor if
   * joined to this node.
   * 
   * @return returns the calculated y coordinate.
   */
  public double getAnchorY(double startX, double startY) {
  	return originY;
  }
}
