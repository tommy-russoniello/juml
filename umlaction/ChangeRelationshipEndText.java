package umlaction;

import umlobject.*;
import juml.*;

/*
 * Action class for changing relationship end Note text.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class ChangeRelationshipEndText extends UMLAction {
  Relationship relationship;
  String alt;
  RelationshipController relationshipController;

  public ChangeRelationshipEndText(Relationship inRelationship, String newValue,
    Controller inController) {
      this(inRelationship, newValue, null, inController);
    }

  public ChangeRelationshipEndText(Relationship inRelationship, String newValue,
    RelationshipController inRelationshipController, Controller inController) {
    relationshipController = inRelationshipController;
    controller = inController;
    relationship = inRelationship;
    alt = newValue.replace("\n", "").replace("\r", "").trim();
    doInitialAction();
  }

  public void doAction() {
    if (relationshipController != null) {
      relationshipController.endText.setText(alt);
    }
    String temp = alt;
    alt = relationship.getEndText();
    relationship.setEndText(temp);
    if (temp.isEmpty()) {
      relationship.hideEndText();
    } else {
      relationship.showEndText();
    }
  }

  public void undoAction() {
    if (relationshipController != null) {
      relationshipController.endText.setText(alt);
    }
    String temp = alt;
    alt = relationship.getEndText();
    relationship.setEndText(temp);
    if (temp.isEmpty()) {
      relationship.hideEndText();
    } else {
      relationship.showEndText();
    }
  }

  public void doInitialAction() {
    String temp = alt;
    alt = relationship.getEndText();
    if (!relationship.setEndText(temp)) {
      System.out.println("New Relationship end text too long to update.");
      if (controller.ACTIONS.peek() == this) {
        controller.ACTIONS.pop();
      }
    } else {
      if (relationshipController != null) {
        relationshipController.endText.setText(temp);
      }
      if (temp.isEmpty()) {
      relationship.hideEndText();
      } else {
        relationship.showEndText();
      }
    }
  }
}
