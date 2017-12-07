package umlaction;

import umlobject.*;
import juml.*;

/*
 * Action class for changing relationship start Note text.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class ChangeRelationshipStartText extends UMLAction {
  Relationship relationship;
  String alt;
  RelationshipController relationshipController;

  public ChangeRelationshipStartText(Relationship inRelationship, String newValue,
    Controller inController) {
      this(inRelationship, newValue, null, inController);
    }

  public ChangeRelationshipStartText(Relationship inRelationship, String newValue,
    RelationshipController inRelationshipController, Controller inController) {
    relationshipController = inRelationshipController;
    controller = inController;
    relationship = inRelationship;
    alt = newValue.replace("\n", "").replace("\r", "").trim();
    doInitialAction();
  }

  public void doAction() {
    if (relationshipController != null) {
      relationshipController.startText.setText(alt);
    }
    String temp = alt;
    alt = relationship.getStartText();
    relationship.setStartText(temp);
    if (temp.isEmpty()) {
      relationship.hideStartText();
    } else {
      relationship.showStartText();
    }
  }

  public void undoAction() {
    if (relationshipController != null) {
      relationshipController.startText.setText(alt);
    }
    String temp = alt;
    alt = relationship.getStartText();
    relationship.setStartText(temp);
    if (temp.isEmpty()) {
      relationship.hideStartText();
    } else {
      relationship.showStartText();
    }
  }

  public void doInitialAction() {
    String temp = alt;
    alt = relationship.getStartText();
    if (!relationship.setStartText(temp)) {
      System.out.println("New Relationship start text too long to update.");
      if (relationship.getStartText().isEmpty()) {
        relationship.hideStartText();
      }
      if (controller.ACTIONS.peek() == this) {
        controller.ACTIONS.pop();
      }
    } else {
      if (relationshipController != null) {
        relationshipController.startText.setText(temp);
      }
      if (temp.isEmpty()) {
      relationship.hideStartText();
      } else {
        relationship.showStartText();
      }
    }
  }
}
