package umlaction;

import juml.*;
import umlobject.*;

/*
 * Class for encapsulating actions in the program.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UMLAction {

  public enum RelationshipType {
		ASSOCIATION, DEPENDENCY, AGGREGATION, COMPOSITION, GENERALIZATION,
	}
  
  public Controller controller;

  public void doAction(){}

  public void undoAction(){}

  public void doInitialAction(){}
}
