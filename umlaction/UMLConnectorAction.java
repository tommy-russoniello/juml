package umlaction;

import juml.*;
import umlobject.*;
import javafx.scene.Node;

/*
 * Class for encapsulating actions on UMLConnectors.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UMLConnectorAction extends UMLAction {
  public UMLConnector connector;
  public Node model;
  public UMLNode start, stop;
}
