package umlaction;

import umlobject.*;
import javafx.scene.Node;

/**
 * Class for encapsulating actions on UMLConnectors.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UMLConnectorAction extends UMLAction {
  
  /** 
   * The connector. 
   */
  public UMLConnector connector;
  
  /** 
   * The model. 
   */
  public Node model;
  
  /** 
   * The beginning and end UMLNodes. 
   */
  public UMLNode start, stop;
}
