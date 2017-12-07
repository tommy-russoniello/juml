package umlaction;

import juml.*;
import umlobject.*;

/**
 * Action class for updating all fields of a Relationship.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UpdateRelationship extends UMLConnectorAction {
  
  /** 
   * The start label text. 
   */
  ChangeRelationshipStartText startText;
  
  /** 
   * The end label text. 
   */
  ChangeRelationshipEndText endText;
  
  /** 
   * The relationship controller. 
   */
  RelationshipController relationshipController;
  boolean noChange;

  /**
   * Instantiates a new update relationship.
   *
   * @param relationship the relationship
   * @param newStartText the new start text
   * @param newEndText the new end text
   * @param inController the in controller
   */
  public UpdateRelationship(Relationship relationship, String newStartText, String newEndText,
    Controller inController) {
    this(relationship, newStartText, newEndText, null, inController);
  }

  /**
   * Instantiates a new update relationship.
   *
   * @param relationship the relationship
   * @param newStartText the new start text
   * @param newEndText the new end text
   * @param inRelationshipController the in relationship controller
   * @param inController the in controller
   */
  public UpdateRelationship(Relationship relationship, String newStartText, String newEndText,
    RelationshipController inRelationshipController, Controller inController) {
    noChange = false;
    controller = inController;
    relationshipController = inRelationshipController;
    String origStartText = relationship.getStartText(), origEndText = relationship.getEndText();

    startText = new ChangeRelationshipStartText(relationship, newStartText, relationshipController,
      controller);
    endText = new ChangeRelationshipEndText(relationship, newEndText, relationshipController,
      controller);

    if (origStartText.equals(relationship.getStartText()) &&
      origEndText.equals(relationship.getEndText())) {
      noChange = true;
    }
  }

  /**
   * @see umlaction.UMLAction#doAction()
   */
  public void doAction() {
    startText.doAction();
    endText.doAction();
  }

  /**
   * @see umlaction.UMLAction#undoAction()
   */
  public void undoAction() {
    startText.undoAction();
    endText.undoAction();
  }

  public boolean noChange() {
    return noChange;
  }
}
