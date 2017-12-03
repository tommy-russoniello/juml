package umlobject;

/*
 * UML relationship representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.1
 */
public class UMLConnector extends UMLObject {
  /*
   * 2 objects that this UMLConnector connects.
   */
  public UMLNode start, stop;

  /*
   * Return the UMLNode that the underlying Line model starts at.
   * @return UMLNode that the underlying Line model starts at.
   */
  public UMLNode getStart() {
    return start;
  }

  /*
   * Return the UMLNode that the underlying Line model stops at.
   * @return UMLNode that the underlying Line model stops at.
   */
  public UMLNode getStop() {
    return stop;
  }

  /*
   * Adds this to starting and stopping UMLNodes' list of UMLConnectors.
   * @postcondition This is added to starting and stopping UMLNodes' list of UMLConnectors.
   */
  public void connect() {
    start.connections.add(this);
    stop.connections.add(this);
  }

  /*
   * Removes this from starting and stopping UMLNodes' list of UMLConnectors.
   * @postcondition This is removed from starting and stopping UMLNodes' list of UMLConnectors.
   */
  public void disconnect() {
    start.connections.remove(this);
    stop.connections.remove(this);
  }
}
