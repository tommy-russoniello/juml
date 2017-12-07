package umlaction;

import juml.*;

/**
 * Class for encapsulating actions in the program.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UMLAction {

  /**
   * The Enum RelationshipType.
   */
  public enum RelationshipType {
		
		/** 
		 * The association realationship type. 
		 */
		ASSOCIATION, 
		
		/** 
		 * The dependency relationship type. 
		 */
		DEPENDENCY, 
		
		/** 
		 * The aggregation relationship type. 
		 */
		AGGREGATION,
		
		/** 
		 * The composition relationship type. 
		 */
		COMPOSITION, 
		
		/** 
		 * The generalization relationship type. 
		 */
		GENERALIZATION,
	}
  
  /** 
   * The controller. 
   */
  public Controller controller;

  /**
   * Does the specified action.
   */
  public void doAction(){}

  /**
   * Undoes the specified action.
   */
  public void undoAction(){}

  /**
   * Does the initial action.
   */
  public void doInitialAction(){}
}
