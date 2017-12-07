package umlaction;

import juml.*;
import umlobject.*;

/*
 * Action class for updating all fields of a Relationship.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UpdateRelationship extends UMLConnectorAction {
  ChangeRelationshipStartText startText;
  ChangeRelationshipEndText endText;
  RelationshipController relationshipController;
  boolean noChange;

  public UpdateRelationship(Relationship relationship, String newStartText, String newEndText,
    Controller inController) {
    this(relationship, newStartText, newEndText, null, inController);
  }

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
      if (relationship.getStartText().isEmpty()) {
      }
      noChange = true;
    }
  }

  public void doAction() {
    startText.doAction();
    endText.doAction();
  }

  public void undoAction() {
    startText.undoAction();
    endText.undoAction();
  }

  public boolean noChange() {
    return noChange;
  }
}
